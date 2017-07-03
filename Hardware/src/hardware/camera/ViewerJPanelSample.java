package hardware.camera;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;

public class ViewerJPanelSample extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> {
            ViewerJPanel viewer = new ViewerJPanel(350, 300);
            try {
                viewer.setCurrentURL(new URL("http://172.30.1.23:50001/?action=stream"));
            } catch (MalformedURLException ex) {}
            swingNode.setContent(viewer);
        });
        root.getChildren().add(swingNode);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(350);
        primaryStage.setHeight(300);   
        /*
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        */
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
