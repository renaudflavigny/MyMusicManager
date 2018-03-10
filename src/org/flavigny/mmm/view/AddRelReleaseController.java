package org.flavigny.mmm.view;

import org.flavigny.mmm.model.Release;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AddRelReleaseController {

	@FXML private TableView<Release> releaseTable;
	@FXML private TableColumn<Release, String> artistColumn;
	@FXML private TableColumn<Release, String> titleColumn;
	@FXML private TableColumn<Release, Integer> yearColumn;
	@FXML private Button addButton;
	
	public AddRelReleaseController() {
		// TODO Auto-generated constructor stub
	}

}
