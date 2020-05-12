package gameAI.mcts

import gameAI.Board
import java.io.Serializable
import javax.naming.OperationNotSupportedException
import kotlin.math.ln
import kotlin.math.sqrt

internal abstract class MCTSTree(internal val root: State) : Serializable {

    internal var currentState: State = root
    fun calculateUCB1(s: State): Double {
        val vi = s.wins.toDouble() / s.plays.toDouble()
        val frac = ln(s.parent!!.plays.toDouble()) / s.plays.toDouble()
        val sqrt = sqrt(frac)
        return vi + sqrt
    }

    open fun hasNextSelection(): Boolean {
        return currentState.children.size > 0
    }

    fun currentWasVisited(): Boolean {
        return currentState.plays != 0
    }


    abstract fun select()
    abstract fun select(move:Board)

    abstract fun expand()
    override fun toString(): String {
        return "MCTSTree{\n" +
                "currentStateId= " + currentState.id +
                "\nroot=" + root.print("") +
                "\n}"
    }



}