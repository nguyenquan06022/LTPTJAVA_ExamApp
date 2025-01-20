package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

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
    public String getLuaChon(String maCauHoi, String luaChon) {
        EntityTransaction tr = em.getTransaction();
        String ketQua = null;
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = :maCauHoi AND luaChon = :luaChon";
            ketQua = (String) em.createNativeQuery(sql)
                    .setParameter("maCauHoi", maCauHoi)
                    .setParameter("luaChon", luaChon)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy lựa chọn", e);
        }
        return ketQua;
    }
    public ArrayList<String> getDSLuaChon(String maCauHoi) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = :maCauHoi";
            List<String> rs= em.createNativeQuery(sql)
                    .setParameter("maCauHoi", maCauHoi)
                    .getResultList();
            dsLuaChon=new ArrayList<>(rs);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách lựa chọn", e);
        }
        return dsLuaChon;
    }


}
