package gameAI

import gameAI.mcts.Config
import gameAI.mcts.State
import gameAI.mcts.Tree
import java.io.*
import java.security.MessageDigest
import java.util.*
import kotlin.math.ln
import kotlin.math.sqrt

abstract class ArtificialIntelligence(internal var monteCarlo: Tree) {

    internal var stateHistory: MutableList<String> = mutableListOf()
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
        props.store(FileOutputStream("${filename}.properties"), "")
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    internal fun loadTree(filename: String): Tree? {
        try {
            val props = Properties()
            props.load(FileInputStream("${filename}.properties"))
            Config.identifier = (props["CurrentIdentifier"] as String).toInt()
        } catch (e: FileNotFoundException) {
            println("Config file not found, loading defaults")
            Config.identifier = -1
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
        monteCarlo.currentState = monteCarlo.root
        println(monteCarlo)
    }

    fun save() {
        saveTree(filename)
    }

    fun update(boardHashCode: String) {
        if (monteCarlo.currentState.boardHashCode != boardHashCode)
            monteCarlo.currentState.boardHashCode = boardHashCode
    }

    fun updateState(gameWon: Boolean){
        monteCarlo.currentState.updateState(gameWon)
    }

    abstract fun move(startFields: Array<Int>): Pair<Int, Int>
    abstract fun setEnemyMove(boardHashCode: String, move: Pair<Int, Int>)
}