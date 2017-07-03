package hardware.camera;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewerCanvasSample extends Application {  
	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		ViewerCanvas viewer = new ViewerCanvas(320, 240);
		viewer.setCurrentURL(new URL("http://192.168.3.30:50001/?action=stream"));
		root.getChildren().add(viewer);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.setWidth(320);
		primaryStage.setHeight(240);
		
		//primaryStage.setFullScreen(true);
		//primaryStage.setFullScreenExitHint("");

		primaryStage.setResizable(false);
		primaryStage.setTitle("JavaFX - MjpegViewer");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
