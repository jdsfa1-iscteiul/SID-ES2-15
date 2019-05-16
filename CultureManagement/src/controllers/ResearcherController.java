package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.LightMeasurement;
import utilities.Measurement;
import utilities.Researcher;
import utilities.TemperatureMeasurement;
import utilities.UtilityFunctions;
import utilities.Variable;
import utilities.VariableBoundaries;

public class ResearcherController {

	@FXML
	private Label researcherNameLabel, currentDateLabel;
	
	@FXML
	private ObservableList<Culture> observableCultureList = FXCollections.observableArrayList();
	@FXML
	private ListView<Culture> culturesList;

	@FXML
	private ObservableList<Measurement> observableMeasurementList = FXCollections.observableArrayList();

	@FXML
	private ObservableList<Variable> observableVariableList = FXCollections.observableArrayList();

	@FXML
	private TableView<Measurement> measurementsTableView;

	@FXML
	private TableView<Variable> variableTableView;

	@FXML
	private TableView<Culture> cultureTableView;

	@FXML
	private CheckBox cultureTableColumnCheckbox;

	@FXML
	private CheckBox variableTableColumnCheckBox;

	@FXML
	private DatePicker beginningDatePicker;

	@FXML
	private DatePicker endingDatePicker;

	@FXML
	private Spinner<Integer> beginningHourSpinner, beginningMinuteSpinner, beginningSecondSpinner;
	
	@FXML
	private Spinner<Integer> endingHourSpinner, endingMinuteSpinner, endingSecondSpinner;

	@FXML
	private TableColumn<Culture, String> cultureColumnCultureTable;
	@FXML
	private TableColumn<Culture, CheckBox> checkBoxColumnCultureTable;

	@FXML
	private TableColumn<Variable, String> variableColumn;

	@FXML
	private TableColumn<Variable, CheckBox> checkBoxColumn;

	@FXML
	private TableColumn<Measurement, String> cultureColumnMeasurementsTable, variableColumnMeasurementsTable, timestampColumnMeasurementsTable;

	@FXML
	private TableColumn<Measurement, Float> lowerBoundColumnMeasurementsTable, upperBoundColumnMeasurementsTable, valueColumnMeasurementsTable;

	@FXML
	private ObservableList<LightMeasurement> observableLightMeasurementList = FXCollections.observableArrayList();
	@FXML
	private TableView<LightMeasurement> lightTableView;

	@FXML
	private ObservableList<TemperatureMeasurement> observableTemperatureMeasurementList = FXCollections.observableArrayList();
	@FXML
	private TableView<TemperatureMeasurement> temperatureTableView;
	
	@FXML
	private TableColumn<TemperatureMeasurement, String> measurementColumnTemperatureTable, timestampColumnTemperatureTable;
	
	@FXML
	private TableColumn<LightMeasurement, String> measurementColumnLightTable, timestampColumnLightTable;


	@FXML
	private Button associateVariableButton ;
	@FXML 
	private Button addMeasurementButton;
	@FXML 
	private Button seeOrEditButton;
	@FXML
	private Button filterButton ;
	@FXML
	private Button manageAccountButton;
	@FXML
	private Button logoutButton;

	
	@FXML
	private TextField initialDateBox;
	@FXML
	private TextField finalDateBox;

	private Researcher researcher;
	
	
	public void initialize() {
		researcher = (Researcher)ClientConnectionHandler.getInstance().getUser();
		researcherNameLabel.setText(researcher.getUsername());
		currentDateLabel.setText(UtilityFunctions.getCurrentDate());
		writeCulturesOnGui();
		writeVariablesOnGui();
		writeSensorMeasurementsOnGui();
		writeMeasurementsOnGui();
		endingDatePicker.setValue(LocalDate.now());
		initializeSpinners();
	}

	//handleButtons

	public void handleAssociateVariableButton() {
		load_scene("(researcher)associateVariable");
	}

	public void handleAddMeasurementButton() {
		load_scene("(researcher)addMeasurement");
	}
	
	public void handleManageAccountButton() {
		load_scene("(researcher)manageAccount");
	}

	public void handleSeeOrEditButton() {
		if(measurementsTableView.getSelectionModel().getSelectedItem() != null) {
			ClientConnectionHandler.getInstance().setSelectedMeasurement(measurementsTableView.getSelectionModel().getSelectedItem());
			load_scene("(researcher)seeOrEditMenu");
		}else{
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Impossivel Ver ou Editar");
			errorAlert.setContentText("Selecione primeiro uma cultura e uma das medições da mesma");
			errorAlert.showAndWait();
		}
	}

	public void handleFilterButton() {
		observableMeasurementList.clear();
		writeSensorMeasurementsOnGui();
		for(Culture culture: cultureTableView.getItems()) 
			if(culture.isSelected()) 
				for(Variable variable: variableTableView.getItems()) 
					if(variable.isSelected()) 
						for(VariableBoundaries vb: researcher.getMeasurementsData().keySet())
							if(vb.getCulture().equals(culture) && vb.getVariable().equals(variable))
								observableMeasurementList.addAll(researcher.getMeasurementsData().get(vb));
		applyDataFilter();
		measurementsTableView.setItems(observableMeasurementList);
		temperatureTableView.setItems(observableTemperatureMeasurementList);
		lightTableView.setItems(observableLightMeasurementList);
	}

	public void handleLogoutButton() {
		load_scene("login");
		closeWindow();
		ClientConnectionHandler.getInstance().resetClientConnection();
	}
	
	public void handleVariableCheckBox() {
		for(Variable variable: variableTableView.getItems())
			variable.setSelected(variableTableColumnCheckBox.isSelected());
	}
	
	public void handleCultureCheckBox() {
		for(Culture culture: cultureTableView.getItems())
			culture.setSelected(cultureTableColumnCheckbox.isSelected());
	}

	private void writeVariablesOnGui() {
		variableTableColumnCheckBox.setSelected(true);
		observableVariableList.clear();
		observableVariableList.addAll(ClientConnectionHandler.getInstance().getUser().getVariableList());
		variableTableView.setItems(observableVariableList);
	}

	private void writeSensorMeasurementsOnGui() {
		observableLightMeasurementList.clear();
		observableTemperatureMeasurementList.clear();
		observableLightMeasurementList.addAll(ClientConnectionHandler.getInstance().getSystem().getLightMeasurements());
		observableTemperatureMeasurementList.addAll(ClientConnectionHandler.getInstance().getSystem().getTemperatureMeasurements());
		lightTableView.setItems(observableLightMeasurementList);
		temperatureTableView.setItems(observableTemperatureMeasurementList);
	}

	private void writeCulturesOnGui() {
		cultureTableColumnCheckbox.setSelected(true);
		observableCultureList.clear();
		observableCultureList.addAll(researcher.getResearcherCultureList());
//		cultureColumnCultureTable.setCellValueFactory(new PropertyValueFactory<>("cultureName"));
//		checkBoxColumnCultureTable.setCellValueFactory(new PropertyValueFactory<>("selected"));
		cultureTableView.setItems(observableCultureList);
	}
	
	private void writeMeasurementsOnGui() {
		observableMeasurementList.clear();
		observableMeasurementList.addAll(researcher.getAllMeasurements());
		measurementsTableView.setItems(observableMeasurementList);
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
	
	private void initializeSpinners() {
		SpinnerValueFactory<Integer> beginningHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
		SpinnerValueFactory<Integer> beginningMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		SpinnerValueFactory<Integer> beginningSecondFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		SpinnerValueFactory<Integer> endingHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 23);
		SpinnerValueFactory<Integer> endingMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 59);
		SpinnerValueFactory<Integer> endingSecondFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		beginningHourSpinner.setValueFactory(beginningHourFactory);
		beginningMinuteSpinner.setValueFactory(beginningMinuteFactory);
		beginningSecondSpinner.setValueFactory(beginningSecondFactory);
		endingHourSpinner.setValueFactory(endingHourFactory);
		endingMinuteSpinner.setValueFactory(endingMinuteFactory);
		endingSecondSpinner.setValueFactory(endingSecondFactory);
	}
	
	private void applyDataFilter() {
		Timestamp beginningTimestamp, endingTimestamp;
		int hour, minute, second;
		if(beginningDatePicker.getValue() != null) {
			hour = beginningHourSpinner.getValue();
			minute = beginningMinuteSpinner.getValue();
			second = beginningSecondSpinner.getValue();
			beginningTimestamp = UtilityFunctions.formatAsTimestamp(beginningDatePicker, hour, minute, second);
			filterAllMeasurementsBeforeTimestamp(beginningTimestamp);
		}
		if(endingDatePicker.getValue() != null) {
			hour = endingHourSpinner.getValue();
			minute = endingMinuteSpinner.getValue();
			second = endingSecondSpinner.getValue();
			endingTimestamp = UtilityFunctions.formatAsTimestamp(endingDatePicker, hour, minute, second);
			filterAllMeasurementsAfterTimestamp(endingTimestamp);
		}
	}

	private void filterAllMeasurementsBeforeTimestamp(Timestamp timestamp) {
		Iterator<Measurement> measurementsIterator = observableMeasurementList.iterator();
		while(measurementsIterator.hasNext())
			if(Timestamp.valueOf(measurementsIterator.next().getTimestamp()).before(timestamp))
				measurementsIterator.remove();
		Iterator<LightMeasurement> lightIterator = observableLightMeasurementList.iterator();
		while(lightIterator.hasNext())
			if(Timestamp.valueOf(lightIterator.next().getTimestamp()).before(timestamp))
				lightIterator.remove();
		Iterator<TemperatureMeasurement> temperatureIterator = observableTemperatureMeasurementList.iterator();
		while(temperatureIterator.hasNext())
			if(Timestamp.valueOf(temperatureIterator.next().getTimestamp()).before(timestamp))
				temperatureIterator.remove();
	}
	
	private void filterAllMeasurementsAfterTimestamp(Timestamp timestamp) {
		Iterator<Measurement> measurementsIterator = observableMeasurementList.iterator();
		while(measurementsIterator.hasNext()) 
			if(Timestamp.valueOf(measurementsIterator.next().getTimestamp()).after(timestamp)) 
				measurementsIterator.remove();
		Iterator<LightMeasurement> lightIterator = observableLightMeasurementList.iterator();
		while(lightIterator.hasNext())
			if(Timestamp.valueOf(lightIterator.next().getTimestamp()).after(timestamp))
				lightIterator.remove();
		Iterator<TemperatureMeasurement> temperatureIterator = observableTemperatureMeasurementList.iterator();
		while(temperatureIterator.hasNext())
			if(Timestamp.valueOf(temperatureIterator.next().getTimestamp()).after(timestamp))
				temperatureIterator.remove();
	}

	public void closeWindow() {
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
	}
}
