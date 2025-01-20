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
            String jpql = "SELECT k FROM KetQuaHocTap k WHERE k.taiKhoan.maTaiKhoan = :maTaiKhoan AND k.lopHoc.maLop = :maLop";
            ketQuaHocTap = em.createQuery(jpql, KetQuaHocTap.class)
                    .setParameter("maTaiKhoan", maTaiKhoan)
                    .setParameter("maLop", maLop)
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
    public boolean xoaKetQuaHocTap(String maTaiKhoan, String maLop) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String jpql = "UPDATE KetQuaHocTap k SET k.GPA = -1 WHERE k.taiKhoan.maTaiKhoan = :maTaiKhoan AND k.lopHoc.maLop = :maLop";
            int rowsAffected = em.createQuery(jpql)
                    .setParameter("maTaiKhoan", maTaiKhoan)
                    .setParameter("maLop", maLop)
                    .executeUpdate();
            tr.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa kết quả học tập", e);
        }
    }
    public ArrayList<KetQuaHocTap> getDanhSachKetQuaHocTap(String maLop) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaHocTap> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            String jpql = "SELECT k FROM KetQuaHocTap k WHERE k.lopHoc.maLop = :maLop";
            List<KetQuaHocTap> results = em.createQuery(jpql, KetQuaHocTap.class)
                    .setParameter("maLop", maLop)
                    .getResultList();
            danhSachKetQua.addAll(results); // Chuyển từ List sang ArrayList
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
