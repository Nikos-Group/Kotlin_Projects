import tavern.visitTavern

/**
 * Игра NyetHack включает в себя несколько уровней.
 * Каждый уровень - фрагмент второго дня игры про героя
 */

/**
 * В данном мини - проекте используются лямбда функции,
 * коллекции,
 * деструктуризация,
 * фишки функционального программирования и многое другое
 */

fun main() {

    /** Вступление */
    println("\t\t  The game begins")
    println(
        "-".repeat(6)
                + "** NyetHack **"
                + "-".repeat(6) + "\n"
    )

    /**
     * Перед рассказом о приключениях героя
     * определяется настроение рассказчика
     */
    changeNarrationMood()

    narrate(
        "A hero enters the town of Kronstadt. What is their name?",
        ::makeYellow
    )
    val heroName = readLine() ?: "Madrigal"

    println("Ok. Hero's name: $heroName\n")

    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")

    visitTavern(heroName)
}

private fun createTitle(name: String) = when {
    /**
     * all() проверяет,
     * что каждый символ строки удовлетворяет
     * заданному предикату
     */
    name.all { it.isDigit() } -> "The Identifiable"

    /** none() - функция, обратная all() */
    name.none { it.isLetter() } -> "The Witness Protection Member"

    name.count {
        it.lowercase() in
                "aeiou"
    } > 4 -> "The Master of Vowels"

    name.all { it.isUpperCase() } -> "Outstanding"

    countLetter(name) > 10 -> "Voluminous"

    palindrome(name) -> "Palindrome Carrier"

    else -> "The Renowned Hero"
}

val countLetter: (String) -> Int = {
    var letters = 0
    for (i in it) {
        if (i.isLetter()) ++letters
    }
    letters
}

val palindrome: (String) -> Boolean = {
    val s1 = it.reversed().lowercase()
    it.lowercase() == s1
}

private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"