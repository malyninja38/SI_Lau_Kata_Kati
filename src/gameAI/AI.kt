package gameAI

import game.Game
import gameAI.mcts.Config
import gameAI.mcts.State
import gameAI.mcts.Tree
import kotlin.concurrent.thread
import kotlin.math.min

class AI : ArtificialIntelligence {

    private val game: Game

    constructor(game: Game) : super(Tree(game.matrix.hashCode())) {
        monteCarlo = Tree(game.matrix.hashCode())
        stateHistory.add(monteCarlo.currentState.boardHashCode)
        this.game = game
    }

    constructor(game: Game, filename: String) : super(Tree(game.matrix.hashCode())) {
        monteCarlo = when (val temp = loadTree(filename)) {
            null -> Tree(game.matrix.hashCode())
            else -> {
                temp
            }
        }
        monteCarlo.currentState = monteCarlo.root
        stateHistory.add(monteCarlo.currentState.boardHashCode)
        this.game = game
    }

    private fun select(startFields: Array<Int>): Pair<Int, Int> {
        println("select")
        var scores = mutableMapOf<State, Double>()
        for (child in monteCarlo.currentState.children) {
            scores[child] = calculateUCB1(child)
        }
        if (startFields.isNotEmpty()) {
            scores = scores.filter { it.key.move.first in startFields }.toMutableMap()
        }
        monteCarlo.currentState = scores.maxBy { it.value }?.key ?: scores.keys.first()
        if (!monteCarlo.currentWasVisited()) {
            simulate()
        }
        return monteCarlo.currentState.move
    }

    override fun setEnemyMove(boardHashCode: Int, move: Pair<Int, Int>) {
        for (child in monteCarlo.currentState.children) {
            if (child.boardHashCode == boardHashCode) {
                monteCarlo.currentState = child
                return
            }
        }
        val newState = State(Config.identifier++, 0, 0, monteCarlo.currentState, boardHashCode, move)
        monteCarlo.currentState.children.add(newState)
        monteCarlo.currentState = newState
    }

    private fun expand(): Pair<Int, Int> {
        println("expand")
        val moves = game.getPossibleMoves(2)
        val newStates: MutableList<State> = mutableListOf()
        for (i in 0 until min(2, moves.size)) {
            newStates.add(
                    State(
                            Config.identifier++,
                            0,
                            0,
                            monteCarlo.currentState,
                            -1,
                            moves[i]
                    )
            )
        }
        for (state in newStates) {
            monteCarlo.currentState.children.add(state)
        }
        monteCarlo.currentState = monteCarlo.currentState.children.first()
        simulate()
        return monteCarlo.currentState.move
    }

    private fun simulate(): Pair<Int, Int> {
        println("Starting simulation")
        val sim = Simulator(game.player1, game.player2, monteCarlo.currentState.copy(), game.matrix, game.currentPlayer!!)
        thread {
            Thread.sleep(Config.ComputeTime.toLong())
            sim.doSimulate = false
            return@thread
        }
        thread {
            sim.run()
            println("Simulation ended with result: ${if (sim.gameWon) "won" else "lost"}")
        }
        monteCarlo.currentState.updateState(sim.gameWon)
        return monteCarlo.currentState.move
    }

    override fun move(startFields: Array<Int>): Pair<Int, Int> {
        print("Move: ")
        return when {
            monteCarlo.hasNextSelection() -> {
                select(startFields)
            }
            else -> {
                expand()
            }
        }
    }



    override fun toString(): String {
        return "Tree: $monteCarlo"
    }
}