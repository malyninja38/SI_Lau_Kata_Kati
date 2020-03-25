package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


/*
circle.setFill(Color.web("#535450"));
#88fbc3   -> #6fc397
#ffd167   -> #c3a467
 */


public class Controller {
    

    @FXML Button PvPbutton; @FXML Button PvAIbutton; @FXML Button AIvAIbutton;

    @FXML ImageView plansza;

    @FXML Circle field1; @FXML Circle field2; @FXML Circle field3; @FXML Circle field4; @FXML Circle field5; @FXML Circle field6;
    @FXML Circle field7; @FXML Circle field8; @FXML Circle field9; @FXML Circle field10; @FXML Circle field11; @FXML Circle field12;
    @FXML Circle field13; @FXML Circle field14; @FXML Circle field15; @FXML Circle field16; @FXML Circle field17; @FXML Circle field18; @FXML Circle field19;

    @FXML Button player1; @FXML Button player2;

    Boolean gracz1 = false;
    Boolean gracz2 = false;
    Boolean koniecGry = false;

    ArrayList<Pole> pola = new ArrayList<Pole>();

    public void ustawKoloryPoczatkowe(){

        field1.setFill(Color.web("#88fbc3")); field2.setFill(Color.web("#88fbc3")); field3.setFill(Color.web("#88fbc3"));
        field4.setFill(Color.web("#88fbc3")); field5.setFill(Color.web("#88fbc3")); field6.setFill(Color.web("#88fbc3"));
        field7.setFill(Color.web("#88fbc3")); field8.setFill(Color.web("#88fbc3")); field9.setFill(Color.web("#88fbc3"));

        field11.setFill(Color.web("#ffd167")); field12.setFill(Color.web("#ffd167")); field13.setFill(Color.web("#ffd167"));
        field14.setFill(Color.web("#ffd167")); field15.setFill(Color.web("#ffd167")); field16.setFill(Color.web("#ffd167"));
        field17.setFill(Color.web("#ffd167")); field18.setFill(Color.web("#ffd167")); field19.setFill(Color.web("#ffd167"));
    }

    public void PvPClick(){
        ustawKoloryPoczatkowe();
        gra();
    };
    public void PvAIClick(){};
    public void AIvAIClick(){};


    public void fieldClick(MouseEvent event){

        Circle circle = (Circle) event.getSource();            // zwraca kliknięty field
        System.out.println(circle);

        for(int i = 0; i < 19; i++){

            System.out.println(pola.get(i).field.getId());

            if (pola.get(i).field.getId().equals(circle.getId())) {
                if (!Pole.czyWolne) {

                    Pionek x = Pole.pionek;
                    System.out.println(Pole.numer);

                    if (x.gracz == 1) {
                        circle.setFill(Color.web("#6fc397"));
                    } else {
                        circle.setFill(Color.web("#c3a467"));
                    }
                }
            }
        }
    }

    void ruchGracza1(){

        gracz1 = true;

        player1.setStyle("-fx-background-color: #32CD32; ");
        player2.setStyle("-fx-background-color: #FFFFFF; ");

    }

    void ruchGracza2(){

        player1.setStyle("-fx-background-color: #FFFFFF; ");
        player2.setStyle("-fx-background-color: #32CD32; ");

    }

    void gra(){

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

        Pole pole1 = new Pole(1, false, pionek1, field1); pola.add(pole1);
        Pole pole2 = new Pole(2, false, pionek2, field2); pola.add(pole2);
        Pole pole3 = new Pole(3, false, pionek3, field3); pola.add(pole3);
        Pole pole4 = new Pole(4, false, pionek4, field4); pola.add(pole4);
        Pole pole5 = new Pole(5, false, pionek5, field5); pola.add(pole5);
        Pole pole6 = new Pole(6, false, pionek6, field6); pola.add(pole6);
        Pole pole7 = new Pole(7, false, pionek7, field7); pola.add(pole7);
        Pole pole8 = new Pole(8, false, pionek8, field8); pola.add(pole8);
        Pole pole9 = new Pole(9, false, pionek9, field9); pola.add(pole9);
        Pole pole10 = new Pole(10, true, null, field10); pola.add(pole10);
        Pole pole11 = new Pole(11, false, pionek10, field11); pola.add(pole11);
        Pole pole12 = new Pole(12, false, pionek11, field12); pola.add(pole12);
        Pole pole13 = new Pole(13, false, pionek12, field13); pola.add(pole13);
        Pole pole14 = new Pole(14, false, pionek13, field14); pola.add(pole14);
        Pole pole15 = new Pole(15, false, pionek14, field15); pola.add(pole15);
        Pole pole16 = new Pole(16, false, pionek15, field16); pola.add(pole16);
        Pole pole17 = new Pole(17, false, pionek16, field17); pola.add(pole17);
        Pole pole18 = new Pole(18, false, pionek17, field18); pola.add(pole18);
        Pole pole19 = new Pole(19, false, pionek18, field19); pola.add(pole19);

        for(int i = 0; i < 19; i ++){
            System.out.println(pole1.field);
        }



        ruchGracza1();

        /*
        while(!koniecGry){
            ruchGracza1();
            ruchGracza2();
        } */


    }



}
