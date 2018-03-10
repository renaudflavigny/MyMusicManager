package org.flavigny.mmm.view;

import org.flavigny.mmm.model.Release;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReleaseEditController {

	@FXML private TextField artistField;
	@FXML private CheckBox variousCheckbox;
	@FXML private TextField titleField;
	@FXML private TextField yearField;
	@FXML private TextField barcodeField;
	@FXML private ComboBox<String> statusCombo;
	private ObservableList<String> statusComboData = FXCollections.observableArrayList();
	@FXML private ComboBox<String> packagingCombo;
	private ObservableList<String> packagingComboData = FXCollections.observableArrayList();
	@FXML ComboBox<String> formatCombo;
	private ObservableList<String> formatComboData = FXCollections.observableArrayList();
	@FXML private TextField commentField;
	@FXML private Button okButton;
	
	private Stage dialogStage;
	private Release release;
	private Boolean okClicked = false;
	
	private ChangeListener<String> changeListener = new ChangeListener<String>() {
		@Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if ( artistField.getText().length() == 0 || titleField.getText().length() == 0) {
				okButton.setDisable(true);
			} else {
				okButton.setDisable(false);
			}
		}
	};
	
	@FXML public void initialize() {
		artistField.textProperty().addListener(changeListener);
		titleField.textProperty().addListener(changeListener);
		
		statusComboData.add("Official");
		statusComboData.add("Bootleg");
		statusComboData.add("Promotional");
		statusComboData.add("Pseudo-release");
		statusCombo.setItems(statusComboData);
		
		packagingComboData.add("Book");
		packagingComboData.add("Cardboard / Paper sleeve");
		packagingComboData.add("Cassette case");
		packagingComboData.add("Digibook");
		packagingComboData.add("Discbox slider");
		packagingComboData.add("Fatbox");
		packagingComboData.add("Gatefold cover");
		packagingComboData.add("Jewel case");
		packagingComboData.add("Keep case");
		packagingComboData.add("Slim jewel case");
		packagingComboData.add("Snap case");
		packagingComboData.add("Super jewel case");
		packagingComboData.add("Other");
		packagingComboData.add("None");
		packagingCombo.setItems(packagingComboData);
		
		formatComboData.add("Compact disc");
		formatComboData.add("Vinyl");
		formatComboData.add("Digital media");
		formatComboData.add("Cassette");
		formatComboData.add("DVD");
		formatComboData.add("SACD");
		formatComboData.add("Dual disc");
		formatComboData.add("Blue-ray");
		formatComboData.add("HD-DVD");
		formatComboData.add("VCD");
		formatComboData.add("Other");
		formatCombo.setItems(formatComboData);
	}
	
	public Boolean isOkClicked() {
		return okClicked;
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public ReleaseEditController() {
		// TODO Auto-generated constructor stub
	}

	@FXML private void handleCheckBoxOnAction() {
		if ( variousCheckbox.isSelected() ) {
			artistField.setText("Various artists");
			artistField.setDisable(true);
		} else {
			artistField.clear();
			artistField.setDisable(false);
		}
	}
	
	@FXML private void handleCancel() {
		dialogStage.close();
	}
	
	@FXML private void handleOk() {
		release.setArtist(artistField.getText());
		release.setTitle(titleField.getText());
		release.setYear(Integer.valueOf(yearField.getText()));
		release.setBarcode(barcodeField.getText());
		release.setStatus(statusCombo.getValue());
		release.setPackaging(packagingCombo.getValue());
		release.setFormat(formatCombo.getValue());
		release.setComment(commentField.getText());
		
		okClicked = true;
		dialogStage.close();
	}
}
