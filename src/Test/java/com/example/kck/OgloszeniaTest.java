package com.example.kck;

import com.example.kck.DaneOgloszenia;
import com.example.kck.Polaczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.sql.*;


class OgloszeniaTest {
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    PreparedStatement pst = null;
    @Test
    void wczytajOgloszenia() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        final ObservableList WczTab = FXCollections.observableArrayList();
        DaneOgloszenia daneOgloszenia;

        String danee = "SELECT idOgloszenia,data,tresc FROM ogloszenia order by idOgloszenia ASC";
        st = connectDB.createStatement();
        rs = st.executeQuery(danee);

        while (rs.next()) {
            int id = rs.getInt("idOgloszenia");
            String tresc = rs.getString("tresc");
            Date data = Date.valueOf(rs.getString("data"));
            daneOgloszenia = new DaneOgloszenia(id, tresc, data);
            WczTab.add(daneOgloszenia);
        }
    }
}