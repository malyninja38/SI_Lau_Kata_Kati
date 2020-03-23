package gameAI.mcts

import java.io.Serializable
import kotlin.collections.ArrayList

internal class State(
        private var wins: Int,
        private var plays: Int,
        private val parent: State?
        /*,private val move: Ruch*/
) : Serializable {

    private val children = ArrayList<State>()

    internal fun getWins(): Int {
        return wins
    }

    internal fun getPlays(): Int {
        return plays
    }

    internal fun addChild(state: State) {
        children.add(state)
    }

    internal fun getChildren(): ArrayList<State> {
        return children
    }

    internal fun getParent(): State? {
        return parent
    }

    internal fun updateState(gameWon: Boolean) {
        plays++
        if (gameWon) wins++
        parent?.updateState(gameWon)
    }

    /*Ruch getMove(){
        return move;
    }*/

    /*Ruch getMove(){
        return move;
    }*/
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("State{ ")
        sb.append("wins=").append(wins).append(" ")
        sb.append("plays=").append(plays).append(" ")
        sb.append("children=").append("[")
        for (child in children) {
            sb.append(child)
        }
        sb.append("]")
        sb.append("}")
        return sb.toString()
    }
}