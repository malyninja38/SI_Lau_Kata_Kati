package GameAI.MCTS;

import java.util.ArrayList;
import sample.*;

class State {
    private int player;
    private int wins;
    private int plays;
    private State parent;
    private ArrayList<State> children;

    //Ruch move

    State(int player, int wins, int plays, State parent/*,Ruch move*/){
        this.player = player;
        this.wins = wins;
        this.plays = plays;
        children = new ArrayList<>();
        this.parent = parent;
        //this.move = move;
    }

    int getPlayer() {
        return player;
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

}
