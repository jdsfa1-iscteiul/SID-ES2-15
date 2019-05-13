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
		closeWindow(logoutButton);
		load_scene("(admin)addResearcherMenu");
	}
	
	public void handleManageResearchersButton() {
		closeWindow(logoutButton);
		load_scene("(admin)manageResearcherMenu");
	}
	
	public void handleAddCultureButton() {
		closeWindow(logoutButton);
		load_scene("(admin)addCultureMenu");
	}
	
	public void handleManageCulturesButton() {
		closeWindow(logoutButton);
		load_scene("(admin)manageCulturesMenu");
	}
	
	public void handleAddVariableButton() {
		closeWindow(logoutButton);
		load_scene("(admin)addVariableMenu");
	}
	
	public void handleManageVariablesButton() {
		closeWindow(logoutButton);
		load_scene("(admin)manageVariablesMenu");
	}
	
	public void handleAssociateVariableButton() {
		closeWindow(logoutButton);
		load_scene("(admin)associateVariablesMenu");
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
		
	}
	
	/* Gerir Investigadores */
	@FXML
	private ListView researchersList;
	@FXML
	private Button removeResearcherButton;
	@FXML
	private Button editResearcherButton;
	
	
	public void handleRemoveResearcherButton() {

	}
	
	public void handleEditResearcherButton() {

	}
	
	/* Adicionar Cultura */
	
	@FXML
	private Button addThisCultureButton;
	
	public void handleAddThisCultureButton() {

	}
	
	/* Gerir Cultura */
	@FXML
	private ListView culturesList;
	@FXML
	private Button editCultureButton;
	@FXML
	private Button removeCultureButton;
	@FXML
	private Button associateCultureButton;
	
	public void handleEditCultureButton() {

	}
	public void handleRemoveCultureButton() {

	}
	public void handleAssociateCultureButton() {

	}
	
	/*Adicionar Variável*/
	@FXML
	private TextField variableNameBox;
	@FXML
	private TextField upperLimitBox;
	@FXML
	private TextField lowerLimitBox;
	
	
	/* Gerir variáveis */
	@FXML
	private ListView variablesList;
	@FXML
	private Button removeVariableButton;
	@FXML
	private Button editVariableButton;
	
	
	public void handleRemoveVariableButton() {

	}
	
	public void handleEditVariableButton() {

	}
	/*Associar variaveis */
	@FXML
	private Button associateButton;
	@FXML
	private Button desassociateButton;
	@FXML
	private ListView culturesAssociateList;
	@FXML
	private ListView variablesAssociateList;
	
	public void handleAssociateButton() {

	}
	public void handleDesassociateButton() {

	}
	
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
