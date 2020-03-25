package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Pole {

    int numer;
    boolean czyWolne;                   // True - wolne, False - zajęte
    int[] sasiedzi = new int[6];         // sasiedzi
    Pionek pionek;
    public javafx.scene.shape.Circle field;


    public Pole(int numer, boolean czyWolne, Pionek pionek, Circle field) {
        this.numer = numer;
        this.czyWolne = czyWolne;
        this.pionek = pionek;
        this.field = field;

        if(numer == 1) {sasiedzi[0] = 2; sasiedzi[1] = 4;}
        if(numer == 2) {sasiedzi[0] = 1; sasiedzi[1] = 2; sasiedzi[2] = 5;}
        if(numer == 3) {sasiedzi[0] = 2; sasiedzi[1] = 6;}
        if(numer == 4) {sasiedzi[0] = 1; sasiedzi[1] = 5; sasiedzi[2] = 7;}
        if(numer == 5) {sasiedzi[0] = 2; sasiedzi[1] = 4; sasiedzi[2] = 6; sasiedzi[3] = 8;}
        if(numer == 6) {sasiedzi[0] = 3; sasiedzi[1] = 5; sasiedzi[2] = 9;}
        if(numer == 7) {sasiedzi[0] = 4; sasiedzi[1] = 8; sasiedzi[2] = 10;}
        if(numer == 8) {sasiedzi[0] = 5; sasiedzi[1] = 7; sasiedzi[2] = 9; sasiedzi[3] = 10;}
        if(numer == 9) {sasiedzi[0] = 6; sasiedzi[1] = 8; sasiedzi[2] = 10;}
        if(numer == 10) {sasiedzi[0] = 7; sasiedzi[1] = 8; sasiedzi[2] = 9; sasiedzi[3] = 11; sasiedzi[4] = 12; sasiedzi[5] = 13;}
        if(numer == 11) {sasiedzi[0] = 10; sasiedzi[1] = 12; sasiedzi[2] = 14;}
        if(numer == 12) {sasiedzi[0] = 10; sasiedzi[1] = 11; sasiedzi[2] = 13; sasiedzi[3] = 15;}
        if(numer == 13) {sasiedzi[0] = 10; sasiedzi[1] = 12; sasiedzi[2] = 16;}
        if(numer == 14) {sasiedzi[0] = 11; sasiedzi[1] = 15; sasiedzi[2] = 17;}
        if(numer == 15) {sasiedzi[0] = 12; sasiedzi[1] = 14; sasiedzi[2] = 16; sasiedzi[3] = 18;}
        if(numer == 16) {sasiedzi[0] = 13; sasiedzi[1] = 15; sasiedzi[2] = 19;}
        if(numer == 17) {sasiedzi[0] = 14; sasiedzi[1] = 18;}
        if(numer == 18) {sasiedzi[0] = 15; sasiedzi[1] = 17; sasiedzi[2] = 19;}
        if(numer == 19) {sasiedzi[0] = 16; sasiedzi[1] = 18;}
    }

    public Boolean przeszukajSasiadow(int wartosc){
        for(int i = 0; i < sasiedzi.length; i++){
            if(sasiedzi[i] == wartosc) return true;
        }
        return false;
    }

}
