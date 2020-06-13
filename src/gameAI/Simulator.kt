package gameAI

import game.Player
import game.SimGame
import gameAI.mcts.Config
import gameAI.mcts.State
import gameAI.mcts.Tree

class Simulator(player1: Player, player2: Player, root: State, matrix: Array<IntArray>, private val currentPlayer: Player) : ArtificialIntelligence(Tree(root)) {

    private val game = SimGame(player1, player2, matrix)
    private var identifier = root.id + 1
    var doSimulate = true

    init {
        game.currentPlayer = currentPlayer
        game.start()
        game.checkCaptureObligation()
        game.move(root.move.first,root.move.second)
        game.currentPlayer = game.getOponent(currentPlayer)
    }

    private fun expand(): Pair<Int, Int> {
        val moves = game.getPossibleMoves()
        val random = State(
                identifier++,
                0,
                0,
                monteCarlo.currentState,
                game.generateBoardHash(),
                moves.shuffled()[0]
        )
        monteCarlo.currentState.children.add(random)
        monteCarlo.currentState = random
        return monteCarlo.currentState.move
    }

    override fun move(startFields: Array<Int>): Pair<Int, Int> {
        return expand()
    }

    fun run() {
        var i = 0
        while (doSimulate && !game.gameOver) {
            if (i >= Config.SimulationIterations) {
                doSimulate = false
            }
            game.checkCaptureObligation()
            val movePair = move(arrayOf())
            game.move(movePair.first, movePair.second)
            game.currentPlayer = game.getOponent(currentPlayer)
            i++
        }
    }

    val gameWon: Boolean
        get() = game.winner == currentPlayer

    override fun setEnemyMove(boardHashCode: String, move: Pair<Int, Int>) {
        TODO("Actually does nothing")
    }
}