package frontend;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class LandPage extends AdmissionPage {

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Button registerButton;

    private static Scene thisScene;
    
    private static Stage primaryStage;
    
    

    public static Scene getScene() {
    	return thisScene;
    }
    
	@Override
	public Scene getScene(Scene previousScene) {
    	if(thisScene == null) {
    	try {
    	URL xmlUrl = getClass().getResource("fxmls/IntroScreen.fxml");
    	//loader.setLocation(xmlUrl);
			Parent root = GUIBackEnd.globalLoader.load(xmlUrl);
			thisScene = new Scene(root);
			new Thread(() -> {
			new RegisterPage().getScene(this.thisScene);
			new MoneyScreen().getScene(this.thisScene);
			new LoginPage().getScene(thisScene);
			new SplashScreen().loadAABackGround();
			System.out.println(RegisterPage.getScene() + " scene loaded...");
			}
			).start();
			return thisScene;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("looks like there was an error trying to find the fxml...");
			e.printStackTrace();
		}
    	return null;
    	}
    	else return thisScene;
    }
    
    public void gotoRegisterPage(ActionEvent e) {
    	System.out.println("System Out Ran!");
    	GUIBackEnd.changeScene(mainAnchor, RegisterPage.getScene());
    }
    
    @FXML
    public void goToLoginPage(ActionEvent e) {
    	//im being ran
    	System.out.println("LOGIN PAGE RAN!");
    	GUIBackEnd.changeScene(mainAnchor, LoginPage.getScene());
    	//GUIBackEnd.getPrimaryStage().setScene(LoginPage.getScene());
    }
 
    public LandPage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    

    public LandPage() {

    }

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}


}
