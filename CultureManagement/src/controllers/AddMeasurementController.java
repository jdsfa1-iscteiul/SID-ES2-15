package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Measurement;

public class AddMeasurementController {
	
	@FXML
	private ListView<Measurement> culturas_list;
	@FXML
	private ListView<Measurement> variaveis_list;
	@FXML
	private TextField valor_box;
	@FXML
	private Button adicionar_button;
	
	public void initialize() {
		
		//Carrega culturas para a lista
		//carrega variaveis para a lista
		
	}
	
	public void handleAdicionarButton() {
		//adiciona à base de dados
		closeWindow();
	} 
	
	public void closeWindow() {
	    Stage stage = (Stage) adicionar_button.getScene().getWindow();
	    stage.close();
	}
}
