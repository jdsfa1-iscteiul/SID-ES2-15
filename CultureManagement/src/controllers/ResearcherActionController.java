package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.mysql.ClientConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.Measurement;
import utilities.Researcher;
import utilities.Variable;
import utilities.VariableBoundaries;

public class ResearcherActionController {
	
	
	//Menu de ver ou editar medição
	
	private Researcher researcher;
	
	@FXML
	private TextField actionCultureBox;
	@FXML
	private TextField actionVariableBox;
	@FXML
	private TextField actionDateBox;
	@FXML
	private TextField actionValueBox;
	@FXML
	private Button saveButton;
	@FXML
	private Button exitButton;
	
	public void handleSaveButton() {
		
	}
	
	public void handleExitButton() {
		
	}
	
	public void initialize() {
		researcher = (Researcher)ClientConnectionHandler.getInstance().getUser();
		for(Culture culture: researcher.getCultureList()) {
			if (culture.getResearcher().equals(researcher.getUsername()))
				cultureAssociateAuxList.add(culture);
		}
		culturesAssociateList.setItems(cultureAssociateAuxList);
	}
	
	
	//Menu adicionar medicao 
	@FXML
	private ListView<VariableBoundaries> variablesList;
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
	@FXML
	private Button disassociateButton;
	@FXML
	private ObservableList<Culture> cultureAssociateAuxList = FXCollections.observableArrayList();
	@FXML
	private ListView<Culture> culturesAssociateList;
	@FXML
	private ObservableList<Variable> variablesAssociatedAuxList = FXCollections.observableArrayList();
	@FXML
	private ObservableList<Variable> variablesNotAssociatedAuxList = FXCollections.observableArrayList();
	@FXML
	private ListView<Variable> variablesAssociatedList;
	@FXML
	private ListView<Variable> variablesNotAssociatedList;
	
	@FXML
	private void displayVariablesForSelectedCulture(MouseEvent event) {
		variablesAssociatedAuxList.clear();
		variablesNotAssociatedAuxList.clear();
		Culture culture = this.culturesAssociateList.getSelectionModel().getSelectedItem();
		if (culture != null) {
			for(VariableBoundaries vb: researcher.getMeasurementsData().keySet())
				if(vb.getCulture().equals(culture) && !variablesAssociatedAuxList.contains(vb.getVariable()))
					variablesAssociatedAuxList.add(vb.getVariable());
			for(Variable var: researcher.getVariableList())
				if(!variablesAssociatedAuxList.contains(var))
				variablesNotAssociatedAuxList.add(var);
		}
		variablesAssociatedList.setItems(variablesAssociatedAuxList);
		variablesNotAssociatedList.setItems(variablesNotAssociatedAuxList);
	}
	
	public void handleAssociateButton() {
		closeWindow(associateButton);
	}
	
	public void handleDisassociateButton() {
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
