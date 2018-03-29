package org.flavigny.mmm.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class MusicBrainz {

	private final String baseURL = "http://musicbrainz.org/ws/2/";
	private String charset = StandardCharsets.UTF_8.name();
	private String ressource;
	private String query;
	private Integer limit = 25;
	private Integer offset = 0;

	private HashMap<String, String> fields = new HashMap<>(20);
	private String responseString;
	
	public void addField(String k, String v) {
		fields.put(k,v);
	}
	
	public void clearFields() {
		fields.clear();
	}
	
	public void removeField(String k) {
		fields.remove(k);
	}
	
	public MusicBrainz() {
		// TODO Auto-generated constructor stub
	}

	public int search(Release release) {
		query = "?query=";
		for ( String k : fields.keySet() ) {
			try {
				query += String.format("%s:%sAND", URLEncoder.encode(k,charset), URLEncoder.encode(fields.get(k),charset));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Suppression du AND final
		query = query.substring(0, query.length()-3);
		
		ressource = "release/";
		URLConnection connection;
		InputStream response;
		try {
			connection = new URL(baseURL+ressource+query+"&fmt=xml").openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			response = connection.getInputStream();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(response);
			Element rootElement = document.getDocumentElement();
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			Double count = (Double)xPath.evaluate("/metadata/release-list/attribute::count", rootElement, XPathConstants.NUMBER);
			if ( count > 0 ) {
				Node title = (Node)xPath.evaluate("//release/title", rootElement, XPathConstants.NODE);
				if ( title != null ) {
					release.setTitle(title.getTextContent());
				}
				Node status = (Node)xPath.evaluate("//release/status", rootElement, XPathConstants.NODE);
				if ( status != null ) {
					release.setStatus(status.getTextContent());
				}
				Node packaging = (Node)xPath.evaluate("//release/packaging", rootElement, XPathConstants.NODE);
				if ( packaging != null ) {
					release.setPackaging(packaging.getTextContent());
				}
				Node artist = (Node)xPath.evaluate("//artist/name", rootElement, XPathConstants.NODE);
				if ( artist != null ) {
					release.setArtist(artist.getTextContent());
				}
				Node format = (Node)xPath.evaluate("//medium/format", rootElement, XPathConstants.NODE);
				if ( format != null ) {
					release.setFormat(format.getTextContent());
				}
				Node date = (Node)xPath.evaluate("//release-event/date", rootElement, XPathConstants.NODE);
				if ( date != null ) {
					release.setYear(Integer.parseInt(date.getTextContent().substring(0, 4)));
				}
			}
			
		} catch  (XPathException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
