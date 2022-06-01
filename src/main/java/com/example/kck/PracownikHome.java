package com.example.kck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class PracownikHome implements Initializable {
    @FXML
    public ListView<String> listaOgloszen;
    @FXML
    public ListView<String> listaOpinie;
    @FXML
    private Button IdProdukty;
    @FXML
    private Button idOgloszeniaBtn;
    @FXML
    private Button IdFaktury;
    @FXML
    private Button IdWiadomosci;
    @FXML
    private Button idWyloguj;

    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();

    public void Ogloszenie() {

        String daneOgl = "SELECT data,tresc FROM ogloszenia";
        Statement st = null;
        try {
            st = connectDB.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = Objects.requireNonNull(st).executeQuery(daneOgl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Objects.requireNonNull(rs).next()) {

                String data = rs.getString("data");
                String tresc = rs.getString("tresc");
                listaOgloszen.getItems().addAll(data + " - " + tresc);
            }
            st.close();
        } catch (Exception e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
    }

    public void Opinie() {
        String daneO = "SELECT o.tresc, o.data, p.nazwa  FROM opinie o, produkty p WHERE o.idProduktu= p.idProduktu";
        Statement st2 = null;
        try {
            st2 = connectDB.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs2 = null;
        try {
            rs2 = Objects.requireNonNull(st2).executeQuery(daneO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Objects.requireNonNull(rs2).next()) {

                String data = rs2.getString("data");
                String tresc = rs2.getString("tresc");
                String nazwa = rs2.getString("nazwa");
                listaOpinie.getItems().addAll(data + "  -  " + nazwa + "  -  " + tresc);
            }
            st2.close();
        } catch (Exception e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
    }

    public void opinieOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdProdukty.getScene().getWindow();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("OpiniePracownik.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 656, 439));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void ogloszeniaOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) idOgloszeniaBtn.getScene().getWindow();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Ogloszenia.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 656, 439));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Ogloszenie();
        Opinie();
    }
}
