package org.flavigny.mmm.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
	
	private File file=null;
	private Connection connection=null;

	public  DataBase() {
		// Used for new function
	}
	
	public DataBase(File dataBaseFile) throws DataBaseException {
		this.openDB(dataBaseFile);
	}
	
	
	public void populateDB() throws DataBaseException {
		String sqlInstructions = "CREATE TABLE albums ("
				+ "albumId INTEGER PRIMARY KEY,"
				+ "artist TEXT,"
				+ "title TEXT,"
				+ "year NUMERIC,"
				+ "primaryType TEXT,"
				+ "secondaryType TEXT,"
				+ "comment TEXT)"
				+ ";"
				+ "CREATE TABLE releases ("
				+ "releaseId INTEGER PRIMARY KEY,"
				+ "artist TEXT,"
				+ "title TEXT,"
				+ "year NUMERIC,"
				+ "barcode TEXT"
				+ "status TEXT"
				+ "packaging TEXT,"
				+ "format TEXT,"
				+ "comment TEXT)"
				+ ";"
				+ "CREATE TABLE relAlbumRelease ("
				+ "albumId INTEGER,"
				+ "releaseId INTEGER)"
				+ ";"
				+ "CREATE TABLE albumTags ("
				+ "albumId INTEGER,"
				+ "name TEXT,"
				+ "value TEXT)"
				+ ";"
				+ "CREATE TABLE releaseTags ("
				+ "releaseId INTEGER,"
				+ "name TEXT,"
				+ "value TEXT)"
				+ ";";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(sqlInstructions);
			s.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
			throw new DataBaseException(e.getClass().getName()+" : "+e.getMessage());
		}
	}
	
	public void openDB(File dataBaseFile) throws DataBaseException {
		// TODO Auto-generated constructor stub
		this.file=dataBaseFile;
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:"+file);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
			throw new DataBaseException(e.getClass().getName()+" : "+e.getMessage());
		}
	}

	public void closeDB() throws DataBaseException {
		try {
			connection.close();
			connection=null;
			file=null;
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
			throw new DataBaseException(e.getClass().getName()+" : "+e.getMessage());
		}
	}
	
	public ArrayList<Album> fetchAlbums(){
		ArrayList<Album> albumList = new ArrayList<Album>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM albums ORDER BY artist,title ASC");
			
			while (rs.next()) {
				Album a = new Album(rs.getString("artist"),rs.getString("title"));
				a.setAlbumId(rs.getInt("albumId"));
				a.setYear(rs.getInt("year"));
				a.setPrimaryType(rs.getString("primaryType"));
				a.setSecondaryType(rs.getString("secondaryType"));
				a.setComment(rs.getString("comment"));
				albumList.add(a);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
		return albumList;
	}
	
	public ArrayList<Album> fetchAlbums(Release release){
		ArrayList<Album> albumList = new ArrayList<Album>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT * FROM albums JOIN ( SELECT albumId FROM relAlbumRelease WHERE releaseId = ? ) USING ( albumId ) ORDER BY artist,title ASC");
			pstmt.setInt(1,release.getReleaseId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Album a = new Album(rs.getString("artist"),rs.getString("title"));
				a.setAlbumId(rs.getInt("albumId"));
				a.setYear(rs.getInt("year"));
				a.setPrimaryType(rs.getString("primaryType"));
				a.setSecondaryType(rs.getString("secondaryType"));
				a.setComment(rs.getString("comment"));
				albumList.add(a);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
		return albumList;
	}
	
	public void insertAlbum( Album album) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO albums (albumId,artist,title,year,primaryType,secondaryType,comment) "
					+"VALUES(null,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, album.getArtist());
			pstmt.setString(2, album.getTitle());
			pstmt.setInt(3, album.getYear());
			pstmt.setString(4, album.getPrimaryType());
			pstmt.setString(5, album.getSecondaryType());
			pstmt.setString(6, album.getComment());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			album.setAlbumId(rs.getInt(1));
		} catch (SQLException e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
	}
	
	public ArrayList<Release> fetchReleases(){
		ArrayList<Release> releaseList = new ArrayList<Release>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM releases ORDER BY artist,title ASC");
			
			while (rs.next()) {
				Release r = new Release(rs.getString("artist"),rs.getString("title"));
				r.setReleaseId(rs.getInt("releaseId"));
				r.setYear(rs.getInt("year"));
				r.setStatus(rs.getString("status"));
				r.setPackaging(rs.getString("packaging"));
				r.setFormat(rs.getString("format"));
				r.setComment(rs.getString("comment"));
				releaseList.add(r);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
		return releaseList;
	}
	
	public void insertRelease(Release release) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO releases (releaseId,artist,title,year,barcode,status,packaging,format,comment) "
					+ "VALUES (null,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, release.getArtist());
			pstmt.setString(2, release.getTitle());
			pstmt.setInt(3, release.getYear());
			pstmt.setString(4, release.getBarcode());
			pstmt.setString(5, release.getStatus());
			pstmt.setString(6, release.getPackaging());
			pstmt.setString(7, release.getFormat());
			pstmt.setString(8, release.getComment());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			release.setReleaseId(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void insertTag(String table, Tag t) {
		String query = String.format("INSERT INTO %s (id,name,value) VALUES (?,?,?)", table );
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, t.getId());
			pstmt.setString(2, t.getName());
			pstmt.setString(3, t.getValue());
			pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertAlbumTag(Tag t) {
		insertTag("albums",t);
	}
	
	public void insertReleaseTag(Tag t) {
		insertTag("releases",t);
	}
}
