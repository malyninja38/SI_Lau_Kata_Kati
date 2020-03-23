package GameAI.MCTS;

class DummyTree extends MCTSTree{

    DummyTree(State root) {
        super(root);
    }

    @Override
    public boolean hasNextSelection() {
        return true;
    }

    @Override
    void select() {
        //get possible moves, and randomly choose one
        State random = new State(0,0, currentState/*,move*/);
        currentState.addChild(random);
        currentState = random;
    }

    @Override
    void expand() {

    }
}
