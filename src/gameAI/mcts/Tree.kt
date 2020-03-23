package gameAI.mcts

import java.util.*

internal class Tree : MCTSTree(State(0, 0, null)) {
    override fun select() {
        val scores = HashMap<State,Double>()
        for (child in currentState.getChildren()) {
            scores[child] = calculateUCB1(child)
        }
        currentState = scores.maxBy { it.value }?.key ?: scores.keys.first();
    }

    override fun expand() {
        //Have to get possible moves in particular state
        //moves = controller.getMoves(currentstate.plansza)
        //Then create two new states with randomly selected moves

        val expand = State(0, 0, currentState /*,move*/) //times few

        //Next add new states to actual leaf of tree
        currentState.addChild(expand)

        //Next AI have to run simulation of every new state
        //Next we go to best of new states
        currentState = expand
    }

    fun simulate() {}
    private fun backProp() {}
}