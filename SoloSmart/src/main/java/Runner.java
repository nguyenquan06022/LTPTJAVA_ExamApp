import DB.CreateDB;
import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Runner {
    private static EntityManager em;
    private static DsLuaChon_DAO dsLuaChon_DAO;

    public static void main(String[] args) {
        try {
            em = CreateDB.createDB();
            CauHoi_DAO cauHoi_DAO = new CauHoi_DAO(em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}