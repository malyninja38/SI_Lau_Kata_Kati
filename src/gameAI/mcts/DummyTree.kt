package gameAI.mcts

import gameAI.Board
import sample.Plansza
import kotlin.random.Random

internal class DummyTree(root: State) : MCTSTree(root) {
    private var identifier = root.id+1

    override fun hasNextSelection(): Boolean {
        return true
    }

    override fun select() {
        //get possible moves, and randomly choose one
        val board = currentState.board.possibleMoves.random()
        val random = State(identifier++, 0, 0, currentState /*,move*/,board, currentState.board.differences(board))
        currentState.children.add(random)
        currentState = random
    }

    override fun select(move: Board) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun expand() {}
}