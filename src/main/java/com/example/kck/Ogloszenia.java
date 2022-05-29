package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Ogloszenia implements Initializable {

    int index=-1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    PreparedStatement pst = null;

    @FXML
    private TableView<DaneOgloszenia> Tab;
    @FXML
    private TableColumn<DaneOgloszenia,Integer> ido;
    @FXML
    private TableColumn<DaneOgloszenia,String> ogl;
    @FXML
    private TableColumn<DaneOgloszenia, Date> dat;
    @FXML
    private TextArea idTresc;
    @FXML
    private DatePicker idData;

    public void wczytajOgloszenia() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneOgloszenia daneOgloszenia;

        String danee = "SELECT idOgloszenia,data,tresc FROM ogloszenia order by idOgloszenia ASC";
        st=connectDB.createStatement();
        rs=st.executeQuery(danee);

        while (rs.next()){
            int id=rs.getInt("idOgloszenia");
            String tresc = rs.getString("tresc");
            Date data = Date.valueOf(rs.getString("data"));
            daneOgloszenia = new DaneOgloszenia(id,tresc,data);
            WczTab.add(daneOgloszenia);
        }
        ido.setCellValueFactory(new PropertyValueFactory<>("id"));
        ogl.setCellValueFactory(new PropertyValueFactory<>("tresc"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        Tab.setItems(WczTab);
    }

    public void usunButtonOnAction(ActionEvent event) {
        String danee="DELETE FROM ogloszenia WHERE idOgloszenia=?";
        try {
            pst=(PreparedStatement) connectDB.prepareStatement(danee);
            pst.setString(1, String.valueOf(ido.getCellData(this.index)));
            pst.execute();
            JOptionPane.showMessageDialog(null,"Usunieto pomyslnie!");
            wczytajOgloszenia();
            idTresc.clear();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Blad przy usuwaniu! "+e);
        }
    }

    public void edytujButtonOnAction(ActionEvent event) {
        try {
            int id1 = ido.getCellData(this.index);
            String tr = idTresc.getText();
            Date dat = Date.valueOf(idData.getValue());

            String danee = "UPDATE ogloszenia SET idOgloszenia='" + id1 + "' ,data='" + dat + "',tresc='" +tr+ "'WHERE idOgloszenia='" + id1 + "'";
            pst = (PreparedStatement) connectDB.prepareStatement(danee);

            if (idTresc.getText().isEmpty()) {
                throw new Exception();
            } else {
                pst.execute();
                JOptionPane.showMessageDialog(null, "Edycja zakonczona pomyslnie!");
                wczytajOgloszenia();
                idTresc.clear();
            }

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Blad przy edycji! " + e);
        }
    }

    public void getSelected(MouseEvent mouseEvent) {
        index=Tab.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        idTresc.setText(ogl.getCellData(index));
        idData.setValue(LocalDate.parse(dat.getCellData(index).toString()));
    }

    public void dodajButtonOnAction(ActionEvent event) throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        st=connectDB.createStatement();
        String danee="SELECT * FROM ogloszenia order by idOgloszenia ASC";
        rs=st.executeQuery(danee);

        int ido = 0;
        while(rs.next()){
            ido=rs.getInt("idOgloszenia");
        }

        String dane="INSERT INTO ogloszenia(idOgloszenia,data,tresc)values(?,?,?)";
        try{
            pst=(PreparedStatement)connectDB.prepareStatement(dane);
            pst.setString(1, String.valueOf(ido+1));
            pst.setString(2, String.valueOf(idData.getValue()));
            pst.setString(3,idTresc.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Dodano pomyslnie!");
            idTresc.clear();
            wczytajOgloszenia();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Blad dodawania! "+e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            wczytajOgloszenia();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
