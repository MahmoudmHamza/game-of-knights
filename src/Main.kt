import kotlin.random.Random

/*
 * Author: Mahmoud Hamza
 * Date: 1/4/2025
 *
 * This class contains the logic for "Game Of Knights".
 * We are tracking the current attacker for the current turn and selecting the next knight
 * in the sequence from the list of alive knights, as displayed in the logs.
 */

class Knight (val id: Int, val name: String, private var health: Int = 100) {

    fun takeDamage(damage: Int){
        health -= damage
        println("[$name] health points: $health")

        //for logging
        if(!isAlive()){
            println("---------------")
            println("[$name] DIED")
            println("---------------")
        }
    }

    fun isAlive(): Boolean{
        return health > 0
    }
}

fun main() {

    //create the knight players and store them into the map of Knight by id map
    val knightByIdMap = mapOf(
        1 to Knight(1, "Knight 1"),
        2 to Knight(2, "Knight 2"),
        3 to Knight(3, "Knight 3"),
        4 to Knight(4, "Knight 4"),
        5 to Knight(5, "Knight 5"),
        6 to Knight(6, "Knight 6")
    )

    //track current attacker
    var currentAttackerId = 1

    //loop through them to simulate duel
    while (true) {

        //check if the current attacker is alive
        if (!knightByIdMap[currentAttackerId]!!.isAlive()) {
            currentAttackerId = (currentAttackerId % 6) + 1
            continue
        }

        println(">> ${knightByIdMap[currentAttackerId]!!.name} initiates attack..")
        println("***********************************")

        //getting alive knights to duel
        val aliveKnights = knightByIdMap
            .filter { it.value.isAlive() && it.key != currentAttackerId }
            .keys
            .sorted()

        println("Alive Knights: ${aliveKnights.toString()}")
        println("...........................")

        //check for the win condition
        //for the current attacker, if no valid defender available to duel then he wins
        if (aliveKnights.isEmpty()) {
            println(">> NO DEFENDERS ALIVE...")
            println("[[${knightByIdMap[currentAttackerId]!!.name}]] WINS THE COMBAT!!")
            break
        }

        //getting the next defender to duel from alive knights list
        val currentDefenderId = aliveKnights[(currentAttackerId-1) % aliveKnights.size]

        //get random value from 1 to 6 and let the defender takes damage
        val damage = Random.nextInt(1, 7)
        println("[${knightByIdMap[currentAttackerId]!!.name}] deals [$damage] damage to [${knightByIdMap[currentDefenderId]!!.name}]")
        knightByIdMap[currentDefenderId]!!.takeDamage(damage)
        println("====================================================")

        //switching turns after attacking
        currentAttackerId = (currentAttackerId % 6) + 1
    }
}