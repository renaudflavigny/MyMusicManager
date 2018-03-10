package org.flavigny.mmm.view;

import org.flavigny.mmm.MainApp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainTabsController {

	@FXML private AnchorPane albumOverview;
	@FXML private AnchorPane releaseOverview;
	@FXML private AlbumOverviewController albumOverviewController;
	@FXML private ReleaseOverviewController releaseOverviewController;
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		albumOverviewController.setMainApp(mainApp);
		releaseOverviewController.setMainApplication(mainApp);
	}
	
	public MainTabsController() {
		// TODO Auto-generated constructor stub
	}

}
