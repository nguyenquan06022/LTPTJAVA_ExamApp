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

    // thống kê điểm theo bài kiểm tra
    public ArrayList<Float> getDsDiemTheoBaiKiemTra(String maLop, String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<Float> danhSachKetQua = new ArrayList<>();
        try {
            String sql = "SELECT diemSo FROM KetQuaKiemTras kqkt JOIN BaiKiemTras bkt\n" +
                    "ON kqkt.maBaiKiemTra = bkt.maBaiKiemTra JOIN LopHocs lh\n" +
                    "ON lh.maLop = bkt.maLop\n" +
                    "WHERE diemCaoNhat = 1 AND lh.maLop = ? AND bkt.maBaiKiemTra = ?";

            List<Float> results = em.createNativeQuery(sql)
                    .setParameter(1, maLop)
                    .setParameter(2, maBaiKiemTra)
                    .getResultList();
            for (Float row : results) {
                danhSachKetQua.add(row);
            }
        }  catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachKetQua;
    }

    // tính điểm sinh viên cho bài kiểm tra theo mã sinh viên và mã bài kiểm tra
    public float tinhDiemChoSinhVien(String maSinhVien,String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        try {
            String sql = "WITH CauTraLoiSinhVien AS (\n" +
                    "    SELECT \n" +
                    "        ROW_NUMBER() OVER (ORDER BY CAST(LEFT(dsctl.cauTraLoi, CHARINDEX('.', dsctl.cauTraLoi) - 1) AS INT)) AS stt,\n" +
                    "        SUBSTRING(dsctl.cauTraLoi, CHARINDEX('.', dsctl.cauTraLoi) + 1, LEN(dsctl.cauTraLoi)) AS dapAn\n" +
                    "    FROM TaiKhoans tk \n" +
                    "    JOIN KetQuaKiemTras kqkt ON tk.maTaiKhoan = kqkt.maTaiKhoan \n" +
                    "    JOIN dsCauTraLoi dsctl ON dsctl.maKetQuaKiemTra = kqkt.maKetQuaKiemTra \n" +
                    "    JOIN BaiKiemTras bkt ON bkt.maBaiKiemTra = kqkt.maBaiKiemTra\n" +
                    "    WHERE tk.maTaiKhoan = ? \n" +
                    "      AND bkt.maBaiKiemTra = ?\n" +
                    "),\n" +
                    "DapAnDung AS (\n" +
                    "    SELECT \n" +
                    "        ROW_NUMBER() OVER (ORDER BY ch.maCauHoi) AS stt,\n" +
                    "        LEFT(dslc.luaChon, 1) AS dapAnDung\n" +
                    "    FROM BaiKiemTras bkt \n" +
                    "    JOIN DeThis dt ON bkt.maDeThi = dt.maDeThi \n" +
                    "    JOIN CauHois ch ON ch.maDeThi = dt.maDeThi \n" +
                    "    JOIN dsLuaChon dslc ON ch.maCauHoi = dslc.maCauHoi\n" +
                    "    WHERE bkt.maBaiKiemTra = ? AND dslc.dapAnDung = 1\n" +
                    ")\n" +
                    "\n" +
                    "SELECT \n" +
                    "    COUNT(*) * 1.0 / (SELECT COUNT(*) FROM DapAnDung) * 10 AS diem\n" +
                    "FROM CauTraLoiSinhVien sv\n" +
                    "JOIN DapAnDung da ON sv.stt = da.stt\n" +
                    "WHERE sv.dapAn = da.dapAnDung";

            float result = ((Number) em.createNativeQuery(sql)
                    .setParameter(1, maSinhVien)
                    .setParameter(2, maBaiKiemTra)
                    .setParameter(3, maBaiKiemTra)
                    .getSingleResult()).floatValue();
            return result;

        }  catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    //cập nhật điểm cao nhất
    public boolean updateDiemCaoNhatChoBaiKiemTraCuaSinhVien(String maTaiKhoan,String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "WITH TopDiem AS (\n" +
                    "    SELECT *,\n" +
                    "           MAX(diemSo) OVER(PARTITION BY maBaiKiemTra, maTaiKhoan) AS maxDiem\n" +
                    "    FROM KetQuaKiemTras\n" +
                    "    WHERE maBaiKiemTra = ?" +
                    "      AND maTaiKhoan = ?" +
                    ")\n" +
                    "UPDATE TopDiem\n" +
                    "SET diemCaoNhat = CASE \n" +
                    "    WHEN diemSo = maxDiem THEN 1\n" +
                    "    ELSE 0\n" +
                    "END;";
            em.createNativeQuery(sql)
                    .setParameter(1, maBaiKiemTra)
                    .setParameter(2, maTaiKhoan)
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
}
