package frontend;

import java.net.URL;

import backend.User;
import backend.UserCenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;


public class RegisterPage extends AdmissionPage{
    @FXML
    private TextField firstnameBox;

    @FXML
    private TextField lastnameBox;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private TextField usernameBox;
    
    private static Scene previousScene;
    
    private static Scene thisScene;
    //initalizers
    
    private INPUTERROR err = INPUTERROR.NOERROR;
    
    private enum INPUTERROR{
    	FIRSTNAMEMISSING,
    	LASTNAMEMISSING,
    	FIRSTANDLASTMISSING,
    	NOERROR
    }
    
    public RegisterPage(){

    }
    
    @Override
    public Scene getScene(Scene previous) {
    	previousScene = previous;
    	if(thisScene == null) {
    	try {
    	Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/RegisterPageOne.fxml"));
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
    

    public static Scene getScene() {
    	return thisScene;
    }
    
    
    @FXML
    public void initialize()  {
    	String tempStyle = passwordBox.getStyle();
    	passwordBox.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(UserCenter.validPassword(newValue))
        		passwordBox.setStyle(tempStyle + "-fx-border-color:#38ff13;");
        	else if(!UserCenter.validPassword(newValue))
        		passwordBox.setStyle(tempStyle + "-fx-border-color:#ed0077;");
        	else if (newValue == null || newValue.trim().isEmpty()) {
        		passwordBox.setStyle(tempStyle);
        	}

    	});
    	
    	usernameBox.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(UserCenter.isEmail(newValue))
        		usernameBox.setStyle(tempStyle + "-fx-border-color:#38ff13;");
        	else if(!UserCenter.validPassword(newValue))
        		usernameBox.setStyle(tempStyle + "-fx-border-color:#ed0077;");
        	else if (newValue == null || newValue.trim().isEmpty()) {
        		usernameBox.setStyle(tempStyle);
        	}

    	});
    }

    //functionality
    @FXML
    public void onNext(ActionEvent e) {
    	//-2 : bad username, -1 : user already exists, 0: bad password, 1:successful
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	if(canMoveOn()) {
    		System.out.println("You can move on!");
    		alert.setTitle("Register Error");
    		//move on code
    		//create a new user with this function then pass it to MoneyScreen.
    		//make a if statment to actually see the user exists...
    		User temp = new User(usernameBox.getText(), passwordBox.getText(), -1);
			temp.setFirstname(firstnameBox.getText() != null ? firstnameBox.getText() : "");
			temp.setLastname(lastnameBox.getText() != null ? lastnameBox.getText() : "");

    		switch (GUIBackEnd.userCenter.addUser(temp)){
    		case -2:
    	    	alert.setHeaderText("Bad Username");
    	    	alert.setContentText("Please enter an email for your username...");
    	    	alert.showAndWait();
    			break;
    		case -1:
    	    	alert.setHeaderText("Uh Oh");
    	    	alert.setContentText("This user already exists, You may already have an account with us.");
    	    	alert.showAndWait();
    			break;
    		case 0:
    	    	alert.setHeaderText("Bad password");
    	    	alert.setContentText("Your password doesn't meet our requirements...");
    	    	alert.showAndWait();
    			break;
    		case 1:
    			//continue execution
    			GUIBackEnd.setUser(GUIBackEnd.userCenter.login(usernameBox.getText().toLowerCase(), passwordBox.getText()));
    			GUIBackEnd.changeScene(mainAnchor,new MoneyScreen().getScene());
    			break;
    		default:
    			break;
    		}
    		
    		
    	}else {
    	System.out.println("are you stupid?");
    	//reject error code
    	if(firstnameBox.getText().isBlank() && lastnameBox.getText().isBlank())
    		err = INPUTERROR.FIRSTANDLASTMISSING;
    	else if(firstnameBox.getText().isBlank())
    		err = INPUTERROR.FIRSTNAMEMISSING;
    	else if(lastnameBox.getText().isBlank())
    		err = INPUTERROR.LASTNAMEMISSING;
    	
    	
    	switch(err) {
    		case FIRSTNAMEMISSING:
    		  	alert.setTitle("Firstname Missing");
    		  	alert.setContentText("The \"Firstname\" input is missing");
    		  	alert.showAndWait();
    		  	break;
    		case LASTNAMEMISSING:
    		  	alert.setTitle("Lastname Missing");
    		  	alert.setContentText("");
    		  	alert.showAndWait();
    	    		break;
    		case FIRSTANDLASTMISSING:
    		  	alert.setTitle("Looks like you are missing something...");
    		  	alert.setContentText("Make sure you've inputed everything correctly! Remember your username has to be an email and your password must follow the guidelines listed!");
    		  	alert.showAndWait();
    	    		break;
    		default:
    			break;
    			
    	}
 

    	}
    }
   
    
    @FXML
    public final void goBack(ActionEvent e) {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
    	alert.setTitle("Are you sure?");
    	alert.setHeaderText("Your Progress!?");
    	alert.setContentText("You sure? This is how you get laid after all...");
    	//ButtonType yesOption = new ButtonType("Yes", ButtonData.YES);
    	//ButtonType noOption = new ButtonType("No", ButtonData.NO);
    //	alert.getButtonTypes().setAll(yesOption, noOption);
    	alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    	alert.showAndWait()
    		.filter(response -> response == ButtonType.YES)
    		.ifPresent(response -> {GUIBackEnd.getPrimaryStage().setScene(previousScene); System.out.println("oops"); restart();});
    }
    
    private void restart() {
    	usernameBox.setText("");
    	firstnameBox.setText("");
    	passwordBox.setText("");
    	lastnameBox.setText("");
    }
    
    private boolean canMoveOn() {
    	return !passwordBox.getText().isBlank() && !usernameBox.getText().isBlank() && !firstnameBox.getText().isBlank() && !lastnameBox.getText().isBlank();
    }

    
}
