package org.flavigny.mmm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Album {

	private Integer albumId;
	private final StringProperty artist;
	private final StringProperty title;
	private final IntegerProperty year;
	private final StringProperty primaryType;
	private final StringProperty secondaryType;
	private final StringProperty comment;
	
	public Album() {
		this(null,null);
	}
	
	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public Album(String artist, String title) {
		this.artist=new SimpleStringProperty(artist);
		this.title=new SimpleStringProperty(title);
		
		this.year=new SimpleIntegerProperty(1900);
		this.primaryType=new SimpleStringProperty("Album");
		this.secondaryType=new SimpleStringProperty("");
		this.comment = new SimpleStringProperty();
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
	

	public StringProperty primaryTypeProperty() {
		return this.primaryType;
	}
	

	public String getPrimaryType() {
		return this.primaryTypeProperty().get();
	}
	

	public void setPrimaryType(final String primaryType) {
		this.primaryTypeProperty().set(primaryType);
	}
	

	public StringProperty secondaryTypeProperty() {
		return this.secondaryType;
	}
	

	public String getSecondaryType() {
		return this.secondaryTypeProperty().get();
	}
	

	public void setSecondaryType(final String secondaryType) {
		this.secondaryTypeProperty().set(secondaryType);
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
	
	public void copy( Album a) {
		this.setAlbumId(a.getAlbumId());
		this.setArtist(a.getArtist());
		this.setComment(a.getComment());
		this.setPrimaryType(a.getPrimaryType());
		this.setSecondaryType(a.getSecondaryType());
		this.setTitle(a.getTitle());
		this.setYear(a.getYear());
	}
	
}
