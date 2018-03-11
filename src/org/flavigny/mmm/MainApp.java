package org.flavigny.mmm;

import java.io.IOException;

import org.flavigny.mmm.model.Album;
import org.flavigny.mmm.model.DataBase;
import org.flavigny.mmm.model.Release;
import org.flavigny.mmm.view.AddRelAlbumController;
import org.flavigny.mmm.view.AddRelReleaseController;
import org.flavigny.mmm.view.AlbumEditController;
import org.flavigny.mmm.view.MainTabsController;
import org.flavigny.mmm.view.ReleaseEditController;
import org.flavigny.mmm.view.RootLayoutController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private DataBase dataBase;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	
	public void setDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}
	public DataBase getDataBase() {
		return dataBase;
	}

	private ObservableList<Album> albumList = FXCollections.observableArrayList();
	private ObservableList<Release> releaseList = FXCollections.observableArrayList();
	
	public ObservableList<Release> getReleaseList() {
		return releaseList;
	}

	public ObservableList<Album> getAlbumList() {
		return albumList;
	}
	
	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("My Music Manager");
		
		initRootLayout();
		showMainTabs();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showMainTabs() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainTabs.fxml"));
			BorderPane mainTabs = loader.load();
			
			rootLayout.setCenter(mainTabs);
			
			MainTabsController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showAlbumEditDialog(Album album) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AlbumEditDialog.fxml"));
			AnchorPane albumEdit = (AnchorPane)loader.load();
			
			Stage dialogStage = new Stage();
			if ( album.getAlbumId() == null) {
				dialogStage.setTitle("New album");
			} else {
				dialogStage.setTitle("Edit album");
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(albumEdit);
			dialogStage.setScene(scene);
			
			AlbumEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setAlbum(album);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showAddRelRelease(Album album, Release release) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddRelRelease.fxml"));
			AnchorPane addRelRelease = (AnchorPane)loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Related Release");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(addRelRelease);
			dialogStage.setScene(scene);
			
			AddRelReleaseController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setAlbum(album);
			controller.setRelease(release);
			controller.setReleaseList(releaseList);
			
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showAddRelAlbum(Album album, Release release) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddRelAlbum.fxml"));
			AnchorPane addRelRelease = (AnchorPane)loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Related Release");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(addRelRelease);
			dialogStage.setScene(scene);
			
			AddRelAlbumController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setAlbum(album);
			controller.setRelease(release);
			controller.setAlbumList(albumList);
			
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showReleaseEditDialog(Release release) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ReleaseEditDialog.fxml"));
			AnchorPane releaseEdit = (AnchorPane)loader.load();
			
			Stage dialogStage = new Stage();
			if ( release.getReleaseId() == null ) {
				dialogStage.setTitle("New release");
			} else {
				dialogStage.setTitle("Edit release");
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(releaseEdit);
			dialogStage.setScene(scene);
			
			ReleaseEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setRelease(release);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
