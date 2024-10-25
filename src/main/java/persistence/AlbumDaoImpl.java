package persistence;

import business.Album;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AlbumDaoImpl extends MySQLDao implements AlbumDao{
    public AlbumDaoImpl(Connection conn) {
        super(conn);
    }

    public AlbumDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public List<Album> getAlbumsByArtist(int artistId) throws SQLException {
        return List.of();
    }
}
