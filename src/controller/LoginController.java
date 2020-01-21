/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.HomeController;

/**
 * FXML Controller class
 *
 * @author DaCodin
 */
public class LoginController extends HomeController implements Initializable  {

   // static String user;
    public static PreparedStatement pst;
    public static ResultSet rs;
    public static Connection conn;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton signin;

    public static void signinAction() {
        if (functions.Login.movelevel.equals("HOD")) {
            // myScreenPane.setScreen("labels");
            System.out.print(functions.Login.movelevel);
        } else if (functions.Login.movelevel.equals("TeamLeader")) {
            System.out.print(functions.Login.movelevel);
        } else if (functions.Login.movelevel.equals("Operator")) {
           System.out.print(functions.Login.movelevel);
        } else if (functions.Login.movelevel.equals("SuperAdmin")) {
            System.out.print(functions.Login.movelevel);
            // myScreenPane.setScreen("labelsf");
        }
    }

    @FXML
    private void Login(ActionEvent evt) {
        functions.Login.getInstance().setTxtField1(username);
        functions.Login.getInstance().setTxtField2(password);
   // connection();
    functions.Login.getInstance().loginAction();
         
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
