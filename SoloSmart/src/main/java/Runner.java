import DB.CreateDB;
import Dao.DsLuaChon_DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Runner {
    private static EntityManager em;
    private static DsLuaChon_DAO dsLuaChon_DAO;
    public static void main(String[] args) {
        try {
            em = CreateDB.createDB();
            dsLuaChon_DAO = new DsLuaChon_DAO(em);
            boolean check = dsLuaChon_DAO.themLuaChon("123","A.1");
            System.out.println(check);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
