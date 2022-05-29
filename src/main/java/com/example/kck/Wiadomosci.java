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

public class Wiadomosci implements Initializable {

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

    public void wyswietlWiadomosci() throws SQLException, IOException {
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneWiadomosci daneWiadomosci;
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
        String dane2 = "SELECT imie, nazwisko FROM klient k WHERE k.idKlienta='" + idZal + "'";
        rs = st.executeQuery(dane2);
        while (rs.next()) {
            String imie = rs.getString("imie");
            String nazwisko = rs.getString("nazwisko");
            idDane.setText(imie + " " + nazwisko);
        }
        st.close();

        st = connectDB.createStatement();
        String dane3 = "SELECT w.idWiadomosci,temat,adresat,tresc,data , imie, nazwisko FROM wiadomosci w , klient k WHERE w.idKlienta=k.idKlienta and  w.idKlienta='" + idZal + "'AND stan='odebranaP'";
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

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (this.index <= -1) {
            return;
        }
        idKto.setText("OD:");
        this.idDo.setText(this.idOd.getCellData(this.index));
        this.idTemW.setText(this.idTemat.getCellData(this.index));
        this.idTresc.setText(this.idTrescW.getCellData(this.index));
    }

    public void wyslijOnActionEvent(ActionEvent event) throws SQLException {
        Polaczenie connectNow = new Polaczenie();
        Connection connectDB = connectNow.getConnection();
        Statement stat, stat2 = null;
        ResultSet rs, rs2 = null;

        int idw = 0;

        stat = connectDB.createStatement();
        String daneP = "SELECT idPracownika FROM pracownik";
        rs2 = stat.executeQuery(daneP);
        int idP = 0;
        while (rs2.next()) {
            idP = rs2.getInt("idPracownika");


            stat2 = connectDB.createStatement();
            String maxID = "SELECT idWiadomosci FROM wiadomosci order BY idWiadomosci ASC";
            rs = stat2.executeQuery(maxID);
            while (rs.next()) {
                idw = rs.getInt("idWiadomosci");
            }
            stat2.close();

            stat2 = connectDB.createStatement();
            String dane = "SELECT idKlienta FROM zalogowany";
            rs = stat2.executeQuery(dane);
            int idZal = 0;
            while (rs.next()) {
                idZal = rs.getInt("idKlienta");
            }
            stat2.close();

            String daneW = "INSERT INTO wiadomosci(idWiadomosci,temat,adresat,tresc,data,idPracownika,idKlienta,stan)values(?,?,?,?,?,?,?,?)";
            try {
                pst = (PreparedStatement) connectDB.prepareStatement(daneW);
                pst.setString(1, String.valueOf(idw + 1));
                pst.setString(2, idTemW.getText());
                pst.setString(3, idDo.getText());
                pst.setString(4, idTresc.getText());
                pst.setString(5, String.valueOf(LocalDate.now()));
                pst.setString(6, String.valueOf(idP));
                pst.setString(7, String.valueOf(idZal));
                pst.setString(8, "odebranaK");
                pst.execute();

                wyswietlWiadomosci();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Blad dodawania! " + e);
            }
            JOptionPane.showMessageDialog(null, "Wiadomosc zostala wyslana! ");
        }
        stat.close();
    }

    public void odpowiedzOnActionEvent(ActionEvent event) throws SQLException {
        idKto.setText("OD:");
        idTemW.setText("RE: " + idTemW.getText());
        idTresc.clear();

        idOdp.setOnAction((ActionEvent actionEvent) -> {
            this.index = this.Tab.getSelectionModel().getSelectedIndex();
            if (this.index <= -1) {
                return;
            }

            Polaczenie connectNow = new Polaczenie();
            Connection connectDB = connectNow.getConnection();

            Statement stat, stat2 = null;
            ResultSet rs, rs2 = null;

            int idw = 0;
            try {
                stat = connectDB.createStatement();
                String daneP = "SELECT idPracownika FROM pracownik";
                rs2 = stat.executeQuery(daneP);
                int idP = 0;
                while (rs2.next()) {
                    idP = rs2.getInt("idPracownika");

                    stat2 = connectDB.createStatement();
                    String maxID = "SELECT idWiadomosci FROM wiadomosci order BY idWiadomosci ASC";
                    rs = stat2.executeQuery(maxID);
                    while (rs.next()) {
                        idw = rs.getInt("idWiadomosci");
                    }
                    stat2.close();

                    stat2 = connectDB.createStatement();
                    String dane = "SELECT idKlienta FROM zalogowany";
                    rs = stat2.executeQuery(dane);
                    int idZal = 0;
                    while (rs.next()) {
                        idZal = rs.getInt("idKlienta");
                    }
                    stat2.close();

                    String daneW = "INSERT INTO wiadomosci(idWiadomosci,temat,adresat,tresc,data,idPracownika,idKlienta,stan)values(?,?,?,?,?,?,?,?)";
                    try {
                        pst = (PreparedStatement) connectDB.prepareStatement(daneW);
                        pst.setString(1, String.valueOf(idw + 1));
                        pst.setString(2, idTemW.getText());
                        pst.setString(3, idDo.getText());
                        pst.setString(4, idTresc.getText());
                        pst.setString(5, String.valueOf(LocalDate.now()));
                        pst.setString(6, String.valueOf(idP));
                        pst.setString(7, String.valueOf(idZal));
                        pst.setString(8, "odebranaK");
                        pst.execute();

                        wyswietlWiadomosci();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Blad dodawania! " + e);
                    }
                }
                stat.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Blad dodawania! " + e);
            }
            JOptionPane.showMessageDialog(null, "Wiadomosc zostala wyslana! ");
        });
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
            wyswietlWiadomosci();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
