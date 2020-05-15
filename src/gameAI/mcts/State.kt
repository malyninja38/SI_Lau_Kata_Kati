package gameAI.mcts

import javafx.scene.shape.Circle
import sample.Pole
import java.io.Serializable
import java.util.ArrayList

internal class State(
        private val _id: Int,
        private var _wins: Int,
        private var _plays: Int,
        private val _parent: State?,
        private var _board : ArrayList<Pole>,
        private val _moveCircles : Pair<Circle, Circle>?
) : Serializable {
    private val _children = mutableListOf<State>()

    internal val id: Int
        get() = _id

    internal val wins: Int
        get() = _wins

    internal val plays: Int
        get() =  _plays

    internal val children: MutableList<State>
        get() = _children

    internal val parent: State?
        get() = _parent

    internal var board : ArrayList<Pole>
        get() = _board
        set(newBoard) {
            _board = newBoard
        }

    internal val move : Pair<Circle, Circle>
        get() = _moveCircles!!

    internal fun updateState(gameWon: Boolean) {
        _plays++
        if (gameWon) _wins++
        _parent?.updateState(gameWon)
    }

    fun copy(
            _id : Int = this._id,
            _wins: Int = this._wins,
            _plays: Int = this._plays,
            _parent: State? = this._parent,
            _board : ArrayList<Pole> = this._board,
            _moveCircles: Pair<Circle, Circle>? = this._moveCircles
    ): State = State(_id, _wins,_plays,_parent,_board, _moveCircles)

    fun print(tabs:String): String {
        val sb = StringBuilder()
        sb.append(tabs).append("State{")
        sb.append("id=").append(_id).append(" ")
        sb.append("wins=").append(_wins).append(" ")
        sb.append("plays=").append(_plays).append(" ")
        sb.append("children=").append("[")
        for (child in _children) {
            sb.append("\n").append(child.print(tabs+"\t"))
        }
        if(_children.size > 0){
            sb.append("\n").append(tabs)
        }
        sb.append("]")
        sb.append("} ")
        return sb.toString()
    }
}