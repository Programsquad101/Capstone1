


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author James, Deelip, Usman
 *
 */
public class Driver extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent pnt=FXMLLoader.load(getClass().getResource("MyModel.fxml")); 
		Scene sn=new Scene(pnt);
		primaryStage.setScene(sn);
		primaryStage.show();
	}
	
	public static void main(String[] a) {
		launch(a);
	}
}
