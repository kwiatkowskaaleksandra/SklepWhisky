package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class OpiniePracownikTest {
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    int index = -1;
    PreparedStatement pst = null;

    @Test
    void wczytajOpinie() throws SQLException {
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


        st2.close();
    }
}