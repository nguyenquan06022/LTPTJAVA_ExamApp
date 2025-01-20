package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class DsCauTraLoi_DAO {
    private EntityManager em;
    public DsCauTraLoi_DAO() {
        this.em = em;
    }
    public boolean themCauTraLoi(String maketquakiemtra, String cauTraLoi){
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO dsCauTraLoi values (maketquakiemtra, cautraloi)";
            em.createNativeQuery(sql)
                    .setParameter(1, maketquakiemtra)
                    .setParameter(2, cauTraLoi)
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }
    public String getCauTraLoi(String maKetQuaKiemTra, String cauTraLoi) {
        EntityTransaction tr = em.getTransaction();
        String ketQua = null;
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maketquakiemtra = ? AND cautraloi = ?";
            ketQua = (String) em.createNativeQuery(sql)
                    .setParameter(1, maKetQuaKiemTra) // Sử dụng vị trí thay vì tên tham số
                    .setParameter(2, cauTraLoi)
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

    public ArrayList<String> getDSCauTraLoi(String maKetQuaKiemTra) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maketquakiemtra = ?";
            List<String> results = em.createNativeQuery(sql)
                    .setParameter(1, maKetQuaKiemTra) // Sử dụng tham số vị trí
                    .getResultList();
            dsLuaChon.addAll(results);
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
