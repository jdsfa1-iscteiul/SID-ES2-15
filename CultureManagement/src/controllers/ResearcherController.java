package controllers;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ClientConnectionHandler;
//import javafx.beans.binding.Bindings;
//import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.Measurement;

public class ResearcherController {
	
	//Listas
	@FXML
	private ObservableList<Culture> listC = FXCollections.observableArrayList();
	@FXML
	private ListView<Culture> culturesList;
	
	@FXML
	private ObservableList<Measurement> listM = FXCollections.observableArrayList();
	@FXML
	private ListView<Measurement> measurementsList;
	
	@FXML
	private ObservableList<Measurement> listL = FXCollections.observableArrayList();
	@FXML
	private ListView<Measurement> lightList;
	
	@FXML
	private ObservableList<Measurement> listT = FXCollections.observableArrayList();
	@FXML
	private ListView<Measurement> temperatureList;
	
	
	//Buttons
	@FXML
	private Button associateVariableButton ;
	@FXML 
	private Button addMeasurementButton;
	@FXML 
	private Button seeOrEditButton;
	@FXML
	private Button filterButton ;
	@FXML
	private Button logoutButton;
	
	//TextBoxs
	@FXML
	private TextField initialDateBox;
	@FXML
	private TextField finalDateBox;
	
	//handleButtons
	
	public void handleAssociateVariableButton() {
		load_scene("(researcher)associateVariable");
	}
	
	public void handleAddMeasurementButton() {
		load_scene("(researcher)addMeasurement");
	}
	
	public void handleSeeOrEditButton() {
		load_scene("(researcher)seeOrEditMenu");
	}
	
	public void handleFilterButton() {}
	
	public void handleLogoutButton() {
		load_scene("login");
		closeWindow();
		ClientConnectionHandler.getInstance().resetClientConnection();
	}
	
	/*---*/
	
	private Connection conn = null;
	
	public void initialize() {
		conn = ClientConnectionHandler.getInstance().getConnection();
		writeCulturesOnGui();
		
	}
	
	private void writeCulturesOnGui() {
		getCulturesFromDB();
		culturesList.setItems(listC);
	}
	
	/*reads cultures from DB and writes on gui*/
	private void writeMeasurementsOnGui(Culture c) {
		getMeasurementsFromDB(c);
		measurementsList.setItems(listM);
	}

	public void getCulturesFromDB() {
		try {
			ClientConnectionHandler.getInstance().prepareStatement("Call show_cultures");
			ClientConnectionHandler.getInstance().executeStatement();
			ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();

			while(results.next()) {
				Culture culture = new Culture(results.getString("culture_name"));
				listC.add(culture);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void displayMeasurementsForSelectedCulture(MouseEvent event) {
		Culture culture = this.culturesList.getSelectionModel().getSelectedItem();
		if (culture != null) {
			writeMeasurementsOnGui(culture);
		}
	}
	
	public void getMeasurementsFromDB(Culture c) {
		try {
			
			listM.clear();
			
			/* Devia ser o SP que permite ver as mediçoes dele*/
			
			ClientConnectionHandler.getInstance().prepareStatement("CALL load_researcher_data");
			ClientConnectionHandler.getInstance().executeStatement();
			
//			String sql = "SELECT * FROM measurements, variable, culture "
//					+ "WHERE measurements.variable_id = variable.variable_id"
//					+ " AND culture.culture_name = " + "'" + c.getCultureName() + "'" +
//					" AND culture.culture_id = measurements.culture_id";
			
//			PreparedStatement statement = conn.prepareStatement(sql); 
			
//			ResultSet results = statement.executeQuery();
			
			ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
			
			while(results.next()) {
//				Measurement measurement = new Measurement(results.getString("variable_name"), 
//											results.getString("timestamp"), results.getString("value"));
				Measurement measurement = new Measurement(results);
				listM.add(measurement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void closeWindow() {
	    Stage stage = (Stage) logoutButton.getScene().getWindow();
	    stage.close();
	}
}