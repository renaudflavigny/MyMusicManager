package org.flavigny.mmm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Release {

	private final IntegerProperty releaseId;
	private final StringProperty artist;
	private final StringProperty title;
	private final IntegerProperty year;
	private final StringProperty barcode;
	private final StringProperty status;
	private final StringProperty packaging;
	private final StringProperty format;
	private final StringProperty comment;
	
	public Release(String artist, String title) {
		this.releaseId = new SimpleIntegerProperty();
		this.artist = new SimpleStringProperty(artist);
		this.title = new SimpleStringProperty(title);
		this.year = new SimpleIntegerProperty();
		this.barcode = new SimpleStringProperty();
		this.status = new SimpleStringProperty();
		this.packaging = new SimpleStringProperty();
		this.format = new SimpleStringProperty();
		this.comment = new SimpleStringProperty();
	}
	

	public Release() {
		this(null,null);
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
	

	public StringProperty barcodeProperty() {
		return this.barcode;
	}
	

	public String getBarcode() {
		return this.barcodeProperty().get();
	}
	

	public void setBarcode(final String barcode) {
		this.barcodeProperty().set(barcode);
	}
	

	public StringProperty statusProperty() {
		return this.status;
	}
	

	public String getStatus() {
		return this.statusProperty().get();
	}
	

	public void setStatus(final String status) {
		this.statusProperty().set(status);
	}
	

	public StringProperty packagingProperty() {
		return this.packaging;
	}
	

	public String getPackaging() {
		return this.packagingProperty().get();
	}
	

	public void setPackaging(final String packaging) {
		this.packagingProperty().set(packaging);
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

	public StringProperty formatProperty() {
		return this.format;
	}
	

	public String getFormat() {
		return this.formatProperty().get();
	}
	

	public void setFormat(final String format) {
		this.formatProperty().set(format);
	}


	public IntegerProperty releaseIdProperty() {
		return this.releaseId;
	}
	


	public int getReleaseId() {
		return this.releaseIdProperty().get();
	}
	


	public void setReleaseId(final int releaseId) {
		this.releaseIdProperty().set(releaseId);
	}
	
	
	
	

}
