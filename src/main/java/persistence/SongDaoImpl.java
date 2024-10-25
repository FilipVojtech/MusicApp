package persistence;

import business.Song;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SongDaoImpl extends MySQLDao implements SongDao{
    public SongDaoImpl(Connection conn) {
        super(conn);
    }

    public SongDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId) throws SQLException {
        return List.of();
    }

    @Override
    public List<Song> searchSongs(String keyword) throws SQLException {
        return List.of();
    }
}
