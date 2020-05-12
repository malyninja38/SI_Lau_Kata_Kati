package gameAI.mcts

import gameAI.Board
import sample.Plansza
import java.util.*
import kotlin.concurrent.thread
import kotlin.concurrent.timer

internal class Tree : MCTSTree(State(Config.identifier++,0, 0, null, Board(), Board())) {
    init{
        //there are only 3 posibilities for first move, so
        root.children.add(State(Config.identifier++,0,0,root, Board(),Board()))
        root.children.add(State(Config.identifier++,0,0,root, Board(),Board()))
        root.children.add(State(Config.identifier++,0,0,root, Board(),Board()))
    }

    override fun select() {
        println("select")
        val scores = HashMap<State,Double>()
        for (child in currentState.children) {
            scores[child] = calculateUCB1(child)
        }
        currentState = scores.maxBy { it.value }?.key ?: scores.keys.first();
    }

    override fun select(move: Board) {
        for (child in currentState.children){
            if (child.board == move) {
                currentState = child
                return
            }
        }
        val newState = State(Config.identifier++, 0,0,currentState,move,currentState.board.differences(move))
        currentState.children.add(newState)
        currentState = newState
    }

    override fun expand() {
        println("expand")
        //Have to get possible moves in particular state
        //moves = controller.getMoves(currentstate.plansza)
        //Then create two new states with randomly selected moves

        val left = State(Config.identifier++, 0, 0, currentState, Board(), Board())
        val right = State(Config.identifier++, 0, 0, currentState, Board(), Board())

        //Next add new states to actual leaf of tree
        currentState.children.add(left)
        currentState.children.add(right)

        //Next we go to first of new states
        currentState = left

    }

    fun simulate() {
        println("simulate")
        val sim = Simulator(currentState.copy())
        thread{
            Thread.sleep(Config.ComputeTime.toLong())
            sim.doSimulate = false
            return@thread
        }
        thread{
            sim.run()
        }
        println("Simulation ended with result: ${if(sim.gameWon) "won" else "lost"}")
        println(sim.gameWon)
        currentState.updateState(sim.gameWon)
    }
}