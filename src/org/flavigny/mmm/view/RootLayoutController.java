package org.flavigny.mmm.view;

import java.io.File;
import java.util.ArrayList;

import org.flavigny.mmm.MainApp;
import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.DataBase;
import org.flavigny.mmm.model.DataBaseException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootLayoutController {

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	private void handleNew() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("New database");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File file=fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file!=null) {
			try {
				DataBase db=new DataBase(file);
				db.populateDB();
				mainApp.setDataBase(db);
				mainApp.getPrimaryStage().setTitle(mainApp.getPrimaryStage().getTitle()+" - "+file);
			} catch ( DataBaseException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("DataBase Error");
				alert.setHeaderText("An error occurs during database creation.");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open database");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File file=fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file!=null) {
			try {
				DataBase db=new DataBase(file);
				mainApp.setDataBase(db);
				mainApp.getAlbumList().addAll(db.fetchAlbums());
				mainApp.getReleaseList().addAll(db.fetchReleases());
				mainApp.getPrimaryStage().setTitle(mainApp.getPrimaryStage().getTitle()+" - "+file);
			} catch ( DataBaseException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("DataBase Error");
				alert.setHeaderText("An error occurs during database opening.");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}
	@FXML
	private void handleClose() {
		try {
			mainApp.getDataBase().closeDB();
		} catch (DataBaseException e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
	}
	@FXML
	private void handleQuit() {
		Platform.exit();
	}
}
