package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML
	private Button addResearcherButton;
	@FXML
	private Button manageResearcherButton;
	@FXML
	private Button addCultureButton;
	@FXML
	private Button manageCulturesButton;
	@FXML
	private Button addVariableButton;
	@FXML
	private Button manageVariablesButton;
	@FXML
	private Button associateVariablesButton;
	@FXML
	private Button logoutButton;
	
	
	public void handleAddResearcherButton() {
		load_scene("(admin)addResearcherMenu");
	}
	
	public void handleManageResearcherButton() {
		load_scene("(admin)manageResearcherMenu");
	}
	
	public void handleAddCultureButton() {
		load_scene("(admin)addCultureMenu");
	}
	
	public void handleManageCulturesButton() {
		load_scene("(admin)manageCulturesMenu");
	}
	
	public void handleAddVariableButton() {
		load_scene("(admin)addVariableMenu");
	}
	
	public void handleManageVariablesButton() {
		load_scene("(admin)manageVariablesMenu");
	}
	
	public void handleAssociateVariablesButton() {
		load_scene("(admin)associateVariable");
	}
	
	public void handleLogoutButton() {
		load_scene("login");
	}
	
	
	public void load_scene(String scene) {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("../FXMLfiles/"+scene+".fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			Logger.getLogger(ResearcherController.class.getName()).log(Level.SEVERE, null, e);
		}
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.show();
	}
	
	public void closeWindow(Button b) {
	    Stage stage = (Stage) b.getScene().getWindow();
	    stage.close();
	}
	
	
	

}
