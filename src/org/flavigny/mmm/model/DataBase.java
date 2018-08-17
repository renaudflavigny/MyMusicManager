package org.flavigny.mmm.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DataBase {
	
	private File file=null;
	private Connection connection=null;
	private final BooleanProperty valid = new SimpleBooleanProperty(false);
	
	public  DataBase() {
		// Used for new function
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
				+ "barcode TEXT,"
				+ "status TEXT,"
				+ "packaging TEXT,"
				+ "format TEXT,"
				+ "comment TEXT)"
				+ ";"
				+ "CREATE TABLE relAlbumRelease ("
				+ "albumId INTEGER,"
				+ "releaseId INTEGER)"
				+ ";"
				+ "CREATE TABLE tags ("
				+ "objectId INTEGER,"
				+ "objectClass TEXT,"
				+ "tagName TEXT,"
				+ "tagValue TEXT )"
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
			this.setValid(true);
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
			this.setValid(false);
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
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName()+" : "+e.getMessage());
		}
	}
	
	public void deleteAlbum(Album a) {
		String query = "DELETE FROM albums WHERE albumId = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, a.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void replaceAlbum( Album album ) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"REPLACE INTO albums (albumId, artist, title, year, primaryType, secondaryType, comment) "
					+ "VALUES (?,?,?,?,?,?,?)" );
			pstmt.setInt(1,album.getAlbumId());
			pstmt.setString(2, album.getArtist());
			pstmt.setString(3, album.getTitle());
			pstmt.setInt(4, album.getYear());
			pstmt.setString(5, album.getPrimaryType());
			pstmt.setString(6, album.getSecondaryType());
			pstmt.setString(7, album.getComment());
			pstmt.executeUpdate();
			pstmt.close();
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
				r.setBarcode(rs.getString("barcode"));
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
	
	public ArrayList<Release> fetchReleases(Album album){
		ArrayList<Release> releaseList = new ArrayList<>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT * FROM releases JOIN ( SELECT releaseId FROM relAlbumRelease WHERE albumId = ? ) USING ( releaseId ) ORDER BY artist,title ASC");
			pstmt.setInt(1, album.getAlbumId());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Release r = new Release(rs.getString("artist"),rs.getString("title"));
				r.setReleaseId(rs.getInt("releaseId"));
				r.setYear(rs.getInt("year"));
				r.setBarcode(rs.getString("barcode"));
				r.setStatus(rs.getString("status"));
				r.setPackaging(rs.getString("packaging"));
				r.setFormat(rs.getString("format"));
				r.setComment(rs.getString("comment"));
				releaseList.add(r);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return releaseList;
	}
	
	public ArrayList<String> fetchTagNames( String  objectClass ) {
		String query = "SELECT DISTINCT tagName FROM tags WHERE objectClass = ? ORDER BY tagName ASC";
		ArrayList<String> tagNameslist = new ArrayList<>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, objectClass);
			ResultSet rs = pstmt.executeQuery();
			while ( rs.next() ) {
				tagNameslist.add(rs.getString("tagName"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagNameslist;
	}
	
	public ArrayList<Tag> fetchTags( ManagedObject o ) {
		ArrayList<Tag> tagsList = new ArrayList();
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT rowid,tagName,tagValue FROM tags where objectId = ? AND objectClass = ? ORDER BY tagName,tagValue");
			pstmt.setInt(1, o.getId());
			pstmt.setString(2, o.getClass().getName());
			ResultSet rs = pstmt.executeQuery();
			while ( rs.next() ) {
				Tag t = new Tag();
				t.setId(rs.getInt("rowid"));
				t.setName(rs.getString("tagName"));
				t.setValue(rs.getString("tagValue"));
				tagsList.add(t);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagsList;
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
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRelease(Release r) {
		String query = "DELETE FROM releases WHERE releaseId = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, r.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void replaceRelease( Release release ) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"REPLACE INTO releases (releaseId, artist, title, year, barcode, status, packaging, format, comment) "
					+ "VALUES (?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, release.getReleaseId());
			pstmt.setString(2, release.getArtist());
			pstmt.setString(3, release.getTitle());
			pstmt.setInt(4, release.getYear());
			pstmt.setString(5, release.getBarcode());
			pstmt.setString(6, release.getStatus());
			pstmt.setString(7, release.getPackaging());
			pstmt.setString(8, release.getFormat());
			pstmt.setString(9, release.getComment());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertTag(ManagedObject o, Tag t) {
		String query = "INSERT INTO tags (objectId,objectClass,tagName,tagValue) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, o.getId());
			pstmt.setString(2, o.getClass().getName());
			pstmt.setString(3, t.getName());
			pstmt.setString(4, t.getValue());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteTag(Tag t) {
		String query = "DELETE FROM tags WHERE rowid = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, t.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertRelAlbumRelease(Album album, Release release) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO relAlbumRelease (albumId,releaseId) VALUES (?,?)");
			pstmt.setInt(1, album.getAlbumId());
			pstmt.setInt(2, release.getReleaseId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRelAlbumRelease(Album a, Release r) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"DELETE FROM relAlbumRelease WHERE albumId = ? AND releaseId = ?");
			pstmt.setInt(1, a.getId());
			pstmt.setInt(2, r.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public BooleanProperty validProperty() {
		return this.valid;
	}

	public boolean isValid() {
		return this.validProperty().get();
	}

	public void setValid(final boolean valid) {
		this.validProperty().set(valid);
	}
}
