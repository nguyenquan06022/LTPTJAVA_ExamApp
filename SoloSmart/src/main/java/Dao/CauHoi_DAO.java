package Dao;

import Entity.CauHoi;
import Entity.DeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CauHoi_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "CH" + df.format(now);
    }

    public CauHoi_DAO() {
    }

    public CauHoi_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addCauHoi(CauHoi cauHoi) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO CauHois (maCauHoi, cauHoi,  kieuTraLoi, loiGiai, mucDo, trangThai, maDeThi)\n" +
                    "VALUES (?,?,?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, cauHoi.getMaCauHoi())
                    .setParameter(2, cauHoi.getCauHoi())
                    .setParameter(3, cauHoi.getKieuTraLoi())
                    .setParameter(4, cauHoi.getLoiGiai())
                    .setParameter(5, cauHoi.getMucDo())
                    .setParameter(6, cauHoi.getTrangThai())
                    .setParameter(7, cauHoi.getDeThi().getMaDeThi())
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();

        }
        return isSuccess;
    }

    public CauHoi getCauHoi(String id) {
        CauHoi cauHoi = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maCauHoi,cauHoi,kieuTraLoi,loiGiai,mucDo,trangThai,maDeThi\n" +
                    "from CauHois where maCauHoi = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                cauHoi = new CauHoi();
                cauHoi.setMaCauHoi((String) result[0]);
                cauHoi.setCauHoi((String) result[1]);
                cauHoi.setKieuTraLoi((int) result[3]);
                cauHoi.setLoiGiai((String) result[4]);
                cauHoi.setMucDo((String) result[5]);
                cauHoi.setTrangThai((String) result[6]);
                cauHoi.setDeThi(new DeThi((String) result[7]));
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return cauHoi;
    }

    public ArrayList<CauHoi> getDanhSachCauHoi() {
        ArrayList<CauHoi> danhSachCauHoi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maCauHoi,cauHoi,kieuTraLoi,loiGiai,mucDo,trangThai,maDeThi from CauHois where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                CauHoi cauHoi = new CauHoi();
                cauHoi.setMaCauHoi((String) row[0]);
                cauHoi.setCauHoi((String) row[1]);
                cauHoi.setKieuTraLoi((int) row[3]);
                cauHoi.setLoiGiai((String) row[4]);
                cauHoi.setMucDo((String) row[5]);
                cauHoi.setTrangThai((String) row[6]);
                cauHoi.setDeThi(new DeThi((String) row[7]));
                danhSachCauHoi.add(cauHoi);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachCauHoi;
    }

    public boolean updateCauHoi(CauHoi cauHoi) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE CauHois SET cauHoi = ?,  kieuTraLoi = ?, loiGiai = ?, mucDo = ?, trangThai = ?, maDeThi = ? WHERE maCauHoi = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, cauHoi.getCauHoi())
                    .setParameter(3, cauHoi.getKieuTraLoi())
                    .setParameter(4, cauHoi.getLoiGiai())
                    .setParameter(5, cauHoi.getMucDo())
                    .setParameter(6, cauHoi.getTrangThai())
                    .setParameter(7, cauHoi.getDeThi().getMaDeThi())
                    .setParameter(8, cauHoi.getMaCauHoi())
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

    public boolean deleteCauHoi(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE CauHois SET trangThai = 'disable' WHERE maCauHoi = ?";
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
