package frontend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.UnaryOperator;

import backend.Address;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

public class MoneyScreen extends AdmissionPage{

    @FXML
    private TextField familyIncomeBox;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField phoneNumberBox;

    @FXML
    private TextField sAddressBox;

    @FXML
    private ComboBox<String> stateBox;

    @FXML
    private Button submitButton;

    @FXML
    private TextField townBox;

    @FXML
    private TextField zipcodeBox;

    private final String[] states = {"AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FM", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };

    private static Scene thisScene;
    private static Scene previousScene;
    
    
    //initalizers:
    public MoneyScreen(){
    	
    }
    

    public static Scene getScene() {
    	return thisScene;
    }
    
    @Override
    public Scene getScene(Scene previous) {
    	previousScene = previous;
    	if(thisScene == null) {
    	try {
    	Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/EnterInfoScreen.fxml"));
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
    

    private void makeFieldNumericalOnly(TextField e, int max, boolean changeStyle) {
    	String tempStyle = phoneNumberBox.getStyle();
    
        	
    	e.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
            		if (oldValue.length() > max - 1) {
            			if(changeStyle)
            			e.setStyle(tempStyle + "-fx-border-color:#38ff13;");
            		}
            		if (newValue.length() > max) {
            			e.setText(oldValue);
            			if(changeStyle)
            			e.setStyle(tempStyle + "-fx-border-color:#38ff13;");
            		}
            		else {
            			if(changeStyle)
            			e.setStyle(tempStyle + "-fx-border-color:#ed0077;");	
            		}
                if (!newValue.matches("\\d*")) {
                    e.setText(newValue.replaceAll("[^\\d]", ""));
                }
               
            }
        });
    }
    @FXML
    public void initialize()  {
    	System.out.println("I HAVE BEEN INITALIZED!");
    	//numbers only
    	makeFieldNumericalOnly(phoneNumberBox, 11, true);
    	makeFieldNumericalOnly(zipcodeBox, 6, false);
    	makeFieldNumericalOnly(familyIncomeBox,8, false);
		new SecondRegisterPage().getScene(this.thisScene);
    	stateBox.getItems().addAll(states);
    }
    //end of init block
    public boolean isNotReady() {
    	boolean phoneNumberLongEnough = false;
    	try {
    		phoneNumberLongEnough = Long.valueOf(phoneNumberBox.getText()) >= 10;
    	}catch (Exception e) {}
    	return familyIncomeBox.getText().isBlank() || phoneNumberBox.getText().isBlank() || !phoneNumberLongEnough || sAddressBox.getText().isBlank() || stateBox.getValue().isBlank() || townBox.getText().isBlank() || zipcodeBox.getText().isBlank();
    }
   
    
    
    @FXML
    public void moveToAcdameicPage(ActionEvent e) {
    	if(!isNotReady()) {
        	//run a if statement to see if everything has been filled in properly.
        	//then in the if statement update all the values in the User class and **save it**.
    		//update user code block...
    		GUIBackEnd.getUser().setAddress(new Address(sAddressBox.getText(), townBox.getText(), stateBox.getValue(), Integer.valueOf(zipcodeBox.getText())));
    		GUIBackEnd.getUser().setPhoneNumber(Long.valueOf(phoneNumberBox.getText()));
    		GUIBackEnd.getUser().setfIncome(Long.valueOf(familyIncomeBox.getText()));
    		//START A THREAD:  save the users info in this spot right here ;)
    		//GUIBackEnd.userCenter.save();
    		//now go to the other page...
    		GUIBackEnd.changeScene(mainAnchor, SecondRegisterPage.getScene());
    	}
    	//debug mode
		

    	
    }

}
