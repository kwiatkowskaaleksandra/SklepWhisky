package com.example.kck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Logowanie {
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();

    @FXML
    private TextField idLogin;
    @FXML
    private PasswordField idHaslo;
    @FXML
    private Label label;
    @FXML
    private Button idZaloguj;
    @FXML
    private Button rejestruj;
    @FXML
    private Button idZarejestruj;
    @FXML
    private Button wrocBtn;


    @FXML
    private TextField idImie;
    @FXML
    private TextField idNazwisko;
    @FXML
    private TextField idEmail;
    @FXML
    private PasswordField idHasloPow;
    @FXML
    private DatePicker idData;

    public void zalogujButtonOnAction(ActionEvent event) {
        if (!idLogin.getText().isBlank() && !idHaslo.getText().isBlank()) {
            String dane = "DELETE FROM zalogowany WHERE idZalogowanego=1";
            try {
                PreparedStatement pst = null;
                pst = (PreparedStatement) connectDB.prepareStatement(dane);
                pst.execute();
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            vlidateLogin();
        } else {
            label.setText("Wpisz login i haslo");
        }
    }

    public void vlidateLogin() {

        String verifyLoginPr = "SELECT idPracownika,login , haslo FROM pracownik WHERE login = '" + idLogin.getText() + "'AND haslo = '" + idHaslo.getText() + "'";
        String verifyLoginKl = "SELECT idKlienta,login , haslo FROM klient WHERE login = '" + idLogin.getText() + "'AND haslo = '" + idHaslo.getText() + "'";

        String dane = "INSERT INTO zalogowany(idZalogowanego,idKlienta,idPracownika,login,haslo)values(?,?,?,?,?)";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResultKl = statement.executeQuery(verifyLoginKl);
            while (queryResultKl.next()) {

                if (queryResultKl.getString("login").equals(idLogin.getText()) && queryResultKl.getString("haslo").equals(idHaslo.getText())) {
                    String id = queryResultKl.getString("idKlienta");
                    try {
                        PreparedStatement pst = null;
                        pst = (PreparedStatement) connectDB.prepareStatement(dane);
                        pst.setString(1, "1");
                        pst.setString(2, id);
                        pst.setString(3, null);
                        pst.setString(4, idLogin.getText());
                        pst.setString(5, idHaslo.getText());
                        pst.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }
                    label.setText("Udalo sie zalogowac!");
                    HelloController helloController = new HelloController();
                    helloController.KontrolerKlient();
                    Stage stage = (Stage) idZaloguj.getScene().getWindow();
                    stage.close();
                }
            }

            if (queryResultKl.next() == false) {
                label.setText("Błędny login lub hasło.");
            }
            statement.close();

            statement=connectDB.createStatement();
            ResultSet queryResultPr = statement.executeQuery(verifyLoginPr);
            while (queryResultPr.next()) {

                if (queryResultPr.getString("login").equals(idLogin.getText()) && queryResultPr.getString("haslo").equals(idHaslo.getText())) {
                    String id = queryResultPr.getString("idPracownika");
                    try {
                        PreparedStatement pst = null;
                        pst = (PreparedStatement) connectDB.prepareStatement(dane);
                        pst.setString(1, "1");
                        pst.setString(2, null);
                        pst.setString(3, id);
                        pst.setString(4, idLogin.getText());
                        pst.setString(5, idHaslo.getText());
                        pst.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }
                    label.setText("Udalo sie zalogowac!");
                    HelloController helloController = new HelloController();
                    helloController.KontrolerPracownik();
                    Stage stage = (Stage) idZaloguj.getScene().getWindow();
                    stage.close();
                }
            }
            if (queryResultPr.next() == false) {
                label.setText("Błędny login lub hasło.");
            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void zarejestrujSie(ActionEvent event) {

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Rejestracja.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setScene(new Scene(root, 550, 600));
            menuStage.setTitle("Rejestracja");
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void rejestracjaOnAction(ActionEvent event) throws SQLException {
        Statement stat = null;
        stat = connectDB.createStatement();
        PreparedStatement pst = null;

        String maxID = "SELECT idKlienta FROM klient";
        ResultSet max = stat.executeQuery(maxID);
        int idK = 0;
        while (max.next()) {
            idK = max.getInt("idKlienta");
        }

        String dane = "INSERT INTO klient (idKlienta, imie,nazwisko,email,login,haslo,dataUr) VALUES (?,?,?,?,?,?,?)";
        if (idImie.getText().isEmpty() || idNazwisko.getText().isEmpty() || idData.getValue() == null || idEmail.getText().isEmpty() || idLogin.getText().isEmpty() || idHaslo.getText().isEmpty() || idHasloPow.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Uzupełnij wszytskie dane!");
        } else {
            if (idHaslo.getText().equals(idHasloPow.getText())) {
                int localDatee = LocalDate.now().getYear();
                int date = idData.getValue().getYear();

                int year = localDatee - date;
                if (year >= 18) {
                    try {
                        LocalDate localDate = idData.getValue();
                        String formatDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        pst = (PreparedStatement) connectDB.prepareStatement(dane);
                        pst.setString(1, String.valueOf(idK + 1));
                        pst.setString(2, idImie.getText());
                        pst.setString(3, idNazwisko.getText());
                        pst.setString(4, idEmail.getText());
                        pst.setString(5, idLogin.getText());
                        pst.setString(6, idHaslo.getText());
                        pst.setString(7, formatDate);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Konto zostało pomyślnie założone. Proszę się zalogować.");

                        Stage stage = (Stage) idZarejestruj.getScene().getWindow();
                        stage.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Blad rejestracji! ");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie jestes pelnoletni. Strona przeznaczona tylko dla osob powyzej 18 roku zycia.");
                }
            } else
                JOptionPane.showMessageDialog(null, "Podane hasła różnią się!");
        }
    }

    public void wrocOnAction(ActionEvent event) {
        Stage stage = (Stage) wrocBtn.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
