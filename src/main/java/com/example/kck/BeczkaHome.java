package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class BeczkaHome implements Initializable {

    public static int ideProdB;
    PreparedStatement pst = null;
    int index = -1;
    @FXML
    private TableView<DaneProdukty> Tab;
    @FXML
    private TableColumn<DaneProdukty, Integer> idP;
    @FXML
    private TableColumn<DaneProdukty, String> idNazwa;
    @FXML
    private TableColumn<DaneProdukty, String> idOpis;
    @FXML
    private TableColumn<DaneProdukty, Float> idCena;
    @FXML
    private TableColumn<DaneProdukty, String> idObraz;
    @FXML
    private TableColumn<DaneProdukty, Button> idKup;
    @FXML
    private Button IdProdukt;
    @FXML
    private Button IdBeczka;
    @FXML
    private Button IdWhisky;
    @FXML
    private Button zaloguj;

    public void wyswietlProdukty() throws SQLException {
        Polaczenie connectNow = new Polaczenie();
        Connection connectDB = connectNow.getConnection();
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneProdukty daneProdukty;
        Statement st = null;
        ResultSet rs = null;

        String danee = "SELECT idProduktu, nazwa, cena, opis, obraz, typ FROM produkty WHERE typ='beczka' order by idProduktu ASC";
        st = connectDB.createStatement();
        rs = st.executeQuery(danee);

        try {
            while (Objects.requireNonNull(rs).next()) {
                int id = rs.getInt("idProduktu");
                String naz = rs.getString("nazwa");
                float cena = rs.getFloat("cena");
                String opis = rs.getString("opis");
                String obrazz = rs.getString("obraz");
                String typ = rs.getString("typ");
                ImageView obraz = new ImageView(new Image(this.getClass().getResourceAsStream("images/" + obrazz)));
                obraz.setFitWidth(220);
                obraz.setFitHeight(250);
                daneProdukty = new DaneProdukty(id, naz, cena, opis, obraz, typ);
                WczTab.add(daneProdukty);

            }
            st.close();
        } catch (Exception e) {
            System.out.println("There is an Exception. " + e);
        }

        idP.setCellValueFactory(new PropertyValueFactory<>("id"));
        idNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        idCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        idOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        idObraz.setCellValueFactory(new PropertyValueFactory<>("obraz"));
        Tab.setItems(WczTab);
    }

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        ideProdB = this.idP.getCellData(this.index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            wyswietlProdukty();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void IdProduktOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("ProduktyHome.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("BeczkaHome.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("WhiskyHome.fxml"));
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

    public void ZalogujOnActionEvent(ActionEvent event) {
        Stage stage = (Stage) zaloguj.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Logowanie.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 548, 439));
            menuStage.setTitle("Logowanie");
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

}
