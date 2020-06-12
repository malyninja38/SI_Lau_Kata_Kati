package sample;


import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class Pole {

    public int numer;
    public boolean czyWolne;                    // True - wolne, False - zajęte
    public Pionek pionek;
    public Circle field;
    int gracz;


    public Pole(int numer, boolean czyWolne, Pionek pionek, Circle field, int gracz) {
        this.numer = numer;
        this.czyWolne = czyWolne;
        this.pionek = pionek;
        this.field = field;
        this.gracz = gracz;
    }

}
