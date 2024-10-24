package persistence;

import java.sql.Connection;

public class SongDaoImpl extends MySQLDao implements SongDao{
    public SongDaoImpl(Connection conn) {
        super(conn);
    }

    public SongDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }
}
