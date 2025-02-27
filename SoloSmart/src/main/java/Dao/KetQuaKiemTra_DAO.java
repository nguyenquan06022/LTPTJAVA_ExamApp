package Dao;

import Entity.BaiKiemTra;
import Entity.KetQuaKiemTra;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KetQuaKiemTra_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "KQKT" + df.format(now);
    }

    public KetQuaKiemTra_DAO() {
    }

    public KetQuaKiemTra_DAO(EntityManager em) {
        this.em = em;
    }
    public boolean themKetQuaKiemTra(KetQuaKiemTra ketQua) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            // Câu lệnh SQL chèn dữ liệu
            String sql = "INSERT INTO KetQuaKiemTras (diemSo, thoiGianLamBai, lanThu, diemCaoNhat, maBaiKiemTra, maTaiKhoan,maKetQuaKiemTra) " +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";

            em.createNativeQuery(sql)
                    .setParameter(1, ketQua.getDiemSo()) // float: điểm số
                    .setParameter(2, ketQua.getThoiGianLamBai()) // int: thời gian làm bài
                    .setParameter(3, ketQua.getLanThu()) // int: lần thử
                    .setParameter(4, ketQua.isDiemCaoNhat()) // boolean: điểm cao nhất
                    .setParameter(5, ketQua.getBaiKiemTra().getMaBaiKiemTra()) // Long: mã bài kiểm tra (FK)
                    .setParameter(6, ketQua.getTaiKhoan().getMaTaiKhoan()) // Long: mã tài khoản (FK)
                    .setParameter(7,ketQua.getMaKetQuaKiemTra())
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

    public KetQuaKiemTra getKetQuaKiemTra(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Mã kết quả kiểm tra không được để trống.");
        }

        try {
            String sql = "SELECT maketquakiemtra, DiemCaoNhat, diemso, lanthu, thoigianlambai, mabaiKiemTra, mataikhoan " +
                    "FROM ketquakiemtras WHERE maketquakiemtra = ?";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getResultList();

            if (results.isEmpty()) {
                return null; // Không có dữ liệu trả về null
            }

            // Lấy dòng đầu tiên
            Object[] row = results.get(0);
            KetQuaKiemTra ketQua = new KetQuaKiemTra();
            ketQua.setMaKetQuaKiemTra((String) row[0]);
            ketQua.setDiemCaoNhat((Boolean)row[1]);
            ketQua.setDiemSo((Float) row[2]);
            ketQua.setLanThu((Integer) row[3]);
            ketQua.setThoiGianLamBai((Integer) row[4]);
            ketQua.setMaKetQuaKiemTra((String) row[5]);
            ketQua.setTaiKhoan(new TaiKhoan((String) row[6]));

            return ketQua;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy kết quả kiểm tra với ID: " + id);
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    public ArrayList<KetQuaKiemTra> getDanhSachKetQuaKiemTra(String maTaiKhoan, String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaKiemTra> danhSachKetQua = new ArrayList<>();
        try {
            String sql = "SELECT maketquakiemtra, DiemCaoNhat, diemso, lanthu, thoigianlambai, mabaiKiemTra, mataikhoan " +
                    "FROM ketquakiemtras WHERE mataikhoan = ? AND mabaiKiemTra = ?";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, maTaiKhoan)
                    .setParameter(2, maBaiKiemTra)
                    .getResultList();


            for (Object[] row : results) {
                KetQuaKiemTra ketQua = new KetQuaKiemTra();
                ketQua.setMaKetQuaKiemTra((String) row[0]);
                ketQua.setDiemCaoNhat((Boolean) row[1]);
                ketQua.setDiemSo((Float) row[2]);
                ketQua.setLanThu((Integer) row[3]);
                ketQua.setThoiGianLamBai((Integer) row[4]);
                ketQua.setBaiKiemTra(new BaiKiemTra((String) row[5])); // Nếu có constructor BaiKiemTra(String id)
                ketQua.setTaiKhoan(new TaiKhoan((String) row[6]));     // Nếu có constructor TaiKhoan(String id)

                danhSachKetQua.add(ketQua);
            }
        }  catch (Exception e) {
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
