package com.example.kck;

import javafx.scene.image.ImageView;

public class DaneProdukty {
    String nazwa, opis, typ;
    float cena;
    int id;
    ImageView obraz;

    public DaneProdukty(int id, String nazwa, float cena, String opis, ImageView obraz, String typ) {
        this.id = id;
        this.nazwa = nazwa;
        this.cena = cena;
        this.opis = opis;
        this.obraz = obraz;
        this.typ = typ;
    }


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public ImageView getObraz() {
        return obraz;
    }

    public void setObraz(ImageView obraz) {
        this.obraz = obraz;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
