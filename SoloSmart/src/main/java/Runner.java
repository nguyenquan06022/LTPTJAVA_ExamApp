import DB.CreateDB;
import Dao.DsLuaChon_DAO;
import Dao.LopHoc_DAO;
import Dao.MonHoc_DAO;
import Dao.TaiKhoan_DAO;
import Entity.LopHoc;
import Entity.MonHoc;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Runner {
    private static EntityManager em;
    private static DsLuaChon_DAO dsLuaChon_DAO;

    public static void main(String[] args) {
        try {
            em = CreateDB.createDB();
            LopHoc_DAO lopHoc_DAO = new LopHoc_DAO(em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}