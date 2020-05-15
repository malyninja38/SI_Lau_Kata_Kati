package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;


/*
circle.setFill(Color.web("#535450"));
#88fbc3   -> #6fc397
#ffd167   -> #c3a467
 */


public class Controller {


    @FXML
    Button PvPbutton;
    @FXML
    Button PvAIbutton;
    @FXML
    Button AIvAIbutton;

    @FXML
    ImageView plansza;

    @FXML
    ArrayList<Circle> fields;         //tablica fieldów (Circle)


    @FXML
    Button player1;
    @FXML
    Button player2;

    @FXML
    TextField text_field;

    public static int obecny_gracz;
    public static Boolean koniecGry;
    static Boolean obowiazek_bicia;
    int klik = 0;
    public Circle poprzednie;
    public Circle zaznaczone;
    static int pionki_gracza_1;
    static int pionki_gracza_2;
    static int zwyciezca;

    public static ArrayList<Pole> pola = new ArrayList<Pole>();


    public void ustawKoloryPoczatkowe() {

        for (int i = 0; i < 19; i++) {
            if (i < 9) {
                fields.get(i).setFill(Color.web("#88fbc3"));
            } else if (i > 9) {
                fields.get(i).setFill(Color.web("#ffd167"));
            } else fields.get(i).setFill(Color.web("WHITE"));
        }
        /*field1.setFill(Color.web("#88fbc3")); field2.setFill(Color.web("#88fbc3")); field3.setFill(Color.web("#88fbc3"));
        field4.setFill(Color.web("#88fbc3")); field5.setFill(Color.web("#88fbc3")); field6.setFill(Color.web("#88fbc3"));
        field7.setFill(Color.web("#88fbc3")); field8.setFill(Color.web("#88fbc3")); field9.setFill(Color.web("#88fbc3"));
        field10.setFill(Color.web("WHITE"));
        field11.setFill(Color.web("#ffd167")); field12.setFill(Color.web("#ffd167")); field13.setFill(Color.web("#ffd167"));
        field14.setFill(Color.web("#ffd167")); field15.setFill(Color.web("#ffd167")); field16.setFill(Color.web("#ffd167"));
        field17.setFill(Color.web("#ffd167")); field18.setFill(Color.web("#ffd167")); field19.setFill(Color.web("#ffd167"));*/
    }

    public void PvPClick() {
        koniecGry = false;
        klik = 0;
        poprzednie = null;
        zaznaczone = null;
        pola = new ArrayList<>();
        pionki_gracza_1 = 9;
        pionki_gracza_2 = 9;
        zwyciezca = 0;
        text_field.setText("");
        ustawKoloryPoczatkowe();
        gra();
    }

    public void PvAIClick() {
    }

    public void AIvAIClick() {
    }

    private static Pole get_pole(Circle klikniete) {
        for (Pole pole : pola) {
            if (pole.field.getId().equals(klikniete.getId())) {
                return pole;
            }
        }
        return null;
    }

    public  void bicie(Pole pole_zaznaczone, Pole pole_puste, int do_bicia, Pionek x, Circle puste_circle) throws InterruptedException {
        Pole pole_do_bicia = pola.get(do_bicia - 1);
        if (pole_do_bicia.czyWolne || pole_do_bicia.pionek.gracz == x.gracz || !pole_zaznaczone.czyMoznaBic(pole_puste.numer))
            text_field.setText("Nie można wykonać ruchu.");
        else {
            System.out.println("Pole z pionkiem przeciwnika: " + pole_do_bicia.numer);

            Circle bite_pole_plansza = pole_do_bicia.field;
            pole_puste.czyWolne = false;
            pole_puste.pionek = x;
            pole_zaznaczone.pionek = null;
            pole_zaznaczone.czyWolne = true;
            pole_do_bicia.pionek = null;
            pole_do_bicia.czyWolne = true;

            if (x.gracz == 1 && obecny_gracz == 1) {
                zaznaczone.setFill(Color.web("WHITE"));
                puste_circle.setFill(Color.web("#88fbc3"));
                bite_pole_plansza.setFill(Color.web("WHITE"));
                pionki_gracza_2--;
                if (pionki_gracza_2 == 0) {
                    text_field.setText("Koniec gry. Zwyciężył gracz 1.");
                    koniecGry = true;
                    zwyciezca = 1;
                } else {
                    pole_zaznaczone = pole_puste;
                    int kolejny_ruch = Pole.przeciwnicy(pole_zaznaczone);
                    if (kolejny_ruch == -1) {
                        klik = 0;
                        poprzednie = null;
                        zaznaczone = null;
                        text_field.setText("");
                        ruchGracza2();
                    } else if (kolejny_ruch == 50) {
                        poprzednie = null;
                        zaznaczone = puste_circle;
                        puste_circle.setFill(Color.web("#6fc397"));
                        text_field.setText("");
                        ruchGracza1();
                    } else {
                        x = pole_zaznaczone.pionek;
                        pole_puste = pola.get(kolejny_ruch - 1);
                        puste_circle.setFill(Color.web("WHITE"));
                        puste_circle = pole_puste.field;
                        if (((pole_zaznaczone.numer == 7 || pole_zaznaczone.numer == 9) && (pole_puste.numer == 7 || pole_puste.numer == 9))) {
                            bicie(pole_zaznaczone, pole_puste, 8, x, puste_circle);
                        } else if (((pole_zaznaczone.numer == 11 || pole_zaznaczone.numer == 13) && (pole_puste.numer == 11 || pole_puste.numer == 13))) {
                            bicie(pole_zaznaczone, pole_puste, 12, x, puste_circle);
                        } else {
                            int przeciwnik_pole = Pole.wspolnySasiad(pole_puste, pole_zaznaczone);
                            if (przeciwnik_pole == 0)
                                text_field.setText("Nie można wykonać ruchu.");
                            else {
                                bicie(pole_zaznaczone, pole_puste, przeciwnik_pole, x, puste_circle);
                            }
                        }
                    }
                }
            } else if (x.gracz == 2 && obecny_gracz == 2) {
                zaznaczone.setFill(Color.web("WHITE"));
                puste_circle.setFill(Color.web("#ffd167"));
                bite_pole_plansza.setFill(Color.web("WHITE"));
                pionki_gracza_1--;
                if (pionki_gracza_1 == 0) {
                    text_field.setText("Koniec gry. Zwyciężył gracz 2.");
                    koniecGry = true;
                    zwyciezca = 2;
                } else {
                    pole_zaznaczone = pole_puste;
                    int kolejny_ruch = Pole.przeciwnicy(pole_zaznaczone);
                    if (kolejny_ruch == -1) {
                        klik = 0;
                        poprzednie = null;
                        zaznaczone = null;
                        text_field.setText("");
                        ruchGracza1();
                    } else if (kolejny_ruch == 50) {
                        poprzednie = null;
                        zaznaczone = puste_circle;
                        puste_circle.setFill(Color.web("#c3a467"));
                        text_field.setText("");
                        ruchGracza2();
                    } else {
                        x = pole_zaznaczone.pionek;
                        pole_puste = pola.get(kolejny_ruch - 1);
                        puste_circle.setFill(Color.web("WHITE"));
                        puste_circle = pole_puste.field;
                        if (((pole_zaznaczone.numer == 7 || pole_zaznaczone.numer == 9) && (pole_puste.numer == 7 || pole_puste.numer == 9))) {
                            bicie(pole_zaznaczone, pole_puste, 8, x, puste_circle);
                        } else if (((pole_zaznaczone.numer == 11 || pole_zaznaczone.numer == 13) && (pole_puste.numer == 11 || pole_puste.numer == 13))) {
                            bicie(pole_zaznaczone, pole_puste, 12, x, puste_circle);
                        } else {
                            int przeciwnik_pole = Pole.wspolnySasiad(pole_puste, pole_zaznaczone);
                            if (przeciwnik_pole == 0)
                                text_field.setText("Nie można wykonać ruchu.");
                            else {
                                bicie(pole_zaznaczone, pole_puste, przeciwnik_pole, x, puste_circle);
                            }
                        }
                    }
                }
            }
        }
    }

    public void ruch(Circle zaznaczone, Circle puste_circle) throws InterruptedException {
        Pole pole_zaznaczone = get_pole(zaznaczone);
        Pole pole_puste = get_pole(puste_circle);

        assert pole_zaznaczone != null;
        assert pole_puste != null;
        Pionek x = pole_zaznaczone.pionek;
        if (obowiazek_bicia) {
            if (!pole_zaznaczone.przeszukajSasiadow(pole_puste.numer)) {                                                                       //jeżeli kliknięte pole nie jest sąsiadem, sprawdza czy można wykonać bicie
                if (((pole_zaznaczone.numer == 7 || pole_zaznaczone.numer == 9) && (pole_puste.numer == 7 || pole_puste.numer == 9))) {
                    bicie(pole_zaznaczone, pole_puste, 8, x, puste_circle);
                } else if (((pole_zaznaczone.numer == 11 || pole_zaznaczone.numer == 13) && (pole_puste.numer == 11 || pole_puste.numer == 13))) {
                    bicie(pole_zaznaczone, pole_puste, 12, x, puste_circle);
                } else {
                    int przeciwnik_pole = Pole.wspolnySasiad(pole_puste, pole_zaznaczone);
                    if (przeciwnik_pole == 0) {
                        text_field.setText("Nie można wykonać ruchu.");
                    } else {
                        bicie(pole_zaznaczone, pole_puste, przeciwnik_pole, x, puste_circle);
                    }
                }
            } else {
                text_field.setText("Należy wykonać bicie.");
            }
        } else {
            if (pole_zaznaczone.przeszukajSasiadow(pole_puste.numer)) {
                System.out.println(x.pole);
                System.out.println(pole_puste.numer);
                x.pole = pole_puste.numer;
                pole_puste.czyWolne = false;
                pole_zaznaczone.czyWolne = true;
                pole_puste.pionek = x;
                pole_zaznaczone.pionek = null;

                System.out.println("zaz" + pole_zaznaczone.field);
                System.out.println("pus" + pole_puste.field);

                if (x.gracz == 1 && obecny_gracz == 1) {
                    zaznaczone.setFill(Color.web("WHITE"));
                    puste_circle.setFill(Color.web("#88fbc3"));
                    klik = 0;
                    text_field.setText("");
                    ruchGracza2();
                } else if (x.gracz == 2 && obecny_gracz == 2) {
                    zaznaczone.setFill(Color.web("WHITE"));
                    puste_circle.setFill(Color.web("#ffd167"));
                    klik = 0;
                    text_field.setText("");
                    ruchGracza1();
                }
            } else {
                text_field.setText("Nie można wykonać ruchu.");
            }
        }
    }

    public void fieldClick(MouseEvent event) throws InterruptedException {

        Circle klikniete_circle = (Circle) event.getSource();            // zwraca kliknięty field
        System.out.println(klikniete_circle);


        Pole pole = get_pole(klikniete_circle);

        assert pole != null;
        if (!pole.czyWolne) {

            Pionek x = pole.pionek;
            System.out.println(pole.numer);

            if (x.gracz == 1 && obecny_gracz == 1) {
                if (obowiazek_bicia && Pole.przeciwnicy(pole) == -1 && !koniecGry) {
                    text_field.setText("Należy wykonać bicie.");
                    klik = 0;
                } else if(koniecGry){
                    if(zwyciezca == 1){
                        text_field.setText("Koniec gry. Zwyciężył gracz 1");
                        klik = 0;
                    }
                    else{
                        text_field.setText("Koniec gry. Zwyciężył gracz 2");
                        klik = 0;
                    }
                }
                else if (klik == 0) {
                    klikniete_circle.setFill(Color.web("#6fc397"));
                    poprzednie = klikniete_circle;
                    zaznaczone = klikniete_circle;
                    klik = 1;
                } else {
                    poprzednie.setFill(Color.web("#88fbc3"));
                    klikniete_circle.setFill(Color.web("#6fc397"));
                    poprzednie = klikniete_circle;
                    zaznaczone = klikniete_circle;
                }
            } else if (x.gracz == 2 && obecny_gracz == 2) {
                if (obowiazek_bicia && Pole.przeciwnicy(pole) == -1) {
                    text_field.setText("Należy wykonać bicie.");
                    klik = 0;
                } else if(koniecGry){
                    if(zwyciezca == 1){
                        text_field.setText("Koniec gry. Zwyciężył gracz 1");
                        klik = 0;
                    }
                    else{
                        text_field.setText("Koniec gry. Zwyciężył gracz 2");
                        klik = 0;
                    }
                }else if (klik == 0) {
                    klikniete_circle.setFill(Color.web("#c3a467"));
                    poprzednie = klikniete_circle;
                    zaznaczone = klikniete_circle;
                    klik = 1;
                } else {
                    poprzednie.setFill(Color.web("#ffd167"));
                    klikniete_circle.setFill(Color.web("#c3a467"));
                    poprzednie = klikniete_circle;
                    zaznaczone = klikniete_circle;
                }
            }

        } else if (klik == 1) {
            poprzednie = null;
            Circle puste_circle = (Circle) event.getSource();
            ruch(zaznaczone, puste_circle);
            }
        }

    void ruchGracza1() {
        obecny_gracz = 1;
        obowiazek_bicia = Pole.obowiazek_bicia(pionki_gracza_1, 1);
        if (obowiazek_bicia) {
            System.out.println("Obowiązek bicia: TAK");
        } else {
            System.out.println("Obowiązek bicia: NIE");
        }
        player1.setStyle("-fx-background-color: #32CD32; ");
        player2.setStyle("-fx-background-color: #FFFFFF; ");

    }

    void ruchGracza2() {

        obecny_gracz = 2;
        obowiazek_bicia = Pole.obowiazek_bicia(pionki_gracza_2, 2);
        if (obowiazek_bicia) {
            System.out.println("Obowiazek bicia: TAK");
        } else {
            System.out.println("Obowiazek bicia: NIE");
        }
        player1.setStyle("-fx-background-color: #FFFFFF; ");
        player2.setStyle("-fx-background-color: #32CD32; ");

    }

    void gra() {

       /* Pionek pionek1 = new Pionek(1, 1,true, 1);
        Pionek pionek2 = new Pionek(2, 1,true, 2);
        Pionek pionek3 = new Pionek(3, 1,true, 3);
        Pionek pionek4 = new Pionek(4, 1,true, 4);
        Pionek pionek5 = new Pionek(5, 1,true, 5);
        Pionek pionek6 = new Pionek(6, 1,true, 6);
        Pionek pionek7 = new Pionek(7, 1,true, 7);
        Pionek pionek8 = new Pionek(8,1, true, 8);
        Pionek pionek9 = new Pionek(9,1, true, 9);

        Pionek pionek10 = new Pionek(10, 2,true, 11);
        Pionek pionek11 = new Pionek(11, 2,true, 12);
        Pionek pionek12 = new Pionek(12, 2,true, 13);
        Pionek pionek13 = new Pionek(13, 2,true, 14);
        Pionek pionek14 = new Pionek(14, 2, true, 15);
        Pionek pionek15 = new Pionek(15, 2,true, 16);
        Pionek pionek16 = new Pionek(16, 2,true, 17);
        Pionek pionek17 = new Pionek(17, 2,true, 18);
        Pionek pionek18 = new Pionek(18, 2,true, 19);

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
        Pole pole19 = new Pole(19, false, pionek18, field19); pola.add(pole19);*/

        int liczba_pionkow = 0;
        for (int i = 1; i < 20; i++) {
            Pionek pionek = null;
            if (i != 10) {
                if (liczba_pionkow < 9) {
                    pionek = new Pionek(i, 1, true, i);
                    liczba_pionkow++;
                } else {
                    pionek = new Pionek(i, 2, true, i);
                    liczba_pionkow++;
                }
            }
            pola.add(new Pole(i, i == 10, pionek, fields.get(i - 1)));
        }

        Random wybor_gracza = new Random();
        int start = wybor_gracza.nextInt(2) + 1;
        if (start == 1)
            ruchGracza1();
        else
            ruchGracza2();

    }


}
