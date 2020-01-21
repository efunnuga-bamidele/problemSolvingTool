/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import static functions.Login.movelevel;
import static functions.Login.user_name;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author DaCodin
 */
public class HomeController implements Initializable {

    public static String searchMove = null;
    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    @FXML
    public BorderPane body;
    @FXML
    private Menu user;

    @FXML
    private MenuItem level;

    @FXML
    private MenuItem department;
@FXML
    void clode(ActionEvent event) {
Runtime.getRuntime().halt(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        functions.Login.getInstance().setBpane(body);
        //functions.transferDetails.getInstance().setEpane(body); 
        
        
        //User Details
        if (movelevel != null) {
            user.setText("Welcome " + user_name);
            level.setText("AccessLevel : " + movelevel);
        } else {
            user.setText("Welcome Guset");
            level.setText("AccessLevel : None");
            department.setText("Department : None");
        }
//refresh label every 2 seconds
        /*   new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (movelevel != null) {
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> user.setText("Welcome " + user_name)))
                            .play();
                    new Timeline(new KeyFrame(
                            Duration.millis(10),
                            ae -> level.setText("AccessLevel : " + movelevel)))
                            .play();
                } else {
                    user.setText("Welcome Guset");
                    level.setText("AccessLevel : None");
                    department.setText("Department : None");
                }
            }
        }, 2000, 2000);*/
        //end details

        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(menu);
        //Drawer Control
        try {
            AnchorPane main = FXMLLoader.load(getClass().getResource("/interfaces/index.fxml"));
            main.getHeight();
            main.getWidth();
            body.setCenter(main);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            VBox box = FXMLLoader.load(getClass().getResource("/interfaces/drawer.fxml"));
            box.getHeight();
            box.getWidth();

            drawer.setSidePane(box);

            for (Node node : box.getChildren()) {

                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "home": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/index.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;
                            }
                            case "login": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/login.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;
                            }
                            case "solution": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/form.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;

                            }
                            case "category": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/category.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;

                            }
                            case "signup": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/signup.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;

                            }
                            case "activitylog": {
                                try {
                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/activitylog.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                break;

                            }
                            case "signout": {
                                try {
                                    

                                    AnchorPane gpane = FXMLLoader.load(getClass().getResource("/interfaces/index.fxml"));
                                    gpane.getHeight();
                                    gpane.getWidth();
                                    body.setCenter(gpane);

                                } catch (IOException ex) {
                                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                functions.Login.movelevel = null;
                                user.setText("Welcome Guset");
                                level.setText("AccessLevel : None");
                                break;

                            }

                        }
                    });
                }
            }
            transition.setRate(-1);
            menu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();
                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();

                }

            });
        } catch (IOException ex) {

        }
//new refresh concept
        new Thread(() -> {

            while (true) {

                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                     if (movelevel != null) {
                                        user.setText("Welcome " + user_name);
                                        level.setText("AccessLevel : " + movelevel);
                                        } else {
                    user.setText("Welcome Guset");
                    level.setText("AccessLevel : None");
                    department.setText("Department : None");
                }
                latch.countDown();
                });

                try {
      
                    latch.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }).start();
        //end
    }
}
