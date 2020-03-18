package sample;

public class Plansza {

    int[][] pola = new int[3][7];

    public Plansza() {
        int licznik = 1;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if(!((i == 0 && j == 3) || (i == 2 && j == 3))){                // Bo w środkowym rzędzie jets tylko jedno pole
                    pola[i][j] = licznik;
                }
            }
        }
    }


}
