package Dao;

import Entity.KetQuaHocTap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class KetQuaHocTap_DAO {
    private EntityManager em;

    public KetQuaHocTap_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean themKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(ketQuaHocTap);
            tr.commit();
            return true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi thêm kết quả học tập", e);
        }
    }
    public KetQuaHocTap getKetQuaHocTap(String maTaiKhoan, String maLop) {
        EntityTransaction tr = em.getTransaction();
        KetQuaHocTap ketQuaHocTap = null;
        try {
            tr.begin();
            String jpql = "SELECT * FROM KetQuaHocTaps  WHERE maTaiKhoan = ? AND maLop = ?";
            ketQuaHocTap = em.createQuery(jpql, KetQuaHocTap.class)
                    .setParameter(1, maTaiKhoan)
                    .setParameter(2, maLop)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy kết quả học tập", e);
        }
        return ketQuaHocTap;
    }
    public boolean capNhatKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(ketQuaHocTap);
            tr.commit();
            return true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật kết quả học tập", e);
        }
    }

    public ArrayList<KetQuaHocTap> getDanhSachKetQuaHocTap(String maLop) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaHocTap> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            String jpql = "SELECT * FROM KetQuaHocTaps  WHERE maLop = ?";
            List<KetQuaHocTap> results = em.createQuery(jpql, KetQuaHocTap.class)
                    .setParameter(1, maLop)
                    .getResultList();
            danhSachKetQua.addAll(results);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả học tập", e);
        }
        return danhSachKetQua;
    }

}
