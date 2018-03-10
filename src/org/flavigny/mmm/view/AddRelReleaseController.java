package org.flavigny.mmm.view;

import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.Release;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AddRelReleaseController {

	@FXML private TableView<Release> releaseTable;
	@FXML private TableColumn<Release, String> artistColumn;
	@FXML private TableColumn<Release, String> titleColumn;
	@FXML private TableColumn<Release, Integer> yearColumn;
	@FXML private Button addButton;
	
	private Stage dialogStage;
	private Album album;
	private Release release;
	private ObservableList<Release> releaseList;
	private boolean okClicked = false;
	
	public void initialize() {
		artistColumn.setCellValueFactory(cellData->cellData.getValue().artistProperty());
		titleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
		yearColumn.setCellValueFactory(cellData->cellData.getValue().yearProperty().asObject());
		releaseTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue)->release.copy(newValue));
	}
	
	@FXML public void handleOk() {
		okClicked=true;
		dialogStage.close();
	}
		
	@FXML public void handleCancel() {
		dialogStage.close();
	}
	
	public AddRelReleaseController() {
		// TODO Auto-generated constructor stub
	}
	
	public Stage getDialogStage() {
		return dialogStage;
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public void setOkClicked(boolean okClicked) {
		this.okClicked = okClicked;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public ObservableList<Release> getReleaseList() {
		return releaseList;
	}

	public void setReleaseList(ObservableList<Release> releaseList) {
		this.releaseList = releaseList;
		releaseTable.setItems(releaseList);
	}

}
