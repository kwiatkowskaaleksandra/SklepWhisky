package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class koszykTest {
    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    final ObservableList WczTab = FXCollections.observableArrayList();
    DaneKoszyk daneKoszyk = null;
    Statement st = null;
    ResultSet rs = null;
    @Test
    void wyswietlKoszyk() throws SQLException {

        float cnK = 0;

        st = connectDB.createStatement();
        String dane = "SELECT idKlienta FROM zalogowany";
        rs = st.executeQuery(dane);
        int idZal = 0;
        while (rs.next()) {
            idZal = rs.getInt("idKlienta");
        }
        st.close();

        st = connectDB.createStatement();
        String dane2 = "SELECT k.idKoszyk,k.ilosc, p.cena as cena, p.nazwa as nazwa FROM koszyk k, produkty p WHERE k.idProduktu=p.idProduktu AND k.idKlienta='" + idZal + "'ORDER BY k.idKoszyk ASC";
        rs = st.executeQuery(dane2);
        while (rs.next()) {
            int id = rs.getInt("k.idKoszyk");
            String naz = rs.getString("nazwa");
            int ilosc = rs.getInt("k.ilosc");
            float cena = rs.getFloat("cena");
            daneKoszyk = new DaneKoszyk(id, naz, cena, ilosc);
            WczTab.add(daneKoszyk);
            System.out.println(daneKoszyk.getCena()+"  "+daneKoszyk.getId()+"  "+daneKoszyk.getNazwa());

            cnK += cena * ilosc;
        }

        st.close();
    }
}