package frontend;

import backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPage extends AdmissionPage {

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;
    
    private static Scene thisScene;
    private static Scene previousScene;
    
    
    //initalizers:
    public LoginPage(){
    	
    }

    public static Scene getScene() {
    	return thisScene;
    }
    
    public Scene getScene(Scene previous) {
    	previousScene = previous;
    	if(thisScene == null) {
    	try {
    		Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/LoginPage.fxml"));
			thisScene = new Scene(root);
			
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
    
    @FXML
    public void initialize()  {
    	System.out.println("LOGIN PAGE INIT");
    	
    }
    //end of init block
    

    
    //button handlers
    @FXML
    void goBack(ActionEvent event) {
    	GUIBackEnd.changeScene(mainAnchor,previousScene);
    }

    @FXML
    void login(ActionEvent event) {
    	//if login comesback true, set the GUIBackEnd User to the return value, if the User isn't a fraud, initalize splash
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	User temp = GUIBackEnd.userCenter.login(usernameField.getText().toLowerCase(), passwordField.getText());
    	GUIBackEnd.setUser(temp);
    	if(temp != null) {
    		if(!temp.getUsername().equals("INVALID"))
    		{
    			//check if the user has finished his application...
    			if(temp.status == -1) {
    			alert.setHeaderText("Incomplete!");
    			alert.setContentText("Please complete your application before the deadline!");
    			alert.showAndWait();
    			GUIBackEnd.changeScene(mainAnchor,new SecondRegisterPage().getScene(thisScene));
    				return;
    			} else {
    				GUIBackEnd.changeScene(mainAnchor,new SplashScreen().getScene(thisScene));
    				
    			//send error that the password is wrong!
    			return;
    			}
    		}
    		else {
        			alert.setHeaderText("Invalid Password");
        			alert.setContentText("The password you entered is incorrect...");
        			alert.showAndWait();
        			return;
    			// initalzie page
    			// set user to temp		
    		}
    	}
    	else {
    		alert.setHeaderText("Username Invalid");
    		alert.setContentText("The username you entered doesn't exist...");
    		alert.showAndWait();
    		//send error that the user doesn't exist!
    		return;
    	}
    }

}
