package frontend;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import backend.TimeComplexity;
import backend.User;
import backend.UserCenter;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import backend.TimeComplexity;
public class GUIBackEnd extends Application{
	private static User setUser;
	private static Stage primaryStage;
	protected static UserCenter userCenter;
	private static ImageView hourglass = new ImageView(); 
	public static FXMLLoader globalLoader = new FXMLLoader();
	BufferedImage bufferedImage;
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		TimeComplexity.checkTime(() -> UserCenter.getInstance());
		userCenter = UserCenter.getInstance();
		launch(args);
	}
	
    //RUN THIS FILE, NOT DEMO
	//user stuff
	public static User getUser() {
		return setUser;
	}
	
	public static void setUser(User user) {
		setUser = user;
	}
	
	  @Override
	    public void start(Stage primaryStage) { // main stage --> run all components from here...
	        this.primaryStage = primaryStage;
	        //initalize file
	        hourglass.setImage(new Image(this.getClass().getResource("assets/hourglass.gif").toExternalForm()));
	        TimeComplexity.checkTime( () -> {
		  	primaryStage.setTitle("SCC Admission Program.");
	        LandPage lH = new LandPage(primaryStage);
	        primaryStage.setScene(lH.getScene(null));
	        primaryStage.show();
	        });
	    }
	  
	  public static Stage getPrimaryStage() {
		  return primaryStage;
	  }
	  
	  public static void changeScene(AnchorPane mainBackground, Scene nextScene) {
		  Platform.runLater(() -> {
		  StackPane loader = new StackPane(); //390.0" prefWidth="651.0"
		  Text waitText = new Text("Loading");
		  loader.getChildren().add(hourglass);
		  StackPane.setAlignment(waitText,Pos.CENTER);
		  loader.setPrefSize(651.0, 390);
		  loader.setBackground(new Background(new BackgroundFill(Color.web("#EEEEEE"), CornerRadii.EMPTY, Insets.EMPTY)));
		  loader.setOpacity(.5);
		  mainBackground.getChildren().add(loader);
		  mainBackground.getChildren().get(mainBackground.getChildren().size() - 1).toFront(); // Move Square to back of AnchorPane
		  });
		  PauseTransition pause = new PauseTransition(
			        Duration.seconds(.15)
			    );
		  pause.setOnFinished(event -> {
			  TimeComplexity.checkTime(() -> {
		  primaryStage.setScene(nextScene);
		  mainBackground.getChildren().remove(mainBackground.getChildren().size() - 1);
			  });});
		  pause.play();
	  }
	  
	  public static void changeScene(AnchorPane mainBackground, Scene nextScene, Executor function ) {
		  Platform.runLater(() -> {
		  StackPane loader = new StackPane(); //390.0" prefWidth="651.0"
		  Text waitText = new Text("Loading");
		  loader.getChildren().add(hourglass);
		  StackPane.setAlignment(waitText,Pos.CENTER);
		  loader.setPrefSize(651.0, 390);
		  loader.setBackground(new Background(new BackgroundFill(Color.web("#EEEEEE"), CornerRadii.EMPTY, Insets.EMPTY)));
		  loader.setOpacity(.5);
		  mainBackground.getChildren().add(loader);
		  mainBackground.getChildren().get(mainBackground.getChildren().size() - 1).toFront(); // Move Square to back of AnchorPane
		  });
		  PauseTransition pause = new PauseTransition(
			        Duration.seconds(.15)
			    );
		  pause.setOnFinished(event -> {
			  TimeComplexity.checkTime(() -> {
		  primaryStage.setScene(nextScene);
		  mainBackground.getChildren().remove(mainBackground.getChildren().size() - 1);
			  });
			  function.execute();
		      });
		  pause.play();
	  }

}





