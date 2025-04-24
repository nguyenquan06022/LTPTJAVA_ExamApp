package Dao;

import Entity.BaiKiemTra;
import Entity.KetQuaKiemTra;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KetQuaKiemTra_DAO extends UnicastRemoteObject implements IKetQuaKiemTra_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException {
        LocalDateTime now = LocalDateTime.now();
        return "KQKT" + df.format(now);
    }

    public KetQuaKiemTra_DAO() throws RemoteException {
        super();
    }

    public KetQuaKiemTra_DAO(EntityManager em) throws RemoteException {
        this.em = em;
    }

    @Override
    public boolean themKetQuaKiemTra(KetQuaKiemTra ketQua) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            // Câu lệnh SQL chèn dữ liệu
            String sql = "INSERT INTO KetQuaKiemTras (diemSo, thoiGianLamBai, lanThu, diemCaoNhat, maBaiKiemTra, maTaiKhoan,maKetQuaKiemTra) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";

            em.createNativeQuery(sql)
                    .setParameter(1, ketQua.getDiemSo()) // float: điểm số
                    .setParameter(2, ketQua.getThoiGianLamBai()) // int: thời gian làm bài
                    .setParameter(3, ketQua.getLanThu()) // int: lần thử
                    .setParameter(4, ketQua.isDiemCaoNhat()) // boolean: điểm cao nhất
                    .setParameter(5, ketQua.getBaiKiemTra().getMaBaiKiemTra()) // Long: mã bài kiểm tra (FK)
                    .setParameter(6, ketQua.getTaiKhoan().getMaTaiKhoan()) // Long: mã tài khoản (FK)
                    .setParameter(7, ketQua.getMaKetQuaKiemTra())
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

    @Override
    public KetQuaKiemTra getKetQuaKiemTra(String id) throws RemoteException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Mã kết quả kiểm tra không được để trống.");
        }

        try {
            String sql = "SELECT maketquakiemtra, DiemCaoNhat, diemso, lanthu, thoigianlambai, mabaiKiemTra, mataikhoan "
                    +
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
            ketQua.setDiemCaoNhat((Boolean) row[1]);
            ketQua.setDiemSo((Float) row[2]);
            ketQua.setLanThu((Integer) row[3]);
            ketQua.setThoiGianLamBai((Integer) row[4]);
            ketQua.setBaiKiemTra(new BaiKiemTra((String) row[5]));
            ketQua.setTaiKhoan(new TaiKhoan((String) row[6]));

            return ketQua;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy kết quả kiểm tra với ID: " + id);
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    @Override
    public float tinhDiemChoSinhVien(String maBaiKiemTra, String maKetQuaKiemTra) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            String sql = "WITH \n" +
                    "DapAnDung AS (\n" +
                    "    SELECT \n" +
                    "        ROW_NUMBER() OVER (ORDER BY dslc.maCauHoi ASC) AS CauHoi,\n" +
                    "        LEFT(dslc.luaChon, CHARINDEX('.', dslc.luaChon) - 1) AS DapAn\n" +
                    "    FROM DeThis dt\n" +
                    "    JOIN CauHois ch ON ch.maDeThi = dt.maDeThi\n" +
                    "    JOIN dsLuaChon dslc ON dslc.maCauHoi = ch.maCauHoi\n" +
                    "    JOIN BaiKiemTras bkt ON bkt.maDeThi = dt.maDeThi\n" +
                    "    WHERE bkt.maBaiKiemTra = ?\n" +
                    "      AND dslc.dapAnDung = 1\n" +
                    "),\n" +
                    "CauTraLoiThiSinh AS (\n" +
                    "    SELECT \n" +
                    "        LEFT(dsctl.cauTraLoi, CHARINDEX('.', dsctl.cauTraLoi + '.') - 1) AS CauHoi,\n" +
                    "        SUBSTRING(dsctl.cauTraLoi, CHARINDEX('.', dsctl.cauTraLoi) + 1, 1) AS TraLoi\n" +
                    "    FROM KetQuaKiemTras kqkt \n" +
                    "    JOIN dsCauTraLoi dsctl ON dsctl.maKetQuaKiemTra = kqkt.maKetQuaKiemTra\n" +
                    "    WHERE kqkt.maKetQuaKiemTra = ?\n" +
                    "),\n" +
                    "TongSoCau AS (\n" +
                    "    SELECT COUNT(*) AS Total FROM DapAnDung\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    COUNT(CASE WHEN c.TraLoi = d.DapAn THEN 1 END) AS SoCauDung,\n" +
                    "    COUNT(CASE WHEN c.TraLoi = d.DapAn THEN 1 END) * 10.0 / (SELECT Total FROM TongSoCau) AS DiemDatDuoc\n"
                    +
                    "FROM CauTraLoiThiSinh c\n" +
                    "JOIN DapAnDung d ON c.CauHoi = d.CauHoi";

            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, maBaiKiemTra)
                    .setParameter(2, maKetQuaKiemTra)
                    .getSingleResult();

            // Chỉ lấy điểm (cột thứ 2 trong kết quả)
            float diem = ((Number) result[1]).floatValue();
            return diem;

        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<KetQuaKiemTra> getDanhSachKetQuaKiemTra(String maTaiKhoan, String maBaiKiemTra)
            throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaKiemTra> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT maketquakiemtra, DiemCaoNhat, diemso, lanthu, thoigianlambai, mabaiKiemTra, mataikhoan "
                    +
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
                ketQua.setTaiKhoan(new TaiKhoan((String) row[6])); // Nếu có constructor TaiKhoan(String id)

                danhSachKetQua.add(ketQua);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả kiểm tra", e);
        }
        return danhSachKetQua;
    }

    @Override
    public KetQuaKiemTra getKetQuaKiemTra(String maBaiKiemTra, String maTaiKhoan) throws RemoteException {
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

    @Override
    public boolean updateKetQuaKiemTra(KetQuaKiemTra ketQua) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "update KetQuaKiemTras set diemCaoNhat = ?, diemSo = ?, lanThu = ?, thoiGianLamBai = ?, maBaiKiemTra = ?, maTaiKhoan = ? where maKetQuaKiemTra = ?";
            em.createNativeQuery(sql)
                    .setParameter(1, ketQua.isDiemCaoNhat())
                    .setParameter(2, ketQua.getDiemSo())
                    .setParameter(3, ketQua.getLanThu())
                    .setParameter(4, ketQua.getThoiGianLamBai())
                    .setParameter(5, ketQua.getBaiKiemTra().getMaBaiKiemTra())
                    .setParameter(6, ketQua.getTaiKhoan().getMaTaiKhoan())
                    .setParameter(7, ketQua.getMaKetQuaKiemTra())
                    .executeUpdate();
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
    @Override
    public ArrayList<Float> getDsDiemTheoBaiKiemTra(String maLop, String maBaiKiemTra) throws RemoteException {
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
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachKetQua;
    }

    // tính điểm sinh viên cho bài kiểm tra theo mã sinh viên và mã bài kiểm tra

    // cập nhật điểm cao nhất
    @Override
    public boolean updateDiemCaoNhatChoBaiKiemTraCuaSinhVien(String maTaiKhoan, String maBaiKiemTra)
            throws RemoteException {
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
