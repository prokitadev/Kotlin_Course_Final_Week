package games.gameOfFifteen

import kotlin.random.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val init = (0..15).shuffled().toMutableList()
        if (!isEven(init)) {
            val ran = Random.nextInt(14)
            val a = init[ran]
            val b = init[ran+1]
            init[ran+1] = a
            init[ran] = b
        }
        init
    }
}

