package sample;

import game.Game;
import game.Player;
import game.PlayerType;
import gameAI.AI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

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
    ArrayList<Circle> fields;
    @FXML
    Button player1;
    @FXML
    Button player2;
    @FXML
    TextField text_field;

    private Game game;
    private Circle pierwszyKlik;
    private Circle drugiKlik;
    private boolean klikalne = false;

    public void PvPClick() {
        game = new Game(new Player(PlayerType.Human, 1), new Player(PlayerType.Human, 2), this);
        game.start();
    }

    public void PvAIClick() {
        game = new Game(new Player(PlayerType.Human, 1), new Player(PlayerType.AI, 2), this);
        game.start();
    }

    public void AIvAIClick() {
        game = new Game(new Player(PlayerType.AI, 1), new Player(PlayerType.AI, 2), this);
        game.start();
    }

    public void koloruj(int[][] matrix) {
        int counter = 0;
        if (matrix.length != 7 || matrix[0].length != 3)
            return;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    fields.get(counter).setFill(Color.web("WHITE"));
                } else if (matrix[i][j] == 1) {
                    fields.get(counter).setFill(Color.web("#88fbc3"));
                } else if (matrix[i][j] == 2) {
                    fields.get(counter).setFill(Color.web("#ffd167"));
                } else
                    continue;
                counter++;
            }
        }
    }

    public void fieldClick(MouseEvent event) {
        if (klikalne) {
            Circle klikniete = (Circle) event.getSource();
            int wartosc = game.getFieldInfo(zamienNaNumer(klikniete))[1];
            if (pierwszyKlik == null) {
                if (wartosc == game.getCurrentPlayer().getNumber()) {
                    pierwszyKlik = klikniete;
                    if (wartosc == 1) {
                        klikniete.setFill(Color.web("#6fc397"));
                    }
                    if (wartosc == 2) {
                        klikniete.setFill(Color.web("#c3a467"));
                    }
                }
            } else {
                if (drugiKlik == null) {
                    drugiKlik = klikniete;
                }
            }
        }
    }

    private int zamienNaNumer(Circle circle) {
        int indeks = fields.indexOf(circle);
        if (indeks < 9) {
            return indeks;
        } else if (indeks == 9) {
            return indeks + 1;
        } else {
            return indeks + 2;
        }
    }

    public void zacznijRuch() {
        klikalne = true;
    }

    public void zmienGracza(int numerGracza) {
        if (numerGracza == 1) {
            player1.setStyle("-fx-background-color: #32CD32; ");
            player2.setStyle("-fx-background-color: #FFFFFF; ");
        } else if (numerGracza == 2) {
            player1.setStyle("-fx-background-color: #FFFFFF; ");
            player2.setStyle("-fx-background-color: #32CD32; ");
        }
    }

    public void wyswietlWiadomosc(String wiadomosc) {
        text_field.setText(wiadomosc);
    }

    public kotlin.Pair<Integer, Integer> pobierzRuch() {
        if (pierwszyKlik != null && drugiKlik != null) {
            klikalne = false;
            int[] pola = new int[2];
            Circle[] kolka = {pierwszyKlik, drugiKlik};
            for (int i = 0; i < 2; i++) {
                pola[i] = zamienNaNumer(kolka[i]);
            }
            pierwszyKlik = null;
            drugiKlik = null;
            return new kotlin.Pair<>(pola[0], pola[1]);
        }
        return null;
    }

    public void onClose() {
        if(game != null)
            game.close();
    }
}
