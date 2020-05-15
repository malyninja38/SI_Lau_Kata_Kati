package gameAI.mcts

import gameAI.AI
import javafx.scene.shape.Circle
import sample.Pole
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

internal abstract class MCTSTree(internal val root: State) : Serializable {

    internal var currentState: State = root
    fun calculateUCB1(s: State): Double {
        val vi = s.wins.toDouble() / s.plays.toDouble()
        val frac = ln(s.parent!!.plays.toDouble()) / s.plays.toDouble()
        val sqrt = sqrt(frac)
        return vi + sqrt
    }

    private fun findFreeNeighbour(board: ArrayList<Pole>, startField: Pole): Circle?{
        val emptyNeighbours = ArrayList<Pole>()
        for(i in startField.sasiedzi){
            val field = board.stream().filter{ t -> t.numer == startField.numer }.findFirst()
            if(field.isEmpty) emptyNeighbours.add(field.get())
        }
        if(emptyNeighbours.isEmpty()) return null
        return emptyNeighbours[Random.nextInt(emptyNeighbours.size)].field
    }

    private fun getMyFields(board: ArrayList<Pole>):ArrayList<Pole>{
        val playerPawns = ArrayList<Pole>()
        for(field in board){
            if(field.pionek.gracz == AI.playerNumber){
                playerPawns.add(field)
            }
        }
        return playerPawns
    }

    internal fun generateMove(board : ArrayList<Pole>):Pair<Circle, Circle>{
        val fields = getMyFields(board)
        var startCircle : Circle
        var endCircle : Circle
        loop@while(true){
            val selectedField = fields[Random.nextInt(fields.size)]
            startCircle = selectedField.field
            val endCandidate = findFreeNeighbour(board, selectedField)
            when(endCandidate){
                null -> continue@loop
                else -> {
                    endCircle = endCandidate
                    break@loop
                }
            }
        }
        return Pair(startCircle, endCircle)
    }

    internal open fun hasNextSelection(): Boolean {
        return currentState.children.size > 0
    }

    internal fun currentWasVisited(): Boolean {
        return currentState.plays != 0
    }


    abstract fun select(): Pair<Circle, Circle>
    abstract fun select(board: ArrayList<Pole>, move: Pair<Circle, Circle>)

    abstract fun expand(): Pair<Circle, Circle>
    override fun toString(): String {
        return "MCTSTree{\n" +
                "currentStateId= " + currentState.id +
                "\nroot=" + root.print("") +
                "\n}"
    }



}