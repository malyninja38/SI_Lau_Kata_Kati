package gameAI.mcts

import javafx.scene.shape.Circle
import sample.Plansza
import sample.Pole
import java.util.ArrayList
import kotlin.random.Random

internal class DummyTree(root: State) : MCTSTree(root) {
    private var identifier = root.id+1

    override fun hasNextSelection(): Boolean {
        return true
    }

    override fun select() : Pair<Circle, Circle>{
        //get possible moves, and randomly choose one
        //TODO: implement
        val random = State(identifier++, 0, 0, currentState /*,move*/,currentState.board, currentState.move)
        currentState.children.add(random)
        currentState = random
        return currentState.move
    }

    override fun select(board: ArrayList<Pole>, move: Pair<Circle, Circle>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun expand() : Pair<Circle, Circle>{
        return Pair(Circle(), Circle())
    }
}