package org.flavigny.mmm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class Tag {

	@FXML private IntegerProperty id = new SimpleIntegerProperty();
	@FXML private StringProperty name = new SimpleStringProperty();
	@FXML private StringProperty value = new SimpleStringProperty();

	public Tag() {
		// TODO Auto-generated constructor stub
	}

	public StringProperty nameProperty() {
		return this.name;
	}
	

	public String getName() {
		return this.nameProperty().get();
	}
	

	public void setName(final String name) {
		this.nameProperty().set(name);
	}
	

	public StringProperty valueProperty() {
		return this.value;
	}
	

	public String getValue() {
		return this.valueProperty().get();
	}
	

	public void setValue(final String value) {
		this.valueProperty().set(value);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}
	

	public int getId() {
		return this.idProperty().get();
	}
	

	public void setId(final int id) {
		this.idProperty().set(id);
	}
	
	

}
