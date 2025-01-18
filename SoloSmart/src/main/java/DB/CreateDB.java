package DB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class CreateDB {
    public static void createDB() {
        EntityManager em = Persistence.createEntityManagerFactory("mssql-pu").createEntityManager();
    }
}
