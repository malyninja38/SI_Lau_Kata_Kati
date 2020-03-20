package GameAI.MCTS;

import javax.naming.OperationNotSupportedException;

abstract class MCTSTree {

    State root;
    State currentState;

    MCTSTree(State root){
        this.root = root;
        currentState = root;
    }

    public boolean hasNextSelection(){
        return currentState.getChildren().size() > 0;
    }
    abstract void select() throws OperationNotSupportedException;
    abstract void expand();
}
