package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utilities.*;

public class AdminMenuController {
	@FXML
	private TextField nome_box;
	@FXML
	private TextField username_box;
	@FXML
	private TextField titleId_box;
	@FXML
	private PasswordField password_box;
	@FXML
	private PasswordField confirm_box;
	@FXML
	private Button criar_button;
	
	
	@FXML
	private TextField nome_var_box;
	@FXML
	private Button adicionar_var_button;
	
	
	@FXML
	private TextField username_gerir_box;
	@FXML
	private TextField password_gerir_box;
	@FXML
	private TextField nome_gerir_box;
	@FXML
	private TextField titleId_gerir_box;
	@FXML
	private Button save_button;
	@FXML
	private Button remove_button;
	
	
	@FXML
	ListView<Variable> variables_list;
	@FXML
	ListView<Culture> cultures_list;
	@FXML
	private TextField up_box;
	@FXML
	private TextField down_box;
	@FXML
	private Button associate_button;
	
	
	
	
	
	public void handleCriarButton() {}
	
	public void handleAddVarButton() {}
	
	public void handleSaveButton() {}
	
	public void handleRemoveButton() {}
	
	public void handleAssociateButton() {}
	
	
}
