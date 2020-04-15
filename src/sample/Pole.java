package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Pole {

    int numer;
    boolean czyWolne;                   // True - wolne, False - zajęte
    int[] sasiedzi = new int[6];         // sasiedzi
    int[] bicia = new int[6];           //Pola na ktore moze sie przesunac pionek podczas bicia
    Pionek pionek;
    public javafx.scene.shape.Circle field;


    public Pole(int numer, boolean czyWolne, Pionek pionek, Circle field) {
        this.numer = numer;
        this.czyWolne = czyWolne;
        this.pionek = pionek;
        this.field = field;

        if(numer == 1) {sasiedzi[0] = 2; sasiedzi[1] = 4;
                        bicia[0] = 3; bicia[1] = 7;}
        if(numer == 2) {sasiedzi[0] = 1; sasiedzi[1] = 2; sasiedzi[2] = 5;
                        bicia[1] = 8;}
        if(numer == 3) {sasiedzi[0] = 2; sasiedzi[1] = 6;
                        bicia[0] = 1; bicia[1] = 9;}
        if(numer == 4) {sasiedzi[0] = 1; sasiedzi[1] = 5; sasiedzi[2] = 7;
                        bicia[1] = 6; bicia[2] = 10;}
        if(numer == 5) {sasiedzi[0] = 2; sasiedzi[1] = 4; sasiedzi[2] = 6; sasiedzi[3] = 8;
                        bicia[3] = 10;}
        if(numer == 6) {sasiedzi[0] = 3; sasiedzi[1] = 5; sasiedzi[2] = 9;
                        bicia[1] = 4; bicia[2] = 10;}
        if(numer == 7) {sasiedzi[0] = 4; sasiedzi[1] = 8; sasiedzi[2] = 10;
                        bicia[0] = 1; bicia[1] = 9; bicia[2] = 13;}
        if(numer == 8) {sasiedzi[0] = 5; sasiedzi[1] = 7; sasiedzi[2] = 9; sasiedzi[3] = 10;
                        bicia[0] = 2; bicia[3] = 12;}
        if(numer == 9) {sasiedzi[0] = 6; sasiedzi[1] = 8; sasiedzi[2] = 10;
                        bicia[0] = 3; bicia[1] = 7; bicia[2] = 11;}
        if(numer == 10) {sasiedzi[0] = 7; sasiedzi[1] = 8; sasiedzi[2] = 9; sasiedzi[3] = 11; sasiedzi[4] = 12; sasiedzi[5] = 13;
                        bicia[0] = 4; bicia[1] = 5; bicia[2] = 6; bicia[3] = 14; bicia[4] = 15; bicia[5] = 16;}
        if(numer == 11) {sasiedzi[0] = 10; sasiedzi[1] = 12; sasiedzi[2] = 14;
                        bicia[0] = 9; bicia[1] = 13; bicia[2] = 17;}
        if(numer == 12) {sasiedzi[0] = 10; sasiedzi[1] = 11; sasiedzi[2] = 13; sasiedzi[3] = 15;
                        bicia[0] = 8; bicia[3] = 18;}
        if(numer == 13) {sasiedzi[0] = 10; sasiedzi[1] = 12; sasiedzi[2] = 16;
                        bicia[0] = 7; bicia[1] = 11; bicia[2] = 19;}
        if(numer == 14) {sasiedzi[0] = 11; sasiedzi[1] = 15; sasiedzi[2] = 17;
                        bicia[0] = 10; bicia[1] = 16;}
        if(numer == 15) {sasiedzi[0] = 12; sasiedzi[1] = 14; sasiedzi[2] = 16; sasiedzi[3] = 18;
                        bicia[0] = 10;}
        if(numer == 16) {sasiedzi[0] = 13; sasiedzi[1] = 15; sasiedzi[2] = 19;
                        bicia[0] = 10; bicia[1] = 14;}
        if(numer == 17) {sasiedzi[0] = 14; sasiedzi[1] = 18;
                        bicia[0] = 11; bicia[1] = 19;}
        if(numer == 18) {sasiedzi[0] = 15; sasiedzi[1] = 17; sasiedzi[2] = 19;
                        bicia[0] = 12;}
        if(numer == 19) {sasiedzi[0] = 16; sasiedzi[1] = 18;
                        bicia[0] = 13; bicia[1] = 17;}
    }

    public Boolean przeszukajSasiadow(int wartosc){
        for(int i = 0; i < sasiedzi.length; i++){
            if(sasiedzi[i] == wartosc) return true;
        }
        return false;
    }

    public static int wspolnySasiad(Pole pole1, Pole pole2){
        for(int i = 0; i < pole1.sasiedzi.length; i++)
            for(int j = 0; j < pole2.sasiedzi.length; j++)
                if (pole1.sasiedzi[i] == pole2.sasiedzi[j]) return pole1.sasiedzi[i];

        return 0;
    }

    public Boolean czyMoznaBic(int wartosc){
        for(int i = 0; i < bicia.length; i++){
            if(bicia[i] == wartosc) return true;
        }
        return false;
    }
}
