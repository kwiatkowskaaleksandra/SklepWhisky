package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OpiniePracownik implements Initializable {

    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    int index = -1;
    PreparedStatement pst = null;

    @FXML
    private TextField idDane;
    @FXML
    private TextArea idTresc;
    @FXML
    private Button idZablokuj;
    @FXML
    private TableView<DaneOpinie> Tab;
    @FXML
    private TableColumn<DaneOpinie, Integer> idOp;
    @FXML
    private TableColumn<DaneOpinie, String> im;
    @FXML
    private TableColumn<DaneOpinie, String> nazw;
    @FXML
    private TableColumn<DaneOpinie, String> prod;
    @FXML
    private TableColumn<DaneOpinie, String> dat;
    @FXML
    private TableColumn<DaneOpinie, String> opi;

    public void wczytajOpinie() throws SQLException {
        Statement st2 = null;
        ResultSet rs2 = null;
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneOpinie daneOpinie;
        String stan = "Zablokowany";
        String daneO = "SELECT o.idOpinia, k.imie, k.nazwisko,o.tresc, o.data, p.nazwa  FROM opinie o, produkty p, klient k WHERE o.idProduktu= p.idProduktu AND o.idKlienta=k.idKlienta AND not k.login = '" + stan + "' ORDER BY o.idOpinia";

        st2 = connectDB.createStatement();
        rs2 = st2.executeQuery(daneO);

        while (rs2.next()) {
            int id = rs2.getInt("o.idOpinia");
            String imie = rs2.getString("k.imie");
            String nazwisko = rs2.getString("k.nazwisko");
            String data = rs2.getString("o.data");
            String tresc = rs2.getString("o.tresc");
            String nazwa = rs2.getString("p.nazwa");
            daneOpinie = new DaneOpinie(id, tresc, data, imie, nazwisko, nazwa);
            WczTab.add(daneOpinie);
        }
        idOp.setCellValueFactory(new PropertyValueFactory<>("idop"));
        opi.setCellValueFactory(new PropertyValueFactory<>("tresc"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        prod.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        im.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazw.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        Tab.setItems(WczTab);

        st2.close();

    }

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (this.index <= -1) {
            return;
        }

        String im = this.im.getCellData(this.index);
        String naz = this.nazw.getCellData(this.index);
        String op = this.opi.getCellData(this.index);
        idDane.setText(naz + " " + im);
        idTresc.setText(op);
    }

    public void zablokujButtonOnAction(ActionEvent event) throws SQLException {
        String dane3 = "SELECT k.idKlienta FROM klient k, opinie o WHERE o.idKlienta=k.idKlienta AND o.idOpinia='" + idOp.getCellData(this.index) + "'";
        ResultSet rs2 = null;
        Statement st = null;

        st = connectDB.createStatement();
        rs2 = st.executeQuery(dane3);
        int idK = 0;
        while (rs2.next()) {
            idK = rs2.getInt("idKlienta");
        }
        String danee = "Update klient set login=?, haslo=? where idKlienta=?";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(danee);
            pst.setString(1, "Zablokowany");
            pst.setString(2, "Zablokowany");
            pst.setString(3, String.valueOf(idK));
            pst.execute();
            idDane.clear();
            idTresc.clear();
            wczytajOpinie();
            JOptionPane.showMessageDialog(null, "Zgłoszono użytkownika!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nie można zgłościć użytkownika. " + e);
        }
        st.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            wczytajOpinie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
