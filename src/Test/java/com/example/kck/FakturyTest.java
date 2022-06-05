package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class FakturyTest {
    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();

    @Test
    void wczytajFaktury() throws SQLException {
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
    }
}