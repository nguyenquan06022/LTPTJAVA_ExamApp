package Dao;

import Entity.KetQuaKiemTra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;


public class KetQuaKiemTra_DAO {
    private EntityManager em;

    public KetQuaKiemTra_DAO(EntityManager em) {
        this.em = em;
    }
    public boolean themKetQuaKiemTra(KetQuaKiemTra ketQua) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            em.persist(ketQua);
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi thêm kết quả kiểm tra", e);
        }
        return isSuccess;
    }
    public KetQuaKiemTra getKetQuaKiemTra(Long id) {
        EntityTransaction tr = em.getTransaction();
        KetQuaKiemTra ketQuaKiemTra = null;
        try {
            tr.begin();
            ketQuaKiemTra = em.find(KetQuaKiemTra.class, id);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy kết quả kiểm tra", e);
        }
        return ketQuaKiemTra;
    }
    public ArrayList<KetQuaKiemTra> getDanhSachKetQuaKiemTra() {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaKiemTra> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            String jpql = "SELECT k FROM KetQuaKiemTra k";
            List<KetQuaKiemTra> resultList = em.createQuery(jpql, KetQuaKiemTra.class).getResultList();
            danhSachKetQua = new ArrayList<>(resultList); // Chuyển đổi sang ArrayList
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả kiểm tra", e);
        }
        return danhSachKetQua;
    }
    public boolean updateKetQuaKiemTra(KetQuaKiemTra ketQua) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            em.merge(ketQua);
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật kết quả kiểm tra", e);
        }
        return isSuccess;
    }



}
