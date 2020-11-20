package dao;

import java.sql.*;

public class DataSourceFactory {

    private static final String HOST = "127.0.0.1";
    private static final String IP = "3306";
    private static final String DBNAME = "ws_factory";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    private Connection conn = null;

    private DataSourceFactory() {
        String url = String.format("jdbc:mysql://%s:%s/%s", HOST, IP, DBNAME);
        try {
            this.conn = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static Connection getConn() {
        return SingletonHelper.INSTANCE.conn;
    }

    private static class SingletonHelper {
        private static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }

}
