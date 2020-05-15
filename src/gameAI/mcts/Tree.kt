package gameAI.mcts

import gameAI.Simulator
import javafx.scene.shape.Circle
import sample.Controller
import sample.Pole
import java.util.*
import kotlin.concurrent.thread

internal class Tree : MCTSTree(State(Config.identifier++,0, 0, null, ArrayList<Pole>(), null)) {
    init{
        //there are only 3 posibilities for first move, so

//        root.children.add(State(Config.identifier++,0,0,root, Board()))
//        root.children.add(State(Config.identifier++,0,0,root, Board()))
//        root.children.add(State(Config.identifier++,0,0,root, Board()))
    }

    override fun select() : Pair<Circle, Circle>{
        println("select")
        val scores = HashMap<State,Double>()
        for (child in currentState.children) {
            scores[child] = calculateUCB1(child)
        }
        currentState = scores.maxBy { it.value }?.key ?: scores.keys.first()
        if(!currentWasVisited()){
            simulate()
        }
        return currentState.move
    }

    override fun select(board: ArrayList<Pole>, move : Pair<Circle, Circle>){
        for (child in currentState.children){
            if (child.board == board) {
                currentState = child
            }
        }
        val newState = State(Config.identifier++, 0,0,currentState,board,Pair(move.first, move.second))
        currentState.children.add(newState)
        currentState = newState
    }

    override fun expand(): Pair<Circle, Circle>{
        println("expand")
        //Have to get possible moves in particular state
        //moves = controller.getMoves(currentstate.plansza)
        //Then create two new states with randomly selected moves

        val left = State(Config.identifier++, 0, 0, currentState, ArrayList<Pole>(),generateMove(Controller.pola))
        val right = State(Config.identifier++, 0, 0, currentState, ArrayList<Pole>(),generateMove(Controller.pola))

        //Next add new states to actual leaf of tree
        currentState.children.add(left)
        currentState.children.add(right)

        //Next we go to first of new states
        currentState = left

        //Next we do a simulation for this state
        simulate()
        
        return currentState.move
    }

    private fun simulate() : Pair<Circle, Circle>{
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
        return currentState.move
    }
}