package GameAI;

import GameAI.MCTS.*;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.nio.file.FileSystem;

public class AI {
    private Tree monteCarlo;
    public AI(){
        monteCarlo = new Tree();
    }
    public AI(String filename) throws FileNotFoundException{
        try {
            Tree temp = loadTree(filename);
            if (temp != null){
                monteCarlo = temp;
            }
            else throw new FileNotFoundException();
        }
        catch (NullPointerException ignored){
            monteCarlo = new Tree();
        }
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

        AI gameAI;
        try {
            gameAI = new AI("tree.bin");
        }
        catch (FileNotFoundException ignored){
             gameAI = new AI();
        }
//        gameAI.move();
//        gameAI.move();
//        gameAI.saveTree("tree.bin");
        System.out.println(gameAI.monteCarlo.toString());
    }

    private void saveTree(String filename){
        try {
            FileOutputStream fs = new FileOutputStream(new File(filename));
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(monteCarlo);
            os.close();
            fs.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private Tree loadTree(String filename){
        try {
            FileInputStream fs = new FileInputStream(new File(filename));
            ObjectInputStream os = new ObjectInputStream(fs);
            Tree temp = (Tree) os.readObject();
            os.close();
            fs.close();
            if(temp != null){
                return temp;
            }
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(NullPointerException e){
            System.out.println("Got null while loading.");
        }
        return null;
    }
}
