package GameAI.MCTS;

import javax.naming.OperationNotSupportedException;

public class Tree extends MCTSTree {
    public Tree() {
        super(new State(0, 0, null));
    }

    @Override
    public void select() throws OperationNotSupportedException{
        if(currentState.getChildren().size() != 0) {
            currentState = currentState.getChildren().get(0);
        }
        else throw new OperationNotSupportedException("Cannot select next move. Expand tree for new moves.");
    }

    @Override
    public void expand(){
        //Have to get possible moves in particular state
        //moves = controller.getMoves(currentstate.plansza)
        //Then create few new states with new moves
        State expand = new State(0,0, currentState/*,move*/); //times few

        //Next add new states to actual leaf of tree
        currentState.addChild(expand);

        //Next AI have to run simulation of every new state

        //Next we go to best of new states
        currentState = expand;
    }

    private void simulate(){

    }

    private void backProp(){

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
