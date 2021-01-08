package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import java.lang.IllegalStateException

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.addValues(initializer.initialPermutation)
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        var order = 1
        for(i in board.getAllCells()) {
            if(order < 16 && board[i] != order) return false
            order++
        }
        return true
    }

    override fun processMove(direction: Direction) {
        with (board) {
            val emptyCell: Cell = find { it == null }!!
            val neighbour = emptyCell.getNeighbour(direction.reversed())
            set(emptyCell, get(neighbour!!))
            set(neighbour, null)
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board[board.getCell(i, j)]
    }
}

fun GameBoard<Int?>.addValues(initialPermutation: List<Int>) {
    var index = 0
    for(i in 1..width) {
        for (j in 1..width) {
            set(getCell(i, j), initialPermutation[index])
            index++
            if (index == 15) break
        }
    }
}

