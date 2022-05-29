package com.example.kck;

import java.sql.Date;

public class DaneOpinie {
    String tresc,nazwa,imie,nazwisko;
    String data;
    int idop;

    public DaneOpinie(int idop, String tresc, String data, String imie, String nazwisko, String nazwa) {
        this.data = data;
        this.idop = idop;
        this.tresc = tresc;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.nazwa=nazwa;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setIdop(int idop) {
        this.idop = idop;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getData() {
        return data;
    }

    public int getIdop() {
        return idop;
    }

    public String getTresc() {
        return tresc;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }
}
