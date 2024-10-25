package persistence;

import business.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDaoImpl extends MySQLDao implements AlbumDao{

    public AlbumDaoImpl(Connection conn) {
        super(conn);
    }
    public AlbumDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    /**
     *This Method Retrieves a list of albums by the specified artist
     *
     * @param artistId The ID of the artist for retrieving albums
     * @return A list of Album objects associated with the specified artist
     */
    @Override
    public List<Album> getAlbumsByArtist(int artistId) {
        List<Album> albums = new ArrayList<>();

        Connection conn = super.getConnection();
        String sql = "SELECT * FROM albums WHERE artist_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, artistId);
            ResultSet resultSet = ps.executeQuery();

            // Iterate through the result set and create Album objects
            while (resultSet.next()) {
                Album album = new Album();
                album.setId(resultSet.getInt("id"));
                album.setTitle(resultSet.getString("title"));
                album.setArtist_Id(resultSet.getInt("artist_id"));
                albums.add(album); // Add the album to the list
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            // Close the connection using the superclass method
            super.freeConnection(conn);
        }

        return albums; // Return the list of albums
    }
}
