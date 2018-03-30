package org.flavigny.mmm.view;

import java.util.ArrayList;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.ManagedObject;
import org.flavigny.mmm.model.Tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTagController {

	@FXML private ComboBox<String> tagNameCombo;
	@FXML private TextField tagValueField;
	@FXML private Button addTagButton;
	@FXML private Button cancelButton;
	
	private MainApp mainApp;
	private Stage dialogStage;
	private ManagedObject managedObject;
	private ObservableList<String> tagNamesList = FXCollections.observableArrayList();
	private boolean okClicked;
	
	public void setMainApplication(MainApp mainApplication) {
		this.mainApp = mainApplication;
	}
	
	public void setDialogStage ( Stage s ) {
		this.dialogStage = s;
	}
	
	public void setManagedObject( ManagedObject o) {
		this.managedObject = o;
		tagNamesList.addAll(mainApp.getDataBase().fetchTagNames(o.getClass().getName()));
	}
	
	public boolean isOkClicked () {
		return okClicked;
	}
	
	@FXML private void initialize() {
		tagNameCombo.valueProperty().addListener(
				(observable,oldvalue,newvalue) -> {
					if ( newvalue.length() == 0 ) {
						addTagButton.setDisable(true);
					} else {
						addTagButton.setDisable(false);
					}
				});
		tagNameCombo.setItems(tagNamesList);
	}
	
	@FXML private void handleOnAction() {
		handleAddTag();
	}

	@FXML private void handleAddTag() {
		okClicked = true;
		Tag t = new Tag();
		t.setName(tagNameCombo.getValue());
		t.setValue(tagValueField.getText());
		mainApp.getDataBase().insertTag(managedObject,t);
		this.dialogStage.close();
	}
	
	@FXML private void handleCancel() {
		okClicked = false;
		dialogStage.close();
	}
}
