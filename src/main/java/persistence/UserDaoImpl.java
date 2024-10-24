package persistence;

import java.sql.Connection;

public class UserDaoImpl extends MySQLDao implements UserDao{
    public UserDaoImpl(Connection conn) {
        super(conn);
    }

    public UserDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }
}
