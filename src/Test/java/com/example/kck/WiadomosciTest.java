package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class WiadomosciTest {
    PreparedStatement pst = null;
    int index = -1;
    Polaczenie connectNow = new Polaczenie();
    Connection connectDB = connectNow.getConnection();
    @Test
    void odebraneOnAction() throws SQLException {
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
    }

    @Test
    public void wyslaneOnAction() throws SQLException, IOException {
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
        }
        st.close();

        st = connectDB.createStatement();
        String dane3 = "SELECT w.idWiadomosci,temat,adresat,tresc,data , imie, nazwisko FROM wiadomosci w , klient k WHERE w.idKlienta=k.idKlienta and  w.idKlienta='" + idZal + "'AND stan='odebranaK' AND w.idPracownika=1";
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
        System.out.println(WczTab);
    }
}