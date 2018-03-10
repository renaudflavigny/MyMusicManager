/**
 * 
 */
package org.flavigny.mmm.view;

import javax.swing.text.html.HTML.Tag;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.Release;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author renaud
 *
 */
public class ReleaseOverviewController {

	/**
	 * Controller for ReleaseOverview pane  
	 */
	
	@FXML private TableView<Release> releaseTable;
	@FXML private TableColumn<Release, String> artistColumn;
	@FXML private TableColumn<Release, String> titleColumn;
	
	@FXML private Label releaseIdLabel;
	@FXML private Label artistLabel;
	@FXML private Label titleLabel;
	@FXML private Label yearLabel;
	@FXML private Label statusLabel;
	@FXML private Label packagingLabel;
	@FXML private Label barcodeLabel;
	@FXML private Label commentLabel;
	@FXML private Label formatLabel;
	
	private ObservableList<Album> relatedAlbumList = FXCollections.observableArrayList();
	
	@FXML private TableView<Album> albumsTable;
	@FXML private TableColumn<Album, String> albumArtistColumn;
	@FXML private TableColumn<Album, String> albumTitleColumn;
	
	private ObservableList<Tag> tagList = FXCollections.observableArrayList();
	
	@FXML private TableView<Tag> tagsTable;
	@FXML private TableColumn<Tag, String> tagNameColumn;
	@FXML private TableColumn<Tag, String> tagValueColum;
	
	private MainApp mainApplication;
	
	@FXML private void initialize() {
		artistColumn.setCellValueFactory(
				cellData -> cellData.getValue().artistProperty());
		titleColumn.setCellValueFactory(
				cellData -> cellData.getValue().titleProperty());
		albumsTable.setItems(relatedAlbumList);
		albumArtistColumn.setCellValueFactory(
				cellData -> cellData.getValue().artistProperty());
		albumTitleColumn.setCellValueFactory(
				cellData -> cellData.getValue().titleProperty());
		showReleaseDetails(null);
		
	}
	
	private void showReleaseDetails( Release release) {
		if ( release == null ) {
			releaseIdLabel.setText(null);
			artistLabel.setText(null);
			titleLabel.setText(null);
			yearLabel.setText(null);
			statusLabel.setText(null);
			packagingLabel.setText(null);
			barcodeLabel.setText(null);
			commentLabel.setText(null);
			formatLabel.setText(null);
			relatedAlbumList.clear();
		} else {
			releaseIdLabel.setText(Integer.toString(release.getReleaseId()));
			artistLabel.setText(release.getArtist());
			titleLabel.setText(release.getTitle());
			yearLabel.setText(Integer.toString(release.getYear()));
			statusLabel.setText(release.getStatus());
			packagingLabel.setText(release.getPackaging());
			barcodeLabel.setText(release.getBarcode());
			commentLabel.setText(release.getComment());
			formatLabel.setText(release.getFormat());
			relatedAlbumList.clear();
			relatedAlbumList.addAll(mainApplication.getDataBase().fetchAlbums(release));
		}
	}

	public void setMainApplication(MainApp mainApp) {
		this.mainApplication = mainApp;
		releaseTable.setItems(mainApp.getReleaseList());
	}
	
	@FXML private void handleNewRelease() {
		Release release = new Release();
		boolean okClicked = mainApplication.showReleaseEditDialog(release);
		if ( okClicked ) {
			mainApplication.getDataBase().insertRelease(release);
			mainApplication.getReleaseList().add(release);
		}
	}
	
	public ReleaseOverviewController() {
		// TODO Auto-generated constructor stub
	}

}
