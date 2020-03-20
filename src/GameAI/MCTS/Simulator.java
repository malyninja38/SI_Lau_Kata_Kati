package GameAI.MCTS;

public class Simulator implements Runnable{

    private DummyTree simTree;

    public Simulator(State root){
        simTree = new DummyTree(root);
    }

    private void move(){
        simTree.select();
    }

    public void run(){

    }
}
