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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WiadomosciPracownik implements Initializable {
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    PreparedStatement pst = null;
    int index = -1;
    @FXML
    private Button IdProdukty;
    @FXML
    private Button IdFaktury;
    @FXML
    private Button IdWiadomosci;
    @FXML
    private Button idWyloguj;
    @FXML
    private TableView<DaneWiadomosci> Tab;
    @FXML
    private TableColumn<DaneWiadomosci, Integer> idW;
    @FXML
    private TableColumn<DaneWiadomosci, String> idOd;
    @FXML
    private TableColumn<DaneWiadomosci, String> idTemat;
    @FXML
    private TableColumn<DaneWiadomosci, String> idData;
    @FXML
    private TableColumn<DaneWiadomosci, String> idTrescW;
    @FXML
    private TableView<DaneWiadomosci> Tab1;
    @FXML
    private TableColumn<DaneWiadomosci, Integer> idW1;
    @FXML
    private TableColumn<DaneWiadomosci, String> idOd1;
    @FXML
    private TableColumn<DaneWiadomosci, String> idTemat1;
    @FXML
    private TableColumn<DaneWiadomosci, String> idData1;
    @FXML
    private TableColumn<DaneWiadomosci, String> idTrescW1;
    @FXML
    private Label idDane;
    @FXML
    private Label idKto;
    @FXML
    private TextField idDo;
    @FXML
    private TextField idTemW;
    @FXML
    private TextArea idTresc;
    @FXML
    private Button idOdp;
    @FXML
    private Tab wyslana;
    @FXML
    private Tab odebrana;

    public void odebraneOnAction() throws IOException, SQLException {
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneWiadomosci daneWiadomosci;
        Statement st = null;
        ResultSet rs = null;

        st = connectDB.createStatement();
        String dane = "SELECT idPracownika FROM zalogowany";
        rs = st.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idPracownika");
        }
        st.close();

        st = connectDB.createStatement();
        String dane2 = "SELECT imie, nazwisko FROM pracownik p WHERE p.idPracownika='" + idZal + "'";
        rs = st.executeQuery(dane2);
        while (rs.next()) {
            String imie = rs.getString("imie");
            String nazwisko = rs.getString("nazwisko");
            idDane.setText(imie + " " + nazwisko);
        }
        st.close();

        st = connectDB.createStatement();
        String dane3 = "SELECT w.idWiadomosci,temat,adresat,tresc,data , imie, nazwisko FROM wiadomosci w , pracownik p WHERE w.idPracownika=p.idPracownika and  w.idPracownika='" + idZal + "'AND stan='odebranaK'";
        rs = st.executeQuery(dane3);
        try {
            while (rs.next()) {
                int id = rs.getInt("idWiadomosci");
                String temat = rs.getString("temat");
                String adresat = rs.getString("adresat");
                String tresc = rs.getString("tresc");
                Date data = rs.getDate("data");
                daneWiadomosci = new DaneWiadomosci(id, temat, adresat, tresc, data);
                WczTab.add(daneWiadomosci);
            }
            st.close();
        } catch (Exception e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }

        idW.setCellValueFactory(new PropertyValueFactory<>("id"));
        idTemat.setCellValueFactory(new PropertyValueFactory<>("temat"));
        idData.setCellValueFactory(new PropertyValueFactory<>("data"));
        idOd.setCellValueFactory(new PropertyValueFactory<>("adresat"));
        idTrescW.setCellValueFactory(new PropertyValueFactory<>("tresc"));
        Tab.setItems(WczTab);
    }

    public void wyslaneOnAction() throws IOException, SQLException {
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneWiadomosci daneWiadomosci;
        Statement st = null;
        ResultSet rs = null;

        st = connectDB.createStatement();
        String dane = "SELECT idPracownika FROM zalogowany";
        rs = st.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idPracownika");
        }
        st.close();

        st = connectDB.createStatement();
        String dane2 = "SELECT imie, nazwisko FROM pracownik p WHERE p.idPracownika='" + idZal + "'";
        rs = st.executeQuery(dane2);
        while (rs.next()) {
            String imie = rs.getString("imie");
            String nazwisko = rs.getString("nazwisko");
            idDane.setText(imie + " " + nazwisko);
        }
        st.close();

        st = connectDB.createStatement();
        String dane3 = "SELECT w.idWiadomosci,temat,k.email,tresc,data  FROM wiadomosci w , pracownik p,klient k WHERE w.idPracownika=p.idPracownika and  w.idPracownika='" + idZal + "'AND stan='odebranaP' AND w.idKlienta=k.idKlienta";
        rs = st.executeQuery(dane3);
        try {
            while (rs.next()) {
                int id = rs.getInt("idWiadomosci");
                String temat = rs.getString("temat");
                String adresat = rs.getString("k.email");
                String tresc = rs.getString("tresc");
                Date data = rs.getDate("data");
                daneWiadomosci = new DaneWiadomosci(id, temat, adresat, tresc, data);
                WczTab.add(daneWiadomosci);
            }
            st.close();
        } catch (Exception e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }

        idW1.setCellValueFactory(new PropertyValueFactory<>("id"));
        idTemat1.setCellValueFactory(new PropertyValueFactory<>("temat"));
        idData1.setCellValueFactory(new PropertyValueFactory<>("data"));
        idOd1.setCellValueFactory(new PropertyValueFactory<>("adresat"));
        idTrescW1.setCellValueFactory(new PropertyValueFactory<>("tresc"));
        Tab1.setItems(WczTab);
    }

    public void getSelected() throws SQLException {
        idKto.setText("OD:");
        if (odebrana.isSelected()) {
            this.index = this.Tab.getSelectionModel().getSelectedIndex();
            if (this.index <= -1) {
                return;
            }
            this.idDo.setText(this.idOd.getCellData(this.index));
            this.idTemW.setText(this.idTemat.getCellData(this.index));
            this.idTresc.setText(this.idTrescW.getCellData(this.index));
        } else if (wyslana.isSelected()) {
            this.index = this.Tab1.getSelectionModel().getSelectedIndex();
            if (this.index <= -1) {
                return;
            }
            this.idDo.setText(this.idOd1.getCellData(this.index));
            this.idTemW.setText(this.idTemat1.getCellData(this.index));
            this.idTresc.setText(this.idTrescW1.getCellData(this.index));
        }
    }

    public void wyslijOnActionEvent(ActionEvent event) throws SQLException {
        Statement stat, stat2 = null;
        ResultSet rs, rs2 = null;

        int idw = 0;

        stat2 = connectDB.createStatement();
        String maxID = "SELECT idWiadomosci FROM wiadomosci order BY idWiadomosci ASC";
        rs = stat2.executeQuery(maxID);
        while (rs.next()) {
            idw = rs.getInt("idWiadomosci");
        }
        stat2.close();

        stat2 = connectDB.createStatement();
        String dane = "SELECT idPracownika FROM zalogowany";
        rs = stat2.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idPracownika");
        }
        stat2.close();

        stat = connectDB.createStatement();
        String daneK = "SELECT idKlienta FROM wiadomosci WHERE idWiadomosci='" + idW.getCellData(this.index) + "'";
        rs2 = stat.executeQuery(daneK);
        int idK = 0;
        while (rs2.next()) {
            idK = rs2.getInt("idKlienta");
        }
        stat.close();

        String daneW = "INSERT INTO wiadomosci(idWiadomosci,temat,adresat,tresc,data,idPracownika,idKlienta,stan)values(?,?,?,?,?,?,?,?)";
        try {
            pst = (PreparedStatement) connectDB.prepareStatement(daneW);
            pst.setString(1, String.valueOf(idw + 1));
            pst.setString(2, idTemW.getText());
            pst.setString(3, "biuroObslugi@whiskyMadness.com");
            pst.setString(4, idTresc.getText());
            pst.setString(5, String.valueOf(LocalDate.now()));
            pst.setString(6, String.valueOf(idZal));
            pst.setString(7, String.valueOf(idK));
            pst.setString(8, "odebranaP");
            pst.execute();
            if (wyslana.isSelected()) {
                wyslaneOnAction();
            } else if (odebrana.isSelected()) {
                odebraneOnAction();
            }
            idDo.clear();
            idTresc.clear();
            idTemW.clear();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad dodawania! " + e);
        }
    }

    public void odpowiedzOnActionEvent(ActionEvent event) throws SQLException {
        idKto.setText("OD:");
        idTemW.setText("RE: " + idTemW.getText());
        idTresc.clear();
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
        try {
            odebraneOnAction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
