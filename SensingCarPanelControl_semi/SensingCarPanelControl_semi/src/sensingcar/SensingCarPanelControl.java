package sensingcar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SensingCarPanelControl extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("motor/Motor.fxml")); //AppMain.class.getResource() 똑같음
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SensingCarPanelControl");
        primaryStage.show();

        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
