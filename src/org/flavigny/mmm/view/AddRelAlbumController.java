package org.flavigny.mmm.view;

import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.Release;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AddRelAlbumController {


	@FXML private TableView<Album> albumTable;
	@FXML private TableColumn<Album, String> artistColumn;
	@FXML private TableColumn<Album, String> titleColumn;
	@FXML private TableColumn<Album, Integer> yearColumn;
	@FXML private Button addButton;
	
	private Stage dialogStage;
	private Album album;
	private Release release;
	private ObservableList<Album> albumList;
	private boolean okClicked = false;
	
	public void initialize() {
		artistColumn.setCellValueFactory(cellData->cellData.getValue().artistProperty());
		titleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
		yearColumn.setCellValueFactory(cellData->cellData.getValue().yearProperty().asObject());
		albumTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue)->album.copy(newValue));
	}
	
	@FXML public void handleOk() {
		okClicked=true;
		dialogStage.close();
	}
		
	@FXML public void handleCancel() {
		dialogStage.close();
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

	public ObservableList<Album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(ObservableList<Album> albumList) {
		this.albumList = albumList;
		albumTable.setItems(albumList);
	}

	public AddRelAlbumController() {
		// TODO Auto-generated constructor stub
	}

}
