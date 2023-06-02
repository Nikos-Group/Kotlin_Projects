/**
 * Маленькая текстовая игра
 * (пока в ней всего лишь один уровень / один день)
 */

/**
 * В данном мини - проекте используются полезные фишки Kotlin.
 * Попробуйте написать проект сами и "потрогать" все фичи
 */

/**
 * Главный герой - персонаж мужского пола,
 * поэтому выбор пола не предусматривается
 */

import java.lang.IllegalArgumentException

/**
 * Словарик, хранящий расы и информацию о них:
 * раса - ключ,
 * информация - значение
 */
val races = mapOf(
    /** Человек */
    "Human" to "a man in a knight's armor",
    /** Гном */
    "Dwarf" to "a little merchant",
    /** Эльф */
    "Elf" to "an archer with sharp ears",
    /** Орк */
    "Orc" to "a strong man with a club and a sword",
    /** Гоблин */
    "Goblin" to "a cunning thief",
    /** Варвар */
    "Barbarian" to "a robber with an axe",
    /** Восставший из мертвых */
    "Risen from the dead"
            to "a skeleton with a shield of bones"
)

/**
 * Уровень героя
 * (уровень может быть только положительным или равняться нулю)
 */
var playerLevel: UInt = 0u

/**
 * Количество монет у героя
 */
var numberOfCoins: Int = 0

/**
 * Удалось ли герою стать хозяином боевого животного
 * (коня, минотавра или дракона)?
 */
var hasSteed: UInt = 0u

/** Что за боевое животное? */
var fightingAnimal = ""

/** Функция, в которой написана вся логика игры */
fun main() {
    /** Вступление */
    println("\t\t  The game begins")
    println(
        "-".repeat(6)
                + "** Game about a hero **"
                + "-".repeat(6) + "\n"
    )

    /**
     * Игрок вводит имя героя.
     * Оно может быть любым (даже ";54546464783883838")
     */
    print("My hero's name: ")
    val heroName = readLine() ?: "Madrigal"
    /** Если пользователь ничего не ввел - "Madrigal" */

    print("Ok. Hero's name: $heroName\n")

    /** Игрок выбирает расу героя */
    val race = raceSelection()

    /** Все готово - герой отправляется в путешествие по миру */
    println("$heroName announces his presence to the world.")
    println("$heroName goes on a journey.")

    /** Сначала герой находит волшебное зеркало */
    meetingWithTheMagicMirror(heroName)

    /**
     * Затем герой отправляется выполнять квест с доски сообщений.
     * Или не отправляется, а становится Богом.
     * Тогда открывается секретная концовка игры
     */
    val pair: Pair<Int, UInt> = bountyBoard(heroName, race)

    numberOfCoins += pair.first
    playerLevel = pair.second

    /** Я предлагаю Вам доработать этот кусочек и проверять божественность не по деньгам, а по расе */
    if (numberOfCoins == 300_000_000) {
        secretEnding(heroName)
        /** Секретная концовка */
        return
    } else {
        /** Герой попадает в город с конюшней */
        println("A portal opens in the city of Rezenburg and the hero quickly flies out of it.")
        println("He finds himself in front of a local stable.")
    }

    /**
     * После - герой идет покупать боевое животное
     */
    val triple: Triple<String, UInt, Int> =
        `**~choosing_a_fighting_animal~**`(heroName, numberOfCoins)

    fightingAnimal = triple.first
    hasSteed = triple.second

    /** Вычитаем из общей суммы денег героя цену купленного животного */
    numberOfCoins -= triple.third

    if (hasSteed == 1u) {
        /**
         * Если герой стал хозяином боевого животного,
         * то он идет в таверну и празднует покупку
         */
        val drinkPrice = `**~feast_at_the_tavern~**`(heroName, numberOfCoins)
        numberOfCoins -= drinkPrice

        /** Подводим итоги дня */
        `**~summing_up_the_results_of_the_day~**`(
            heroName, playerLevel,
            numberOfCoins, hasSteed, fightingAnimal
        )
    } else {
        /**
         * Если же герой не смог купить боевое животное,
         * то он попадает в новое приключение.
         * Рассказ о нем будет во втором дне.
         */
        goToNewAdventure(heroName)
        return
        /** Завершаем игру (игра внезапно завершается - намек на продолжение) */
    }
}

/** Выбор расы героя */
private fun raceSelection(): String {
    println(
        "\n" + "-".repeat(6)
                + "** Race selection **"
                + "-".repeat(6) + "\n"
    )

    /** Выводим расы и информацию о них */
    for (race in races) {
        println(
            race.key + " "
                    + "-".repeat(3) + " "
                    + race.value + "\n"
        )
    }

    println("-".repeat(20) + "\n")

    /* Игрок вводит расу героя */
    print("My hero's race: ")
    var race = readLine() ?: "Human"
    if (race !in races) race = "Human"

    print("Ok. Hero's race: $race\n")

    println(
        "\n" + "-".repeat(6)
                + "** Hooray, you have chosen a race! **"
                + "-".repeat(6) + "\n"
    )
    return race
}

/** Встреча с волшебным зеркалом */
private fun meetingWithTheMagicMirror(heroName: String) {
    println(
        "\n" + "-".repeat(6)
                + "** Meeting with the magic mirror **"
                + "-".repeat(6) + "\n"
    )
    println("$heroName enters the cave.")
    println("$heroName sees a magic mirror.\n")

    println("""The mirror says: "Hello, wanderer. What do you want?"""" + "\n")
    println(
        """$heroName satisfy: "My name is $heroName.
        |"Tell me my name on the contrary."""".trimMargin()
    )

    println("\n" + "The mirror says: \"Ok - ${heroName.reversed()}\"")
    /** Зеркало говорит герою его имя наоборот */
    /**
     * reversed() - возвращает строку с символами в обратном порядке.
     */

    /** Галочка */
    val checkMark = '\u2705'
    println(
        "\n" + "-".repeat(6)
                + "** Mirror ${("-").repeat(3)} $checkMark **"
                + "-".repeat(6) + "\n"
    )
}

/** Метод для выбора квеста на доске сообщений */
private fun questSelection(
    race: String,
    level: Int,
    /** Дружит с варварами (по умолчанию герой дружит с варварами */
    hasBefriendBarbarians: Boolean = true,
    /** Не дружит с варварами */
    hasAngredBarbarians: Boolean = false,
): String? {
    return when (level) {
        1 -> "meet Mr. Bubbles in the land of soft things."
        in 2..5 -> {
            /** Проверяем возможность дипломатического решения */
            val canTalkToBarbarians = !hasAngredBarbarians &&
                    (hasBefriendBarbarians || race == "Barbarian")
            if (canTalkToBarbarians) "convince the barbarians not to attack the city"
            else "save the town from the barbarian invasions."
        }

        6 -> "locate the enchanted sword."
        7 -> "recover the long-lost artifact of creation."
        8 -> "defeat Nogartse, bringer of death and eaters of worlds."
        /**
         * Уровень героя стал очень высоким, и герой стал Богом
         */
        else -> null
    }
}

/** Метод для рассказа о приключении с доской сообщений */
private fun bountyBoard(
    heroName: String,
    race: String,
): Pair<Int, UInt> {
    /**
     * Метод возвращает:
     * - количество золотых, которое герой получит за выполнение определенного квеста
     * (если герой станет Богом, то он станет сказочно богатым :) );
     * - уровень героя
     */
    println(
        "-".repeat(6)
                + "** Bounty Board **"
                + "-".repeat(6) + "\n"
    )
    println("$heroName comes to the town of Springfield.") /* Нашли пасхалку? */
    println("$heroName sees a sign with an assignment on the main floor")

    println("\n$heroName approaches the sign and begins to read")

    /** Появляется волшебник, с этого момента я призываю Вас творить */
    println("\nSuddenly a wizard appears")

    println(
        "\nHellooooo, hero. " +
                "Lettttt me raise your level so that you cannnnn complete any of the questssssss on this board!"
    )

    print("\nAll right wizard, raise my level to... ")

    /**
     * Игрок вводит тот уровень, до которого хочет повысить уровень своего персонажа.
     * Обработка исключений тут не предусматривалась - прошу тех,
     * кто захочет потренироваться - написать эту обработку
     */
    val level: Int = readLine()?.toInt() ?: 8

    println("\nOk, $heroName - says wizard with enthusiasm")
    println("The wizard waves his staff and lightning strikes $heroName")

    println("\n$heroName feels the power")

    println("\n" + "I'm ready!" + "\n")

    /**
     * Подразумевается,
     * что герой просит волшебника повысить уровень
     * для выполнения конкретного квеста
     */

    /** Выбранный героем квест (null допускается) */
    val quest: String? = questSelection(
        race,
        level
    )

    /** Выбранный героем квест и последствия решения героя */
    val message = quest?.replace("Nogartse", "xxx")
        ?.let { censoredQuest ->
            "Quest: $censoredQuest"
        } ?: "$heroName becomes a god and go to rule the heavenly city."

    if (message.contains("Quest")) {
        println(message)
        println(
            "\nA portal opens and drags $heroName."
                    + "\nHe goes to perform the task."
        )
        println("\nAnd executes it.")
    } else println(message)

    /** золотые */
    var goldCoins: Int = if (message.contains("god")) {
        /** Если герой стал Богом */
        300_000_000
    } else {
        /** Если появился портал, и герой отправился выполнять задание */
        14
        /**
         * Это сделано для того чтобы
         * игрок увидел разочарование героя при покупке дракона
         */
    }

    /** Галочка */
    val checkMark = '\u2705'
    println(
        "\n" + "-".repeat(6)
                + "** Bounty Board ${("-").repeat(3)} $checkMark **"
                + "-".repeat(6) + "\n"
    )
    return Pair(goldCoins, level.toUInt())
}

/** Секретная концовка */
fun secretEnding(heroName: String) {
    println("$heroName arrives in the heavenly city.")
    println("$heroName sits on the throne of God.\n")

    /** Концовка немного смятая (в качестве обучения можете ее доделать) */
    println(
        "-".repeat(5) +
                "** The end **" +
                "-".repeat(5)
    )
}

/** Выбор боевого животного */
private fun `**~choosing_a_fighting_animal~**`(
    heroName: String,
    numberOfCoins: Int
): Triple<String, UInt, Int> {
    /**
     * Возвращаются объект класса Triple с тремя значениями:
     * - название купленного животного (если герой смог купить животное);
     * - герой стал хозяином боевого животного / не стал;
     * - сколько денег герой потратил на покупку животного
     *  (если герой не купил животное - 0)
     */

    /** Я нарушил правила названий функций - ну да ладно! */

    println(
        "\n" + "-".repeat(6)
                + "** Choosing a fighting animal **"
                + "-".repeat(6) + "\n"
    )
    println("$heroName comes to the stable.\n")

    println("""The groom says: "Hello!"""")
    println("$heroName says: \"Hello. I want to buy a fighting animal\"\n")

    /** Боевые животные и их цены */
    val animals = mapOf(
        "horse" to 5,
        "minotaur" to 10,
        "dragon" to 15
    )

    println(
        """The groom says: "Ok. 
        |I can sell a${
            animals.keys
                .toString()
                .replace('[', ' ')
                .replace(']', ' ')
        }to ${
            animals.values
                .toString()
                .replace('[', ' ')
                .replace("]", "")
        }.
        |Who do you want?"""".trimMargin()
    )

    var fightingAnimal = readLine() ?: "horse"
    if (fightingAnimal !in listOf("horse", "minotaur", "dragon"))
        fightingAnimal = "horse"

    println()
    println("""$heroName says: "I want to buy a $fightingAnimal"""")

    /** Вычисляем цену животного */
    val price: Int = animals.getValue(fightingAnimal)

    println()
    println(
        """The groom says: "Ok. 
        |This animal costs $price"""".trimMargin()
    )

    println()
    println("$heroName says: \"I'm buying!\"\n")
    if (numberOfCoins - price >= 0) { /* Герой может купить животное */
        println("""The groom says: "Sold!"""")
        println(
            "\n" +
                    when (fightingAnimal) {
                        "minotaur" -> "$heroName jumps on the minotaur and rushes to the nearest town."
                        "dragon" -> "$heroName jumps on a dragon and flies to the nearest town."
                        /** Конь */
                        else -> "$heroName gets on a horse and rides to the nearest town."
                    }
        )

        /** Галочка - герою удалось приобрести боевое животное */
        val checkMark = '\u2705'
        println(
            "\n" + "-".repeat(6)
                    + "** Fighting animal ${("-").repeat(3)} $checkMark **"
                    + "-".repeat(6) + "\n"
        )
        return Triple(fightingAnimal, 1u, price)
    } else {
        println(
            """The groom says: "Oh..
            |I'm sorry, my friend. 
            |You don't have enough money to buy"""".trimMargin()
        )
        println(
            "\n"
                    + """$heroName says: "It's sad... 
            |but..I won't give up.. 
            |One day I'll earn enough to buy a $fightingAnimal"""".trimMargin()
        )

        println("\n$heroName said goodbye to the groom and left the stable.")

        /** Крестик  - покупка не удалась */
        val checkMark = '\u274C'
        println(
            "\n" + "-".repeat(6)
                    + "** Fighting animal ${("-").repeat(3)} $checkMark **"
                    + "-".repeat(6) + "\n"
        )
        return Triple("", 0u, 0)
    }
}

/**
 * Герой попадает в новое приключение
 * (функция вызывается только тогда,
 * когда герой не смог стать хозяином боевого животного)
 */
private fun goToNewAdventure(heroName: String) {
    println("$heroName comes out of the stable and sees an angel in front of him.")
    println("""$heroName - "Oh my globe!!!!"""")
    println(
        "-".repeat(5) +
                "** To be continued... **" +
                "-".repeat(5)
    )
    println(
        "-".repeat(5) +
                "** Thanks for playing **" +
                "-".repeat(5)
    )
}

/** Празднование в таверне в честь проделанной работы */
private fun `**~feast_at_the_tavern~**`(
    heroName: String,
    numberOfCoins: Int
): Int {
    /** Метод возвращает цену напитка (если герою удалось купить какой - то напиток) */
    println(
        "-".repeat(6)
                + "** Feast at the tavern **"
                + "-".repeat(5) + "\n"
    )
    println("$heroName arrives in the town of Kronstadt.") /* Кто нашел пасхалку? */
    println("$heroName enters the the Unicorn Horn Tavern.")

    /* Диалог героя и трактирщика */
    println("\nThe innkeeper asks: \"Do you need a stable?\"")
    println(""""Yes, thank you" - the hero answers.""")

    println("\nAfter 5 minutes...")
    println("$heroName coming back from the stable.\n")

    println(""""I want a drink!!" - the hero says.""")

    /** Напитки и их цены */
    val drinks = mapOf(
        /** Мед */
        "honey" to 2,
        /** Пиво */
        "beer" to 4,
        /** Вино */
        "wine" to 7,
    )

    println(
        """"Wonderful" - says the innkeeper. 
        |"I can offer ${
            drinks.keys
                .toString()
                .replace('[', ' ')
                .replace(']', ' ')
        } to ${
            drinks.values.toString()
                .replace('[', ' ')
                .replace("]", "")
        }. 
        |What do you want?"""".trimMargin()
    )

    var `what_does_the_hero_want` = readLine() ?: "honey"
    if (what_does_the_hero_want !in drinks) what_does_the_hero_want = "honey"

    /** Цена напитка */
    val price = drinks.getValue(what_does_the_hero_want)

    if (numberOfCoins - price >= 0) {
        /** Денег на напиток хватает */
        println("\n$heroName drinking a mug of $what_does_the_hero_want.")
        println("Refreshingly!!! Thanks!! Here's the money.\n")

        println("Come to us again.")
        println("Well.\n")

        println("$heroName leaves the tavern alone with his war animal.")

        /** Галочка */
        val checkMark = '\u2705'
        println(
            "\n" + "-".repeat(6)
                    + "** Feast at the tavern ${("-").repeat(3)} $checkMark **"
                    + "-".repeat(6) + "\n"
        )
        return price
    } else {
        /** Денег на напиток не хватает */
        println(
            "\n" +
                    """The innkeeper says: "Oh..
            |I'm sorry, my friend. 
            |You don't have enough money to buy"""".trimMargin()
        )

        println(
            "\n" +
                    """$heroName says: "It's sad... 
            |but..Nothing wrong"""".trimMargin()
        )

        /** Крестик  - покупка не удалась */
        val checkMark = '\u274C'
        println(
            "\n" + "-".repeat(6)
                    + "** Feast at the tavern ${("-").repeat(3)} $checkMark **"
                    + "-".repeat(6) + "\n"
        )
        return 0 /* Возвращаем 0, та как герой не смог купить напиток -> цена равна 0 */
    }
}

/** Подведение итогов дня */
private fun `**~summing_up_the_results_of_the_day~**`(
    heroName: String,
    playerLevel: UInt,
    numberOfCoins: Int,
    hasSteed: UInt,
    fightingAnimal: String
) {
    println(
        "-".repeat(5)
                + "** Summing up the results of the day **"
                + "-".repeat(5) + "\n"
    )

    val `about_animal` = if (hasSteed == 1u) {
        "I have a $fightingAnimal."
    } else {
        "I didn't manage to buy a fighting animal, but I will definitely do it tomorrow."
    }

    println(
        """$heroName sits down on the pavement and says:
        |"The day was very successful.
        |Now I have а $playerLevel level, $numberOfCoins gold coins.
        |$about_animal 
        |And I liked this day"
    """.trimMargin()
    )

    println("\nHaving said that, he got up from the pavement and went to meet adventures")

    /** Галочка - первый дунь пройден удачно */
    val checkMark = '\u2705'
    println(
        "\n" + "-".repeat(6)
                + "** First Day ${("-").repeat(3)} $checkMark **"
                + "-".repeat(6) + "\n"
    )

    println(
        "-".repeat(5) +
                "** You have passed the first day **" +
                "-".repeat(5)
    )
    println(
        "-".repeat(5) +
                "** The second day will be released very soon **" +
                "-".repeat(5)
    )
    println(
        "-".repeat(5) +
                "** Wait **" +
                "-".repeat(5)
    )
}

/** Пользовательское исключение - на всякий случай, вдруг в будущем пригодится */
class InvalidPlayerLevelException :
    IllegalArgumentException("invalid player level (must be at least 1).")

/**
 * Первый уровень написан.
 * Предлагаю Вам написать еще 11 уровней (и детально прописать каждый квест с доски).
 * Так Вы изучите Kotlin в очень веселой форме
 */