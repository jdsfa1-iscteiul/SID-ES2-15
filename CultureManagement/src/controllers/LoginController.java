package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ClientConnectionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Researcher;


public class LoginController {
	@FXML
	private TextField usernameBox;
	
	@FXML
	private TextField passwordBox;
	
	@FXML
	private Button loginButton;
	
	private String host = "localhost:3306/";
	private String dbName = "grupo15_culture_management";
//	private String username="root";
//	private String password="";
			
	
	
	/*
	 * Deve verificar se os dados de login estao corretos
	 */
	public void handleLoginButton() {
		try {
			String username = usernameBox.getText();
			String password = passwordBox.getText();
			Connection connection = DriverManager.getConnection("jdbc:mariadb://"+host+dbName+"?user="
																			+username+"&password="+password);
			ClientConnectionHandler.getInstance().setDbConnection(connection, username);
//			ClientConnectionHandler.getInstance().setUsername(username);
			
			load_main_scene();
			closeWindow();
		} catch(SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Impossível conectar à base de dados");
			errorAlert.setContentText("O username ou a password estão incorrectos");
			errorAlert.showAndWait();
		}
	}
	
	/*
	 * Se o utilizador se conseguir autenticar o programa deve chamar esta função para carregar o proximo menu.
	 */
	public void load_main_scene() {
		FXMLLoader Loader = new FXMLLoader();
//		if(ClientConnectionHandler.getInstance().getAccountType().equals("Researcher"))
		if(ClientConnectionHandler.getInstance().getUser() instanceof Researcher)
			Loader.setLocation(getClass().getResource("../FXMLfiles/main_gui.fxml"));
		else
			Loader.setLocation(getClass().getResource("../FXMLfiles/adminInicialMenu.fxml"));
		
		try {
			Loader.load();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Main menu");
		stage.show();
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) loginButton.getScene().getWindow();
	    stage.close();
	}
}
