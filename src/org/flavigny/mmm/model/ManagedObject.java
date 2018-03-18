package org.flavigny.mmm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ManagedObject {

	private Integer id = new Integer(0);
	private final StringProperty artist = new SimpleStringProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final IntegerProperty year = new SimpleIntegerProperty(0);
	private final StringProperty comment = new SimpleStringProperty();
	
	public ManagedObject() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StringProperty artistProperty() {
		return this.artist;
	}
	

	public String getArtist() {
		return this.artistProperty().get();
	}
	

	public void setArtist(final String artist) {
		this.artistProperty().set(artist);
	}
	

	public StringProperty titleProperty() {
		return this.title;
	}
	

	public String getTitle() {
		return this.titleProperty().get();
	}
	

	public void setTitle(final String title) {
		this.titleProperty().set(title);
	}
	

	public IntegerProperty yearProperty() {
		return this.year;
	}
	

	public int getYear() {
		return this.yearProperty().get();
	}
	

	public void setYear(final int year) {
		this.yearProperty().set(year);
	}
	

	public StringProperty commentProperty() {
		return this.comment;
	}
	

	public String getComment() {
		return this.commentProperty().get();
	}
	

	public void setComment(final String comment) {
		this.commentProperty().set(comment);
	}
	

}
