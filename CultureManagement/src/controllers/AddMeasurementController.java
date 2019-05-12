package controllers;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.mariadb.jdbc.MariaDbStatement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Culture;
import utilities.Measurement;

public class AddMeasurementController {
	
	@FXML
	private ListView<Culture> culturas_list;
	@FXML
	private ListView<Measurement> variaveis_list;
	@FXML
	private TextField valor_box;
	@FXML
	private Button adicionar_button;
	
	
	//private Connection conn;

	
	public void handleAdicionarButton() {
		//adiciona à base de dados
		closeWindow();
	} 
	
	public void closeWindow() {
	    Stage stage = (Stage) adicionar_button.getScene().getWindow();
	    stage.close();
	}
}
