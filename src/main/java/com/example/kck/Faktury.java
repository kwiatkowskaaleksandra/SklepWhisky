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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class Faktury implements Initializable {
    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    ObservableList listProd = FXCollections.observableArrayList();
    @FXML
    private Button IdProdukt;
    @FXML
    private Button IdBeczka;
    @FXML
    private Button IdWhisky;
    @FXML
    private TableView<DaneKoszyk> Tab;
    @FXML
    private TableColumn<DaneKoszyk, Integer> idFakt;
    @FXML
    private TableColumn<DaneKoszyk, String> idData;
    @FXML
    private TableColumn<DaneKoszyk, String> idProdukty;
    @FXML
    private TableColumn<DaneKoszyk, Float> idKwota;
    @FXML
    private TableView<DaneOpinie> Tab1;
    @FXML
    private TableColumn<DaneOpinie, Integer> idOp;
    @FXML
    private TableColumn<DaneOpinie, String> idPr;
    @FXML
    private TableColumn<DaneOpinie, String> idData1;
    @FXML
    private TableColumn<DaneOpinie, String> idTr;
    @FXML
    private TextArea idOpinia;
    @FXML
    private ChoiceBox<String> idPrd;
    @FXML
    private TextField idDat;
    @FXML
    private TextField idDane;
    @FXML
    private TextField idKw;
    @FXML
    private AnchorPane pp;

    public void wczytajFaktury() throws SQLException, IOException {
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneFaktury daneFaktury;
        Statement st = null;
        ResultSet rs = null;

        st = connectDB.createStatement();
        String dane = "SELECT idKlienta FROM zalogowany";
        rs = st.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idKlienta");
        }
        st.close();

        st = connectDB.createStatement();
        String daneFakt = "SELECT f.idFaktury,f.data,f.kwota,p.nazwa,h.ilosc FROM faktury f, produkty p,historia h ,klient k" +
                " WHERE f.idKlienta='" + idZal + "' AND h.idFaktury=f.idFaktury AND h.idProduktu=p.idProduktu" +
                " GROUP BY f.idFaktury ORDER BY f.idFaktury ASC";
        rs = st.executeQuery(daneFakt);
        while (rs.next()) {
            int idF = rs.getInt("f.idFaktury");
            Date data = Date.valueOf(rs.getString("f.data"));
            String nazwa = rs.getString("p.nazwa");
            float kwota = rs.getFloat("f.kwota");
            int ile = rs.getInt("h.ilosc");
            daneFaktury = new DaneFaktury(idF, nazwa + " szt." + ile, kwota, data);
            WczTab.add(daneFaktury);
        }
        st.close();

        idFakt.setCellValueFactory(new PropertyValueFactory<>("id"));
        idProdukty.setCellValueFactory(new PropertyValueFactory<>("produkty"));
        idData.setCellValueFactory(new PropertyValueFactory<>("data"));
        idKwota.setCellValueFactory(new PropertyValueFactory<>("kwota"));
        Tab.setItems(WczTab);
    }

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (this.index <= -1) {
            return;
        }

        ResultSet rs = null;
        Statement st = null;
        String im = null, naz = null;
        String daneKl = "SELECT imie, nazwisko FROM klient k, zalogowany z WHERE k.idKlienta=z.idKlienta";
        st = connectDB.createStatement();
        rs = st.executeQuery(daneKl);
        while (rs.next()) {
            im = rs.getString("imie");
            naz = rs.getString("nazwisko");
        }
        st.close();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(idData.getCellData(this.index));
        idDane.setText(naz + " " + im);
        idDat.setText(strDate);
        idKw.setText(String.valueOf(idKwota.getCellData(this.index)));

        st = connectDB.createStatement();
        String daneFakt = "SELECT f.idFaktury,f.data,f.kwota,p.nazwa,h.ilosc FROM faktury f, produkty p,historia h ,klient k, zalogowany z" +
                " WHERE f.idKlienta=z.idKlienta AND h.idFaktury=f.idFaktury AND h.idProduktu=p.idProduktu AND f.idFaktury='" + idFakt.getCellData(this.index) + "'" +
                " GROUP by p.idProduktu ORDER BY f.idFaktury ASC";
        rs = st.executeQuery(daneFakt);
        int i = 35, j = 1;
        while (rs.next()) {
            TextField pr = new TextField();
            pr.setEditable(false);
            pr.setPrefWidth(219);
            pr.setPrefHeight(25);
            pr.setLayoutX(512);
            pr.setLayoutY(95 + i * j);
            pr.setText(rs.getString("p.nazwa"));

            TextField il = new TextField();

            il.setEditable(false);
            il.setPrefWidth(80);
            il.setPrefHeight(25);
            il.setLayoutX(750);
            il.setLayoutY(95 + i * j);
            il.setText("szt. " + rs.getString("h.ilosc"));
            j++;
            pp.getChildren().add(pr);
            pp.getChildren().add(il);
        }
    }

    public void wczytajOpinie() throws SQLException {
        DaneOpinie daneOpinie;
        final ObservableList WczTab = FXCollections.observableArrayList();
        Statement st2 = null;
        ResultSet rs2 = null;
        int idP = 0;

        String daneO = "SELECT o.idOpinia,o.tresc, o.data, p.nazwa,k.imie,k.nazwisko FROM opinie o, produkty p, zalogowany z,klient k WHERE o.idProduktu= p.idProduktu AND o.idKlienta=z.idKlienta AND k.idKlienta=z.idKlienta";
        st2 = connectDB.createStatement();
        rs2 = st2.executeQuery(daneO);

        try {
            while (Objects.requireNonNull(rs2).next()) {
                int idO = rs2.getInt("o.idOpinia");
                String data = rs2.getString("o.data");
                String tresc = rs2.getString("o.tresc");
                String im = rs2.getString("k.imie");
                String naz = rs2.getString("k.nazwisko");
                String nazwa = rs2.getString("p.nazwa");
                daneOpinie = new DaneOpinie(idO, tresc, data, im, naz, nazwa);
                WczTab.add(daneOpinie);
            }
            st2.close();

            idOp.setCellValueFactory(new PropertyValueFactory<>("idop"));
            idData1.setCellValueFactory(new PropertyValueFactory<>("data"));
            idTr.setCellValueFactory(new PropertyValueFactory<>("tresc"));
            idPr.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
            Tab1.setItems(WczTab);

        } catch (Exception e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
    }

    public void RodzajeProd() throws SQLException {
        ResultSet rs = null;
        Statement st = null;

        listProd.removeAll(listProd);
        String a = "Produkty";

        String daneP = "SELECT p.nazwa FROM historia h, produkty p, zalogowany z WHERE h.idProduktu=p.idProduktu AND h.idKlienta=z.idKlienta GROUP BY p.idProduktu";
        st = connectDB.createStatement();
        rs = st.executeQuery(daneP);
        while (rs.next()) {
            String b = rs.getString("p.nazwa");
            listProd.addAll(b);
        }
        st.close();
        idPrd.getItems().addAll(listProd);
        idPrd.setValue(a);
    }

    public void dodajOpnieOnAction(ActionEvent event) throws SQLException {
        ResultSet rs = null;
        Statement st = null;
        int idOp = 0, idKl = 0, idP = 0;

        String idOpn = "SELECT idOpinia FROM opinie ORDER BY idOpinia ASC";
        st = connectDB.createStatement();
        rs = st.executeQuery(idOpn);
        while (rs.next()) {
            idOp = rs.getInt("idOpinia");
        }
        st.close();

        String idKln = "SELECT idKlienta FROM zalogowany ORDER BY idKlienta ASC";
        st = connectDB.createStatement();
        rs = st.executeQuery(idKln);
        while (rs.next()) {
            idKl = rs.getInt("idKlienta");
        }
        st.close();

        String daneOp = "INSERT INTO opinie (idOpinia,tresc,data,idKlienta,idProduktu)VALUES(?,?,?,?,?)";
        try {
            if (idPrd.getValue().equals("Produkty") || idOpinia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nalezy uzupelnic wczytskie pola.");
            } else {
                String danePr = "SELECT idProduktu FROM produkty WHERE nazwa='" + idPrd.getValue() + "'";
                st = connectDB.createStatement();
                rs = st.executeQuery(danePr);
                while (rs.next()) {
                    idP = rs.getInt("idProduktu");
                }
                st.close();

                pst = (PreparedStatement) connectDB.prepareStatement(daneOp);
                pst.setString(1, String.valueOf(idOp + 1));
                pst.setString(2, idOpinia.getText());
                pst.setString(3, String.valueOf(LocalDate.now()));
                pst.setString(4, String.valueOf(idKl));
                pst.setString(5, String.valueOf(idP));
                pst.execute();
                JOptionPane.showMessageDialog(null, "Opinie dodano pomyslnie.");
                idPrd.setValue("Produkty");
                idOpinia.clear();
                wczytajOpinie();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy dodawaniu! " + e);
        }
    }

    public void usunOpnieOnAction(ActionEvent event) {
        String dane = "DELETE FROM opinie WHERE idOpinia='" + idOp.getCellData(this.index) + "'";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(dane);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Opinia usunieta pomyslnie.");
            idPrd.setValue("Produkty");
            idOpinia.clear();
            wczytajOpinie();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy usuwaniu! " + e);
        }
    }

    public void edytujOpnieOnAction(ActionEvent event) throws SQLException {
        ResultSet rs = null;
        Statement st = null;
        int idp = 0;
        String daneId = "SELECT idProduktu FROM produkty p WHERE nazwa='" + idPrd.getValue() + "'";
        st = connectDB.createStatement();
        rs = st.executeQuery(daneId);
        while (rs.next()) {
            idp = rs.getInt("idProduktu");
        }

        String dane = "UPDATE opinie SET tresc='" + idOpinia.getText() + "',idProduktu='" + idp + "' WHERE idOpinia='" + idOp.getCellData(this.index) + "'";

        try {
            if (idOpinia.getText().isEmpty() || idPrd.getValue().equals("Produkty")) {
                JOptionPane.showMessageDialog(null, "Nalezy uzupelnic wczytskie pola.");
            } else {
                pst = (PreparedStatement) connectDB.prepareStatement(dane);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Opinia edytowana pomyslnie.");
                idPrd.setValue("Produkty");
                idOpinia.clear();
                wczytajOpinie();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy usuwaniu! " + e);
        }
    }

    public void getSelectedOpinia(MouseEvent mouseEvent) {
        this.index = this.Tab1.getSelectionModel().getSelectedIndex();
        if (this.index <= -1) {
            return;
        }
        idOpinia.setText(idTr.getCellData(this.index));
        idPrd.setValue(idPr.getCellData(this.index));
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
            wczytajFaktury();
            wczytajOpinie();
            RodzajeProd();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
