package controllers;


import java.io.IOException;
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
	private ObservableList<Measurement> list = FXCollections.observableArrayList();

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
	@FXML
	private ListView<Measurement> measurements_list;

	public void initialize() {
		
		writeDataOnGui();
		
	}
	
	/*reads cultures from DB and writes on gui*/
	private void writeDataOnGui() {
		
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
		stage.setTitle("Adicionar medição");
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
}
