package Dao;

import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.LopHoc;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaiKiemTra_DAO  extends UnicastRemoteObject implements IBaiKiemTra_DAO {
    private EntityManager em;
    private static DateTimeFormatter df= DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException{
        LocalDateTime now = LocalDateTime.now();
        return "BKT" + df.format(now);
    }

    public BaiKiemTra_DAO() throws RemoteException {
        super();
    }

    public BaiKiemTra_DAO(EntityManager em) throws RemoteException{
        this.em = em;
    }

    @Override
    public boolean themBaiKiemTra(BaiKiemTra bkt) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO BaiKiemTras values (?,?, ?,?,? ,?,?,?,?,?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1,bkt.getMaBaiKiemTra() )
                    .setParameter(2,bkt.isChoPhepXemDiem() )
                    .setParameter(3,bkt.isChoPhepXemLai() )
                    .setParameter(4,bkt.getHeSo() )
                    .setParameter(5,bkt.isHienThiDapAn() )
                    .setParameter(6,bkt.getMatKhauBaiKiemTra())
                    .setParameter(7,bkt.getSoLanLamBai() )
                    .setParameter(8,bkt.getThangDiem() )
                    .setParameter(9,bkt.getThoiGianBatDau() )
                    .setParameter(10,bkt.getThoiGianKetThuc() )
                    .setParameter(11,bkt.getThoiGianLamBai() )
                    .setParameter(12,bkt.getTrangThai() )
                    .setParameter(13,bkt.getDeThi().getMaDeThi() )
                    .setParameter(14,bkt.getLopHoc().getMaLop() )
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public BaiKiemTra getBaiKiemTra(String id) throws RemoteException{
        BaiKiemTra baiKiemTra = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT mabaikiemtra, chophepxemdiem, chophepxemlai, heso, hienthidapan, matkhaubaikiemtra, " +
                    "solanlambai, thangdiem, thoigianbatdau, thoigianketthuc, thoigianlambai, trangthai, madethi, malop " +
                    "FROM BaiKiemTras WHERE maBaiKiemTra = ? and trangthai ='enable'";

            // Lấy kết quả truy vấn dưới dạng Object[]
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();

            // Tạo đối tượng BaiKiemTra và set giá trị
            if (result != null) {
                baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
                baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);

                // Set các đối tượng liên kết
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) result[12]);
                baiKiemTra.setDeThi(deThi);

                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) result[13]);
                baiKiemTra.setLopHoc(lopHoc);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return baiKiemTra;
    }
    @Override
    public BaiKiemTra getBaiKiemTra() throws RemoteException{
        BaiKiemTra baiKiemTra = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT top 1 mabaikiemtra, chophepxemdiem, chophepxemlai, heso, hienthidapan, matkhaubaikiemtra, " +
                    "solanlambai, thangdiem, thoigianbatdau, thoigianketthuc, thoigianlambai, trangthai, madethi, malop " +
                    "FROM BaiKiemTras WHERE maBaiKiemTra = ?";

            // Lấy kết quả truy vấn dưới dạng Object[]
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .getSingleResult();

            // Tạo đối tượng BaiKiemTra và set giá trị
            if (result != null) {
                baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
                baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);

                // Set các đối tượng liên kết
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) result[12]);
                baiKiemTra.setDeThi(deThi);

                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) result[13]);
                baiKiemTra.setLopHoc(lopHoc);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return baiKiemTra;
    }
    @Override
    public List<BaiKiemTra> getBaiKiemTraTheoTaiKhoan(String id) throws RemoteException{
    List<BaiKiemTra> list = new ArrayList<>();
    EntityTransaction tr = em.getTransaction();

    try {
        tr.begin();
        String sql = """
            SELECT bkt.maBaiKiemTra, bkt.choPhepXemDiem, bkt.choPhepXemLai, bkt.heSo,
                   bkt.hienThiDapAn, bkt.matKhauBaiKiemTra, bkt.soLanLamBai, bkt.thangDiem,
                   bkt.thoiGianBatDau, bkt.thoiGianKetThuc, bkt.thoiGianLamBai, bkt.trangThai,
                   bkt.maDeThi, bkt.maLop
            FROM TaiKhoans tk 
            INNER JOIN KetQuaHocTaps kqht ON kqht.maTaiKhoan = tk.maTaiKhoan
            INNER JOIN LopHocs lh ON lh.maLop = kqht.maLop
            INNER JOIN BaiKiemTras bkt ON bkt.maLop = lh.maLop
            WHERE tk.maTaiKhoan = ? AND bkt.trangThai = 'enable'
            AND bkt.thoiGianBatDau > GETDATE()
            """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter(1, id)
                .getResultList();

        for (Object[] result : results) {
            BaiKiemTra baiKiemTra = new BaiKiemTra();
            baiKiemTra.setMaBaiKiemTra((String) result[0]);
            baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
            baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
            baiKiemTra.setHeSo((Float) result[3]);
            baiKiemTra.setHienThiDapAn((Boolean) result[4]);
            baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
            baiKiemTra.setSoLanLamBai((Integer) result[6]);
            baiKiemTra.setThangDiem((Integer) result[7]);
            baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
            baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
            baiKiemTra.setThoiGianLamBai((Integer) result[10]);
            baiKiemTra.setTrangThai((String) result[11]);

            // Set quan hệ với DeThi
            DeThi deThi = new DeThi();
            deThi.setMaDeThi((String) result[12]);
            baiKiemTra.setDeThi(deThi);

            // Set quan hệ với LopHoc
            LopHoc lopHoc = new LopHoc();
            lopHoc.setMaLop((String) result[13]);
            baiKiemTra.setLopHoc(lopHoc);

            list.add(baiKiemTra);
        }

        tr.commit();
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException(e);
    }
    return list;
}

    @Override
    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTra() throws RemoteException{
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            // JPQL query
            String jpql = "SELECT b FROM BaiKiemTra b " +
                    "WHERE b.trangThai = 'enable'";
            danhSachBaiKiemTra = new ArrayList<>(em.createQuery(jpql, BaiKiemTra.class)
                    .getResultList());

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachBaiKiemTra;
    }
    @Override
    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTraTheoLop(String maLop) throws RemoteException{
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT * FROM BaiKiemTras \n" +
            "WHERE trangThai = 'enable' AND  maLop=?";
             List<Object[]> results = em.createNativeQuery(sql)
                .setParameter(1, maLop)
                .getResultList();
             for (Object[] result : results) {
            BaiKiemTra baiKiemTra = new BaiKiemTra();
            baiKiemTra.setMaBaiKiemTra((String) result[0]);
            baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
            baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
            baiKiemTra.setHeSo((Float) result[3]);
            baiKiemTra.setHienThiDapAn((Boolean) result[4]);
            baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
            baiKiemTra.setSoLanLamBai((Integer) result[6]);
            baiKiemTra.setThangDiem((Integer) result[7]);
            baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
            baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
            baiKiemTra.setThoiGianLamBai((Integer) result[10]);
            baiKiemTra.setTrangThai((String) result[11]);

            // Set quan hệ với DeThi
            DeThi deThi = new DeThi();
            deThi.setMaDeThi((String) result[12]);
            baiKiemTra.setDeThi(deThi);

            // Set quan hệ với LopHoc
            LopHoc lopHoc = new LopHoc();
            lopHoc.setMaLop((String) result[13]);
            baiKiemTra.setLopHoc(lopHoc);

            danhSachBaiKiemTra.add(baiKiemTra);
        }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachBaiKiemTra;
    }

    @Override
    public boolean updateBaiKiemTra(BaiKiemTra baiKiemTra) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "UPDATE BaiKiemTras " +
                    "SET choPhepXemDiem = ?, " +
                    "    choPhepXemLai = ?, " +
                    "    heSo = ?, " +
                    "    hienThiDapAn = ?, " +
                    "    soLanLamBai = ?, " +
                    "    thangDiem = ?, " +
                    "    thoiGianBatDau = ?, " +
                    "    thoiGianKetThuc = ?, " +
                    "    thoiGianLamBai = ?, " +
                    "    trangThai = ? ," +
                    "    matkhaubaikiemtra = ? " +
                    "WHERE maBaiKiemTra = ?";

            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, baiKiemTra.isChoPhepXemDiem())
                    .setParameter(2, baiKiemTra.isChoPhepXemLai())
                    .setParameter(3, baiKiemTra.getHeSo())
                    .setParameter(4, baiKiemTra.isHienThiDapAn())
                    .setParameter(5, baiKiemTra.getSoLanLamBai())
                    .setParameter(6, baiKiemTra.getThangDiem())
                    .setParameter(7, baiKiemTra.getThoiGianBatDau())
                    .setParameter(8, baiKiemTra.getThoiGianKetThuc())
                    .setParameter(9, baiKiemTra.getThoiGianLamBai())
                    .setParameter(10, baiKiemTra.getTrangThai())
                    .setParameter(11, baiKiemTra.getMatKhauBaiKiemTra())
                    .setParameter(12, baiKiemTra.getMaBaiKiemTra())
                    .executeUpdate();

            tr.commit();

            return updatedRows > 0;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật bài kiểm tra: " + baiKiemTra.getMaBaiKiemTra(), e);
        }
    }

    @Override
    public boolean deleteBaiKiemTra(String id) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "UPDATE BaiKiemTras SET trangThai = 'disable' WHERE mabaikiemtra = ?";

            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();
            tr.commit();

            return updatedRows > 0;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    // lấy ra danh sách bài kiểm tra của sinh viên theo lớp học
    @Override
    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTraCuaSinhVienTheoLop(String maTaiKhoan, String maLop) throws RemoteException{
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "select maBaiKiemTra,choPhepXemDiem,choPhepXemLai,heSo,hienThiDapAn,matKhauBaiKiemTra,soLanLamBai,thangDiem,thoiGianBatDau,thoiGianKetThuc,thoiGianLamBai,bkt.trangThai,bkt.maDeThi,bkt.maLop from BaiKiemTras bkt \n" +
                    "join LopHocs lh on lh.maLop = bkt.maLop\n" +
                    "join KetQuaHocTaps kqht on kqht.maLop = lh.maLop\n" +
                    "join TaiKhoans tk on tk.maTaiKhoan = kqht.maTaiKhoan\n" +
                    "where tk.maTaiKhoan = ? and lh.maLop = ?\n" +
                    "and bkt.trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, maTaiKhoan)
                    .setParameter(2,maLop)
                    .getResultList();
            for (Object[] result : results) {
                BaiKiemTra baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
                baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);

                // Set quan hệ với DeThi
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) result[12]);
                baiKiemTra.setDeThi(deThi);

                // Set quan hệ với LopHoc
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) result[13]);
                baiKiemTra.setLopHoc(lopHoc);

                danhSachBaiKiemTra.add(baiKiemTra);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachBaiKiemTra;
    }

    // lấy ra danh sách bài kiểm tra theo lớp học của giáo viên
    @Override
    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTraCuaGiaoVienTheoLop(String maTaiKhoan, String maLop) throws RemoteException{
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "select maBaiKiemTra,choPhepXemDiem,choPhepXemLai,heSo,hienThiDapAn,matKhauBaiKiemTra,soLanLamBai,thangDiem,thoiGianBatDau,thoiGianKetThuc,thoiGianLamBai,bkt.trangThai,bkt.maDeThi,bkt.maLop from BaiKiemTras bkt join LopHocs lh on lh.maLop = bkt.maLop\n" +
                    "join TaiKhoans tk on tk.maTaiKhoan = lh.maGiaoVien\n" +
                    "where tk.maTaiKhoan = ? and lh.maLop = ?\n" +
                    "and bkt.trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, maTaiKhoan)
                    .setParameter(2,maLop)
                    .getResultList();
            for (Object[] result : results) {
                BaiKiemTra baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
                baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);

                // Set quan hệ với DeThi
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) result[12]);
                baiKiemTra.setDeThi(deThi);

                // Set quan hệ với LopHoc
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) result[13]);
                baiKiemTra.setLopHoc(lopHoc);

                danhSachBaiKiemTra.add(baiKiemTra);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachBaiKiemTra;
    }

    // Lấy ra danh sách tài khoản đã tham gia kiểm tra và điểm số của tài khoản đó trong bài kiểm tra đó
    @Override
    public Map<TaiKhoan,Float> getDsTaiKhoanThamGiaKiemTraVaDiemSo(String maBaiKiemTra) throws RemoteException{
        Map<TaiKhoan,Float> res = new HashMap<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT tk.maTaiKhoan,Ho,Ten,dangOnline,gioiTinh,matKhau,tenTaiKhoan,tk.trangThai,vaiTro,kqkt.diemSo FROM KetQuaKiemTras kqkt JOIN BaiKiemTras bkt\n" +
                    "ON kqkt.maBaiKiemTra = bkt.maBaiKiemTra JOIN TaiKhoans tk\n" +
                    "ON tk.maTaiKhoan = kqkt.maTaiKhoan\n" +
                    "WHERE diemCaoNhat = 1 AND bkt.maBaiKiemTra = ?\n" +
                    "ORDER BY tk.Ten ASC";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, maBaiKiemTra)
                    .getResultList();
            for (Object[] result : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[5]);
                taiKhoan.setTenTaiKhoan((String) result[6]);
                taiKhoan.setTrangThai((String) result[7]);
                taiKhoan.setVaiTro((String) result[8]);
                taiKhoan.setDangOnline((String) result[3]);
                taiKhoan.setGioiTinh((String) result[4]);
                taiKhoan.setHo((String) result[1]);
                taiKhoan.setTen((String) result[2]);
                float diemSo = (Float) result[9];
                res.put(taiKhoan,diemSo);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return res;
    }
    @Override
    public List<BaiKiemTra> getBaiKiemTraTheoTaiKhoanGV(String id) throws RemoteException{
    List<BaiKiemTra> list = new ArrayList<>();
    EntityTransaction tr = em.getTransaction();

    try {
        tr.begin();
        String sql = """
            select bkt.* from BaiKiemTras bkt inner join LopHocs lh on lh.maLop=bkt.maLop
            where lh.maGiaoVien=? and bkt.trangThai='enable' and bkt.thoiGianBatDau>GETDATE()
            """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter(1, id)
                .getResultList();

        for (Object[] result : results) {
            BaiKiemTra baiKiemTra = new BaiKiemTra();
            baiKiemTra.setMaBaiKiemTra((String) result[0]);
            baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
            baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
            baiKiemTra.setHeSo((Float) result[3]);
            baiKiemTra.setHienThiDapAn((Boolean) result[4]);
            baiKiemTra.setMatKhauBaiKiemTra((String) result[5]);
            baiKiemTra.setSoLanLamBai((Integer) result[6]);
            baiKiemTra.setThangDiem((Integer) result[7]);
            baiKiemTra.setThoiGianBatDau(((Timestamp) result[8]).toLocalDateTime());
            baiKiemTra.setThoiGianKetThuc(((Timestamp) result[9]).toLocalDateTime());
            baiKiemTra.setThoiGianLamBai((Integer) result[10]);
            baiKiemTra.setTrangThai((String) result[11]);

            // Set quan hệ với DeThi
            DeThi deThi = new DeThi();
            deThi.setMaDeThi((String) result[12]);
            baiKiemTra.setDeThi(deThi);

            // Set quan hệ với LopHoc
            LopHoc lopHoc = new LopHoc();
            lopHoc.setMaLop((String) result[13]);
            baiKiemTra.setLopHoc(lopHoc);

            list.add(baiKiemTra);
        }

        tr.commit();
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException(e);
    }
    return list;
}

}
