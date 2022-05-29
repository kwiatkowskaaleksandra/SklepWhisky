package com.example.kck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.time.LocalDate;


public class Weryfikacja {

    @FXML
    private DatePicker idData;
    @FXML
    private Button idWejdz;


    public void weryfikacjaWieku(ActionEvent event) {
        Stage stage = (Stage) idWejdz.getScene().getWindow();

        int localDatee = LocalDate.now().getYear();
        int datee = idData.getValue().getYear();

        int yearr = localDatee - datee;
        if (yearr >= 18) {

            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage menuStage = new Stage();
                menuStage.initStyle(StageStyle.DECORATED);
                menuStage.setScene(new Scene(root, 1360, 768));
                menuStage.setTitle("WHISKY MADNESS");
                menuStage.show();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nie jestes pelnoletni. Strona przeznaczona tylko dla osob powyzej 18 roku zycia.");
        }
    }

}
