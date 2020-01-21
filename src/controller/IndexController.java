/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.prism.paint.Color;
import connection.Sqlite_Connection;
import functions.searchResult;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author DaCodin
 */
public class IndexController implements Initializable {

    public static PreparedStatement pst;
    public static ResultSet rs;
    public static Connection conn;
    
    public static String idMove = null;
    public static String machineMove= null;
    public static String equipmentMove= null;
    public static String departmentMove= null;
    public static String fullnameMove= null;
    public static LocalDate dateMove= null;
    public static String descriptionMove= null;
    public static String titleMove= null;
    public static String solutionMove= null;
    public static String galleryMove= null;
    public static String approvalMove= null;
    public static String ratingMove= null;
    private static final int shadowSize = 50;

    
    @FXML
    // JFXTreeTableView<searchResult> result = new JFXTreeTableView<>();
    TableView<searchResult> result = new TableView<>();
    final ObservableList<searchResult> data = FXCollections.observableArrayList();
    @FXML
    private TextField search_bar;
//search result to view / filter table by testfield input

    @FXML
    private void getResult(KeyEvent event) {
        //if(search_bar.getText() != null){
        // result.setVisible(true);
        FilteredList<searchResult> filterData = new FilteredList<>(data, e -> true);
        search_bar.textProperty().addListener((observablevalue, oldValue, newValue) -> {
            filterData.setPredicate((Predicate<? super searchResult>) filterstaff -> {

                if (newValue == null || newValue.isEmpty()) {
                    result.setVisible(false);
                    return true;

                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (filterstaff.getEquipment().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;

                } else if (filterstaff.getMachine().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;
                } else if (filterstaff.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;
                } else if (filterstaff.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;
                } else if (filterstaff.getIn_date().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;
                } else if (filterstaff.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
                    //if(filterstaff.getUID().contains(newValue)){
                    result.setVisible(true);
                    return true;
                }
                return false;
            });

            SortedList<searchResult> sortedData = new SortedList<>(filterData);
            // sortedData.comparatorProperty().bind(Student.comparatorProperty());

            result.setItems(sortedData);
            //}else{
            //   result.setVisible(false);
            // }
        });
    }
    @FXML
    private StackPane stackpane;
//view result in dialog
    @FXML
    void showDialog(MouseEvent event) {
        resultisselected();
           Node main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("/interfaces/information.fxml"));
           
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Information Dialog"));
        content.setBody(main);
         JFXDialog dialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
         content.setStyle("-fx-text-fill:yellow;-fx-font-size:14px;");
         //content.setStyle("-fx-text-fill:yellow;-fx-background-color:rgb(0,0,0,0.5);-fx-font-size:14px;");
         content.setMaxWidth(2600);
         JFXButton button = new JFXButton("Close");
         button.setStyle("-fx-text-fill:yellow;-fx-background-color:black;-fx-font-weight:bold;");
         
         button.setOnAction( new EventHandler<ActionEvent>(){
             @Override
             public void handle(ActionEvent event){
                dialog.close();
               // refresh();
              //  buildData();
             }
         });
       content.setActions(button);
       if(idMove != null){
        dialog.show();
       }else{
      // dialog.show();
       
    }
       
    }
//get click response
    private void resultisselected() {
    idMove = null;
        try {
            conn = Sqlite_Connection.DBConnect();
            searchResult searchR = (searchResult) result.getSelectionModel().getSelectedItem();

            String query = "select * from information where id =?";
            result.getSelectionModel().select(0);

            pst = conn.prepareStatement(query);
            pst.setString(1, searchR.getId());

            // clear();
            rs = pst.executeQuery();
            while (rs.next()) {
                idMove = rs.getString("id");
                machineMove = rs.getString("machine");
                equipmentMove = rs.getString("equipment");
                departmentMove = rs.getString("department");
                fullnameMove = rs.getString("username");
                dateMove = LocalDate.parse(rs.getString("in_date"), DateTimeFormatter.ISO_DATE);
                descriptionMove = rs.getString("description");
                titleMove = rs.getString("title");
                solutionMove = rs.getString("solution");
                galleryMove = rs.getString("gallery_name");
                approvalMove = rs.getString("approval");
                ratingMove = rs.getString("rating");
                
                pst.execute();

            }

        } catch (Exception ex) {

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
    //table data
    public  void buildData() {
        //Table Columns Add
        TableColumn col1 = new TableColumn("ID");
        col1 = new TableColumn("ID");
        //  col1.setMinWidth(40);
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn col2 = new TableColumn("DEPARTMENT");
        col2 = new TableColumn("DEPARTMENT");
        // col2.setMinWidth(40);
        col2.setCellValueFactory(new PropertyValueFactory<>("department"));
        TableColumn col3 = new TableColumn("MACHINE");
        col3 = new TableColumn("MACHINE");
        // col3.setMinWidth(70);
        col3.setCellValueFactory(new PropertyValueFactory<>("machine"));
        TableColumn col4 = new TableColumn("LOCATION");
        col4 = new TableColumn("LOCATION");
        //col4.setMinWidth(70);
        col4.setCellValueFactory(new PropertyValueFactory<>("equipment"));
        TableColumn col5 = new TableColumn("TITLE");
        col5 = new TableColumn("TITLE");
        // col5.setMinWidth(70);
        col5.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn col6 = new TableColumn("STAFF NAME");
        col6 = new TableColumn("STAFF NAME");
        //col6.setMinWidth(70);
        col6.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn col7 = new TableColumn("REGISTER DATE");
        col7 = new TableColumn("REGISTER DATE");
        //col6.setMinWidth(70);
        col7.setCellValueFactory(new PropertyValueFactory<>("in_date"));
        TableColumn col8 = new TableColumn("APPROVAL STATUS");
        col8 = new TableColumn("APPROVAL STATUS");
        //col6.setMinWidth(70);
        col8.setCellValueFactory(new PropertyValueFactory<>("approval"));
        col8.setCellFactory(CheckBoxTableCell.forTableColumn(col8));
       // col8.setCellValueFactory(new PropertyValueFactory<>("approval"));
       
 

        result.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);

        //  tableview.getColumns().addAll(col2, col3, col4, col5);
        try {
            conn = Sqlite_Connection.DBConnect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from information";

            pst = conn.prepareStatement(SQL);
            rs = pst.executeQuery();

            while (rs.next()) {

                String id = rs.getString("id");
                String department = rs.getString("department");
                String machine = rs.getString("machine");
                String equipment = rs.getString("equipment");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String indate = rs.getString("in_date");
                /*boolean approval;
                if(rs.getString("approval") =="1"){
                   approval = Boolean.TRUE;
                }else{
                   approval = Boolean.FALSE; 
                }*/
               boolean approval = rs.getBoolean( "approval");

                data.add(new searchResult(id, department, machine, equipment,
                        title, username, indate, approval));

                //tableview = new TableView();
                //buildData();
            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        } finally {
            result.getItems().setAll(data);
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

    //clear table
    public void refresh() {
        //tableview.getColumns().get(0).setVisible(false);
        result.getColumns().clear();

        data.removeAll(data);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        result.setVisible(false);
        refresh();
        buildData();

    }

}
