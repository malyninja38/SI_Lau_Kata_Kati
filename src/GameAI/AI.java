package GameAI;

import GameAI.MCTS.*;

import javax.naming.OperationNotSupportedException;

public class AI {
    private Tree monteCarlo;
    public AI(int player){
        monteCarlo = new Tree(player);
    }

    public void move(){
        if(monteCarlo.hasNextSelection()){
            try{
                monteCarlo.select();
            }
            catch (OperationNotSupportedException e){
                monteCarlo.expand();
            }
        }
        else monteCarlo.expand();

        
    }

    public static void main(String[] args){
        AI gameAI = new AI(0);
    }
}
