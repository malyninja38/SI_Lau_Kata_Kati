package gameAI.mcts

import java.io.Serializable

class Tree(internal val root: State) : Serializable {
    var currentState: State = root

    constructor(boardHashCode: Int) :
            this(State(Config.identifier++, 0, 0, null, boardHashCode, null))

    internal fun currentWasVisited(): Boolean {
        return currentState.plays != 0
    }

    override fun toString(): String {
        return "MCTSTree{\n" +
                "currentStateId= " + currentState.id +
                "\nroot=" + root.print("") +
                "\n}"
    }

    internal fun hasNextSelection(): Boolean {
        return currentState.children.size > 0
    }
}