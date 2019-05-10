package controllers;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.stage.Stage;
import utilities.Context;
import utilities.Culture;
import utilities.Measurement;

public class MainController {
	
	@FXML
	private CheckBox luz_checkbox ;
	@FXML
	private CheckBox temperatura_checkbox ;
	
	@FXML
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
//	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
//	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	@FXML
	private ObservableList<Measurement> listM = FXCollections.observableArrayList();
	@FXML
	private ListView<Measurement> measurements_list;

	@FXML
	private Button addCulture_button ;
	@FXML 
	private Button edit_button;
	@FXML 
	private Button adminMenu_button;
	@FXML
	private Button filtrar_button ;

	
	@FXML
	public TextArea measurements_text_area;	
	
	private Connection conn = null;
	
	public void initialize() {
		conn = Context.getInstance().getConn();

		writeDataOnGui();
		
	}
	
	/*reads cultures from DB and writes on gui*/
	private void writeDataOnGui() {
		getMeasurementsFromDB();
		measurements_list.getItems().addAll(listM);
	}

	public void handleAddCultureButton() {
		load_add_measurement_scene();
	}
	
	public void handleEditButton() {}
	
	public void handleAdminMenuButton() {
		
		load_admin_menu();
	}
	
	public void handleFiltrarButton() {}
	
	public void displaySelected() {}
	
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
		stage.setTitle(Context.getInstance().getUsername());
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
	
	public void getMeasurementsFromDB() {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM measurements"); /* Devia ser o SP que permite ver as mediçoes dele*/
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				Measurement measurement = new Measurement();
				listM.add(measurement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
