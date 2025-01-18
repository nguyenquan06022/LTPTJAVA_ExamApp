import DB.ConnectDB;
import DB.CreateDB;

public class Runner {
    public static void main(String[] args) {
        try {
            CreateDB.createDB();
            ConnectDB.getInstance().Connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
