package GameAI.MCTS;

import java.io.Serializable;
import java.util.ArrayList;
import sample.*;

class State implements Serializable {
    private int wins;
    private int plays;
    private State parent;
    private ArrayList<State> children;

    //Ruch move

    State(int wins, int plays, State parent/*,Ruch move*/){
        this.wins = wins;
        this.plays = plays;
        children = new ArrayList<>();
        this.parent = parent;
        //this.move = move;
    }

    int getWins() {
        return wins;
    }

    int getPlays() {
        return plays;
    }

    void addChild(State state){
        children.add(state);
    }

    ArrayList<State> getChildren() {
        return children;
    }

    void updateState(boolean gameWon){
        plays++;
        if(gameWon) wins++;
        if(parent != null) parent.updateState(gameWon);
    }

    /*Ruch getMove(){
        return move;
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State{ ");
        sb.append("wins=").append(wins).append(" ");
        sb.append("plays=").append(plays).append(" ");
        sb.append("children=").append("[");
        for(State child : children){
            sb.append(child);
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
