package gameAI.mcts

import java.util.concurrent.CompletableFuture

internal class Simulator(root: State) : Runnable {
    private val simTree: DummyTree = DummyTree(root)
    private fun move() {
        simTree.select()
    }

    override fun run() {
        val i = 0
        CompletableFuture.runAsync {
            try {
                Thread.sleep(Config.ComputeTime.toLong())
            } catch (ignored: InterruptedException) {
            } finally {
                doSimulate = false
            }
        }
        while (doSimulate) {
            if (i >= Config.SimulationIterations) {
                doSimulate = false
            }
            move()
        }
    }

    val state: State
        get() = simTree.root

    companion object {
        var doSimulate = true
    }

}