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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController {
	
	/*Barra lateral*/
	
	@FXML
	private Button addResearcherButton;
	@FXML
	private Button manageResearchersButton;
	@FXML
	private Button addCultureButton;
	@FXML
	private Button manageCulturesButton;
	@FXML
	private Button addVariableButton;
	@FXML
	private Button manageVariablesButton;
	@FXML
	private Button associateVariableButton;
	@FXML
	private Button logoutButton;
	
	
	public void handleAddResearcherButton() {
		load_scene("(admin)addResearcherMenu");
	}
	
	public void handleManageResearchersButton() {
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
	
	public void handleAssociateVariableButton() {
		load_scene("(admin)associateVariable");
	}
	
	public void handleLogoutButton() {
		load_scene("login");
		closeWindow(logoutButton);
		ClientConnectionHandler.getInstance().resetClientConnection();
	}
	
	/* Adicionar Investigador */
	
	@FXML
	private Button addThisResearcherButton;
	@FXML
	private TextField nameUserBox;
	@FXML
	private TextField usernameUserBox;
	@FXML
	private TextField emailUserBox;
	@FXML
	private TextField passwordUserBox;
	@FXML
	private TextField confirmPasswordUserBox;
	@FXML
	private TextField titleIdUserBox;
	
	public void handleAddThisResearcherButton() {
		load_scene("(admin)assResearcherButton");
	}
	
	/* Gerir Investigadores */
	
	
	
	
	/* Adicionar Cultura */
	
	
	
	/* Gerir culturas Cultura */
	
	
	
	/*Adicionar Variável*/
	
	
	
	/* Gerir variáveis */
	
	
	/*Associar variaveis */
	
	
	
	/*Funcoes globais da classe*/
	
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
