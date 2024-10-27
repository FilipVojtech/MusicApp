package persistence;
import business.Album;
import java.sql.SQLException;
import java.util.List;
///Dylan
public interface AlbumDao {
    List<Album> getAlbumsByArtist(int artistId) throws SQLException;

}
