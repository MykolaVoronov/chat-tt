package chat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String ABSOLUTE_PROPERTIES_PATH = "PATH";

    static {
        File file = new File(ABSOLUTE_PROPERTIES_PATH);
        try (FileInputStream stream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(stream);
            JDBC_DRIVER = properties.getProperty("db.driver");
            URL = properties.getProperty("db.url");
            USERNAME = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find SQL Driver", e);
        } catch (IOException e) {
            throw new RuntimeException("Can`t find db.properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }
}
