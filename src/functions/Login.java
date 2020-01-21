/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connection.Sqlite_Connection;
import static controller.LoginController.signinAction;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.HomeController;
//import static main.HomeController.body;

/**
 *
 * @author DaCodin
 */
public class Login {

    // setting instance
    private static Login instance = new Login();

    public static Login getInstance() {
        return instance;
    }

    //declaring connection library
    public PreparedStatement pst;
    public ResultSet rs;
    public Connection conn;
    //
    //Declare other variables
    public static String movelevel = null;
    public static String user_name = null;
    //fields data
    private JFXTextField txtField1;
    private JFXPasswordField txtField2;
    private BorderPane bpane;

    //password instance
    public TextField getTxtField2() {
        return txtField2;
    }
    
    public void setTxtField2(JFXPasswordField txtField2) {
        this.txtField2 = txtField2;
    }
    //border pane

    public BorderPane getBpane() {
        return bpane;
    }

    public void setBpane(BorderPane bpane) {
        this.bpane = bpane;
    }
    
    //username instance
    public TextField getTxtField1() {
        return txtField1;
    }

    public void setTxtField1(JFXTextField txtField1) {
        this.txtField1 = txtField1;
    }

    //
    public void loginAction() {
        String query = "SELECT * FROM user WHERE username = ? AND password =?";
        try {
            conn = Sqlite_Connection.DBConnect();
            pst = conn.prepareStatement(query);

            pst.setString(1, txtField1.getText());

            pst.setString(2, txtField2.getText());

            rs = pst.executeQuery();
            if (rs.next()) {
                try {
                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/index.fxml"));
                    gpane.getHeight();
                    gpane.getWidth();
                    bpane.setCenter(gpane);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                movelevel = rs.getString("access");
                user_name = rs.getString("username");
                signinAction();
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
}
