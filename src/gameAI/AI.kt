package gameAI

import gameAI.mcts.Config
import gameAI.mcts.Tree
import javafx.scene.shape.Circle
import sample.Controller
import sample.Pole
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class AI : Runnable{
    private var monteCarlo: Tree
    private var stateHistory: MutableList<ArrayList<Pole>> = mutableListOf()
    private val controller : Controller


    constructor(controller: Controller, playerNumber : Int) {
        monteCarlo = Tree()
        stateHistory.add(monteCarlo.currentState.board)
        this.controller = controller
        AI.playerNumber = playerNumber
    }

    constructor(controller: Controller, playerNumber: Int, filename: String) {
        monteCarlo = when(val temp = loadTree(filename)){
            null -> Tree()
            else -> {
                temp
            }
        }
        monteCarlo.currentState = monteCarlo.root
        stateHistory.add(monteCarlo.currentState.board)
        this.controller = controller
        AI.playerNumber = playerNumber
    }

    //Ma zwracać źródłowe i docelowe Circle, albo pole ruchu.
    fun move(): Pair<Circle, Circle>{
        print("Move: ")
        return when {
            monteCarlo.hasNextSelection() -> {
                monteCarlo.select()
            }
            else -> {
                monteCarlo.expand()
            }
        }
    }

    fun update(board: ArrayList<Pole>){
        val boardClone = ArrayList<Pole>()
        for(field in board){
            boardClone.add(field)
        }
        monteCarlo.currentState.board = boardClone
    }

    fun move(board: ArrayList<Pole>, move: Pair<Circle,Circle>){
        monteCarlo.select(board, move)
    }

    private fun saveTree(filename: String) {
        val props = Properties()
        props.setProperty("CurrentIdentifier", Config.identifier.toString())
        props.store(FileOutputStream("tree.properties"),"")
        ObjectOutputStream(FileOutputStream(File(filename))).use {
            it.writeObject(monteCarlo)
        }
    }

    override fun run(){
        while(Controller.koniecGry){
            if(Controller.obecny_gracz == playerNumber){
                move()
            }
            Thread.sleep(100)
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
        internal var playerNumber : Int = 0
        @JvmStatic
        fun main(args: Array<String>) {
            val controller = Controller()
            val gameAI = AI(controller,2, "tree.bin")
//            val gameAI = AI(controller,2)
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