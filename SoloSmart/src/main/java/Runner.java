import DB.CreateDB;

public class Runner {
    public static void main(String[] args) {
        try {
            CreateDB.createDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
