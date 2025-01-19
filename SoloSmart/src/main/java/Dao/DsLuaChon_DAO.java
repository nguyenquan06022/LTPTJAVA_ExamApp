package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DsLuaChon_DAO {

    private EntityManager em;

    public DsLuaChon_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean themLuaChon(String maCauHoi, String luaChon) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO dsLuaChon values (:maCauHoi, :luaChon)";
            em.createNativeQuery(sql)
                    .setParameter("maCauHoi", maCauHoi)
                    .setParameter("luaChon", luaChon)
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }
}
