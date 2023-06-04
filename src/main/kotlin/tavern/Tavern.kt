/**
 * Посещение таверны
 */

@file:Suppress("UNUSED_EXPRESSION")

package tavern

import narrate
import java.beans.Visibility
import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val menuData = File("tavern/data/menu")
    /** Из этого файлика мы берем меню таверны */
    .readText()
    .split("\n")

/** Список, хранящий названия блюд */
private val menuDishes = menuData.map { mapEntry ->
    /** Берём строчку из меню, достаем из нее три элемента */
    val (_, name, _) = mapEntry.split(",")
    name
    /** В список сохраняем второй элемент - название блюда */
}

private val menuDishPrices: Map<String, Double> = menuData.associate { mapEntry ->
    val (_, name, price) = mapEntry.split(",")
    name to price.toDouble()
}

private val menuDishTypes: Map<String, String> = menuData.associate { mapEntry ->
    val (type, name, _) = mapEntry.split(",")
    name to type
}.toMap()

private const val MENU_HEADING = " *** Welcome to $TAVERN_NAME ***"

private const val MENU_HEADING_LENGTH = MENU_HEADING.length

private val firstNames = mutableSetOf(
    "Alex", "Mordoc",
    "Sophie", "Tariq"
)

private val lastNames = mutableSetOf(
    "Ironfoot", "Fernsworth",
    "Baggins", "Downstrider"
)

private val visitors = mutableListOf<String>()

private val gold = mutableMapOf(
    TAVERN_MASTER to 56.00
)

fun visitTavern(heroName: String) {

    /** Создаем посетителей таверны */
    while (visitors.size < 10) {
        val visitor = "${firstNames.random()} ${lastNames.random()}"
        visitors += visitor
        gold += visitor to 15.0
    }

    println(
        "\n" + "-".repeat(6)
                + "** The tavern incident **"
                + "-".repeat(6) + "\n"
    )
    narrate("$heroName enters $TAVERN_NAME\n")

    narrate(
        """The tavern master says: "Hello!
        |What's your name?
        |And how many gold pieces do you have with you?"""".trimMargin()
    )
    narrate(
        """The $heroName satisfy: "My name is $heroName.
        |I have 20 gold pieces"""".trimMargin()
    )
    narrate(
        """The tavern master says: "I got you.
        |Now I will write down the information in a special list.
        |....""".trimMargin()
    )

    visitors += heroName
    gold += heroName to 20.00

    narrate(
        """Done. 
        |Sit down, please.
        |Look at this menu and choose some food or any drink.""".trimMargin()
    )
    narrate(
        """The $heroName says: "Ok.
        |Let's see what we have here"""".trimMargin()
    )

    println()
    getMenuVersionFirst(menuDishPrices)
    println()

    narrate("Hero wants:")

    val heroWant = readLine() ?: "Goblet of LaCroix, Dragon's Breath"

    val dishes: List<String> = heroWant.split(",")

    narrate("""The $heroName says: "Mmm... I want... """")

    narrate(dishes.joinToString { dish ->
        if (dish == dishes.last()) {
            "and $dish."
        }
        dish
    })




    narrate("$heroName ses several visitors in the tavern.")
    narrate(visitors.joinToString())
    println()

    repeat(7) {
        val visitor = visitors.random()
        val visitorGold = gold.get(visitor)
        val firstDish = menuDishes.random()
        val secondDish = (menuDishes + null).random()
        val thirdDish = (menuDishes + null).random()

        placeOrder(
            visitor, visitorGold,
            firstDish, secondDish, thirdDish
        )
    }

    println()
    getBalances(heroName, gold)
}

private fun placeOrder(
    patronName: String,
    visitorGold: Double,
    vararg dishes: String?,
) {

    val firstDish = dishes.first()
    val secondDish = dishes[1]
    val thirdDish = dishes.last()

    val order: Pair<Double, String> = when {

        /** firstDish никогда != null */

        secondDish != null && thirdDish == null -> {
            menuDishPrices.getValue(firstDish!!)
            +menuDishPrices.getValue(secondDish) to "12"
        }

        secondDish == null && thirdDish != null -> {
            menuDishPrices.getValue(firstDish!!)
            +menuDishPrices.getValue(thirdDish) to "13"
        }

        secondDish != null && thirdDish != null -> {
            val sum = menuDishPrices.getValue(firstDish!!)
            +menuDishPrices.getValue(secondDish)
            +menuDishPrices.getValue(thirdDish)

            sum to "123"
        }

        else -> {
            menuDishPrices.getValue(firstDish!!) to "1"
        }
    }

    val sum = order.first
    val content = order.second

    if (sum > visitorGold) {
        narrate(
            """The tavern master says: "Sorry, $patronName.
                |You need more coin for a this order.
                |Try to choose something cheaper""".trimMargin()
        )

        println()
        getMenuVersionFirst(menuDishPrices)
        println()

    } else {
        when (content) {
            "1" -> {
                executionOrder(patronName, visitorGold, firstDish)
            }

            "13" -> {
                executionOrder(
                    patronName, visitorGold,
                    firstDish, thirdDish!!
                )
            }

            "12" -> {
                executionOrder(
                    patronName, visitorGold,
                    firstDish, secondDish!!,
                )
            }

            "123" -> {
                executionOrder(
                    patronName, visitorGold,
                    firstDish, secondDish!!,
                    thirdDish!!
                )
            }

        }
    }
}

fun executionOrder(
    patronName: String,
    visitorGold: Double,
    vararg dishes: String,
) {

    when (dishes.size) {

        1 -> {

            val dish = dishes.first()

            getOneDish(patronName, visitorGold, dish)
        }

        2 -> {

            val firstDish = dishes[0]
            val secondDish = dishes[1]

            getTwoDishes(
                patronName, visitorGold,
                firstDish, secondDish
            )
        }

        3 -> {

            val firstDish = dishes.first()
            val secondDish = dishes[1]
            val thirdDish = dishes.last()

            getThreeDishes(
                patronName, visitorGold,
                firstDish, secondDish, thirdDish
            )
        }

    }
    println()
}

val action: (String) -> String = { dish ->
    when (dish) {
        "shandy", "elixir" -> "pours"
        "meal" -> "serves"
        else -> "hands"
    }
}

private fun getOneDish(
    patronName: String,
    gold: MutableMap<String, Double>,
    dish: String
) {

    val dishPrice = menuDishPrices.getValue(dish)

    val actionDish = action(menuDishTypes[dish]!!)

    narrate("$TAVERN_MASTER $actionDish $patronName a $dish")

    narrate("$patronName pays $TAVERN_MASTER $dishPrice gold")
    gold[patronName] = gold.getValue(patronName) - dishPrice
    gold[TAVERN_MASTER] = gold.getValue(TAVERN_MASTER) + dishPrice
}

private fun getTwoDishes(
    patronName: String,
    gold: MutableMap<String, Double>,
    firstDish: String,
    secondDish: String,
) {

    val firstDishPrice = menuDishPrices.getValue(firstDish)
    val secondDishPrice = menuDishPrices.getValue(secondDish)

    val (actionFirstItem,
        actionSecondItem) = listOf(
        menuDishTypes[firstDish],
        menuDishTypes[secondDish],
    ).map {
        action(it!!)
    }

    val orderPrice = firstDishPrice + secondDishPrice

    narrate(
        "$TAVERN_MASTER $actionFirstItem $patronName a $firstDish "
                + "and $actionSecondItem a $secondDish"
    )

    narrate("$patronName pays $TAVERN_MASTER $orderPrice gold")
    gold[patronName] = gold.getValue(patronName) - orderPrice
    gold[TAVERN_MASTER] = gold.getValue(TAVERN_MASTER) + orderPrice
}

private fun getThreeDishes(
    patronName: String,
    gold: MutableMap<String, Double>,
    firstDish: String,
    secondDish: String,
    thirdDish: String
) {

    val firstDishPrice = menuDishPrices.getValue(firstDish)
    val secondDishPrice = menuDishPrices.getValue(secondDish)
    val thirdDishPrice = menuDishPrices.getValue(thirdDish)

    val (actionFirstItem,
        actionSecondItem,
        actionThirdItem) = listOf(
        menuDishTypes[firstDish],
        menuDishTypes[secondDish],
        menuDishTypes[thirdDish]
    ).map {
        action(it!!)
    }

    val orderPrice = firstDishPrice + secondDishPrice + thirdDishPrice

    narrate(
        "$TAVERN_MASTER $actionFirstItem $patronName a $firstDish,"
                + "$actionSecondItem a $secondDish"
                + "and $actionThirdItem a $thirdDish"
    )

    narrate("$patronName pays $TAVERN_MASTER $orderPrice gold")
    gold[patronName] = gold.getValue(patronName) - orderPrice
    gold[TAVERN_MASTER] = gold.getValue(TAVERN_MASTER) + orderPrice
}

fun getBalances(
    heroName: String,
    gold: MutableMap<String, Double>
) {
    narrate("$heroName intuitively knows how much money each visitor has")

    gold.forEach { (visitor, balance) ->
        narrate("$visitor has ${"%.2f".format(balance)} gold")
    }
}

val menuLines = {
    menuDishPrices.forEach { (dish, price) ->
        println(
            dish +
                    ".".repeat(
                        MENU_HEADING_LENGTH - price.toString()
                            .length - dish.length
                    ) +
                    "$price"
        )
    }
}

fun getMenuVersionFirst(menuDishPrices: Map<String, Double>) {
    println(MENU_HEADING)
    menuLines()
}

fun getMenuVersionSecond(menuItemPrices: Map<String, Double>) {
    println(MENU_HEADING)
    // TODO ДОРАБОТАТЬ
}