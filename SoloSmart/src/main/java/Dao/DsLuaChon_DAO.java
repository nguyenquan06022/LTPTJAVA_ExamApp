package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            String sql = "INSERT INTO dsLuaChon values (?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, luaChon)
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
            String sql = "UPDATE dsLuaChon SET luaChon = ? WHERE maCauHoi = ? AND luaChon = ?";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, luaChonMoi)
                    .setParameter(2, maCauHoi)
                    .setParameter(3, luaChonCu)
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
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = ? AND luaChon = ?";
            ketQua = (String) em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, luaChon)
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
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = ?";
            // Sử dụng NativeQuery trả về danh sách các đối tượng
            List<String> rs = em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .getResultList();
            dsLuaChon = new ArrayList<>(rs);


            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách lựa chọn", e);
        }
        return dsLuaChon;
    }


    public boolean xoaLuaChon(String maCauHoi, String luaChon) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "DELETE FROM dsLuaChon WHERE maCauHoi = ? AND luaChon = ?";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, luaChon)
                    .executeUpdate();
            tr.commit();
            isSuccess = result > 0; // Kiểm tra xem có bản ghi nào bị xóa không
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa lựa chọn", e);
        }
        return isSuccess;
    }


}
