package persistence;

import java.sql.Connection;

public class PlaylistDaoImpl extends MySQLDao implements PlaylistDao {
    public PlaylistDaoImpl(Connection conn) {
        super(conn);
    }

    public PlaylistDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }
}
