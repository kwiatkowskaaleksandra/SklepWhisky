package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DodawanieProduktowTest {
    ObservableList listTyp = FXCollections.observableArrayList();
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    PreparedStatement pst = null;
    int index = -1;
    @Test
    void wyswietlProdukty() throws SQLException {
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
    }

    @Test
    void dodajOnAction() throws SQLException {
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

                pst = (PreparedStatement) connectDB.prepareStatement(dane);
                pst.setString(1, String.valueOf(idProd + 1));
                pst.setString(2, "naz");
                pst.setString(3,"88");
                pst.setString(4,"popso");
                pst.setString(5,"obraz.jpg" );
                pst.setString(6, "alko");
                pst.execute();
                JOptionPane.showMessageDialog(null, "Produkt dodano pomyslnie!");

                wyswietlProdukty();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Blad przy dodawaniu! " + e);
        }
    }

}