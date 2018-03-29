package org.flavigny.mmm.view;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.Album;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AlbumEditController {

	@FXML private TextField artistField;
	@FXML private TextField titleField;
	@FXML private TextField yearField;
	@FXML private ComboBox<String> primaryTypeCombo;
	private ObservableList<String> primaryTypeComboData = FXCollections.observableArrayList();
	@FXML private ComboBox<String> secondaryTypeCombo;
	private ObservableList<String> secondaryTypeComboData = FXCollections.observableArrayList();
	@FXML private TextField commentField;
	
	private MainApp mainApplication;
	private Stage dialogStage;
	private Album album;
	private Boolean okClicked = false;
	
	@FXML private void initialize() {
		primaryTypeComboData.add("Album");
		primaryTypeComboData.add("Single");
		primaryTypeComboData.add("EP");
		primaryTypeComboData.add("Broadcast");
		primaryTypeComboData.add("Other");
		primaryTypeCombo.setItems(primaryTypeComboData);
		secondaryTypeComboData.add("");
		secondaryTypeComboData.add("Compilation");
		secondaryTypeComboData.add("Soundtrack");
		secondaryTypeComboData.add("Spokenword");
		secondaryTypeComboData.add("Interview");
		secondaryTypeComboData.add("Audiobook");
		secondaryTypeComboData.add("Live");
		secondaryTypeComboData.add("Remix");
		secondaryTypeComboData.add("DJ-remix");
		secondaryTypeComboData.add("Mixtape/Street");
		secondaryTypeCombo.setItems(secondaryTypeComboData);
	}
	
	public void setMainApplication(MainApp mainApplication) {
		this.mainApplication = mainApplication;
	}

	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}
	
	public void setAlbum(Album album) {
		this.album = album;
		
		artistField.setText(album.getArtist());
		titleField.setText(album.getTitle());
		yearField.setText(Integer.toString(album.getYear()));
		primaryTypeCombo.getSelectionModel().select(album.getPrimaryType());
		secondaryTypeCombo.getSelectionModel().select(album.getSecondaryType());
		commentField.setText(album.getComment());
	}
	
	public Boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML private void handleOk() {
		if (isInputValid()) {
			album.setArtist(artistField.getText());
			album.setTitle(titleField.getText());
			album.setYear(Integer.valueOf(yearField.getText()));
			album.setPrimaryType(primaryTypeCombo.getValue());
			album.setSecondaryType(secondaryTypeCombo.getValue());
			album.setComment(commentField.getText());
			
			okClicked=true;
			
			if (album.getId()==0) {
				mainApplication.getDataBase().insertAlbum(album);
				mainApplication.getAlbumList().add(album);
			} else {
				mainApplication.getDataBase().replaceAlbum(album);
			}
			
			dialogStage.close();
		} 
	}
	
	@FXML private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (artistField.getText()==null || artistField.getText().length()==0) {
			errorMessage += "Incorrect artist name\n";
		}
		if (titleField.getText()==null || titleField.getText().length()==0) {
			errorMessage += "Incorrect title\n";
		}
		if (yearField.getText()!=null && yearField.getText().length()!=0) {
			try {
				Integer.parseInt(yearField.getText());
			} catch (NumberFormatException e){
				errorMessage += "Incorrect year (must be an integer)\n";
			}
		}
		if (errorMessage.length()==0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Incorrect fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}
	
	public AlbumEditController() {
	}
}
