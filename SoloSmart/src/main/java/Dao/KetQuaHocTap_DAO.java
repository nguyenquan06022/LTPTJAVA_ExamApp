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

    // Thêm kết quả học tập
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

    // Lấy kết quả học tập dựa trên mã tài khoản và mã lớp
    public KetQuaHocTap getKetQuaHocTap(String maTaiKhoan, String maLop) {
        EntityTransaction tr = em.getTransaction();
        KetQuaHocTap ketQuaHocTap = null;
        try {
            tr.begin();
            String jpql = "SELECT k FROM KetQuaHocTap k " +
                    "WHERE k.taiKhoan.maTaiKhoan = :maTaiKhoan AND k.lopHoc.maLop = :maLop";
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

    // Cập nhật kết quả học tập
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

    // Lấy danh sách kết quả học tập dựa trên mã lớp
    public ArrayList<KetQuaHocTap> getDanhSachKetQuaHocTap(String maLop) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaHocTap> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            // Sử dụng cú pháp JPQL
            String jpql = "SELECT k FROM KetQuaHocTap k WHERE k.lopHoc.maLop = :maLop";
            List<KetQuaHocTap> results = em.createQuery(jpql, KetQuaHocTap.class)
                    .setParameter("maLop", maLop)
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
