package gameAI

import sample.Plansza
import java.io.Serializable
import kotlin.random.Random

class Board() : Plansza(), Serializable {

    val endGame:Boolean
        get() = Random.nextBoolean()

    constructor(existing:Array<IntArray>) : this() {
        pola = existing
    }

    fun differences(next:Board):Board{
        var differences = pola.copyOf()
        for(i in next.pola.indices){
            for (j in next.pola[i].indices){
                differences[i][j] = next.pola[i][j] - pola[i][j]
            }
        }
        return Board(differences)
    }
    val possibleMoves: List<Board>
        get() = listOf(Board())
}