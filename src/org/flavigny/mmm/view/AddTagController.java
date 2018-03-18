package org.flavigny.mmm.view;

import java.util.ArrayList;

import javax.print.attribute.standard.DialogTypeSelection;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.Album;
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
	private Object albumOrRelease;
	private ObservableList<String> tagNamesList = FXCollections.observableArrayList();
	private boolean okClicked;
	
	public void setMainApplication(MainApp mainApplication) {
		this.mainApp = mainApplication;
	}
	
	public void setDialogStage ( Stage s ) {
		this.dialogStage = s;
	}
	
	public void setAlbumOrRelease( Object o) {
		this.albumOrRelease = o;
		ArrayList<Tag> tagList = new ArrayList<>();
		if ( albumOrRelease instanceof Album ) {
			tagList.addAll(mainApp.getDataBase().fetchAlbumTags());
		} else {
			tagList.addAll( mainApp.getDataBase().fetchReleaseTags());
		}
		for ( Tag t : tagList ) {
			tagNamesList.add(t.getName());
		}
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

	@FXML private void handleAddTag() {
		okClicked = true;
		Tag t = new Tag();
		t.setId( ((Album) albumOrRelease).getAlbumId());
		t.setName(tagNameCombo.getValue());
		t.setValue(tagValueField.getText());
		if ( albumOrRelease instanceof Album ) {
			mainApp.getDataBase().insertAlbumTag(t);
		} else {
			mainApp.getDataBase().insertReleaseTag(t);
		}
		this.dialogStage.close();
	}
	
	@FXML private void handleCancel() {
		okClicked = false;
		dialogStage.close();
	}
}
