package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ClientConnectionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.Measurement;
import utilities.Variable;

public class ResearcherActionController {
	
	
	//Menu de ver ou editar medição
	
	@FXML
	private TextField actionCultureBox;
	@FXML
	private TextField actionVariableBox;
	@FXML
	private TextField actionDateBox;
	@FXML
	private TextField actionValueBox;
	
	public void handleSaveButton() {};
	
	public void handleExitButton() {};
	
	
	//Menu adicionar medicao 
	
	@FXML
	private ListView<Measurement> variablesList;
	@FXML
	private TextField valueBox;
	@FXML
	private Button addMeasurementButton;
	
	public void handleAddMeasurementButton() {
		closeWindow(addMeasurementButton);
	}
	
	//Menu associar variável
	
	@FXML
	private Button cancelButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button associateButton;
	
	ListView<Culture> culturesAssociateList;
	
	ListView<Variable> variablesAssociateList;
	
	public void handleAssociateButton() {
		closeWindow(associateButton);
	}
	
	public void handleCancelButton() {
		closeWindow(cancelButton);
	}
	
	public void handleLogoutButton() {
		load_scene("login");
		closeWindow(logoutButton);
		ClientConnectionHandler.getInstance().resetClientConnection();
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
