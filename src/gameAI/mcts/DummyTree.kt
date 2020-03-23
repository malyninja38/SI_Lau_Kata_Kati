package gameAI.mcts

internal class DummyTree(root: State) : MCTSTree(root) {
    override fun hasNextSelection(): Boolean {
        return true
    }

    override fun select() {
        //get possible moves, and randomly choose one
        val random = State(0, 0, currentState /*,move*/)
        currentState.addChild(random)
        currentState = random
    }

    override fun expand() {}
}