package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class koszyk implements Initializable {
    public static int ideKoszyk;
    public static float cenaKoncowa;
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
    private TableView<DaneKoszyk> Tab;
    @FXML
    private TableColumn<DaneKoszyk, Integer> idKosz;
    @FXML
    private TableColumn<DaneKoszyk, String> idNazwa;
    @FXML
    private TableColumn<DaneKoszyk, Integer> idIlosc;
    @FXML
    private TableColumn<DaneKoszyk, Float> idCena;
    @FXML
    private Spinner<Integer> idIleSztk;
    @FXML
    private TextField idCenSztk;
    @FXML
    private TextField idCenaK;
    @FXML
    private TextField idRabat;

    public void wyswietlKoszyk() throws SQLException, IOException {
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneKoszyk daneKoszyk;
        Statement st = null;
        ResultSet rs = null;
        float cnK = 0;

        st = connectDB.createStatement();
        String dane = "SELECT idKlienta FROM zalogowany";
        rs = st.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idKlienta");
        }
        st.close();

        st = connectDB.createStatement();
        String dane2 = "SELECT k.idKoszyk,k.ilosc, p.cena as cena, p.nazwa as nazwa FROM koszyk k, produkty p WHERE k.idProduktu=p.idProduktu AND k.idKlienta='" + idZal + "'ORDER BY k.idKoszyk ASC";
        rs = st.executeQuery(dane2);
        while (rs.next()) {
            int id = rs.getInt("k.idKoszyk");
            String naz = rs.getString("nazwa");
            int ilosc = rs.getInt("k.ilosc");
            float cena = rs.getFloat("cena");
            daneKoszyk = new DaneKoszyk(id, naz, cena, ilosc);
            WczTab.add(daneKoszyk);

            cnK += cena * ilosc;
        }
        st.close();
        idKosz.setCellValueFactory(new PropertyValueFactory<>("id"));
        idNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        idCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        idIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        Tab.setItems(WczTab);
        idCenaK.setText(String.valueOf(cnK));
    }

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (this.index <= -1) {
            return;
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(this.idIlosc.getCellData(this.index));
        idIleSztk.setValueFactory(valueFactory);
        this.idCenSztk.setText(String.valueOf(this.idCena.getCellData(this.index)));
        ideKoszyk = this.idKosz.getCellData(this.index);
    }

    public void dodajDoKoszyka(javafx.event.ActionEvent event) {
        try {
            int val = idIleSztk.getValue();
            String dane = "UPDATE koszyk SET ilosc='" + val + "'WHERE idKoszyk='" + ideKoszyk + "'";
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.execute();
            wyswietlKoszyk();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy edycji! " + e);
        }
    }

    public void usunProdukt(javafx.event.ActionEvent event) {
        String dane = "DELETE FROM koszyk WHERE idKoszyk=?";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.setString(1, String.valueOf(ideKoszyk));
            pst.execute();
            idCenSztk.clear();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
            valueFactory.setValue(0);
            idIleSztk.setValueFactory(valueFactory);
            wyswietlKoszyk();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy usuwaniu! " + e);
        }

    }

    public void rabatOnAction(javafx.event.ActionEvent event) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        String kod = idRabat.getText();
        String dane = "SELECT kod,wartosc FROM rabat";

        st = connectDB.createStatement();
        rs = st.executeQuery(dane);
        String kodRab = null, kodRabP = null;
        int wartoscRab = 0, wartoscRabP = 0;
        while (rs.next()) {

            kodRab = rs.getString("kod");
            wartoscRab = rs.getInt("wartosc");
            if (kodRab.equals(kod)) {
                kodRabP = kodRab;
                wartoscRabP = wartoscRab;
            }
        }
        if (kodRabP != null) {
            if (kodRabP.equals(kod)) {
                float c = Float.parseFloat(idCenaK.getText());
                float r = (float) wartoscRabP / 100;
                c = c - (c * r);
                c *= 100;
                c = round(c);
                c /= 100;
                idCenaK.setText(String.valueOf(c));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bledny kod rabatowy");
        }
        st.close();
    }

    public void platnoscOnAction(javafx.event.ActionEvent event) {
        cenaKoncowa = Float.parseFloat(idCenaK.getText());
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Zamawianie.fxml"));
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

    public void homeOnAction(ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
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
            wyswietlKoszyk();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
