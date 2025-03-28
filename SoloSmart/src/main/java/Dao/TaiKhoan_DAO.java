package Dao;

import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "TK" + df.format(now);
    }

    public TaiKhoan_DAO() {
    }

    public TaiKhoan_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addTaiKhoan(TaiKhoan taiKhoan) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO TaiKhoans (maTaiKhoan,matKhau,tenTaiKhoan,trangThai,vaiTro,dangOnline,gioiTinh,ho,ten) VALUES (?, ?, ?, ?, ?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMaTaiKhoan())
                    .setParameter(2, taiKhoan.getMatKhau())
                    .setParameter(3, taiKhoan.getTenTaiKhoan())
                    .setParameter(4, taiKhoan.getTrangThai())
                    .setParameter(5, taiKhoan.getVaiTro())
                    .setParameter(6, taiKhoan.getDangOnline())
                    .setParameter(7,taiKhoan.getGioiTinh())
                    .setParameter(8, taiKhoan.getHo())
                    .setParameter(9,taiKhoan.getTen())
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }

    public TaiKhoan getTaiKhoan(String id) {
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline,gioiTinh,ho,ten FROM TaiKhoans WHERE maTaiKhoan = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return taiKhoan;
    }

    public ArrayList<TaiKhoan> getDanhSachTaiKhoan() {
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh,ho,ten FROM TaiKhoans where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                danhSachTaiKhoan.add(taiKhoan);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachTaiKhoan;
    }
    public ArrayList<TaiKhoan> getDanhSachTaiKhoanGV() {
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro,dangOnline,gioiTinh,ho,ten FROM TaiKhoans where trangThai = 'enable' and vaitro='GV'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                danhSachTaiKhoan.add(taiKhoan);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachTaiKhoan;
    }
    public ArrayList<TaiKhoan> getDanhSachTaiKhoanSV() {
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro,dangOnline,gioiTinh,ho,ten FROM TaiKhoans where trangThai = 'enable' and vaitro='SV'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                danhSachTaiKhoan.add(taiKhoan);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachTaiKhoan;
    }
    public boolean updateTaiKhoan(TaiKhoan taiKhoan) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE TaiKhoans SET matKhau = ?, tenTaiKhoan = ?, trangThai = ?, vaiTro = ?, dangOnline = ?, gioiTinh = ?, ho = ?, ten = ? WHERE maTaiKhoan = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMatKhau())
                    .setParameter(2, taiKhoan.getTenTaiKhoan())
                    .setParameter(3, taiKhoan.getTrangThai())
                    .setParameter(4, taiKhoan.getVaiTro())
                    .setParameter(5, taiKhoan.getMaTaiKhoan())
                    .setParameter(6, taiKhoan.getDangOnline())
                    .setParameter(7, taiKhoan.getGioiTinh())
                    .setParameter(8, taiKhoan.getHo())
                    .setParameter(9,taiKhoan.getTen())
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

    public boolean deleteTaiKhoan(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "UPDATE TaiKhoans SET trangThai = 'disable' WHERE maTaiKhoan = ?";

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

    public TaiKhoan dangNhap(String userName,String password) {
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh, ho, ten FROM TaiKhoans WHERE tenTaiKhoan = ? AND matKhau = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, userName)
                    .setParameter(2, password)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
        }

        return taiKhoan;
    }

    public boolean updateTrangThaiOnline(TaiKhoan taiKhoan) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE TaiKhoans\n" +
                    "SET dangOnline = CASE \n" +
                    "                    WHEN dangOnline = 'offline' THEN 'online' \n" +
                    "                    ELSE 'offline' \n" +
                    "                 END\n" +
                    "WHERE maTaiKhoan = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMaTaiKhoan())
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
