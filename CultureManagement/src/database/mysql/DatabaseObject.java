package database.mysql;

import javafx.scene.control.CheckBox;

public class DatabaseObject {
	
	protected CheckBox selected;
	
	public DatabaseObject() {
		selected = new CheckBox();
		selected.setSelected(true);
	}

	public DatabaseObject(CheckBox selected) {
		this.selected = selected;
	}
	
	public CheckBox getSelected() {
		return selected;
	}

	public void setSelected(CheckBox selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected.isSelected();
	}
	
	public void setSelected(boolean value) {
		this.selected.setSelected(value);
	}
}
