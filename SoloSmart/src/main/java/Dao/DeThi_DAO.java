package Dao;

import Entity.DeThi;
import Entity.NganHangDeThi;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DeThi_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "DT" + df.format(now);
    }

    public DeThi_DAO() {
    }

    public DeThi_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addDeThi(DeThi deThi) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO DeThis (maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi)\n" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, deThi.getMaDeThi())
                    .setParameter(2, deThi.getLinkFile())
                    .setParameter(3, deThi.getMonHoc())
                    .setParameter(4, deThi.getSoLuongCauHoi())
                    .setParameter(5, deThi.getTrangThai())
                    .setParameter(6, deThi.getNganHangDeThi().getMaNganHang())
                    .setParameter(7, deThi.getTaiKhoan().getMaTaiKhoan())
                    .setParameter(8, deThi.getTenDeThi())
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }

    public DeThi getDeThi(String id) {
        DeThi deThi = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi from DeThis where maDeThi = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                deThi = new DeThi();
                deThi.setMaDeThi((String) result[0]);
                deThi.setLinkFile((String) result[1]);
                deThi.setMonHoc((String) result[2]);
                deThi.setSoLuongCauHoi((int) result[3]);
                deThi.setTrangThai((String) result[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) result[5]));
                deThi.setTaiKhoan(new TaiKhoan((String) result[6]));
                deThi.setTenDeThi((String) result[7]);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return deThi;
    }

    public ArrayList<DeThi> getDanhSachDeThi() {
        ArrayList<DeThi> danhSachDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi from DeThis where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();
            for (Object[] row : results) {
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) row[0]);
                deThi.setLinkFile((String) row[1]);
                deThi.setMonHoc((String) row[2]);
                deThi.setSoLuongCauHoi((int) row[3]);
                deThi.setTrangThai((String) row[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) row[5]));
                deThi.setTaiKhoan(new TaiKhoan((String) row[6]));
                deThi.setTenDeThi((String) row[7]);
                danhSachDeThi.add(deThi);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachDeThi;
    }
    public ArrayList<DeThi> getDanhSachDeThiTheoMon(String mon) {
        ArrayList<DeThi> danhSachDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi from DeThis where trangThai = 'enable' and monhoc like ?\n" +
"order by monHoc asc";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,mon).getResultList();
            for (Object[] row : results) {
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) row[0]);
                deThi.setLinkFile((String) row[1]);
                deThi.setMonHoc((String) row[2]);
                deThi.setSoLuongCauHoi((int) row[3]);
                deThi.setTrangThai((String) row[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) row[5]));
                deThi.setTaiKhoan(new TaiKhoan((String) row[6]));
                deThi.setTenDeThi((String) row[7]);
                danhSachDeThi.add(deThi);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachDeThi;
    }

    public boolean updatDeThi(DeThi deThi) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE DeThis SET linkFile = ?, monHoc = ?, soLuongCauHoi = ?, trangThai = ?, maNganHang = ?, maTaiKhoan = ?, tenDeThi = ? WHERE maDeThi = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1,deThi.getLinkFile())
                    .setParameter(2, deThi.getMonHoc())
                    .setParameter(3,deThi.getSoLuongCauHoi())
                    .setParameter(4,deThi.getTrangThai())
                    .setParameter(5,deThi.getNganHangDeThi().getMaNganHang())
                    .setParameter(6,deThi.getTaiKhoan().getMaTaiKhoan())
                    .setParameter(7,deThi.getMaDeThi())
                    .setParameter(8,deThi.getTenDeThi())
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

    public boolean deleteDeThi(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE DeThis SET trangThai = 'disable' WHERE maDeThi = ?";
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

    // lấy ra danh sách đề thi của giáo viên đó tạo
    public ArrayList<DeThi> getDanhSachDeThiCuaGiaoVien(String maTaiKhoan) {
        ArrayList<DeThi> danhSachDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maDeThi, linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi FROM DeThis\n" +
                    "WHERE maTaiKhoan = ? AND trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maTaiKhoan).getResultList();
            for (Object[] row : results) {
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) row[0]);
                deThi.setLinkFile((String) row[1]);
                deThi.setMonHoc((String) row[2]);
                deThi.setSoLuongCauHoi((int) row[3]);
                deThi.setTrangThai((String) row[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) row[5]));
                deThi.setTaiKhoan(new TaiKhoan((String) row[6]));
                deThi.setTenDeThi((String) row[7]);
                danhSachDeThi.add(deThi);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachDeThi;
    }

    // filter dethi của giáo viên
    public ArrayList<DeThi> filterDeThiCuaGiaoVien(String maDeThi,String monHoc,String tenDeThi,String maTaiKhoan) {
        ArrayList<DeThi> danhSachDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maDeThi, linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan,tenDeThi from DeThis\n" +
                    "where maDeThi LIKE ? and monHoc LIKE ? and tenDeThi LIKE ? and trangThai = 'enable' and maTaiKhoan = ?";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,'%'+maDeThi+'%')
                    .setParameter(2,'%'+monHoc+'%')
                    .setParameter(3,'%'+tenDeThi+'%')
                    .setParameter(4,maTaiKhoan)
                    .getResultList();
            for (Object[] row : results) {
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) row[0]);
                deThi.setLinkFile((String) row[1]);
                deThi.setMonHoc((String) row[2]);
                deThi.setSoLuongCauHoi((int) row[3]);
                deThi.setTrangThai((String) row[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) row[5]));
                deThi.setTaiKhoan(new TaiKhoan((String) row[6]));
                deThi.setTenDeThi((String) row[7]);
                danhSachDeThi.add(deThi);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachDeThi;
    }
}
