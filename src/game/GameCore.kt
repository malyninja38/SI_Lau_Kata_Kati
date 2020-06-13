package game

import java.security.MessageDigest
import kotlin.math.abs

abstract class GameCore(val player1: Player, val player2: Player) {
    var gameOver = false
    val matrix = Array(7) { IntArray(3) }
    var captureRequired = false
    var captureMoves: MutableList<Pair<Int, Int>> = mutableListOf()
    var currentPlayer: Player? = null
    var winner :Player? = null

    internal fun generateBoardHash(): String{
        val hexChars = "0123456789ABCDEF"
        val sb = StringBuilder()
        for(row in matrix){
            for(value in row){
                sb.append(value)
            }
        }
        val bytes = MessageDigest.getInstance("SHA-1").digest(sb.toString().toByteArray())

        val result = StringBuilder(bytes.size * 2)
        bytes.forEach {
            val i = it.toInt()
            result.append(hexChars[i shr 4 and 0x0f])
            result.append(hexChars[i and 0x0f])
        }
        return result.toString()
    }

    internal fun prepareGame() {
        gameOver = false
        winner = null
    }

    @JvmOverloads
    fun getFieldInfo(inputField: Int, vector: Array<Int> = arrayOf(0, 0)): Array<Int> {
        val x = inputField % 3
        val y = inputField / 3
        return arrayOf((y + vector[1]) * 3 + x + vector[0], matrix[y + vector[1]][x + vector[0]])
    }

    internal fun getOponent(player: Player?): Player? {
        return when (player) {
            player2 -> player1
            player1 -> player2
            else -> null
        }
    }

    internal fun getCoords(field: Int): Array<Int> {
        return arrayOf(field % 3, field / 3)
    }

    private fun findNeighbors(startField: Int): Array<Int> {
        val neighbours = ArrayList<Int>()
        val x = startField % 3
        val y = startField / 3
        //up
        if (y > 0) {
            neighbours.add((y - 1) * 3 + x)
        }
        //down
        if (y < 6) {
            neighbours.add((y + 1) * 3 + x)
        }
        //left
        if (x > 0) {
            neighbours.add(y * 3 + x - 1)
        }
        //right
        if (x < 2) {
            neighbours.add(y * 3 + x + 1)
        }
        //diagonal checks
        if (y == 2 && x == 0) {
            neighbours.add((y + 1) * 3 + x + 1)
        }
        if (y == 2 && x == 2) {
            neighbours.add((y + 1) * 3 + x - 1)
        }
        if (y == 4 && x == 0) {
            neighbours.add((y - 1) * 3 + x + 1)
        }
        if (y == 4 && x == 2) {
            neighbours.add((y - 1) * 3 + x - 1)
        }
        if (y == 3) {
            neighbours.add((y - 1) * 3 + x - 1)
            neighbours.add((y - 1) * 3 + x + 1)
            neighbours.add((y + 1) * 3 + x - 1)
            neighbours.add((y + 1) * 3 + x + 1)
        }

        return neighbours.toTypedArray()
    }

    private fun calculateVector(startField: Int, endField: Int): Array<Int> {
        val x1 = startField % 3
        val y1 = startField / 3
        val x2 = endField % 3
        val y2 = endField / 3
        return arrayOf(x2 - x1, y2 - y1)
    }

    internal fun checkCaptureObligation() {
        captureMoves.clear()
        for (i in getPlayerFields(currentPlayer!!.number)) {
            val enemyNeighbors = findNeighbors(i).filter { matrix[it / 3][it % 3] == if (currentPlayer!!.number == 1) 2 else 1 }
            for (enemyField in enemyNeighbors) {
                try {
                    val vec = when (i) {
                        10 -> arrayOf(0, calculateVector(i, enemyField)[1])
                        else -> calculateVector(i, enemyField)
                    }
                    val endField = getFieldInfo(enemyField, vec)
                    if (endField[0] == 11 || endField[0] == 9) {
                        endField[0] = 10
                        endField[1] = getFieldInfo(10)[1]
                    }
                    if (endField[1] == 0) {
                        captureMoves.add(Pair(i, endField[0]))
                    }
                } catch (ignored: Throwable) {
                }
            }
        }
        captureRequired = captureMoves.isNotEmpty()
    }

    private fun getPlayerFields(playerNumber: Int): ArrayList<Int> {
        val playerFields = ArrayList<Int>()
        for (i in 0 until 21) {
            val fieldInfo = getFieldInfo(i)
            if (fieldInfo[1] == playerNumber) {
                playerFields.add(fieldInfo[0])
            }
        }
        return playerFields
    }

    internal fun getPossibleMoves(numberOfMoves: Int = Int.MAX_VALUE): ArrayList<Pair<Int, Int>> {
        val moves = ArrayList<Pair<Int, Int>>()
        when (captureRequired) {
            false -> {
                var startField: Int
                var endField: Int
                val fields = getPlayerFields(currentPlayer!!.number).shuffled()
                var counter = 0
                while (moves.size != numberOfMoves && counter != fields.size) {
                    startField = fields[counter]
                    val neighbors = findNeighbors(fields[counter]).filter { matrix[it / 3][it % 3] == 0 }
                    when {
                        neighbors.isNotEmpty() -> {
                            endField = neighbors.shuffled()[0]
                            moves.add(Pair(startField, endField))
                        }
                    }
                    counter++
                }
            }
            else -> {
                moves.addAll(captureMoves)
            }
        }
        return moves
    }

    internal fun prepareBoard() {
        for (i in 0 until 21) {
            when (i) {
                in 0..8 -> matrix[i / 3][i % 3] = 1
                10 -> matrix[i / 3][i % 3] = 0
                in 12..20 -> matrix[i / 3][i % 3] = 2
                else -> matrix[i / 3][i % 3] = -1
            }
        }
    }

    fun checkGameOver() {
        when {
            getPlayerFields(1).isEmpty() -> {
                gameOver = true
                winner = player2
            }
            getPlayerFields(2).isEmpty() -> {
                gameOver = true
                winner = player1
            }
        }

    }

    internal fun makeMove(startField: Int, endField: Int) {
        val startCoords = getCoords(startField)
        val endCoords = getCoords(endField)
        val temp = matrix[startCoords[1]][startCoords[0]]
        matrix[startCoords[1]][startCoords[0]] = matrix[endCoords[1]][endCoords[0]]
        matrix[endCoords[1]][endCoords[0]] = temp
    }

    internal open fun move(startField: Int, endField: Int) {
        assert(startField != -1)
        assert(endField != -1)
        if (captureRequired) {
            var canProceed = false
            for (captureMove in captureMoves) {
                if (startField == captureMove.first && endField == captureMove.second) {
                    canProceed = true
                }
            }
            if (!canProceed) {
                throw CaptureRequiredException()
            }
            val middleField: Int
            when (startField) {
                10 -> {
                    val vec = calculateVector(endField, startField)
                    vec[0] = vec[0] / 2
                    vec[1] = vec[1] / 2
                    middleField = getFieldInfo(endField, vec)[0]
                }
                else -> {
                    val vec = calculateVector(startField, endField)
                    vec[0] = vec[0] / 2
                    vec[1] = vec[1] / 2
                    middleField = getFieldInfo(startField, vec)[0]
                }
            }
            capture(startField, endField, middleField)
        } else {
            if (getFieldInfo(endField)[1] != 0) {
                throw InvalidMoveException()
            }

            val vec = calculateVector(startField, endField)
            if (startField != 10 && endField != 10) {
                if (abs(vec[0]) + abs(vec[1]) == 1) {
                    makeMove(startField, endField)
                } else {
                    throw InvalidMoveException()
                }
            } else {
                when {
                    startField == 10 -> {
                        if (vec[1] == 0 && vec[0] in arrayOf(-1, 1)) {
                            throw InvalidMoveException()
                        } else {
                            makeMove(startField, endField)
                        }
                    }
                    endField == 10 -> {
                        makeMove(startField, endField)
                    }
                }
            }
        }
        checkGameOver()
    }

    private fun capture(startField: Int, endField: Int, middleField: Int) {
        val startCoords = getCoords(startField)
        val middleCoords = getCoords(middleField)
        val endCoords = getCoords(endField)

        matrix[endCoords[1]][endCoords[0]] = matrix[startCoords[1]][startCoords[0]]
        matrix[middleCoords[1]][middleCoords[0]] = 0
        matrix[startCoords[1]][startCoords[0]] = 0
    }

    abstract fun start()

}