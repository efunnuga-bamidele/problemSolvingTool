/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import connection.Sqlite_Connection;

import static controller.IndexController.ratingMove;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author DaCodin
 */
public class FormController implements Initializable {

    public static PreparedStatement pst;
    public static ResultSet rs;
    public static Connection conn;

    @FXML
    private Label status;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField machine;

    @FXML
    private JFXTextField equipment;

    @FXML
    private JFXComboBox<String> department;
    //combobox
    ObservableList<String> options
            = FXCollections.observableArrayList(
                    "..Select Department",
                    "Utilities",
                    "Packaging",
                    "Brewing"
            );

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXDatePicker dateEntry;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextArea solution;

    @FXML
    private JFXButton addPictures;

    @FXML
    private Rating rating;

    @FXML
    private JFXToggleButton approval;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton print;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField title;

    public void clear() {
        department.setValue("..Select Department");
        machine.setText("");
        equipment.setText("");
        title.setText("");
        description.setText("");
        solution.setText("");
        fullName.setText("");
        approval.setSelected(false);
        rating.setRating(0);
        dateEntry.setValue(null);
        id.setText("");
    }

    @FXML
    void clearSolution(ActionEvent event) {
        clear();
    }

    @FXML
    void deleteSolution(ActionEvent event) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation Dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("Are you sure you want to delete this details");
        Optional<ButtonType> action = alert2.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {

                String query = "DELETE FROM information WHERE id = ?";
                conn = Sqlite_Connection.DBConnect();
                pst = conn.prepareStatement(query);
                pst.setString(1, id.getText());
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Information Deleted Sussessfully");
                alert.showAndWait();
                clear();
            } catch (SQLException ex) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);

                alert1.setTitle("Error Dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("" + ex);
                alert1.showAndWait();
                //System.out.print(ex);
                //Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    rs.close();
                    System.out.println("ResultSet Closed");
                } catch (Exception e) {
                    /* ignored */ }
                try {
                    pst.close();
                    System.out.println("PreparedStatement Closed");
                } catch (Exception e) {
                    /* ignored */ }
                try {
                    conn.close();
                    System.out.println("Connection Closed");
                } catch (Exception e) {
                    /* ignored */ }
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User cancel operation");
            alert.showAndWait();
        }
    }

    @FXML
    void editSolution(ActionEvent event) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation Dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("Are you sure you want to update this details");
        Optional<ButtonType> action = alert2.showAndWait();

        if (action.get() == ButtonType.OK) {
            if ((machine.getText().equals("")) || (equipment.getText().equals("")) || (title.getText().equals("")) || (description.getText().equals("")) || (solution.getText().equals("")) || (fullName.getText().equals(""))) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("ERROR : Please Fill All Field with (*) Sign");
                alert.showAndWait();

            } else {

                String query = "UPDATE information SET id = ?, department = ?,machine = ?,equipment = ?,"
                        + " title= ?, description= ?,solution= ?, username= ?, gallery_name= ?, "
                        + "approval= ?, rating= ? WHERE id ='" + id + "' ";//11 inputs

                //database code
                try {
                    conn = Sqlite_Connection.DBConnect();
                    pst = conn.prepareStatement(query);
                    pst.setString(1, id.getText());
                    pst.setString(2, (String) department.getSelectionModel().getSelectedItem());
                    pst.setString(3, machine.getText().toUpperCase());
                    pst.setString(4, equipment.getText().toUpperCase());
                    pst.setString(5, title.getText().toUpperCase());
                    pst.setString(6, description.getText().toLowerCase());
                    pst.setString(7, solution.getText().toLowerCase());
                    pst.setString(8, fullName.getText().toUpperCase());
                    //check here to modify later
                    pst.setString(9, "gallery_" + mac + equ + n);
                    if (approval.isSelected()) {
                        pst.setString(10, "1");
                    } else {
                        pst.setString(10, "0");
                    }
                    pst.setString(11, String.valueOf(rating.getRating()));
                    pst.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Update Sussessful");
                    alert.showAndWait();
                    clear();

                } catch (Exception e) {
                    //System.out.println(e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("ERROR : " + e);
                    alert.showAndWait();

                } finally {
                    try {
                        rs.close();
                        System.out.println("ResultSet Closed");
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pst.close();
                        System.out.println("PreparedStatement Closed");
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                        System.out.println("Connection Closed");
                    } catch (Exception e) {
                        /* ignored */ }

                }
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User cancel operation");
            alert.showAndWait();
        }
    }

    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX
                = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);

        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();

            }
        }
        node.getTransforms().remove(scale);
    }
    @FXML
    private AnchorPane page;

    @FXML
    void printPage(ActionEvent event) {
        try {
            printNode(page);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    char mac;
    char equ;
    int n;

    @FXML
    void gallerySet(ActionEvent event) {
        Random rand = new Random();

        n = rand.nextInt(1000000000) + 1;
        System.out.print(n);
        mac = machine.getText().toLowerCase().charAt(0);
        equ = equipment.getText().toLowerCase().charAt(0);
    }

    @FXML
    void saveSolution(ActionEvent event) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation Dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("Are you sure you want to save this details");
        Optional<ButtonType> action = alert2.showAndWait();

        if (action.get() == ButtonType.OK) {
            if ((machine.getText().equals("")) || (equipment.getText().equals("")) || (title.getText().equals("")) || (description.getText().equals("")) || (solution.getText().equals("")) || (fullName.getText().equals(""))) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("ERROR : Please Fill All Field with (*) Sign");
                alert.showAndWait();
            } else {

                String query = "INSERT INTO information(department, machine, equipment, title, description,"
                        + " solution, username,gallery_name, approval, rating)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?)";//10 INPUTS
                //database code
                try {
                    conn = Sqlite_Connection.DBConnect();
                    pst = conn.prepareStatement(query);
                    pst.setString(1, (String) department.getSelectionModel().getSelectedItem());
                    pst.setString(2, machine.getText().toUpperCase());
                    pst.setString(3, equipment.getText().toUpperCase());
                    pst.setString(4, title.getText().toUpperCase());
                    pst.setString(5, description.getText().toLowerCase());
                    pst.setString(6, solution.getText().toLowerCase());
                    pst.setString(7, fullName.getText().toUpperCase());
                    pst.setString(8, "gallery_" + mac + equ + n);
                    if (approval.isSelected()) {
                        pst.setString(9, "1");
                    } else {
                        pst.setString(9, "0");
                    }
                    pst.setString(10, String.valueOf(rating.getRating()));
                    pst.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Solution Documented Sussessful");
                    alert.showAndWait();
                    clear();

                } catch (Exception e) {
                    //System.out.println(e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("ERROR : " + e);
                    alert.showAndWait();

                } finally {
                    try {
                        rs.close();
                        System.out.println("ResultSet Closed");
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        pst.close();
                        System.out.println("PreparedStatement Closed");
                    } catch (Exception e) {
                        /* ignored */ }
                    try {
                        conn.close();
                        System.out.println("Connection Closed");
                    } catch (Exception e) {
                        /* ignored */ }

                }
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User cancel operation");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department.setItems(options);
        new Thread(() -> {

            while (true) {

                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    if (functions.Login.movelevel == null) {
                        rating.setDisable(true);
                        approval.setDisable(true);
                        save.setDisable(true);
                        edit.setDisable(true);
                        delete.setDisable(true);
                        status.setText("Login To Add Solution");
                    } else if (functions.Login.movelevel.equals("TeamLeader")) {
                        rating.setDisable(false);
                        approval.setDisable(false);
                        save.setDisable(false);
                        edit.setDisable(false);
                        delete.setDisable(false);
                        status.setText("");
                    } else if (functions.Login.movelevel.equals("Operator")) {
                        rating.setDisable(true);
                        approval.setDisable(true);
                        save.setDisable(false);
                        edit.setDisable(false);
                        delete.setDisable(false);
                        status.setText("");
                    } else if (functions.Login.movelevel.equals("SuperAdmin")) {
                        rating.setDisable(false);
                        approval.setDisable(false);
                        save.setDisable(false);
                        edit.setDisable(false);
                        delete.setDisable(false);
                        status.setText("");
                    } else if (functions.Login.movelevel.equals("HOD")) {
                        rating.setDisable(false);
                        approval.setDisable(false);
                        save.setDisable(false);
                        edit.setDisable(false);
                        delete.setDisable(false);
                        status.setText("");
                    } else {
                        rating.setDisable(true);
                        approval.setDisable(true);
                        save.setDisable(true);
                        edit.setDisable(true);
                        delete.setDisable(true);
                    }

                    latch.countDown();
                });

                try {

                    latch.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        ).start();
        //end

        

    }

}
