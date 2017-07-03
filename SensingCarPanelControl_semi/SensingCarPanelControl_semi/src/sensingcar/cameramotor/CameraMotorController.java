package sensingcar.cameramotor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sensingcar.cameramotor.camera.ViewerCanvas;

public class CameraMotorController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(CameraMotorController.class);

    private int[] cameraAngles;
    private int fronttireAngle;
    private int backtireSpeed;
    private String backtireDirection;
    private String uri = "192.168.3.30";

    @FXML
    private Button btnCameraLeft;
    @FXML
    private Button btnCameraRight;
    @FXML
    private Button btnCameraUp;
    @FXML
    private Button btnCameraDown;
    @FXML
    private Button btnCameraFront;
    @FXML
    private Label lblLeftRight;
    @FXML
    private Label lblUpDown;
    @FXML
    private Button btnHome;
    @FXML
    private AnchorPane motorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        motorClients();
        btnCameraLeft.setOnAction(e -> handleBtnCamera(e, "left"));
        btnCameraRight.setOnAction(e -> handleBtnCamera(e, "right"));
        btnCameraUp.setOnAction(e -> handleBtnCamera(e, "up"));
        btnCameraDown.setOnAction(e -> handleBtnCamera(e, "down"));
        btnCameraFront.setOnAction(e -> handleBtnCamera(e, "front"));

        btnHome.setOnAction(e -> handleBtnHome(e));
        
        ViewerCanvas viewer = new ViewerCanvas(470, 280);
        try {
            viewer.setCurrentURL(new URL("http://" + uri + ":50001/?action=stream"));
            viewer.setLayoutX(275);
            viewer.setLayoutY(125);
            motorPane.getChildren().add(viewer);
        } catch (MalformedURLException ex) {
        }
        
    }

    private void motorClients() {
        logger.info("");
        CoapClient coapClient = new CoapClient();
        JSONObject resJsonObject = null;
        CoapResponse coapResponse = null;
        String resJson = null;

        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("command", "status");
        String reqJson = reqJsonObject.toString();

        // 카메라 -----------------------------------------------------------------------
        coapClient.setURI("coap://" + uri + "/camera");
        coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
        resJson = coapResponse.getResponseText();
        resJsonObject = new JSONObject(resJson);
        cameraAngles = new int[2];
        cameraAngles[0] = resJsonObject.getInt("leftright");
        cameraAngles[1] = resJsonObject.getInt("updown");
        lblLeftRight.setText(String.valueOf(cameraAngles[0]));
        lblUpDown.setText(String.valueOf(cameraAngles[1]));

        // 앞바퀴 -----------------------------------------------------------------------
        coapClient.setURI("coap://" + uri + "/fronttire");
        coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
        resJson = coapResponse.getResponseText();
        resJsonObject = new JSONObject(resJson);
        fronttireAngle = resJsonObject.getInt("angle");

        // 뒷바퀴 -----------------------------------------------------------------------
        coapClient.setURI("coap://" + uri + "/backtire");
        coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
        resJson = coapResponse.getResponseText();
        resJsonObject = new JSONObject(resJson);
        backtireDirection = resJsonObject.getString("direction");
        backtireSpeed = resJsonObject.getInt("speed");

        // 종료 ---------------------------------------------------
        coapClient.shutdown();
    }

    private void handleBtnCamera(ActionEvent e, String direction) {

        if (direction.equals("left")) {
            cameraAngles[0] += 20;
        } else if (direction.equals("right")) {
            cameraAngles[0] -= 20;
        } else if (direction.equals("up")) {
            cameraAngles[1] += 20;
        } else if (direction.equals("down")) {
            cameraAngles[1] -= 20;
        } else if (direction.equals("front")) {
            cameraAngles[0] = 90;
            cameraAngles[1] = 10;
        }

        CoapClient coapClient = new CoapClient();

        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("command", "change");
        reqJsonObject.put("leftright", String.valueOf(cameraAngles[0]));
        reqJsonObject.put("updown", String.valueOf(cameraAngles[1]));
        String reqJson = reqJsonObject.toString();

        coapClient.setURI("coap://" + uri + "/camera");
        CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
        String resJson = coapResponse.getResponseText();
        coapClient.shutdown();

        JSONObject resJsonObject = new JSONObject(resJson);
        cameraAngles[0] = resJsonObject.getInt("leftright");
        cameraAngles[1] = resJsonObject.getInt("updown");
        lblLeftRight.setText(String.valueOf(cameraAngles[0]));
        lblUpDown.setText(String.valueOf(cameraAngles[1]));
    }

    private void handleBtnHome(ActionEvent e) {
        StackPane rootPane = (StackPane) btnHome.getScene().getRoot();
        motorPane.setOpacity(1);
        KeyValue keyValue = new KeyValue(motorPane.opacityProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500),
                event -> rootPane.getChildren().remove(motorPane),
                keyValue
        );
        Timeline timeLine = new Timeline();
        timeLine.getKeyFrames().add(keyFrame);
        timeLine.play();
    }
}
