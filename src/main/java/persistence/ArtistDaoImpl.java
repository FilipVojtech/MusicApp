package persistence;

import business.Artist;

import java.sql.SQLException;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {
    @Override
    public List<Artist> getAllArtists() throws SQLException {
        return List.of();
    }
}
