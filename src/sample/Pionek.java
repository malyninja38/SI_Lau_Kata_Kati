package sample;

public class Pionek {

    int gracz;               // 1 - gracz 1, 2 - gracz 2
    Boolean stan;            // zbity - False, aktywny - True
    int pole;                // Numer pola, na którym stoi

    public Pionek(int gracz, Boolean stan, int pole) {
        this.gracz = gracz;
        this.stan = stan;
        this.pole = pole;
    }
}
