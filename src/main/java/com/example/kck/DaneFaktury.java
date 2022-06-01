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

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getKwota() {
        return kwota;
    }

    public void setKwota(float kwota) {
        this.kwota = kwota;
    }

    public String getProdukty() {
        return produkty;
    }

    public void setProdukty(String produkty) {
        this.produkty = produkty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
