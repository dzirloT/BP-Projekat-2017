package com.example.seilab.bp007;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by seilab on 11/19/17.
 */

public class ConnectionHelper extends Application {
    // Toast.makeText(MainActivity.this,
    //        "Success", Toast.LENGTH_LONG).show();
    // Statement stmt=connection.createStatement();

    //  ResultSet rs=stmt.executeQuery("select * from TYPEOFEMPLOYEE");
    //////  }

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    // private static final String DEFAULT_URL = "jdbc:oracle:thin:@80.65.65.66:1521:ETFLAB";
    //private static final String DEFAULT_USERNAME = "BP07";
    //private static final String DEFAULT_PASSWORD = "eyEeo2es";
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static Connection connection;
    private static ConnectionHelper sInstance;

    public static ConnectionHelper getApplication() {
        return sInstance;
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static void Initial(String url,String username, String password)
    {

        URL=url;
        USERNAME=username;
        PASSWORD=password;
    }

    public static void createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection=DriverManager.getConnection(url, username, password);
    }

    public static void createConnection() throws ClassNotFoundException, SQLException {
        createConnection(DRIVER, URL, USERNAME, PASSWORD);
    }

    public Connection getCurrentConnection()
    {
        return connection;
    }

    public void closeCurrentConnection()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
