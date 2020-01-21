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
import static controller.IndexController.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author DaCodin
 */
public class InformationController implements  Initializable {

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
    private JFXTextField fullname;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextArea solution;

    @FXML
    private JFXButton gallery;

    @FXML
    private Rating rating;

    @FXML
    private JFXToggleButton approved;

    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton print;

    @FXML
    private JFXTextField title;
    @FXML
    private Label status;
    @FXML
    private JFXDatePicker in_date;

    public void clear() {
        department.setValue("..Select Department");
        machine.setText("");
        equipment.setText("");
        title.setText("");
        description.setText("");
        solution.setText("");
        fullname.setText("");
        approved.setSelected(false);
        rating.setRating(0);
        in_date.setValue(null);
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

    char mac;
    char equ;
    int n;

    @FXML
    void editSolution(ActionEvent event) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation Dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("Are you sure you want to update this details");
        Optional<ButtonType> action = alert2.showAndWait();

        if (action.get() == ButtonType.OK) {
            if ((machine.getText().equals("")) || (equipment.getText().equals("")) || (title.getText().equals("")) || (description.getText().equals("")) || (solution.getText().equals("")) || (fullname.getText().equals(""))) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("ERROR : Please Fill All Field with (*) Sign");
                alert.showAndWait();

            } else {

                String query = "UPDATE information set id = ?, department = ?,machine = ?,equipment = ?,"
                        + " title= ?, description= ?,solution= ?, username= ?, gallery_name= ?, "
                        + "approval= ?, rating= ? WHERE id ='" + id.getText() + "' ";//11 inputs

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
                    pst.setString(8, fullname.getText().toUpperCase());
                    //check here to modify later
                    pst.setString(9, "gallery_" + mac + equ + n);
                    if (approved.isSelected()) {
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
            try {
                printNode(page);
            } catch (InstantiationException ex) {
                Logger.getLogger(InformationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(InformationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InformationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        department.setItems(options);
        id.setText(idMove);
        machine.setText(machineMove);
        equipment.setText(equipmentMove);
        department.getSelectionModel().select(departmentMove);
        fullname.setText(fullnameMove);
        in_date.setValue(dateMove);
        description.setText(descriptionMove);
        title.setText(titleMove);
        solution.setText(solutionMove);
        //gallery.setText(galleryMove);
        if (approvalMove.equals("1")) {
            approved.setSelected(true);
            //System.out.print(approvalMove);
        } else {
            approved.setSelected(false);
            // System.out.print(approvalMove);
        }
        rating.setRating(Double.parseDouble(ratingMove));

        //DATE CONVERTION
        String pattern = "yyyy-M-d";
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        ///////////////////////////////////////////////////// 
        in_date.setConverter(converter);
        in_date.setPromptText(pattern.toLowerCase());
        in_date.requestFocus();

        //Level Timer
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (functions.Login.movelevel == null) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(true)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(true)))
                            .play();
                    //disable buttons
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(true)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(true)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("Login To Edit Information")))
                            .play();
                } else if (functions.Login.movelevel.equals("TeamLeader")) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(false)))
                            .play();
                    //disable buttons

                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(false)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("")))
                            .play();
                } else if (functions.Login.movelevel.equals("Operator")) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(true)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(true)))
                            .play();
                    //disable buttons

                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(false)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("")))
                            .play();
                } else if (functions.Login.movelevel.equals("SuperAdmin")) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(false)))
                            .play();
                    //disable buttons

                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(false)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("")))
                            .play();
                } else if (functions.Login.movelevel.equals("HOD")) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(false)))
                            .play();
                    //disable buttons
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(false)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(false)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("")))
                            .play();
                } else {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> rating.setDisable(true)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> approved.setDisable(true)))
                            .play();
                    //disable buttons
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> edit.setDisable(true)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> delete.setDisable(true)))
                            .play();
                    //Message
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> status.setText("Login To Edit Information")))
                            .play();
                }
            }
        }, 1000, 1000);
        //end details
    }

}
