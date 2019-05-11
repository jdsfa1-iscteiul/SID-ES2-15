package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.mysql.ClientConnectionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminAddVariableController {
		
		@FXML
		private TextField variableNameBox;
		@FXML
		private Button addButton;
		@FXML
		public void handleAddButton() {}
	
	
		//PARA O MENU LATERAL DO ADMIN FUNCIONAR
		
		@FXML
		private Button addResearcherButton;
		@FXML
		private Button addVariableButton;
		@FXML
		private Button manageResearchersButton;
		@FXML
		private Button logoutButton;
		@FXML
		private Button associateVariableButton;
		
		public void handleAddResearcherButton() {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("../FXMLfiles/adminAddResearcher.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
			}
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Adicionar Investigador");
			stage.show();
			closeWindow(addResearcherButton);
		}
		
		public void handleAddVariableButton() {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("../FXMLfiles/adminAddVariable.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
			}
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Adicionar Investigador");
			stage.show();
			closeWindow(addVariableButton);
		}
		
		public void handleManageResearcherButton() {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("../FXMLfiles/adminManageResearchers.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
			}
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Gerir Investigadores");
			stage.show();
			closeWindow(addVariableButton);
		}

		public void handleAssociateVariableButton() {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("../FXMLfiles/adminAssociateVariables.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
			}
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Associar Variáveis");
			stage.show();
			closeWindow(associateVariableButton);
		}

		public void handleLogoutButton () {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("../FXMLFiles/login.fxml"));
			try {
				Loader.load();
			} catch (IOException e) {
				Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
			}
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Login");
			stage.show();
			closeWindow(logoutButton);
			ClientConnectionHandler.getInstance().resetClientConnection();
		}
		
		public void closeWindow(Button b) {
		    Stage stage = (Stage) b.getScene().getWindow();
		    stage.close();
		}
}
