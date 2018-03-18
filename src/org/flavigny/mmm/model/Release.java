package org.flavigny.mmm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Release extends ManagedObject {

	private final StringProperty barcode = new SimpleStringProperty();
	private final StringProperty status = new SimpleStringProperty();
	private final StringProperty packaging = new SimpleStringProperty();
	private final StringProperty format = new SimpleStringProperty();
	
	public Release(String artist, String title) {
		setArtist(artist);
		setTitle(title);
		setYear(1900);
		setComment("");
		setBarcode(null);
		setStatus(null);
		setPackaging(null);
		setFormat(null);
	}

	public Release() {
		this(null,null);
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

	public StringProperty formatProperty() {
		return this.format;
	}
	
	public String getFormat() {
		return this.formatProperty().get();
	}

	public void setFormat(final String format) {
		this.formatProperty().set(format);
	}

	public Integer getReleaseId() {
		return getId();
	}
	
	public void setReleaseId(Integer releaseId) {
		setId(releaseId);
	}
	
	public void copy( Release r) {
		setArtist(r.getArtist());
		setBarcode(r.getBarcode());
		setComment(r.getComment());
		setFormat(r.getFormat());
		setPackaging(r.getPackaging());
		setReleaseId(r.getReleaseId());
		setStatus(r.getStatus());
		setTitle(r.getTitle());
		setYear(r.getYear());
	}

}
