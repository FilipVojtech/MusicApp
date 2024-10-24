package persistence;

import java.sql.Connection;

public class RatingDaoImpl extends MySQLDao implements RatingDao{
    public RatingDaoImpl(Connection conn) {
        super(conn);
    }

    public RatingDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }
}
