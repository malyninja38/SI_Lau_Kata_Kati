package sample;

public class Pionek {

    int id;
    int gracz;               // 1 - gracz 1, 2 - gracz 2
    boolean stan;            // zbity - False, aktywny - True
    int pole;                // Numer pola, na którym stoi

    public Pionek(int id, int gracz, boolean stan, int pole) {
        this.id = id;
        this.gracz = gracz;
        this.stan = stan;
        this.pole = pole;
    }
}
