package com.example.kck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class Platnosc implements Initializable {
    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    koszyk koszyk = new koszyk();
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
    private TextField idKarta;
    @FXML
    private TextField idKwota;

    public void wczytajDane() throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        st = connectDB.createStatement();
        String dane = "SELECT z.idKlienta,imie,nazwisko,email FROM zalogowany z, klient k WHERE k.idKlienta=z.idKlienta";
        rs = st.executeQuery(dane);
        while (rs.next()) {
            String imie = rs.getString("imie");
            String nazwisko = rs.getString("nazwisko");
            String email = rs.getString("email");

            idImie.setText(imie);
            idNazwisko.setText(nazwisko);
            idEmail.setText(email);
            idKwota.setText(String.valueOf(koszyk.cenaKoncowa));
        }
        st.close();
    }

    public void zaplacOnAction(javafx.event.ActionEvent event) throws SQLException {
        if (idKarta.getLength() == 16) {
            dodajFakture();
            wyslijWiadomosc();
            usunKoszyk();
            JOptionPane.showMessageDialog(null, "Dokonano platnosci.");
        } else {
            JOptionPane.showMessageDialog(null, "Numer karty jest bledny. Prosze wpisac poprawny numer karty.");
        }
    }

    public String kodRabatowyGen() throws SQLException {
        String kod = "RABAT";
        Random rD = new Random();
        Random rW = new Random();
        int l = rD.nextInt(99) + 10;
        int w = rW.nextInt(50) + 5;

        Statement st = null;
        ResultSet rs = null;
        st = connectDB.createStatement();
        String idR = "SELECT idRabat FROM rabat order by idRabat ASC";
        rs = st.executeQuery(idR);
        int idRab = 0;
        while (rs.next()) {
            idRab = rs.getInt("idRabat");
        }
        st.close();
        String dane = "INSERT INTO rabat(idRabat,kod,wartosc)values(?,?,?)";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.setString(1, String.valueOf(idRab + 1));
            pst.setString(2, kod + l);
            pst.setString(3, String.valueOf(w));
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Błędne wysłanie wiadomosci! " + e);
        }
        return kod + l;
    }

    public void wyslijWiadomosc() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        String kod = kodRabatowyGen();

        st = connectDB.createStatement();
        String daneKl = "SELECT z.idKlienta FROM zalogowany z, klient k WHERE z.idKlienta=k.idKlienta";
        rs = st.executeQuery(daneKl);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("z.idKlienta");
        }
        st.close();

        st = connectDB.createStatement();
        String daneW = "SELECT idWiadomosci FROM wiadomosci order by idWiadomosci ASC";
        rs = st.executeQuery(daneW);
        int idW = 0;
        while (rs.next()) {
            idW = rs.getInt("idWiadomosci");
        }
        st.close();

        String daneWiad = "INSERT INTO wiadomosci(idWiadomosci,temat,adresat,tresc,data,idPracownika,idKlienta,stan)values(?,?,?,?,?,?,?,?)";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(daneWiad);
            pst.setString(1, String.valueOf(idW + 1));
            pst.setString(2, "Zamowienie");
            pst.setString(3, "biuroObslugi@whiskyMadness.com");
            pst.setString(4, "Dzien dobry!\nTwoje zamowienie zostalo przyjete do realizacji. Calkowity koszt to: '" + koszyk.cenaKoncowa + "'zl. Kod rabatowy na nastepne zakupy: '" + kod + "'.\nDziekujemy za skorzystanie z naszego sklepu. Zapraszamy ponownie!\nPozdrawiamy WHISKY MADNESS.");
            pst.setString(5, String.valueOf(LocalDate.now()));
            pst.setString(6, String.valueOf(1));
            pst.setString(7, String.valueOf(idZal));
            pst.setString(8, "odebranaP");
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Błędne wysłanie wiadomosci! " + e);
        }
    }

    public void usunKoszyk() throws SQLException {
        String dane = "DELETE FROM koszyk";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy usuwaniu! " + e);
        }
    }

    public void dodajFakture() throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        st = connectDB.createStatement();
        String daneFaktura = "SELECT idFaktury FROM faktury order by idFaktury ASC";
        rs = st.executeQuery(daneFaktura);
        int idFaktury = 0;
        while (rs.next()) {
            idFaktury = rs.getInt("idFaktury");
        }
        st.close();

        st = connectDB.createStatement();
        String daneKl = "SELECT idKlienta FROM zalogowany";
        rs = st.executeQuery(daneKl);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idKlienta");
        }
        st.close();

        st = connectDB.createStatement();
        String daneProd = "SELECT p.idProduktu,k.ilosc FROM produkty p,koszyk k WHERE p.idProduktu=k.idProduktu";
        rs = st.executeQuery(daneProd);

        String daneFaktury = "INSERT INTO faktury(idFaktury,idKlienta,kwota,data)values (?,?,?,?)";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(daneFaktury);
            pst.setString(1, String.valueOf(idFaktury + 1));
            pst.setString(2, String.valueOf(idZal));
            pst.setString(3, String.valueOf(koszyk.cenaKoncowa));
            pst.setString(4, String.valueOf(LocalDate.now()));
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Błędne dane faktury! " + e);
        }

        while (rs.next()) {
            int idProd = rs.getInt("p.idProduktu");
            int ilProd = rs.getInt("k.ilosc");

            Statement st2 = connectDB.createStatement();
            String daneHist = "SELECT idHistoria FROM historia";
            ResultSet rs2 = st2.executeQuery(daneHist);
            int idHis = 0;
            while (rs2.next()) {
                idHis = rs2.getInt("idHistoria");
            }
            st2.close();

            Statement stt2 = connectDB.createStatement();
            String daneFk = "SELECT idFaktury FROM faktury";
            ResultSet rss2 = stt2.executeQuery(daneFk);
            int idF = 0;
            while (rss2.next()) {
                idF = rss2.getInt("idFaktury");
            }
            st2.close();

            String daneHistoria = "INSERT INTO historia(idHistoria,idKlienta,idProduktu,ilosc,idFaktury)values (?,?,?,?,?)";
            try {
                pst = (PreparedStatement) connectDB.prepareStatement(daneHistoria);
                pst.setString(1, String.valueOf(idHis + 1));
                pst.setString(2, String.valueOf(idZal));
                pst.setString(3, String.valueOf(idProd));
                pst.setString(4, String.valueOf(ilProd));
                pst.setString(5, String.valueOf(idF));
                pst.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Błędne dane faktury! " + e);
            }
        }
        st.close();



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
