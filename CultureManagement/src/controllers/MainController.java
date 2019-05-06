package controllers;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import utilities.Measurement;

public class MainController {
	
	@FXML
	private CheckBox luz_checkbox ;
	@FXML
	private CheckBox temperatura_checkbox ;
	
	@FXML
	private Button filtrar_button ;
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

	@FXML
	private ObservableList<Measurement> list = FXCollections.observableArrayList();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	@FXML
	private Button add_button ;
	@FXML 
	private Button edit_button;
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

	public void handleAddButton() {}
	
	public void handleEditButton() {}
	
	public void handleFiltrarButton() {}
	
	public void handleLoginButton() {}
	
	public void displaySelected() {}
}
