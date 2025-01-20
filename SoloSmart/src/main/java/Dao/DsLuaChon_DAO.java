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

    public boolean capNhatLuaChon(String maCauHoi, String luaChonCu, String luaChonMoi) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "UPDATE dsLuaChon SET luaChon = :luaChonMoi WHERE maCauHoi = :maCauHoi AND luaChon = :luaChonCu";
            int result = em.createNativeQuery(sql)
                    .setParameter("luaChonMoi", luaChonMoi)
                    .setParameter("maCauHoi", maCauHoi)
                    .setParameter("luaChonCu", luaChonCu)
                    .executeUpdate();

            tr.commit();
            isSuccess = result > 0;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }



}
