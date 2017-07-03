/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensingcar.motor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class MotorController implements Initializable {

    @FXML
    private AnchorPane motorPane;
    @FXML
    private Button btnCameraLeft;
    @FXML
    private Button btnCameraRight;
    @FXML
    private Button btnCameraUp;
    @FXML
    private Button btnCameraDown;
    @FXML
    private Label lblLeftRight;
    @FXML
    private Label lblUpDown;
    @FXML
    private Button btnCameraFront;
    @FXML
    private Button btnHome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
