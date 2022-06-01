package com.example.kck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class WhiskyKontroler implements Initializable {
    public static int ideProdW;
    PreparedStatement pst = null;
    int index = -1;
    @FXML
    private TableView<DaneProdukty> Tab;
    @FXML
    private TableColumn<DaneProdukty, Integer> idP;
    @FXML
    private TableColumn<DaneProdukty, String> idNazwa;
    @FXML
    private TableColumn<DaneProdukty, String> idOpis;
    @FXML
    private TableColumn<DaneProdukty, Float> idCena;
    @FXML
    private TableColumn<DaneProdukty, String> idObraz;
    @FXML
    private TableColumn<DaneProdukty, Button> idKup;
    @FXML
    private Button IdProdukt;
    @FXML
    private Button IdBeczka;
    @FXML
    private Button IdWhisky;

    public void wyswietlProdukty() {
        Polaczenie connectNow = new Polaczenie();
        Connection connectDB = connectNow.getConnection();
        final ObservableList WczTab = FXCollections.observableArrayList();

        String danee = "SELECT idProduktu, nazwa, cena, opis, obraz, typ FROM produkty WHERE typ='alko' order by idProduktu ASC";
        Statement st = null;
        try {
            st = connectDB.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = Objects.requireNonNull(st).executeQuery(danee);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DaneProdukty daneProdukty;
        try {
            while (Objects.requireNonNull(rs).next()) {
                int id = rs.getInt("idProduktu");
                String naz = rs.getString("nazwa");
                float cena = rs.getFloat("cena");
                String opis = rs.getString("opis");
                String obrazz = rs.getString("obraz");
                String typ = rs.getString("typ");
                ImageView obraz = new ImageView(new Image(this.getClass().getResourceAsStream("images/" + obrazz)));
                obraz.setFitWidth(220);
                obraz.setFitHeight(250);
                daneProdukty = new DaneProdukty(id, naz, cena, opis, obraz, typ);
                WczTab.add(daneProdukty);

            }
            st.close();
        } catch (Exception e) {
            System.out.println("There is an Exception. " + e);
        }


        idP.setCellValueFactory(new PropertyValueFactory<>("id"));
        idNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        idCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        idOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        idObraz.setCellValueFactory(new PropertyValueFactory<>("obraz"));
        Tab.setItems(WczTab);
        addButonToTable();

    }

    private void addButonToTable() {
        TableColumn<DaneProdukty, Void> colBtn = new TableColumn("");
        colBtn.setPrefWidth(150);
        Callback<TableColumn<DaneProdukty, Void>, TableCell<DaneProdukty, Void>> cellFactory = new Callback<TableColumn<DaneProdukty, Void>, TableCell<DaneProdukty, Void>>() {
            public TableCell<DaneProdukty, Void> call(final TableColumn<DaneProdukty, Void> param) {
                final TableCell<DaneProdukty, Void> cell = new TableCell<DaneProdukty, Void>() {
                    private final Button btn = new Button("Dodaj do koszyka");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Polaczenie connectNow = new Polaczenie();
                            Connection connectDB = connectNow.getConnection();

                            Statement stat = null;
                            try {
                                stat = connectDB.createStatement();

                                int idZal = 0;
                                String idZ = "SELECT idKlienta from zalogowany";
                                ResultSet maxz = stat.executeQuery(idZ);
                                while (maxz.next()) {
                                    idZal = maxz.getInt("idKlienta");
                                }
                                stat.close();

                                stat = connectDB.createStatement();

                                int idKosz = 0;
                                String idK = "SELECT idKoszyk from koszyk order by idKoszyk asc";
                                ResultSet maxk = stat.executeQuery(idK);
                                while (maxk.next()) {
                                    idKosz = maxk.getInt("idKoszyk");
                                }
                                stat.close();

                                stat = connectDB.createStatement();
                                float cena = 0;
                                String cn = "SELECT cena FROM produkty p WHERE idProduktu='" + ideProdW + "'";
                                ResultSet cnB = stat.executeQuery(cn);
                                while (cnB.next()) {
                                    cena = cnB.getFloat("cena");
                                }
                                stat.close();

                                String danee = "INSERT INTO koszyk(idKoszyk,idProduktu,cenaCalosc,idKlienta,ilosc)values(?,?,?,?,?)";
                                try {
                                    pst = (PreparedStatement) connectDB.prepareStatement(danee);
                                    pst.setString(1, String.valueOf(idKosz + 1));
                                    pst.setString(2, String.valueOf(ideProdW));
                                    pst.setString(3, String.valueOf(cena));
                                    pst.setString(4, String.valueOf(idZal));
                                    pst.setString(5, String.valueOf(1));
                                    pst.execute();
                                    JOptionPane.showMessageDialog(null, "Dodano pomyslnie!");
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "Blad dodawania! " + e);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getCause();
                            }
                        });
                    }

                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        Tab.getColumns().add(colBtn);
    }

    public void getSelected() throws SQLException {
        this.index = this.Tab.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        ideProdW = this.idP.getCellData(this.index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wyswietlProdukty();
    }

    public void IdProduktOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("NaszeProdukty.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void IdBeczkaOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdBeczka.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("KupBeczke.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void IdWhiskyOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("KupWhisky.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void wylogujOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void politykaOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("polityka-prywatnosci.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void kontaktOnActionEvent(javafx.event.ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("kontakt.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void wiadomosciOnActionEvent(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Wiadomosci.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void koszykOnAction(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("koszyk.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void mojProfilOnActionEvent(ActionEvent event) {
        Stage stage = (Stage) IdWhisky.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Moj_profil.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void homeOnAction(ActionEvent event) {
        Stage stage = (Stage) IdProdukt.getScene().getWindow();
        stage.close();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage menuStage = new Stage();
            menuStage.initStyle(StageStyle.DECORATED);
            menuStage.setTitle("WHISKY MADNESS");
            menuStage.setResizable(false);
            menuStage.setScene(new Scene(root, 1360, 770));
            menuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
