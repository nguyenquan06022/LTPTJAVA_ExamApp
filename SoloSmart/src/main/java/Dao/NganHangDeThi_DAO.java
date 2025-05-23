package Dao;

import Entity.MonHoc;
import Entity.NganHangDeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NganHangDeThi_DAO extends UnicastRemoteObject implements INganHangDeThi_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException {
        LocalDateTime now = LocalDateTime.now();
        return "NH" + df.format(now);
    }

    public NganHangDeThi_DAO() throws RemoteException{
        super();
    }

    public NganHangDeThi_DAO(EntityManager em) throws RemoteException {
        this.em = em;
    }

    @Override
    public boolean addNganHangDeThi(NganHangDeThi nganHangDeThi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO NganHangDeThis (maNganHang, loaiNganHang, tenNganHang, maMonHoc) VALUES (?, ?, ?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, nganHangDeThi.getMaNganHang())
                    .setParameter(2, nganHangDeThi.isLoaiNganHang())
                    .setParameter(3, nganHangDeThi.getTenNganHang())
                    .setParameter(4, nganHangDeThi.getMonHoc().getMaMonHoc())
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }

    @Override
    public NganHangDeThi getNganHangDeThi(String id) throws RemoteException {
        NganHangDeThi nganHangDeThi = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maNganHang, loaiNganHang, tenNganHang, maMonHoc FROM NganHangDeThis WHERE maNganHang = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                nganHangDeThi = new NganHangDeThi();
                nganHangDeThi.setMaNganHang((String)result[0]);
                nganHangDeThi.setLoaiNganHang((Boolean)result[1]);
                nganHangDeThi.setTenNganHang((String)result[2]);
                nganHangDeThi.setMonHoc(new MonHoc((String)result[3]));
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return nganHangDeThi;
    }

    @Override
    public ArrayList<NganHangDeThi> getDanhSachNganHangDeThi() throws RemoteException {
        ArrayList<NganHangDeThi> danhSachNganHangDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maNganHang, loaiNganHang, tenNganHang, maMonHoc FROM NganHangDeThis";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                NganHangDeThi nganHangDeThi = new NganHangDeThi();
                nganHangDeThi.setMaNganHang((String)row[0]);
                nganHangDeThi.setLoaiNganHang((Boolean)row[1]);
                nganHangDeThi.setTenNganHang((String)row[2]);
                nganHangDeThi.setMonHoc(new MonHoc((String)row[3]));
                danhSachNganHangDeThi.add(nganHangDeThi);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachNganHangDeThi;
    }

    @Override
    public boolean updateNganHangDeThi(NganHangDeThi nganHangDeThi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE NganHangDeThis SET loaiNganHang = ?, tenNganHang = ?, maMonHoc = ? WHERE maNganHang = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, nganHangDeThi.isLoaiNganHang())
                    .setParameter(2, nganHangDeThi.getTenNganHang())
                    .setParameter(3, nganHangDeThi.getMonHoc().getMaMonHoc())
                    .setParameter(4, nganHangDeThi.getMaNganHang())
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
