package Dao;

import Entity.CauHoi;
import Entity.DeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CauHoi_DAO extends UnicastRemoteObject implements ICauHoi_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException{
        LocalDateTime now = LocalDateTime.now();
        return "CH" + df.format(now);
    }

    public CauHoi_DAO() throws RemoteException {
        super();
    }

    public CauHoi_DAO(EntityManager em) throws RemoteException {
        this.em = em;
    }

    @Override
    public boolean addCauHoi(CauHoi cauHoi) throws RemoteException{
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

    @Override
    public CauHoi getCauHoi(String id) throws RemoteException{
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

    @Override
    public ArrayList<CauHoi> getDanhSachCauHoi() throws RemoteException{
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

    @Override
    public boolean updateCauHoi(CauHoi cauHoi) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE CauHois SET cauHoi = ?,  kieuTraLoi = ?, loiGiai = ?, mucDo = ?, trangThai = ?, maDeThi = ? WHERE maCauHoi = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, cauHoi.getCauHoi())
                    .setParameter(2, cauHoi.getKieuTraLoi())
                    .setParameter(3, cauHoi.getLoiGiai())
                    .setParameter(4, cauHoi.getMucDo())
                    .setParameter(5, cauHoi.getTrangThai())
                    .setParameter(6, cauHoi.getDeThi().getMaDeThi())
                    .setParameter(7, cauHoi.getMaCauHoi())
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

    @Override
    public boolean deleteCauHoi(String id) throws RemoteException{
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

    // getdscauhoi theo madethi
    @Override
    public ArrayList<CauHoi> getDsCauHoiTheoDeThi(String maDeThi) throws RemoteException{
        ArrayList<CauHoi> danhSachCauHoi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maCauHoi,cauHoi,kieuTraLoi,loiGiai,mucDo,trangThai,maDeThi from CauHois where trangThai = 'enable' and maDeThi = ?";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maDeThi)
                    .getResultList();

            for (Object[] row : results) {
                CauHoi cauHoi = new CauHoi();
                cauHoi.setMaCauHoi((String) row[0]);
                cauHoi.setCauHoi((String) row[1]);
                cauHoi.setKieuTraLoi((int) row[2]);
                cauHoi.setLoiGiai((String) row[3]);
                cauHoi.setMucDo((String) row[4]);
                cauHoi.setTrangThai((String) row[5]);
                cauHoi.setDeThi(new DeThi((String) row[6]));
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
}
