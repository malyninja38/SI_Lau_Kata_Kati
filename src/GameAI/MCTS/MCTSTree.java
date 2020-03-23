package GameAI.MCTS;

import javax.naming.OperationNotSupportedException;
import java.io.Serializable;

abstract class MCTSTree implements Serializable {

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

    @Override
    public String toString() {
        return "MCTSTree{\n" +
                "currentState=" + currentState +
                "\nroot=" + root +
                "\n}";
    }
}
