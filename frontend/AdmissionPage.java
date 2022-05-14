package frontend;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class AdmissionPage{
	@FXML private AnchorPane mainAnchor;
	
	public AdmissionPage() {
	}
	
	public abstract Scene getScene(Scene previousScene);
	
	@FXML public abstract void initialize();

}

