package frontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;

import backend.Essay;
import backend.TimeComplexity;
import backend.UserCenter.STATUS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class SecondRegisterPage extends AdmissionPage {

    @FXML
    private Button browse;

    @FXML
    private TextField gpaBox;

    @FXML
    private TextArea essay;
    
    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField satScoreBox;

    @FXML
    private Button submitButton;

    private static Scene thisScene;
    private static Scene previousScene;
    
    
    //initalizers:
    public SecondRegisterPage(){
    	
    }

    public static Scene getScene() {
    	return thisScene;
    }
    
    @Override
    public Scene getScene(Scene previous) {
    	previousScene = previous;
    	if(thisScene == null) {
    	try {
    		Parent root = GUIBackEnd.globalLoader.load(getClass().getResource("fxmls/RegisterPageTwo.fxml"));
			thisScene = new Scene(root);
			new Thread(() -> {
			new SplashScreen().getScene(this.thisScene);
			});
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
    	System.out.println("I HAVE BEEN INITALIZED!");
    	String tempEssay = essay.getStyle();
    	//gpa value
    	gpaBox.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			if(newValue.isEmpty()) {
    				gpaBox.setText(newValue);
    				return;
    			}
    			if(newValue.length() >= 4) {
    				gpaBox.setText(oldValue);
    			}
    		
    			
    			if(newValue.matches("[\\d*^\\.]") && !oldValue.matches("\\d*\\.")) {
    				gpaBox.setText(newValue + ".");
    				if(Double.valueOf(newValue) > 4)
    					gpaBox.setText("4.0");
    				else if(Double.valueOf(newValue) <= 0)
    					gpaBox.setText("0.0");
    				return;
    			} else if (newValue.matches("\\d+\\.{2}")) {
    				gpaBox.setText(newValue.replaceAll("[^\\d]", ""));    				
    			}
    		}
    	});
    	
    	gpaBox.focusedProperty().addListener((obs, old, newVal) -> {
    		if(!newVal) {
    			if(gpaBox.getText().isBlank()) return;
    			if(gpaBox.getText().matches("\\d*")) {
    				if(Integer.valueOf(gpaBox.getText()) > 4.0)
    					gpaBox.setText("4.0");
    				else if(Integer.valueOf(gpaBox.getText()) < 0.0)
    					gpaBox.setText("0.0");
    			}
    		}
    	});
    	
    	
    	
    	//sat score
    	satScoreBox.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			if(newValue.isBlank()) return;
    			if(newValue.length() > 4) {
    				satScoreBox.setText(oldValue);
    			}
    			if(!newValue.matches("\\d*")) {
    				satScoreBox.setText(newValue.replaceAll("[^\\d]", ""));
    			}
    			if(newValue.matches("\\d*")) {
    				if(Integer.valueOf(newValue) > 1600)
    					satScoreBox.setText("1600");
    				else if(Integer.valueOf(newValue) < 400 && Integer.valueOf(newValue) > 160)
    					satScoreBox.setText("400");
    			}
    		}
    	});
    	satScoreBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
    		if(!newVal) {
    			if(satScoreBox.getText().isBlank()) return;
    			
    			if(satScoreBox.getText().matches("\\d*")) {
    				if(Integer.valueOf(satScoreBox.getText()) > 1600)
    					satScoreBox.setText("1600");
    				else if(Integer.valueOf(satScoreBox.getText()) < 400)
    					satScoreBox.setText("400");
    			}
    		}
    		
    	});
    	
    	
    	essay.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			if(Essay.wordCount(newValue) > 250) 
    				essay.setStyle( tempEssay + "-fx-border-color:#38ff13;");
    			else
    				essay.setStyle(tempEssay + "-fx-border-color:#FF7900;");
    		}
    	});
    }
    //end of init block
    
    @FXML public void finalizeUser() {
    	//save user + update him + scurtnize him
    	if(!isNotReady()) {
    		GUIBackEnd.getUser().setEssay(essay.getText());
    		GUIBackEnd.getUser().setSatScore(Integer.valueOf(this.satScoreBox.getText()));
    		GUIBackEnd.getUser().setGpa(Double.valueOf(this.gpaBox.getText()));
    		//scurtinze code here...
    		STATUS status = GUIBackEnd.userCenter.scrutinizeUser(GUIBackEnd.getUser());
    		new Thread(() -> {
    			if(GUIBackEnd.userCenter.saveData())
    				System.out.println("save success"); ///save code:)
    			else
    				System.out.println("save not so success"); ///save code:)
    			
    		}).run();
    		//finalize/save code here
    		//switch page to splash...
    		new SplashScreen().reloadSplashScreen(thisScene);
    		switch(status) {
    		case ACCEPTED:
    			GUIBackEnd.changeScene(mainAnchor, new SplashScreen().getScene(thisScene), () -> new SplashScreen().playAcceptedAnimation());
    			return;
    		default:
    			GUIBackEnd.changeScene(mainAnchor, new SplashScreen().getScene(thisScene));
    			return;
    		}

    	}else {
    		System.out.println("something aint right...");
    	}
    }
    
    
    
    private boolean isNotReady() {
    	boolean isRealSatScore = false;
    	try {
    		int temp = Integer.valueOf(this.satScoreBox.getText());
    		isRealSatScore = temp <= 1600 && temp >= 400;
    		System.out.println(isRealSatScore);
    	} catch(Exception e) {}
    	return essay.getText().isBlank() || !isRealSatScore || gpaBox.getText().isBlank();
    }
    
    @FXML
    void doLaterAction(ActionEvent event) {
		GUIBackEnd.changeScene(mainAnchor, new SplashScreen().getScene(thisScene));
		GUIBackEnd.userCenter.saveData();
		//run a dolater code
    }
    
    @FXML
    public void browseFile(ActionEvent event) {
    	String path = "";
    	System.out.println("browsing!");
    	//open file browser, when file loaded(make sure .txt), replace the essayBox text;
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    	File selectedFile = fileChooser.showOpenDialog(GUIBackEnd.getPrimaryStage());
    	if (selectedFile == null) {
    	    System.out.println("No file selected!");
    	    path = "";
    	}
    	else {
    	    path = selectedFile.getPath();
	        BufferedReader br;
	        essay.setText("");
			try {
				br = new BufferedReader(new FileReader(selectedFile));
				while(br.ready()) {
					essay.setText(essay.getText() + br.readLine());
				}
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    

    	}
    	


    }
    
}
