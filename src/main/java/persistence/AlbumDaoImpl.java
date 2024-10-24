package persistence;

import java.sql.Connection;

public class AlbumDaoImpl extends MySQLDao implements AlbumDao{
    public AlbumDaoImpl(Connection conn) {
        super(conn);
    }

    public AlbumDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }
}
