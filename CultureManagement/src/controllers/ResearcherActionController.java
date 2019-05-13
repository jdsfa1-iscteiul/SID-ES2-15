package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.mysql.ClientConnectionHandler;
import gmail.BDAGmailClient;
import gmail.GmailAccount;
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
	
	@FXML
	private ListView<Culture> culturesListSeeEdit;
	@FXML
	private ListView<Variable> variablesListSeeEdit;

	@FXML
	private void displayVariablesForSeeEdit() {
		variablesListAddAux.clear();
		Culture culture = this.culturesListSeeEdit.getSelectionModel().getSelectedItem();
		if (culture != null) {
			for(VariableBoundaries vb: researcher.getVariableBoundariesList())
				if(vb.getCulture().equals(culture) && !variablesListAddAux.contains(vb.getVariable()))
					variablesListAddAux.add(vb.getVariable());
		}
		variablesListSeeEdit.setItems(variablesListAddAux);
	}

	public void handleSaveButton() {
		
		actionValueBox.getText();
		
		if(variablesListSeeEdit.getSelectionModel().getSelectedItem() != null) {
			Culture culture = culturesListSeeEdit.getSelectionModel().getSelectedItem();
			Variable variable = variablesListSeeEdit.getSelectionModel().getSelectedItem();

			try {
				String sqlCommand = "Update measurements Set "; 
				sqlCommand += "timestamp = '"+actionDateBox.getText()+"', value = "+actionValueBox.getText()+" where timestamp = '" + ClientConnectionHandler.getInstance().getSelectedMeasurement().getTimestamp() +"'";
				System.out.println(sqlCommand);
				ClientConnectionHandler.getInstance().prepareStatement(sqlCommand);
				ClientConnectionHandler.getInstance().executeStatement();
				researcher.updateResearcherLists();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		ClientConnectionHandler.getInstance().setSelectedMeasurement(null);
		closeWindow(exitButton);
	}

	public void handleExitButton() {
		ClientConnectionHandler.getInstance().setSelectedMeasurement(null);
		closeWindow(exitButton);
	}

	public void initialize() {
		researcher = (Researcher)ClientConnectionHandler.getInstance().getUser();
		for(Culture culture: researcher.getCultureList()) {
			if (culture.getResearcher().equals(researcher.getUsername()))
				cultureAssociateAuxList.add(culture);
		}
		if(culturesAssociateList != null)
			culturesAssociateList.setItems(cultureAssociateAuxList);
		if(culturesListAddMeasurement != null)
			culturesListAddMeasurement.setItems(cultureAssociateAuxList);
		if(culturesListSeeEdit!=null)
			culturesListSeeEdit.setItems(cultureAssociateAuxList);
	}


	//Menu adicionar medicao 
	@FXML
	private ObservableList<Culture> culturesListAddAux = FXCollections.observableArrayList();
	@FXML
	private ListView<Culture> culturesListAddMeasurement;
	@FXML
	private ObservableList<Variable> variablesListAddAux = FXCollections.observableArrayList();
	@FXML
	private ListView<Variable> variablesListAddMeasurement;
	@FXML
	private TextField valueBox;
	@FXML
	private Button addMeasurementButton;

	public void handleAddMeasurementButton() {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		if(variablesListAddMeasurement.getSelectionModel().getSelectedItem() != null) {
			Culture culture = culturesListAddMeasurement.getSelectionModel().getSelectedItem();
			Variable variable = variablesListAddMeasurement.getSelectionModel().getSelectedItem();

			try {
				String sqlCommand = "INSERT INTO measurements (culture_id, variable_id,timestamp, value) "; 
				sqlCommand += "VALUES ("+culture.getCultureId()+","+variable.getVariableId()+",'"+time+"',"+valueBox.getText()+")";
				System.out.println(sqlCommand);
				ClientConnectionHandler.getInstance().prepareStatement(sqlCommand);
				ClientConnectionHandler.getInstance().executeStatement();
				checkForAlerts(culture, variable, Float.parseFloat(valueBox.getText()));
				researcher.updateResearcherLists();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		closeWindow(addMeasurementButton);
	}
	
	private void checkForAlerts(Culture culture, Variable variable, Float value) {
		for(VariableBoundaries vb: researcher.getVariableBoundariesList())
			if(vb.getCulture().equals(culture) && vb.getVariable().equals(variable))
				if(value > vb.getUpperBound() || value < vb.getLowerBound()) {
					GmailAccount acc = new GmailAccount("investigadorSIDES@gmail.com", "123grupo15");
					BDAGmailClient client = new BDAGmailClient(acc);
					client.reply("investigadorSIDES@gmail.com", "alert", "mediçao fora dos limites: " + value, "cumprimentos", researcher.getName());
					
				}
	}
	
	@FXML
	private void displayVariablesForAddMeasurement() {
		variablesListAddAux.clear();
		Culture culture = this.culturesListAddMeasurement.getSelectionModel().getSelectedItem();
		if (culture != null) {
			for(VariableBoundaries vb: researcher.getVariableBoundariesList())
				if(vb.getCulture().equals(culture) && !variablesListAddAux.contains(vb.getVariable()))
					variablesListAddAux.add(vb.getVariable());
		}
		variablesListAddMeasurement.setItems(variablesListAddAux);
	}

	//Menu associar variável

	@FXML
	private Button cancelButton;
	@FXML
	private Button associateButton;
	@FXML
	private Button disassociateButton;
	@FXML
	private TextField variableLowerBound;
	@FXML
	private TextField variableUpperBound;
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
			for(VariableBoundaries vb: researcher.getVariableBoundariesList())
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
		if(variablesNotAssociatedList.getSelectionModel().getSelectedItem() != null) {
			Culture culture = culturesAssociateList.getSelectionModel().getSelectedItem();
			Variable variable = variablesNotAssociatedList.getSelectionModel().getSelectedItem();
			String lowerBound = variableLowerBound.getText();
			String upperBound = variableUpperBound.getText();
			VariableBoundaries newVb = 
					new VariableBoundaries(culture, variable, Float.parseFloat(lowerBound), Float.parseFloat(upperBound));
			try {
				String sqlCommand = "INSERT INTO variable_boundaries(culture_id, variable_id, lower_bound, upper_bound) "; 
				sqlCommand += "VALUES ("+culture.getCultureId()+","+variable.getVariableId()+","+lowerBound+","+upperBound+")";
				ClientConnectionHandler.getInstance().prepareStatement(sqlCommand);
				ClientConnectionHandler.getInstance().executeStatement();
				researcher.getVariableBoundariesList().add(newVb);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		closeWindow(associateButton);
	}

	public void handleDisassociateButton() {
		if(variablesAssociatedList.getSelectionModel().getSelectedItem() != null) {
			Culture culture = culturesAssociateList.getSelectionModel().getSelectedItem();
			Variable variable = variablesAssociatedList.getSelectionModel().getSelectedItem();
			VariableBoundaries oldVb = 
					new VariableBoundaries(culture, variable, 0,0);
			try {
				String sqlCommand = "DELETE FROM variable_boundaries ";
				sqlCommand+="WHERE culture_id = " + culture.getCultureId() + " AND " + "variable_id = " + variable.getVariableId();
				ClientConnectionHandler.getInstance().prepareStatement(sqlCommand);
				ClientConnectionHandler.getInstance().executeStatement();
				researcher.getVariableBoundariesList().remove(oldVb);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		closeWindow(associateButton);
	}

	public void handleCancelButton() {
		closeWindow(cancelButton);
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
