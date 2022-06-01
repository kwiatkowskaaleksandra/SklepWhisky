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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DodawanieProduktow implements Initializable {

    ObservableList listTyp = FXCollections.observableArrayList();
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    PreparedStatement pst = null;
    int index = -1;
    @FXML
    private Button IdProdukty;
    @FXML
    private Button IdWiadomosci;
    @FXML
    private Button idWyloguj;
    @FXML
    private Button idWybierz;
    @FXML
    private TextField idNazwa;
    @FXML
    private TextField idCena;
    @FXML
    private TextField idPlik;
    @FXML
    private ChoiceBox<String> idTyp;
    @FXML
    private TextArea idOpis;
    @FXML
    private TableView<DaneProdukty> Tab;
    @FXML
    private TableColumn<DaneProdukty, Integer> idTab;
    @FXML
    private TableColumn<DaneProdukty, String> nazwaTab;
    @FXML
    private TableColumn<DaneProdukty, Float> cenaTab;
    @FXML
    private TableColumn<DaneProdukty, String> opisTab;
    @FXML
    private TableColumn<DaneProdukty, String> typTab;
    @FXML
    private TableColumn<DaneProdukty, String> obrazTab;

    public void wyswietlProdukty() throws SQLException {
        ResultSet rs = null;
        Statement st = null;
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneProdukty daneProdukty;

        st = connectDB.createStatement();
        String dane = "SELECT * FROM produkty ORDER BY idProduktu ASC";
        rs = st.executeQuery(dane);
        while (rs.next()) {
            int id = rs.getInt("idProduktu");
            String nazwa = rs.getString("nazwa");
            float cena = rs.getFloat("cena");
            String opis = rs.getString("opis");
            String typ = rs.getString("typ");
            String obrazz = rs.getString("obraz");
            ImageView obraz = new ImageView(new Image(this.getClass().getResourceAsStream("images/" + obrazz)));
            obraz.setFitWidth(220);
            obraz.setFitHeight(250);
            daneProdukty = new DaneProdukty(id, nazwa, cena, opis, obraz, typ);
            WczTab.add(daneProdukty);
        }
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        nazwaTab.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        cenaTab.setCellValueFactory(new PropertyValueFactory<>("cena"));
        opisTab.setCellValueFactory(new PropertyValueFactory<>("opis"));
        typTab.setCellValueFactory(new PropertyValueFactory<>("typ"));
        obrazTab.setCellValueFactory(new PropertyValueFactory<>("obraz"));
        Tab.setItems(WczTab);
        st.close();
    }

    public void dodajOnAction(ActionEvent event) throws SQLException {
        ResultSet rs = null;
        Statement st = null;
        int idProd = 0;

        String idP = "SELECT idProduktu FROM produkty ORDER BY idProduktu ASC";
        st = connectDB.createStatement();
        rs = st.executeQuery(idP);
        while (rs.next()) {
            idProd = rs.getInt("idProduktu");
        }
        st.close();

        String dane = "INSERT INTO produkty (idProduktu,nazwa,cena,opis,obraz,typ) VALUES (?,?,?,?,?,?)";
        try {
            if (idNazwa.getText().isEmpty() || idCena.getText().isEmpty() || idOpis.getText().isEmpty() || idPlik.getText().isEmpty() || idTyp.getValue().equals("Typ")) {
                JOptionPane.showMessageDialog(null, "Nalezy uzupelnic wszytskie dane.");
            } else {
                pst = (PreparedStatement) connectDB.prepareStatement(dane);
                pst.setString(1, String.valueOf(idProd + 1));
                pst.setString(2, idNazwa.getText());
                pst.setString(3, String.valueOf(idCena.getText()));
                pst.setString(4, idOpis.getText());
                pst.setString(5, idPlik.getText());
                pst.setString(6, idTyp.getValue());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Produkt dodano pomyslnie!");

                wyswietlProdukty();
                idNazwa.clear();
                idTyp.setValue("Typ");
                idOpis.clear();
                idCena.clear();
                idPlik.clear();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy dodawaniu! " + e);
        }
    }

    public void WybierzZdjecie() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("all", "*.*"), new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\48732\\Desktop\\kck_main\\kck\\src\\main\\resources\\com\\example\\kck\\images"));
        File selectedFile = fileChooser.showOpenDialog(idWybierz.getScene().getWindow());
        if (selectedFile != null)
            idPlik.setText(selectedFile.getName());
    }

    public void edytujOnAction(ActionEvent event) {

        String dane = "UPDATE produkty SET nazwa='" + idNazwa.getText() + "', cena='" + idCena.getText() + "',opis='" + idOpis.getText() + "',obraz='" + idPlik.getText() + "',typ='" + idTyp.getValue() + "'WHERE idProduktu='" + idTab.getCellData(this.index) + "'";
        try {
            if (idNazwa.getText().isEmpty() || idCena.getText().isEmpty() || idOpis.getText().isEmpty() || idPlik.getText().isEmpty() || idTyp.getValue().equals("Typ")) {
                JOptionPane.showMessageDialog(null, "Nalezy uzupelnic wszytskie dane.");
            } else {
                pst = (PreparedStatement) connectDB.prepareStatement(dane);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Produkt edytowano pomyslnie!");

                wyswietlProdukty();
                idNazwa.clear();
                idTyp.setValue("Typ");
                idOpis.clear();
                idCena.clear();
                idPlik.clear();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy dodawaniu! " + e);
        }
    }

    public void usunOnAction(ActionEvent event) throws SQLException {

        String dane = "DELETE FROM produkty WHERE idProduktu='" + idTab.getCellData(this.index) + "'";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.execute();

            wyswietlProdukty();
            idNazwa.clear();
            idTyp.setValue("Typ");
            idOpis.clear();
            idCena.clear();
            idPlik.clear();
            JOptionPane.showMessageDialog(null, "Produkt usunieto pomyslnie!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy usuwaniu! " + e);
        }
    }

    public void IdProduktyOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdProdukty.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Dodawanie_produktu.fxml"));
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

    public void IdWiadomosciOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWiadomosci.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("WiadomosciPracownik.fxml"));
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

    public void IdWylogujOnAciotn(javafx.event.ActionEvent event) {
        Stage stage = (Stage) idWyloguj.getScene().getWindow();
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

    public void homeOnAction(ActionEvent event) {
        Stage stage = (Stage) IdProdukty.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("StronaPracownika.fxml"));
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

    public void getSelected() throws SQLException {
        ResultSet rs = null;
        Statement st = null;
        String obrazz = null;

        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        idNazwa.setText(nazwaTab.getCellData(this.index));
        idCena.setText(String.valueOf(cenaTab.getCellData(this.index)));
        idOpis.setText(opisTab.getCellData(this.index));
        idTyp.setValue(typTab.getCellData(this.index));


        st = connectDB.createStatement();
        String dane = "SELECT * FROM produkty WHERE idProduktu='" + idTab.getCellData(this.index) + "'";
        rs = st.executeQuery(dane);
        while (rs.next()) {
            obrazz = rs.getString("obraz");
        }
        idPlik.setText(obrazz);
        st.close();
    }

    public void RodzajeTyp() {
        listTyp.removeAll(listTyp);
        String a = "Typ";

        String b = "alko";
        String c = "beczka";
        listTyp.addAll(b, c);
        idTyp.getItems().addAll(listTyp);
        idTyp.setValue(a);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RodzajeTyp();
        try {
            wyswietlProdukty();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
