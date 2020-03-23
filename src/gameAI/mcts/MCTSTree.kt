package gameAI.mcts

import java.io.Serializable
import javax.naming.OperationNotSupportedException
import kotlin.math.ln
import kotlin.math.sqrt

internal abstract class MCTSTree(internal val root: State) : Serializable {
    internal var currentState: State = root
    fun calculateUCB1(s: State): Double {
        val vi = s.getWins().toDouble() / s.getPlays().toDouble()
        val frac = ln(s.getParent()!!.getPlays().toDouble()) / s.getPlays().toDouble()
        val sqrt = sqrt(frac)
        return vi + sqrt
    }

    open fun hasNextSelection(): Boolean {
        return currentState.getChildren().size > 0
    }

    fun currentWasVisited(): Boolean {
        return currentState.getPlays() != 0
    }

    @Throws(OperationNotSupportedException::class)
    abstract fun select()

    abstract fun expand()
    override fun toString(): String {
        return "MCTSTree{\n" +
                "currentState=" + currentState +
                "\nroot=" + root +
                "\n}"
    }

}