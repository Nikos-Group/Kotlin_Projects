@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
}

fun changeNarrationMood() {

    val mood: String

    val modifier: (String) -> String

    when (Random.nextInt(1..7)) {

        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace("", "...")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }

        4 -> {
            var narrationGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationGiven++
                "$message.\n(I have narrated $narrationGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = { message ->
                message.substring(0..message.length / 2)
            }
        }

        /** Далее идет не самый производительный код */
        6 -> {
            mood = "mysterious"
            var str = ""
            modifier = { message ->
                for (i in message) {
                    str += leet(i.toString())
                }
                str
            }
        }

        /** Такое же замечание, как и к предыдущему case - у */
        7 -> {
            mood = "poetic"
            var str = ""
            modifier = { message ->
                for (i in message) {
                    if (i == ' ') str = message.replace(i, '\n')
                }
                str
            }
        }

        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }

    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}

var leet = { symbol: String ->
    val s = when (symbol) {
        /** Коды символов, применяемые в шифре Leet */
        "A" -> "4"
        "B" -> "8"
        "C" -> "5"
        "D" -> "2"
        "E" -> "3"
        "F" -> "7"
        "G" -> "9"
        "H" -> "6"
        "I" -> "1"
        "J" -> "01"
        "K" -> "05"
        "L" -> "07"
        "M" -> "02"
        "N" -> "03"
        "O" -> "08"
        "P" -> "66"
        "Q" -> "99"
        "R" -> "44"
        "S" -> "55"
        "T" -> "77"
        "U" -> "88"
        "V" -> "007"
        "W" -> "008"
        "X" -> "001"
        "Y" -> "002"
        "Z" -> "003"
        "a" -> "4"
        "b" -> "8"
        "c" -> "5"
        "d" -> "2"
        "e" -> "3"
        "f" -> "7"
        "g" -> "9"
        "h" -> "6"
        "i" -> "1"
        "g" -> "01"
        "k" -> "05"
        "l" -> "07"
        "m" -> "02"
        "n" -> "03"
        "o" -> "08"
        "p" -> "66"
        "q" -> "99"
        "r" -> "44"
        "s" -> "55"
        "t" -> "77"
        "u" -> "88"
        "v" -> "007"
        "w" -> "008"
        "x" -> "001"
        "y" -> "002"
        "z" -> "003"
        else -> "0"
    }
    /** Возвращаем код переданного символа */
    s
}