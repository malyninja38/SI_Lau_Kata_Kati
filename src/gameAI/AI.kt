package gameAI

import gameAI.mcts.Tree
import sample.Plansza
import java.io.*

class AI {
    private var monteCarlo: Tree
    private var stateHistory: MutableList<Plansza> = mutableListOf()

    constructor() {
        monteCarlo = Tree()
        stateHistory.add(monteCarlo.currentState.board)
    }

    constructor(filename: String) : this() {
        when (val temp = loadTree(filename)){
            null -> AI()
            else -> {
                monteCarlo = temp
            }
        }
        monteCarlo.currentState = monteCarlo.root
        stateHistory.add(monteCarlo.currentState.board)
    }

    fun move() {
        print("Move: ")
        when {
            monteCarlo.hasNextSelection() -> {
                monteCarlo.select()
            }
            monteCarlo.currentWasVisited() -> {
                monteCarlo.expand()
            }
            else -> {
                monteCarlo.simulate()
            }
        }
    }

    fun move(move: Board){
        monteCarlo.select(move)
    }

    private fun saveTree(filename: String) {
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    private fun loadTree(filename: String):Tree? {
        try {
            ObjectInputStream(FileInputStream(File(filename))).use {
                return it.readObject() as Tree
            }
        }
        catch (e: Throwable) {
            println(e.message)
        }
        return null
    }

    override fun toString(): String {
        return "Tree: $monteCarlo"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val gameAI = AI("tree.bin")
//            val gameAI = AI()
            println(gameAI)
            loop@ while(true){
                print("> ")
                when (readLine()) {
                    "m" -> {
                        gameAI.move()
                        println(gameAI)
                    }
                    "q" -> {
                        break@loop
                    }
                    else -> continue@loop
                }
            }
            gameAI.saveTree("tree.bin")
        }
    }
}