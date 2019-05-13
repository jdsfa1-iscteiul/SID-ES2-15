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
	
	/*
	 * Deve verificar se os dados de login estao corretos
	 */
	public void handleLoginButton() {
		/*try {
			String username = usernameBox.getText();
			String password = passwordBox.getText();
			Connection connection = DriverManager.getConnection("jdbc:mariadb://"+host+dbName+"?user="
																			+username+"&password="+password);
			ClientConnectionHandler.getInstance().setDbConnection(connection, username);
			
			if(ClientConnectionHandler.getInstance().getUser() instanceof Researcher)
				load_scene("(admin)initialMenu");
			else
				load_scene("(researcher)initialMenu");
			
			closeWindow();
		} catch(SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Impossível conectar à base de dados");
			errorAlert.setContentText("O username ou a password estão incorrectos");
			errorAlert.showAndWait();
		}	*/
		load_scene("(researcher)initialMenu");
		closeWindow();
//		load_scene("(admin)initialMenu");
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
	    Stage stage = (Stage) loginButton.getScene().getWindow();
	    stage.close();
	}
}
