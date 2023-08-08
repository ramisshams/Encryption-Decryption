package encryptdecrypt

import java.io.File

var mode = "enc"
var key = 0
var data = ""
var alg = "shift"
val alphabet = "abcdefghijklmnopqrstuvwxyz"

fun parse(args: Array<String>) {
    val input = args.toMutableList()
    if (args.contains("-mode")) {
        mode = input[input.indexOf("-mode") + 1]
    }
    if (args.contains("-key")) {
        key = input[input.indexOf("-key") + 1].toInt()
    }
    if (args.contains("-data")) {
        data = input[input.indexOf("-data") + 1]
    } else if (args.contains("-in")) {
        val fileName = "${input[input.indexOf("-in") + 1]}"
        try {
            data = File(fileName).readText()
        } catch (e: Exception) {
            println("Error")
        }
    }
    if (args.contains("-alg")) {
        alg = input[input.indexOf("-alg") + 1]
    }
}

fun output(args: Array<String>, output: String) {
    val input = args.toMutableList()
    if (args.contains("-out")) {
        try {
            val file = File("${input[input.indexOf("-out") + 1]}")
            file.writeText(output)
        } catch (e: Exception) {
            println("Error")
        }
    } else println(output)
}

fun encrypt(phrase: String) : String {
    val output = mutableListOf<Char>()
    if (alg == "shift") {
        for (ch in phrase) {
            if (ch in alphabet) {
                output.add(alphabet[(alphabet.indexOf(ch) + key) % 26])
            } else if (ch in alphabet.uppercase()) {
                output.add(alphabet.uppercase()[(alphabet.uppercase().indexOf(ch) + key) % 26])
            } else output.add(ch)
        }
    } else {
        for (ch in phrase) {
            output.add((ch.code + key).toChar())
        }
    }
    return output.joinToString("")
}

fun decrypt(phrase: String) : String {
    val output = mutableListOf<Char>()
    if (alg == "shift") {
        for (ch in phrase) {
            if (ch in alphabet) {
                output.add(alphabet[(alphabet.indexOf(ch) + (26 - key)) % 26])
            } else if (ch in alphabet.uppercase()) {
                output.add(alphabet.uppercase()[(alphabet.uppercase().indexOf(ch) + (26 - key)) % 26])
            } else output.add(ch)
        }
    } else {
        for (ch in phrase) {
            output.add((ch.code - key).toChar())
        }
    }
    return output.joinToString("")
}

fun main(args: Array<String>) {
    parse(args)
    if (mode == "enc") {
        output(args, encrypt(data))
    } else output(args, decrypt(data))
}