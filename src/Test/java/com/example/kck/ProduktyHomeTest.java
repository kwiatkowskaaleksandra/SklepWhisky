package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

class ProduktyHomeTest {

    @Test
    void wyswietlProdukty() throws SQLException {
        Polaczenie connectNow = new Polaczenie();
        Connection connectDB = connectNow.getConnection();
        final ObservableList WczTab = FXCollections.observableArrayList();
        Statement st = null;
        ResultSet rs = null;
        DaneProdukty daneProdukty;

        String danee = "SELECT idProduktu, nazwa, cena, opis, obraz, typ FROM produkty order by idProduktu ASC";
        st=connectDB.createStatement();
        rs=st.executeQuery(danee);

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

    }

}