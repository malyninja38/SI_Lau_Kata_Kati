package gameAI

import gameAI.mcts.Config
import gameAI.mcts.DummyTree
import gameAI.mcts.State

internal class Simulator(root: State) {
    private val simTree: DummyTree = DummyTree(root)
    private val controller = SimulatedController()
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
        get() = controller.koniecGry

    val rootState: State
        get() = simTree.root

}