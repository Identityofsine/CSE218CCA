package frontend;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.GroupLayout.Alignment;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SplashScreen extends AdmissionPage {

    @FXML
    private Label addressPlaceHolder;

    @FXML
    private Button comButt;

    @FXML
    private TextArea decision;
    
    @FXML
    private Pane darkPane;

    @FXML
    private TextArea essay;

    @FXML
    private Label familyIncomePlace;

    @FXML
    private Text firstnamePlaceholder;

    @FXML
    private Label emailPlaceholder;
    
    @FXML
    private Text gpaPlaceholder;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Label phonenumberPlaceholder;

    @FXML
    private Text satPlaceholder;

    private static Scene thisScene;
    private static Scene previousScene;
	private ImageView confetti = new ImageView(); 
	private StackPane root = new StackPane();
    
    //initalizers:
    public SplashScreen(){
    	
    }

    public static Scene getScene() {
    	return thisScene;
    }
    @Override
    public Scene getScene(Scene previous) {
    	previousScene = previous;
    	if(thisScene == null) {
    	try {
    		Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/SplashScreen.fxml"));
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
    
    public void loadAABackGround() {
    	new Thread( () -> {
        	root.setStyle("-fx-background-image: url(\"frontend/assets/confetti.gif\"); -fx-font-size: 16px;");
    	}).start();
    }
    
    public void playAcceptedAnimation() {
    	//run code here that makes a new window, that shows confetti and says "Congratulations! You have been Accepted Into Squidward Community College!";
    	// create sample content
    	loadAABackGround();
    	VBox vStack = new VBox();
    	vStack.setPadding(new Insets(10, 50, 50, 50));
    	vStack.setSpacing(10);
    	Text congrats = new Text("Congratulations!\nYou have been accepted Into Squidward Community College!");
    	Text context = new Text("\nYour journey begins now!");
    	congrats.setStyle("-fx-fill: #EEEEEE;");
    	context.setStyle("-fx-fill: #EEEEEE;");
    	congrats.setTextAlignment(TextAlignment.CENTER);
    	vStack.setAlignment(Pos.CENTER);
    	vStack.getChildren().addAll(congrats, context);
    	root.setPrefSize(250, 100);
    	root.getChildren().add(vStack);
    	Parent content = root;
    	// create scene containing the content
    	Scene scene = new Scene(content);
    	Stage window = new Stage();
    	window.setScene(scene);
    	window.show();
    }
    
    public Scene reloadSplashScreen(Scene previous) {
    	
    	previousScene = previous;
    	try {
    		Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/SplashScreen.fxml"));
			thisScene = new Scene(root);
			
			return thisScene;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("looks like there was an error trying to find the fxml...");
			e.printStackTrace();
		}
    	return thisScene;
    }
    
    //weird code
    
    private String parsePhoneNumber(String phoneNumber) {
    	if(phoneNumber.length() < 11) return "N/A";
    	StringBuilder sTE = new StringBuilder(phoneNumber);
    	sTE.insert(0, '+');
    	sTE.insert(2, '(');
    	sTE.insert(6, ')');
    	sTE.insert(10, '-');
    	return sTE.toString();
    }
    
    @FXML
    public void initialize()  {
    	//confetti.setImage(new Image(this.getClass().getResource("assets/confetti.gif").toExternalForm()));
    	loadAABackGround();
    	System.out.println("I HAVE BEEN INITALIZED!");
    	addressPlaceHolder.setText(GUIBackEnd.getUser().getAddress() != null ? GUIBackEnd.getUser().getAddress().toString() : "N/A");
    	emailPlaceholder.setText(GUIBackEnd.getUser().getUsername());
    	essay.setText(GUIBackEnd.getUser().getEssay() != null ? GUIBackEnd.getUser().getEssay() : "N/A");
    	familyIncomePlace.setText("$" + (Integer.toString((int)GUIBackEnd.getUser().getfIncome())));
    	firstnamePlaceholder.setText(GUIBackEnd.getUser().getFirstname() + " " + GUIBackEnd.getUser().getLastname());
    	gpaPlaceholder.setText(GUIBackEnd.getUser().getGpa() != 0 ? Double.toString(GUIBackEnd.getUser().getGpa()) : "N/A");
    	phonenumberPlaceholder.setText(parsePhoneNumber(Long.toString((long) GUIBackEnd.getUser().getPhoneNumber())));
    	satPlaceholder.setText(GUIBackEnd.getUser().getSatScore() != 0 ? Integer.toString(GUIBackEnd.getUser().getSatScore()) : "N/A");
    	//load decision;
    	decision.setStyle("-fx-font-family: monospace");
    	String temp = "You did something you weren't supposed to...\nYou will be met with a terrible fate.";
    	try {
    		File decisionTXT = new File(String.format("./src/decisions/%s-%s-%s.txt", GUIBackEnd.getUser().getID(), GUIBackEnd.getUser().getLastname(), GUIBackEnd.getUser().getFirstname()));
    		try (Scanner input = new Scanner(decisionTXT)) {
    		    while (input.hasNextLine()) {
    		        decision.appendText(input.nextLine());
    		        decision.appendText("\n");
    		    }
    		} catch (FileNotFoundException ex) {
    		    ex.printStackTrace();
    		    decision.setText(temp);
    		}

    	}
    	catch(Exception e) {
    		decision.setText(temp);
    	}
		

    	
    	if(GUIBackEnd.getUser().status != -1) {
    		darkPane.setDisable(true);
    		darkPane.setVisible(false);
    		comButt.setDisable(true);
    	}
    	
    	
    	
    }
    //end of init block
    

    @FXML
    void completeApplication(ActionEvent event) {
    	//finish application page.
    	GUIBackEnd.changeScene(mainAnchor, new SecondRegisterPage().getScene(thisScene));
    }
    
    
}
