package Dao;

import Entity.DeThi;
import Entity.NganHangDeThi;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class DeThi_DAO {
    private EntityManager em;
    public DeThi_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addDeThi(DeThi deThi) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO DeThis (maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan)\n" +
                    "VALUES (?,?,?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, deThi.getMaDeThi())
                    .setParameter(2, deThi.getLinkFile())
                    .setParameter(3, deThi.getMonHoc())
                    .setParameter(4, deThi.getSoLuongCauHoi())
                    .setParameter(5, deThi.getTrangThai())
                    .setParameter(6, deThi.getNganHangDeThi().getMaNganHang())
                    .setParameter(7, deThi.getTaiKhoan().getMaTaiKhoan())
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
            String sql = "select maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan from DeThis where maDeThi = ?";
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
            String sql = "select maDeThi,linkFile,monHoc,soLuongCauHoi,trangThai,maNganHang,maTaiKhoan from DeThis where trangThai = 'enable'";
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
            String sql = "UPDATE DeThis SET linkFile = ?, monHoc = ?, soLuongCauHoi = ?, trangThai = ?, maNganHang = ?, maTaiKhoan = ? WHERE maDeThi = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1,deThi.getLinkFile())
                    .setParameter(2, deThi.getMonHoc())
                    .setParameter(3,deThi.getSoLuongCauHoi())
                    .setParameter(4,deThi.getTrangThai())
                    .setParameter(5,deThi.getNganHangDeThi().getMaNganHang())
                    .setParameter(6,deThi.getTaiKhoan().getMaTaiKhoan())
                    .setParameter(7,deThi.getMaDeThi())
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
}
