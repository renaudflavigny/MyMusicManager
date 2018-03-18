package org.flavigny.mmm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Album extends ManagedObject {

	private final StringProperty primaryType = new SimpleStringProperty();
	private final StringProperty secondaryType = new SimpleStringProperty();
	
	public Album() {
		this(null,null);
	}
	
	public Integer getAlbumId() {
		return getId();
	}

	public void setAlbumId(Integer albumId) {
		setId(albumId);
	}

	public Album(String artist, String title) {
		setArtist(artist);
		setTitle(title);
		setYear(1900);
		setPrimaryType("Album");
		setSecondaryType("");
		setComment("");
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
	
	public void copy( Album a) {
		setAlbumId(a.getAlbumId());
		setArtist(a.getArtist());
		setComment(a.getComment());
		setPrimaryType(a.getPrimaryType());
		setSecondaryType(a.getSecondaryType());
		setTitle(a.getTitle());
		setYear(a.getYear());
	}
	
}
