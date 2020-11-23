package dao;

import java.sql.*;

public class DataSourceFactory {

    private static String HOST = "127.0.0.1";
    private static String IP = "3306";
    private static String DBNAME = "ws_factory";
    private static String USER = "root";
    private static String PASSWORD = "123";

    private Connection conn = null;

    private static void configureEnvironment(){
        String ENV_HOST = System.getenv("WS_FACTORY_HOST");
        String ENV_PORT = System.getenv("WS_FACTORY_PORT");
        String ENV_DBNAME = System.getenv("WS_FACTORY_DBNAME");
        String ENV_USER = System.getenv("WS_FACTORY_USER");
        String ENV_PASSWORD = System.getenv("WS_FACTORY_PASSWORD");

        HOST = ENV_HOST==null ? HOST : ENV_HOST;
        PORT = ENV_PORT==null ? PORT : ENV_PORT;
        DBNAME = ENV_DBNAME==null ? DBNAME : ENV_DBNAME;
        USER = ENV_USER==null ? USER : ENV_USER;
        PASSWORD = ENV_PASSWORD==null ? PASSWORD : ENV_PASSWORD;
    }

    private DataSourceFactory() {
        configureEnvironment();
        String url = String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, DBNAME);
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
