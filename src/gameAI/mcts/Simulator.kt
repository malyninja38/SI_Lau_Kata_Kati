package gameAI.mcts

internal class Simulator(root: State) {
    private val simTree: DummyTree = DummyTree(root)
    private fun move() {
        simTree.select()
    }
    var doSimulate = true
    fun run() {
        var i = 0
        while (doSimulate) {
            if (i >= Config.SimulationIterations) {
                doSimulate = false
            }
            move()
            i++
        }
    }

    val gameWon:Boolean
        get() = simTree.currentState.board.endGame

    val rootState: State
        get() = simTree.root

}