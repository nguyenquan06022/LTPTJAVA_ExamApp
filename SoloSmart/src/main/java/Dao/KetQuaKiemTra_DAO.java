package Dao;

import Entity.BaiKiemTra;
import Entity.KetQuaKiemTra;
import Entity.TaiKhoan;
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
            // Câu lệnh SQL chèn dữ liệu
            String sql = "INSERT INTO KetQuaKiemTras (diemSo, thoiGianLamBai, lanThu, diemCaoNhat, maBaiKiemTra, maTaiKhoan) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            em.createNativeQuery(sql)
                    .setParameter(1, ketQua.getDiemSo()) // float: điểm số
                    .setParameter(2, ketQua.getThoiGianLamBai()) // int: thời gian làm bài
                    .setParameter(3, ketQua.getLanThu()) // int: lần thử
                    .setParameter(4, ketQua.isDiemCaoNhat()) // boolean: điểm cao nhất
                    .setParameter(5, ketQua.getBaiKiemTra().getMaBaiKiemTra()) // Long: mã bài kiểm tra (FK)
                    .setParameter(6, ketQua.getTaiKhoan().getMaTaiKhoan()) // Long: mã tài khoản (FK)
                    .executeUpdate();

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
    public ArrayList<KetQuaKiemTra> getDanhSachKetQuaKiemTra(String maTaiKhoan, String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaKiemTra> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            // Truy vấn JPQL
            String jpql = "SELECT k FROM KetQuaKiemTra k WHERE k.taiKhoan.maTaiKhoan = :maTaiKhoan AND k.baiKiemTra.maBaiKiemTra = :maBaiKiemTra";
            List<KetQuaKiemTra> resultList = em.createQuery(jpql, KetQuaKiemTra.class)
                    .setParameter("maTaiKhoan", maTaiKhoan)
                    .setParameter("maBaiKiemTra", maBaiKiemTra)
                    .getResultList();
            danhSachKetQua = new ArrayList<>(resultList);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả kiểm tra", e);
        }
        return danhSachKetQua;
    }

    public KetQuaKiemTra getKetQuaKiemTra(String maBaiKiemTra, String maTaiKhoan) {
        EntityTransaction tr = em.getTransaction();
        KetQuaKiemTra ketQuaKiemTra = null;
        try {
            tr.begin();
            // Truy vấn JPQL
            String jpql = "SELECT k FROM KetQuaKiemTra k WHERE k.baiKiemTra.maBaiKiemTra = :maBaiKiemTra AND k.taiKhoan.maTaiKhoan = :maTaiKhoan";
            ketQuaKiemTra = em.createQuery(jpql, KetQuaKiemTra.class)
                    .setParameter("maBaiKiemTra", maBaiKiemTra)
                    .setParameter("maTaiKhoan", maTaiKhoan)
                    .getSingleResult();
            tr.commit();

        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả kiểm tra", e);
        }
        return ketQuaKiemTra;
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
