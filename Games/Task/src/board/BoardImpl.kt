package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardClass(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardClass(width)

open class SquareBoardClass(final override val width: Int) : SquareBoard {
    val listOfCells = mutableListOf<Cell>()

    init {
        for(i in 1..width) {
            for (j in 1..width) {
                listOfCells.add(Cell(i, j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i !in 1.. width || j !in 1..width) return null
        return getCell(i, j)
    }

    override fun getCell(i: Int, j: Int): Cell {
        return listOfCells[(i-1)* width + (j-1)]
    }

    override fun getAllCells(): Collection<Cell> {
        return listOfCells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        var f = jRange.first
        var l = jRange.last
        var row = listOfCells.subList(((i - 1) * width), (i * width))
        if (f > l) {
            row = row.reversed() as MutableList<Cell>
            f = l
            l = jRange.first
        }
        if (l < row.size) row = row.subList(0, l)
        if (f > 1) row = row.subList(f, row.size)
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        var col = mutableListOf<Cell>()
        for (i in 0 until width) {
            col.add(listOfCells.get((j - 1) + i * width))
        }
        if (iRange.first < iRange.last) {
            if (iRange.last < col.size) col = col.subList(0, iRange.last)
            if (iRange.first > 1) col = col.subList(iRange.first - 1, col.size)
        } else {
            if (iRange.first < col.size) col = col.subList(0, iRange.first)
            if (iRange.last > 1) col = col.subList(iRange.last - 1, col.size)
            col = col.reversed() as MutableList<Cell>
        }
        return col
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        if(direction == UP)  return getCellOrNull(i-1, j)
        if(direction == DOWN)  return getCellOrNull(i+1, j)
        if(direction == LEFT)  return getCellOrNull(i, j-1)
        if(direction == RIGHT)  return getCellOrNull(i, j+1)
        return null;
    }
}

class GameBoardClass<T>(width: Int) : SquareBoardClass(width), GameBoard<T> {
    private val mapOfCells : HashMap<Cell, T?> = HashMap()

    init {
        for(i in 0 until width*width) {
            mapOfCells.put(listOfCells[i], null)
        }
    }

    override fun get(cell: Cell): T? {
        return mapOfCells.get(cell)
    }

    override fun set(cell: Cell, value: T?) {
        mapOfCells.set(cell, value)
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return mapOfCells.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return mapOfCells.filterValues(predicate).keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return mapOfCells.any { predicate(it.value) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return mapOfCells.all { predicate(it.value) }
    }

}
