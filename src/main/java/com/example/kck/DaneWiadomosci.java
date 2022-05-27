package com.example.kck;

import java.sql.Date;

public class DaneWiadomosci {
    String temat, tresc, adresat;
    Date data;
    int id;

    public DaneWiadomosci(int id, String temat, String adresat, String tresc, Date data) {
        this.id = id;
        this.temat = temat;
        this.adresat = adresat;
        this.tresc = tresc;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public String getAdresat() {
        return adresat;
    }

    public String getTemat() {
        return temat;
    }

    public String getTresc() {
        return tresc;
    }

    public void setAdresat(String adresat) {
        this.adresat = adresat;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }
}
