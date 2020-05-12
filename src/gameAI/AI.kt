package gameAI

import gameAI.mcts.Config
import gameAI.mcts.Tree
import sample.Controller
import sample.Plansza
import sample.Pole
import java.io.*
import java.util.*

class AI {
    private var monteCarlo: Tree
    private var stateHistory: MutableList<Plansza> = mutableListOf()
    private val controller : Controller

    constructor(controller: Controller) {
        monteCarlo = Tree()
        stateHistory.add(monteCarlo.currentState.board)
        this.controller = controller
    }

    constructor(controller: Controller, filename: String) {
        monteCarlo = when(val temp = loadTree(filename)){
            null -> Tree()
            else -> {
                temp
            }
        }
        monteCarlo.currentState = monteCarlo.root
        stateHistory.add(monteCarlo.currentState.board)
        this.controller = controller
    }

    //Ma zwracać źródłowe i docelowe Circle, albo pole ruchu.
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

    //TODO:Update method in order to set new state of board to 'currentState'

    fun move(move: Board){
        monteCarlo.select(move)
    }

    private fun saveTree(filename: String) {
        val props = Properties()
        props.setProperty("CurrentIdentifier", Config.identifier.toString())
        props.store(FileOutputStream("tree.properties"),"")
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    private fun loadTree(filename: String):Tree? {
        try{
            val props = Properties()
            props.load(FileInputStream("tree.properties"))
            Config.identifier = (props["CurrentIdentifier"] as String).toInt()
        }
        catch(e: FileNotFoundException){
            println("Config file not found, loading defaults")
        }
        catch (e:Throwable){
            println(e.localizedMessage)
        }
        try {
            ObjectInputStream(FileInputStream(File(filename))).use {
                return it.readObject() as Tree
            }
        }
        catch (e: FileNotFoundException) {
            println("No saved tree found with name '$filename'")
        }
        catch (e:Throwable){
            println(e.localizedMessage)
        }
        Config.identifier = 0
        return null
    }

    override fun toString(): String {
        return "Tree: $monteCarlo"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val controller = Controller()
            val gameAI = AI(controller, "tree.bin")
//            val gameAI = AI()
            println(gameAI)
            controller.PvPClick()
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
                    "s" -> {
                        gameAI.saveTree("tree.bin")
                        println("Tree saved")
                    }
                    else -> continue@loop
                }
            }

        }
    }
}