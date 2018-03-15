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
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MusicBrainz {

	private final String baseURL = "http://musicbrainz.org/ws/2/";
	private String charset = StandardCharsets.UTF_8.name();
	private String service;
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

	public int search() {
		query = "?";
		for ( String k : fields.keySet() ) {
			try {
				query += String.format("%s:%s&", URLEncoder.encode(k,charset), URLEncoder.encode(fields.get(k),charset));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Suppression du & final
		query = query.substring(0, query.length()-1);
		
		service = "release/";
		URLConnection connection;
		InputStream response;
		try {
			connection = new URL(baseURL+service+query).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			response = connection.getInputStream();
		try (Scanner s = new Scanner(response)) {
			responseString = s.useDelimiter("\\A").next();
			System.out.println(responseString);
		}
			return ((HttpsURLConnection)connection).getResponseCode();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
