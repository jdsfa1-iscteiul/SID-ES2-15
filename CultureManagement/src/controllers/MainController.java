package controllers;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import utilities.Measurement;

public class MainController {
	
	@FXML
	private CheckBox luz_checkbox ;
	@FXML
	private CheckBox temperatura_checkbox ;
	
	@FXML
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

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

	public void handleAddCultureButton() {}
	
	public void handleEditButton() {}
	
	public void handleAdminMenuButton() {}
	
	public void handleFiltrarButton() {}
	
	public void displaySelected() {}
}
