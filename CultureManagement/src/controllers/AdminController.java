package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ClientConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Administrator;
import utilities.Culture;
import utilities.Researcher;
import utilities.Variable;

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
	private Button logoutButton;
	
	private Administrator admin;
	
	/* Comum a todos */
	public void initialize() {
		admin = (Administrator)ClientConnectionHandler.getInstance().getUser();
		if(researchersList!=null) {
			writeResearchersOnGui();
		} 
		if(researcherCultureAddList!=null) {
			writeResearchersOnGuiToAddCulture();
		} 
		if(culturesList!=null) {
			writeCulturesOnGuiToManageCulture();
		}
		
		if(variablesList!=null) {
			writeVariableOnGuiToManageVariable();
		}
	}
	
	
	public void handleAddResearcherButton() {
		load_scene("(admin)addResearcherMenu");
		closeWindow(logoutButton);
	}
	
	public void handleManageResearchersButton() {
		load_scene("(admin)manageResearcherMenu");
		closeWindow(logoutButton);
	}
	
	public void handleAddCultureButton() {
		load_scene("(admin)addCultureMenu");
		closeWindow(logoutButton);
	}
	
	public void handleManageCulturesButton() {
		load_scene("(admin)manageCulturesMenu");
		closeWindow(logoutButton);
	}
	
	public void handleAddVariableButton() {
		load_scene("(admin)addVariableMenu");
		closeWindow(logoutButton);
	}
	
	public void handleManageVariablesButton() {
		load_scene("(admin)manageVariablesMenu");
		closeWindow(logoutButton);
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
		String email = emailUserBox.getText();
		String name = nameUserBox.getText();
		String username =usernameUserBox.getText();
		String pass = passwordUserBox.getText();
		String title = titleIdUserBox.getText();
		String sqlCommand = "CALL create_researcher('"+email+"','"+pass+"','"+name+"','"+username+"','"+title+"')";
		try {
			ClientConnectionHandler.getInstance().executeStatement(sqlCommand);
			this.admin.updateResearcherList();
			load_scene("(admin)initialMenu");
			closeWindow(addThisResearcherButton);
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Impossível criar investigador");
			errorAlert.setContentText("Dados duplicados ou incorretos");
			errorAlert.showAndWait();
		}
	}
	
	/* Gerir Investigadores */
	
	@FXML
	ObservableList<Researcher> listR = FXCollections.observableArrayList();
	@FXML
	private ListView<Researcher> researchersList;
	@FXML
	private Button removeResearcherButton;
	@FXML
	private Button editResearcherButton;
	
	public void handleRemoveResearcherButton() throws SQLException {
		Researcher researcher = this.researchersList.getSelectionModel().getSelectedItem();
		String sqlC = "DELETE FROM researcher WHERE username_db='"; 
		System.out.println(sqlC+researcher.getUsername());
		try {
			ClientConnectionHandler.getInstance().executeStatement(sqlC+researcher.getUsername()+"'");
			admin.getResearcherList().remove(researcher);
			load_scene("(admin)initialMenu");
			closeWindow(removeResearcherButton);
			//closeWindow(removeResearcherButton);
		} catch (SQLException e) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText("Impossível apagar");
		errorAlert.setContentText("Este investigador tem culturas e medições associadas.");
		errorAlert.showAndWait();
		}
	}
	
	public void handleEditResearcherButton() {

	}
	
	public void writeResearchersOnGui() {
		listR.clear();
		getResearchersFromDB();
		researchersList.setItems(listR);
	}
	
	public void getResearchersFromDB() {
		for(Researcher r: admin.getResearcherList()) {
			listR.add(r);
		}
	}
	
	/* Adicionar Cultura */
	
	@FXML
	private Button addThisCultureButton;
	@FXML
	private TextField cultureNameBox;
	@FXML
	private TextField cultureDescriptionBox;
	@FXML 
	private ListView<Researcher> researcherCultureAddList;
	@FXML
	ObservableList<Researcher> listRC = FXCollections.observableArrayList();
	
	public void handleAddThisCultureButton() throws SQLException {
		addCultureToDB();
	}
	
	private void addCultureToDB() throws SQLException {
		Researcher researcher = this.researcherCultureAddList.getSelectionModel().getSelectedItem();
			if (researcher!=null) {
				String name = cultureNameBox.getText();
				String desc = cultureDescriptionBox.getText();
				String values="'"+name+"','"+desc+"','"+researcher.getUsername()+"'";
				String d = "INSERT INTO culture(`culture_name`, `culture_description`, `researcher`) VALUES ("+values+")";
				System.out.println(d);
//				admin.updateCultureList();
				admin.updateAllLists();
				ClientConnectionHandler.getInstance().executeStatement(d);
				
			}
	}
	
	public void writeResearchersOnGuiToAddCulture() {
		listRC.clear();
		getResearchersFromDBToAddCulture();
		researcherCultureAddList.setItems(listRC);
	}
	
	public void getResearchersFromDBToAddCulture() {
		for(Researcher r: admin.getResearcherList()) {
			listRC.add(r);
		}
	}
	
	/* Gerir Cultura */
	@FXML
	private ListView<Culture> culturesList;
	@FXML
	ObservableList<Culture> listCULL = FXCollections.observableArrayList();
	
	@FXML
	private Button editCultureButton;
	@FXML
	private Button removeCultureButton;
	
	public void handleEditCultureButton() {

	}
	public void handleRemoveCultureButton() {
		Culture culture = this.culturesList.getSelectionModel().getSelectedItem();
		if (culture!=null) {
			String id = String.valueOf(culture.getCultureId());
			String sql = "DELETE FROM culture WHERE culture_id="+id;
			System.out.println(sql);
			try {
				ClientConnectionHandler.getInstance().executeStatement(sql);
				admin.getCultureList().remove(culture);
				load_scene("(admin)initialMenu");
				closeWindow(removeCultureButton);
			} catch (SQLException e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Impossível apagar");
				errorAlert.setContentText("Esta cultura tem medições associadas.");
				errorAlert.showAndWait();
			}
		}
	}
	
	public void writeCulturesOnGuiToManageCulture() {
		listCULL.clear();
		getCulturesFromDBToManageCulture();
		culturesList.setItems(listCULL);
	}
	
	public void getCulturesFromDBToManageCulture() {
		for(Culture c: admin.getCultureList()) {
			listCULL.add(c);
		}
	}
	
	/*Adicionar Variável*/
	@FXML
	private TextField variableNameBox;
	@FXML
	private TextField upperLimitBox;
	@FXML
	private TextField lowerLimitBox;
	@FXML
	private Button addConfirmVariableButton;
	
	public void handleAddConfirmVariableButton() throws SQLException {
		String name = variableNameBox.getText();
		String d = "INSERT INTO variable(`variable_name`) VALUES ('"+name+"')";
		System.out.println(d);
		ClientConnectionHandler.getInstance().executeStatement(d);
//		admin.updateVariableList();
		admin.updateAllLists();
		load_scene("(admin)initialMenu");
		closeWindow(addConfirmVariableButton);
	}
	
	
	/* Gerir variáveis */
	@FXML
	private ListView<Variable> variablesList;
	@FXML
	ObservableList<Variable> listV = FXCollections.observableArrayList();
	@FXML
	private Button removeVariableButton;
	@FXML
	private Button editVariableButton;
	
	
	public void handleRemoveVariableButton() {
		Variable variable = this.variablesList.getSelectionModel().getSelectedItem();
		if (variable!=null) {
			String id = String.valueOf(variable.getVariableId());
			String sql = "DELETE FROM variable WHERE variable_id="+id;
			System.out.println(sql);
			try {
				ClientConnectionHandler.getInstance().executeStatement(sql);
				admin.getVariableList().remove(variable);
				load_scene("(admin)initialMenu");
				closeWindow(removeVariableButton);
			} catch (SQLException e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Impossível apagar");
				errorAlert.setContentText("Esta variável tem medições associadas.");
				errorAlert.showAndWait();
			}
		}
	}
	
	public void writeVariableOnGuiToManageVariable() {
		getVariablesFromDBToManageVariable();
		variablesList.setItems(listV);
	}
	
	public void getVariablesFromDBToManageVariable() {
		for(Variable v: admin.getVariableList()) {
			listV.add(v);
		}
	}
	
	public void handleEditVariableButton() {

	}
	/*Associar variaveis */
	@FXML
	private Button associateButton;
	@FXML
	private Button desassociateButton;
	@FXML
	
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
