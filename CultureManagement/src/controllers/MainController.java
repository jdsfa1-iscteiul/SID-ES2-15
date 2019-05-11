package controllers;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.Measurement;

public class MainController {
	
	@FXML
	private CheckBox lightCheckbox;
	@FXML
	private CheckBox temperatureCheckbox ;
	
	@FXML
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
//	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
//	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	@FXML
	private ObservableList<Culture> listC = FXCollections.observableArrayList();
	@FXML
	private ListView<Culture> culturesList;
	@FXML
	private ObservableList<Measurement> listM = FXCollections.observableArrayList();
	@FXML
	private ListView<Measurement> measurementsList;

	@FXML
	private Button addButton ;
	@FXML 
	private Button editButton;
	@FXML 
	private Button logoutButton;
	@FXML
	private Button filterButton ;
	@FXML
	private Button seeButton;
	
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

	public void handleAddButton() {
		load_add_measurement_scene();
	}
	
	public void handleEditButton() {}
	
	public void handleLogoutButton() {
		
		load_login_menu();
		closeWindow();
		ClientConnectionHandler.getInstance().resetClientConnection();
	}
	
	public void handleSeeButton() {}
	
	public void handleFilterButton() {}
	
	
	
	public void getCulturesFromDB() {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM culture");
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Culture culture = new Culture(results.getString("culture_name"));
				listC.add(culture);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			String sql = "SELECT * FROM measurements, variable, culture "
					+ "WHERE measurements.variable_id = variable.variable_id"
					+ " AND culture.culture_name = " + "'" + c.getCultureName() + "'" +
					" AND culture.culture_id = measurements.culture_id";
			
			PreparedStatement statement = conn.prepareStatement(sql); 
			
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Measurement measurement = new Measurement(results.getString("variable_name"), 
											results.getString("timestamp"), results.getString("value"));
				listM.add(measurement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load_add_measurement_scene() {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("../FXMLFiles/addMeasurement.fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Adicionar Medição");
		stage.show();
	}
	
	public void load_admin_menu() {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("../FXMLFiles/adminMenu.fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Admin menu");
		stage.show();
	}
	
	public void load_login_menu() {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("../FXMLFiles/login.fxml"));
		try {
			Loader.load();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Login");
		stage.show();
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) logoutButton.getScene().getWindow();
	    stage.close();
	}
}
