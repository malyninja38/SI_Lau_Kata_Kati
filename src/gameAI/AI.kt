package gameAI

import gameAI.mcts.Tree
import java.io.*

class AI {
    private lateinit var monteCarlo: Tree

    constructor() {
        monteCarlo = Tree()
    }

    constructor(filename: String) : this() {
        if (!loadTree(filename)) {
            println("Could not load file")
            AI()
        }
    }

    fun move() {
        if (monteCarlo.hasNextSelection()) {
            monteCarlo.select()
        } else if (!monteCarlo.currentWasVisited()) {
            monteCarlo.expand()
        } else {
            monteCarlo.simulate()
        }
    }

    private fun saveTree(filename: String) {
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    private fun loadTree(filename: String):Boolean {
        ObjectInputStream(FileInputStream(File(filename))).use {
            return try{
                val loaded = it.readObject() as Tree
                monteCarlo = loaded
                true
            } catch(ignored: Throwable){
                false
            }
        }
    }

    override fun toString(): String {
        return "Tree: $monteCarlo"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val gameAI = AI("tree.bin")
//            gameAI.move();
//            gameAI.move();
//            gameAI.saveTree("tree.bin");
            println(gameAI.monteCarlo)
        }
    }
}