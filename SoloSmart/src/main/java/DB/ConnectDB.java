package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public void Connect() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=SoloSmartDB;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "sapassword";
        try {
            con=DriverManager.getConnection(url,user,password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void disconnect() {
        if(con!=null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public Connection getConnection() {
        return con;
    }
}
