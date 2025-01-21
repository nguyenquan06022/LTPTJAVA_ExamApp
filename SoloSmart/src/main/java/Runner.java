import DB.CreateDB;
import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;

public class Runner {
    private static EntityManager em;
    private static DsLuaChon_DAO dsLuaChon_DAO;

    public static void main(String[] args) {
        try {
            em = CreateDB.createDB();
            KetQuaKiemTra_DAO dsLuaChonDAO = new KetQuaKiemTra_DAO(em);
            KetQuaKiemTra ketqua= dsLuaChonDAO.getKetQuaKiemTra(1L);
            System.out.println(ketqua.getDiemSo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}