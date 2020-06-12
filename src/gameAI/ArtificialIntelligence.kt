package gameAI

import gameAI.mcts.Config
import gameAI.mcts.State
import gameAI.mcts.Tree
import java.io.*
import java.util.*
import kotlin.math.ln
import kotlin.math.sqrt

abstract class ArtificialIntelligence(internal var monteCarlo: Tree) {

    internal var stateHistory: MutableList<Int> = mutableListOf()
    var filename = "tree.bin"

    internal fun calculateUCB1(s: State): Double {
        val vi = s.wins.toDouble() / s.plays.toDouble()
        val frac = ln(s.parent!!.plays.toDouble()) / s.plays.toDouble()
        val sqrt = sqrt(frac)
        return vi + sqrt
    }

    internal fun saveTree(filename: String) {
        val props = Properties()
        props.setProperty("CurrentIdentifier", Config.identifier.toString())
        props.store(FileOutputStream("tree.properties"), "")
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    internal fun loadTree(filename: String): Tree? {
        try {
            val props = Properties()
            props.load(FileInputStream("tree.properties"))
            Config.identifier = (props["CurrentIdentifier"] as String).toInt()
        } catch (e: FileNotFoundException) {
            println("Config file not found, loading defaults")
        } catch (e: Throwable) {
            println(e.localizedMessage)
        }
        try {
            ObjectInputStream(FileInputStream(File(filename))).use {
                return it.readObject() as Tree
            }
        } catch (e: FileNotFoundException) {
            println("No saved tree found with name '$filename'")
        } catch (e: Throwable) {
            println(e.localizedMessage)
        }
        Config.identifier = 0
        return null
    }

    fun load() {
        monteCarlo = when (val newTree = loadTree(filename)) {
            null -> monteCarlo
            else -> newTree
        }
    }

    fun save() {
        saveTree(filename)
    }

    fun update(boardMatrix: Array<IntArray>) {
        if (monteCarlo.currentState.boardHashCode != boardMatrix.hashCode())
            monteCarlo.currentState.boardHashCode = boardMatrix.hashCode()
    }

    abstract fun move(startFields: Array<Int>): Pair<Int, Int>
    abstract fun setEnemyMove(boardHashCode: Int, move: Pair<Int, Int>)
}