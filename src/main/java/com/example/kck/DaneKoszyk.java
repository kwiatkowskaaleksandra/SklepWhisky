package com.example.kck;

public class DaneKoszyk {
    String nazwa;
    float cena;
    int ilosc, id;

    public DaneKoszyk(int id, String nazwa, float cena, int ilosc) {
        this.id = id;
        this.nazwa = nazwa;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
