package persistence;

import business.Playlist;

import java.sql.Connection;
import java.util.List;

public class PlaylistDaoImpl extends MySQLDao implements PlaylistDao {
    public PlaylistDaoImpl(Connection conn) {
        super(conn);
    }

    public PlaylistDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {
        return false;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        return false;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        return false;
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        return false;
    }

    @Override
    public boolean renamePlaylist(int playlistId, String newName) {
        return false;
    }

    @Override
    public Playlist getPlaylistById(int playlistId) {
        return null;
    }

//    @Override
//    public List<Playlist> getUserPlaylists(int userId) {
//    }
//
//    @Override
//    public List<Playlist> getPublicPlaylists() {
//    }
//
//    @Override
//    public List<Song> getSongsInPlaylist(int playlistId) {
//    }

}
