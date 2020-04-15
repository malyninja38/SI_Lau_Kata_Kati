package sample;

public class Plansza {

    protected int[][] pola = new int[7][3];

    public Plansza() {
        int licznik = 1;
        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if(!((i == 3 && j == 0) || (i == 3 && j == 2))){                // Bo w środkowym rzędzie jest tylko jedno pole
                    pola[i][j] = licznik;
                }
            }
        }
    }


}
