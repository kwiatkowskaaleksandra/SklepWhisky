package com.example.kck;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;

public class DaneProdukty {
    String nazwa,opis,typ;
    float cena;
    int id;
    ImageView obraz;

    public DaneProdukty(int id, String nazwa, float cena, String opis, ImageView obraz,String typ){
        this.id=id;
        this.nazwa=nazwa;
        this.cena=cena;
        this.opis=opis;
        this.obraz=obraz;
        this.typ=typ;
    }



    public String getNazwa() {
        return nazwa;
    }

    public int getId() {
        return id;
    }

    public float getCena() {
        return cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getObraz() {
        return obraz;
    }

    public void setObraz(ImageView obraz) {
        this.obraz = obraz;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }
}
