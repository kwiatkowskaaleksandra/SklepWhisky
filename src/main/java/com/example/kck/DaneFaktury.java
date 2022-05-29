package com.example.kck;

import java.sql.Date;

public class DaneFaktury {
    String produkty;
    float kwota;
    int ilosc, id;
    Date data;

    public DaneFaktury(int id, String produkty, float kwota, Date data) {
        this.id = id;
        this.produkty = produkty;
        this.kwota = kwota;
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setKwota(float kwota) {
        this.kwota = kwota;
    }

    public void setProdukty(String produkty) {
        this.produkty = produkty;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getIlosc() {
        return ilosc;
    }

    public Date getData() {
        return data;
    }

    public float getKwota() {
        return kwota;
    }

    public String getProdukty() {
        return produkty;
    }

    public int getId() {
        return id;
    }
}
