package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ConnectionMariaDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Context;

public class LoginController {
	@FXML
	private TextField username_box;
	
	@FXML
	private TextField password_box;
	
	@FXML
	private Button login_button;
	
	
	/*
	 * Deve verificar se os dados de login estao corretos
	 */
	public void handleLoginButton() {
		
		ConnectionMariaDB conn = new ConnectionMariaDB();
		
		//if sucesso
		if(conn.connectToDB(username_box.getText(), password_box.getText())) {
			Context.getInstance().setConn(conn);
			load_main_scene();
			closeWindow();
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Impossivel conectar à base de dados");
			errorAlert.setContentText("O username ou a password estão incorrectos");
			errorAlert.showAndWait();
		}	
	}
	
	/*
	 * Se o utilizador se conseguir autenticar o programa deve chamar esta função para carregar o proximo menu.
	 */
	public void load_main_scene() {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("../FXMLFiles/gui.fxml"));
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
	    Stage stage = (Stage) login_button.getScene().getWindow();
	    stage.close();
	}
}
