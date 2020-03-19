package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


public class Controller {
    

    @FXML Button PvPbutton; @FXML Button PvAIbutton; @FXML Button AIvAIbutton;

    @FXML ImageView plansza;

    @FXML Circle field1; @FXML Circle field2; @FXML Circle field3; @FXML Circle field4; @FXML Circle field5; @FXML Circle field6;
    @FXML Circle field7; @FXML Circle field8; @FXML Circle field9; @FXML Circle field10; @FXML Circle field11; @FXML Circle field12;
    @FXML Circle field13; @FXML Circle field14; @FXML Circle field15; @FXML Circle field16; @FXML Circle field17; @FXML Circle field18; @FXML Circle field19;

    @FXML Button player1; @FXML Button player2;


    public void PvPClick(){};
    public void PvAIClick(){};
    public void AIvAIClick(){};

    public void fieldClick(){};

    Boolean gracz1 = false;
    Boolean gracz2 = false;
    Boolean koniecGry = false;


    void stworzPionki(){

        Pionek pionek1 = new Pionek(1, true, 1);
        Pionek pionek2 = new Pionek(1, true, 2);
        Pionek pionek3 = new Pionek(1, true, 3);
        Pionek pionek4 = new Pionek(1, true, 4);
        Pionek pionek5 = new Pionek(1, true, 5);
        Pionek pionek6 = new Pionek(1, true, 6);
        Pionek pionek7 = new Pionek(1, true, 7);
        Pionek pionek8 = new Pionek(1, true, 8);
        Pionek pionek9 = new Pionek(1, true, 9);

        Pionek pionek10 = new Pionek(2, true, 11);
        Pionek pionek11 = new Pionek(2, true, 12);
        Pionek pionek12 = new Pionek(2, true, 13);
        Pionek pionek13 = new Pionek(2, true, 14);
        Pionek pionek14 = new Pionek(2, true, 15);
        Pionek pionek15 = new Pionek(2, true, 16);
        Pionek pionek16 = new Pionek(2, true, 17);
        Pionek pionek17 = new Pionek(2, true, 18);
        Pionek pionek18 = new Pionek(2, true, 19);

    }

    void stworzPola(){

        Pole pole1 = new Pole(1, false);
        Pole pole2 = new Pole(2, false);
        Pole pole3 = new Pole(3, false);
        Pole pole4 = new Pole(4, false);
        Pole pole5 = new Pole(5, false);
        Pole pole6 = new Pole(6, false);
        Pole pole7 = new Pole(7, false);
        Pole pole8 = new Pole(8, false);
        Pole pole9 = new Pole(9, false);
        Pole pole10 = new Pole(10, true);
        Pole pole11 = new Pole(11, false);
        Pole pole12 = new Pole(12, false);
        Pole pole13 = new Pole(13, false);
        Pole pole14 = new Pole(14, false);
        Pole pole15 = new Pole(15, false);
        Pole pole16 = new Pole(16, false);
        Pole pole17 = new Pole(17, false);
        Pole pole18 = new Pole(18, false);
        Pole pole19 = new Pole(19, false);

    }


    void ruchGracza1(){

        player1.setStyle("-fx-background-color: #32CD32; ");
        player2.setStyle("-fx-background-color: #FFFFFF; ");

    }

    void ruchGracza2(){

        player1.setStyle("-fx-background-color: #FFFFFF; ");
        player2.setStyle("-fx-background-color: #32CD32; ");

    }

    void gra(){

        stworzPionki();
        stworzPola();

        while(!koniecGry){
            ruchGracza1();
            ruchGracza2();
        }


    }



}
