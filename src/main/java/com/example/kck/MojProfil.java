package com.example.kck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MojProfil implements Initializable {

    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    @FXML
    private Button IdProdukt;
    @FXML
    private Button IdBeczka;
    @FXML
    private Button IdWhisky;
    @FXML
    private TextField idImie;
    @FXML
    private TextField idNazwisko;
    @FXML
    private TextField idEmail;
    @FXML
    private TextField idLogin;
    @FXML
    private DatePicker idData;
    @FXML
    private PasswordField idHaslo;
    @FXML
    private PasswordField idHasloPow;
    @FXML
    private PasswordField idHasloNowe;


    public void wczytajDane() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        int idZal = 0;
        String imie = null, nazwisko = null, email = null, data = null, login = null;

        st = connectDB.createStatement();
        String daneKl = "SELECT k.idKlienta,k.imie,k.nazwisko,k.email,k.dataUr,k.login FROM zalogowany z, klient k WHERE z.idKlienta=k.idKlienta";
        rs = st.executeQuery(daneKl);
        while (rs.next()) {
            idZal = rs.getInt("k.idKlienta");
            imie = rs.getString("k.imie");
            nazwisko = rs.getString("k.nazwisko");
            email = rs.getString("k.email");
            data = rs.getString("k.dataUr");
            login = rs.getString("k.login");
        }
        st.close();
        idImie.setText(imie);
        idNazwisko.setText(nazwisko);
        idEmail.setText(email);
        idLogin.setText(login);
        idData.setValue(LocalDate.parse(data));
    }

    public void zapiszDaneOnAction(ActionEvent event) throws SQLException {
        int localDatee = LocalDate.now().getYear();
        int datee = idData.getValue().getYear();

        int yearr = localDatee - datee;
        if (idImie.getText().isEmpty() || idNazwisko.getText().isEmpty() || idLogin.getText().isEmpty() || idEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Uzupelnij wszytskie dane.");
        } else {
            if (yearr >= 18) {
                Statement st = null;
                ResultSet rs = null;
                int idZal = 0;

                st = connectDB.createStatement();
                String daneKl = "SELECT k.idKlienta FROM zalogowany z, klient k WHERE z.idKlienta=k.idKlienta";
                rs = st.executeQuery(daneKl);
                while (rs.next()) {
                    idZal = rs.getInt("k.idKlienta");
                }
                st.close();

                String zmiana = "UPDATE klient SET imie='" + idImie.getText() + "',nazwisko='" + idNazwisko.getText() + "',email='" + idEmail.getText() + "',login='" + idLogin.getText() + "' WHERE idKlienta='" + idZal + "'";
                pst = (PreparedStatement) connectDB.prepareStatement(zmiana);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Dane zostały pomyślnie zmienione.");
                wczytajDane();
            } else {
                JOptionPane.showMessageDialog(null, "Nie jestes pelnoletni. Strona przeznaczona tylko dla osob powyzej 18 roku zycia.");
            }
        }
    }

    public void zmienHaslo(ActionEvent event)  throws SQLException{
        Statement st = null;
        ResultSet rs = null;
        int idZal = 0;
        String hasl=null;

        st = connectDB.createStatement();
        String daneKl = "SELECT k.idKlienta,k.haslo FROM zalogowany z, klient k WHERE z.idKlienta=k.idKlienta";
        rs = st.executeQuery(daneKl);
        while (rs.next()) {
            idZal = rs.getInt("k.idKlienta");
            hasl=rs.getString("k.haslo");
        }
        st.close();

        if(idHaslo.getText().equals(hasl)){
            if(!idHasloNowe.getText().equals("")|| !idHasloNowe.getText().isEmpty()) {
                if(!idHasloPow.getText().equals("") || !idHasloPow.getText().isEmpty()){
                    String zmiana="UPDATE klient SET haslo='"+idHasloNowe.getText()+"'WHERE idKlienta='"+idZal+"'";
                    pst=(PreparedStatement) connectDB.prepareStatement(zmiana);
                    pst.execute();
                    wczytajDane();
                    JOptionPane.showMessageDialog(null, "Hasło zostało pomyślnie zmienione.");
                }
                else JOptionPane.showMessageDialog(null, "Powtórz nowe hasło.");
            }
            else  JOptionPane.showMessageDialog(null, "Podaj nowe hasło.");
        }else  JOptionPane.showMessageDialog(null, "Podano błędne stare hasło");

    }

    public void fakturyOnAction(ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Faktury.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void IdProduktOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("NaszeProdukty.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void IdBeczkaOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdBeczka.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("KupBeczke.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void IdWhiskyOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("KupWhisky.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void wylogujOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void politykaOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("polityka-prywatnosci.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void kontaktOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("kontakt.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void wiadomosciOnActionEvent(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Wiadomosci.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void koszykOnAction(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("koszyk.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void mojProfilOnActionEvent(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Moj_profil.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            wczytajDane();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
