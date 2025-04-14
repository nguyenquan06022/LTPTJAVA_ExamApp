package Dao;

import Entity.KetQuaHocTap;
import Entity.LopHoc;
import Entity.MonHoc;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LopHoc_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "LH" + df.format(now);
    }

    public LopHoc_DAO() {
    }

    public LopHoc_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addLopHoc(LopHoc lopHoc) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO LopHocs (maLop,namHoc,siSo,tenLop,trangThai,maMonHoc,maGiaoVien) VALUES (?, ?, ?, ?, ?, ?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, lopHoc.getMaLop())
                    .setParameter(2, lopHoc.getNamHoc())
                    .setParameter(3, lopHoc.getSiSo())
                    .setParameter(4, lopHoc.getTenLop())
                    .setParameter(5, lopHoc.getTrangThai())
                    .setParameter(6, lopHoc.getMonHoc().getMaMonHoc())
                    .setParameter(7, lopHoc.getGiaoVien().getMaTaiKhoan())
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }

    public LopHoc getLopHoc(String id) {
        LopHoc lopHoc = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maLop,namHoc,siSo,tenLop,trangThai,maMonHoc,maGiaoVien from LopHocs where maLop = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                lopHoc = new LopHoc();
                lopHoc.setMaLop((String)result[0]);
                lopHoc.setNamHoc((String)result[1]);
                lopHoc.setSiSo((Integer)result[2]);
                lopHoc.setTenLop((String)result[3]);
                lopHoc.setTrangThai((String)result[4]);
                lopHoc.setMonHoc(new MonHoc((String) result[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) result[6]));
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return lopHoc;
    }

    public ArrayList<LopHoc> getDanhSachLopHoc() {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maLop,namHoc,siSo,tenLop,trangThai,maMonHoc,maGiaoVien from LopHocs where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();
            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String)row[0]);
                lopHoc.setNamHoc((String)row[1]);
                lopHoc.setSiSo((Integer)row[2]);
                lopHoc.setTenLop((String)row[3]);
                lopHoc.setTrangThai((String)row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachLopHoc;
    }

    public boolean updateLopHoc(LopHoc lopHoc) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE LopHocs SET namHoc = ?, siSo = ?, tenLop = ?, trangThai = ?, maMonHoc = ?, maGiaoVien = ? WHERE maLop = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1,lopHoc.getNamHoc())
                    .setParameter(2,lopHoc.getSiSo())
                    .setParameter(3,lopHoc.getTenLop())
                    .setParameter(4,lopHoc.getTrangThai())
                    .setParameter(5,lopHoc.getMonHoc().getMaMonHoc())
                    .setParameter(7,lopHoc.getMaLop())
                    .setParameter(6,lopHoc.getGiaoVien().getMaTaiKhoan())
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

    public boolean deleteLopHoc(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE LopHocs SET trangThai = 'disable' WHERE maLop = ?";
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

    public ArrayList<LopHoc> getDanhSachLopHocTheoNamHocCuaSinhVien(String maTaiKhoan, String namHoc) {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT lh.maLop, lh.namHoc, lh.siSo, lh.tenLop, lh.trangThai, lh.maMonHoc, lh.maGiaoVien " +
                    "FROM LopHocs lh " +
                    "JOIN KetQuaHocTaps kqht ON lh.maLop = kqht.maLop " +
                    "JOIN TaiKhoans tk ON tk.maTaiKhoan = kqht.maTaiKhoan " +
                    "WHERE lh.namHoc = ? " +
                    "AND tk.maTaiKhoan = ? " +
                    "AND lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, namHoc)
                    .setParameter(2, maTaiKhoan)
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachLopHoc;
    }

    public ArrayList<LopHoc> getDanhSachLopHocTheoTenLopHocCuaSinhVien(String maTaiKhoan, String tenLopHoc) {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            // Câu lệnh SQL với COLLATE Latin1_General_CI_AI để không phân biệt dấu
            String sql = "SELECT lh.maLop, lh.namHoc, lh.siSo, lh.tenLop, lh.trangThai, lh.maGiaoVien, lh.maMonHoc " +
                    "FROM LopHocs lh " +
                    "JOIN KetQuaHocTaps kqht ON lh.maLop = kqht.maLop " +
                    "JOIN TaiKhoans tk ON tk.maTaiKhoan = kqht.maTaiKhoan " +
                    "WHERE lh.tenLop COLLATE Latin1_General_CI_AI LIKE '%' + ? + '%' " +
                    "AND tk.maTaiKhoan = ? " +
                    "AND lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, tenLopHoc)  // Set tenLopHoc vào tham số đầu tiên
                    .setParameter(2, maTaiKhoan) // Set maTaiKhoan vào tham số thứ hai
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachLopHoc;
    }

    // get danh sach lop hoc theo nam hoc cua admin Jcombobox
    public ArrayList<LopHoc> getDanhSachLopHocTheoNamHoc(String namHoc) {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select * from LopHocs lh\n" +
                    "where lh.namHoc = ? and lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, namHoc)
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachLopHoc;
    }

    // get danh sach lop hoc theo ten monHoc cua admin Jcombobox
    public ArrayList<LopHoc> getDanhSachLopHocTheoTenMonHoc(String tenMonHoc) {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select * from LopHocs lh \n" +
                    "join MonHocs mh\n" +
                    "on lh.maMonHoc = mh.maMonHoc\n" +
                    "where mh.tenMonHoc = ? and lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, tenMonHoc)
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachLopHoc;
    }

    //lọc lớp học theo tên lớp của admin JtextField
    public ArrayList<LopHoc> getDanhSachLopHocTheoTenLop(String tenLop) {
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select * from LopHocs lh\n" +
                    "where lh.tenLop like ? and lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, "%"+tenLop+"%")
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[5]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[6]));
                danhSachLopHoc.add(lopHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachLopHoc;
    }
}
