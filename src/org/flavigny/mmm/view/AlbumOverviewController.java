package org.flavigny.mmm.view;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.Release;
import org.flavigny.mmm.model.Tag;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AlbumOverviewController {
	
	@FXML private TableView<Album> albumTable;
	@FXML private TableColumn<Album,String> artistColumn;
	@FXML private TableColumn<Album,String> titleColumn;
	
	@FXML private Label albumIdLabel;
	@FXML private Label artistLabel;
	@FXML private Label titleLabel;
	@FXML private Label yearLabel;
	@FXML private Label primaryTypeLabel;
	@FXML private Label secondaryTypeLabel;
	@FXML private Label commentLabel;
	
	@FXML private Button newButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	
	@FXML private Button addRelatedReleaseButton;
	@FXML private Button delRelatedReleaseButton;
	
	@FXML private Button addTagButton;
	@FXML private Button delTagButton;
	
	private ObservableList<Release> relatedReleaseList = FXCollections.observableArrayList();
	
	@FXML private TableView<Release> releaseTable;
	@FXML private TableColumn<Release, String> releaseArtistColumn;
	@FXML private TableColumn<Release, String> releaseTitleColumn;
	@FXML private TableColumn<Release, Integer> releaseYearColumn;
	
	private ObservableList<Tag> tagList = FXCollections.observableArrayList();
	
	@FXML private TableView<Tag> tagsTable;
	@FXML private TableColumn<Tag, String> tagNameColumn;
	@FXML private TableColumn<Tag, String> tagValueColumn;
	
	private MainApp mainApp;
	
	@FXML private void initialize() {
		artistColumn.setCellValueFactory(
				cellData -> cellData.getValue().artistProperty());
		titleColumn.setCellValueFactory(
				cellData -> cellData.getValue().titleProperty());
		releaseTable.setItems(relatedReleaseList);
		releaseArtistColumn.setCellValueFactory(
				cellData->cellData.getValue().artistProperty());
		releaseTitleColumn.setCellValueFactory(
				cellData->cellData.getValue().titleProperty());
		releaseYearColumn.setCellValueFactory(
				cellData->cellData.getValue().yearProperty().asObject());
		tagsTable.setItems(tagList);
		tagNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tagValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
		
		showAlbumDetails(null);
		albumTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue)-> showAlbumDetails(newValue));
		albumTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldvalue, newvalue)-> {
					if ( albumTable.getSelectionModel().isEmpty() ) {
						editButton.setDisable(true);
						deleteButton.setDisable(true);
						addRelatedReleaseButton.setDisable(true);
						addTagButton.setDisable(true);
					} else {
						editButton.setDisable(false);
						deleteButton.setDisable(false);
						addRelatedReleaseButton.setDisable(false);
						addTagButton.setDisable(false);
					}
				});
		releaseTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldvalue,newvalue)->{
					if ( releaseTable.getSelectionModel().isEmpty() ) {
						delRelatedReleaseButton.setDisable(true);
					} else {
						delRelatedReleaseButton.setDisable(false);
					}
				});
		tagsTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldvalue,newvalue) -> {
					if ( tagsTable.getSelectionModel().isEmpty() ) {
						delTagButton.setDisable(true);
					} else {
						delTagButton.setDisable(false);
					}
				});
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp=mainApp;
		albumTable.setItems(mainApp.getAlbumList());
		// Gestion du bouton New en fonction de l'état de la base de donnée
		mainApp.getDataBase().validProperty().addListener((Observable o) -> {
			if ( mainApp.getDataBase().isValid() ) {
				newButton.setDisable(false);
			} else {
				newButton.setDisable(true);
			}
		});
	}

	private void showAlbumDetails(Album album) {
		if (album != null) {
			albumIdLabel.setText(Integer.toString(album.getAlbumId()));
			artistLabel.setText(album.getArtist());
			titleLabel.setText(album.getTitle());
			yearLabel.setText(Integer.toString(album.getYear()));
			primaryTypeLabel.setText(album.getPrimaryType());
			secondaryTypeLabel.setText(album.getSecondaryType());
			commentLabel.setText(album.getComment());
			relatedReleaseList.clear();
			relatedReleaseList.addAll(mainApp.getDataBase().fetchReleases(album));
			tagList.clear();
			tagList.addAll(mainApp.getDataBase().fetchTags(album));
		} else {
			albumIdLabel.setText("");
			artistLabel.setText("");
			titleLabel.setText("");
			yearLabel.setText("");
			primaryTypeLabel.setText("");
			secondaryTypeLabel.setText("");
			commentLabel.setText("");
			relatedReleaseList.clear();
			tagList.clear();
		}
	}
	
	@FXML private void handleAddTag() {
		Album album = albumTable.getSelectionModel().getSelectedItem();
		if ( album != null ) {
			mainApp.showAddTagDialog(album);
			showAlbumDetails(album);
		}
	}
	
	@FXML private void handleDeleteTag() {
		Tag t = tagsTable.getSelectionModel().getSelectedItem();
		mainApp.getDataBase().deleteTag(t);
		showAlbumDetails(albumTable.getSelectionModel().getSelectedItem());
	}
	
	@FXML private void handleAddRelRelease() {
		Album album = albumTable.getSelectionModel().getSelectedItem();
		Release release = new Release();
		if ( album != null ) {
			boolean okClicked = mainApp.showAddRelRelease(album, release);
			if ( okClicked ) {
				relatedReleaseList.add(release);
				mainApp.getDataBase().insertRelAlbumRelease(album, release);
			}
		} 
	}
	
	@FXML private void handleDeleteRelRelease() {
		Album album = albumTable.getSelectionModel().getSelectedItem();
		Release release = releaseTable.getSelectionModel().getSelectedItem();
		mainApp.getDataBase().deleteRelAlbumRelease(album, release);
		showAlbumDetails(album);
	}
	
	@FXML private void handleDeleteAlbum() {
		int selectedIndex = albumTable.getSelectionModel().getSelectedIndex();
		Album album = albumTable.getSelectionModel().getSelectedItem();
		if (selectedIndex>=0) {
			albumTable.getItems().remove(selectedIndex);
			mainApp.getDataBase().deleteAlbum(album);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No album selected");
			alert.setContentText("Please select an album in the list.");
			alert.showAndWait();
		}
	}
	
	@FXML private void handleNewAlbum() {
		Album album = new Album();
		if ( mainApp.showAlbumEditDialog(album) ) {
			showAlbumDetails(album);
		}
	}
	
	@FXML private void handleEditAlbum() {
		Album album = albumTable.getSelectionModel().getSelectedItem();
		if (album!=null) {
			boolean okClicked = mainApp.showAlbumEditDialog(album);
		 	if (okClicked) {
		 		showAlbumDetails(album);
		 	}
		 } else {
		 	Alert alert = new Alert(AlertType.WARNING);
		 	alert.initOwner(mainApp.getPrimaryStage());
		 	alert.setTitle("No selection");
		 	alert.setHeaderText("No album selected");
		 	alert.setContentText("Please select an album in the table.");
		 	alert.showAndWait();
		 }	
	}
}
