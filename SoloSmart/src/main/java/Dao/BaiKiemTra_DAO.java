package Dao;

import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.LopHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BaiKiemTra_DAO {
    private EntityManager em;
    private static DateTimeFormatter df= DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "BKT" + df.format(now);
    }

    public BaiKiemTra_DAO() {
    }

    public BaiKiemTra_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean themBaiKiemTra(BaiKiemTra bkt){
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
                    .setParameter(6,bkt.getMaBaiKiemTra() )
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

    public BaiKiemTra getBaiKiemTra(String id) {
        BaiKiemTra baiKiemTra = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT mabaikiemtra, chophepxemdiem, chophepxemlai, heso, hienthidapan, matkhaubaikiemtra, " +
                    "solanlambai, thangdiem, thoigianbatdau, thoigianketthuc, thoigianlambai, trangthai, madethi, malop " +
                    "FROM BaiKiemTras WHERE maBaiKiemTra = ?";

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



    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTra() {
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT * FROM BaiKiemTras " +
                    "where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] result : results) {
                BaiKiemTra baiKiemTra = new BaiKiemTra();
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
    public ArrayList<BaiKiemTra> getDanhSachBaiKiemTraTheoLop(String maLop) {
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT * FROM BaiKiemTras " +
                    "where trangThai = 'enable' and maLop= ?";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maLop)
                    .getResultList();

            for (Object[] result : results) {
                BaiKiemTra baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMaBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau((LocalDateTime) result[8]);
                baiKiemTra.setThoiGianKetThuc((LocalDateTime) result[9]);
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);
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
    public boolean updateBaiKiemTra(BaiKiemTra baiKiemTra) {
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

    public boolean deleteBaiKiemTra(String id) {
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



}
