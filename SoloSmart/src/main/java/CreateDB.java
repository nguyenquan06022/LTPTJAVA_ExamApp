import DataFaker.Data;
import jakarta.persistence.EntityManager;

public class CreateDB {
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            em = DB.CreateDB.createDB();
            Data.generateData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}