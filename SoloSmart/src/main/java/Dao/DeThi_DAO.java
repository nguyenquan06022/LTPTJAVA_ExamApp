package Dao;

import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeThi_DAO extends UnicastRemoteObject implements IDeThi_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException{
        LocalDateTime now = LocalDateTime.now();
        return "DT" + df.format(now);
    }

    public DeThi_DAO() throws RemoteException {
        super();
    }

    public DeThi_DAO(EntityManager em) throws RemoteException{
        this.em = em;
    }

    @Override
    public boolean addDeThi(DeThi deThi) throws RemoteException{
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

    // get de thi theo ma roi dung ma do lay ra danh sách caâu hỏi
    @Override
    public DeThi getDeThi(String id) throws RemoteException{
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

    @Override
    public ArrayList<DeThi> getDanhSachDeThi() throws RemoteException{
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
    @Override
    public ArrayList<DeThi> getDanhSachDeThiTheoMon(String mon) throws RemoteException{
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
    
    @Override
    public ArrayList<DeThi> getDanhSachDeThiTheoMonCuaGV(String maTaiKhoan, String mon) throws RemoteException{
        ArrayList<DeThi> danhSachDeThi = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = """
                         select dt.* 
                         from dethis dt 
                         where monHoc like ? and dt.trangThai='enable' and dt.maTaiKhoan =?
                         group by dt.maDeThi, dt.linkFile, dt.monHoc, dt.soLuongCauHoi,dt.tenDeThi,dt.trangThai, dt.maNganHang, dt.maTaiKhoan
                         """;
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,"%"+mon+"%")
                    .setParameter(2, maTaiKhoan).getResultList();
            
            for (Object[] row : results) {
                DeThi deThi = new DeThi();
                deThi.setMaDeThi((String) row[0]);
                deThi.setLinkFile((String) row[1]);
                deThi.setMonHoc((String) row[2]);
                deThi.setSoLuongCauHoi((int)row[3]);
                deThi.setTenDeThi((String) row[4]);
                deThi.setNganHangDeThi(new NganHangDeThi((String) row[6]));
                deThi.setTaiKhoan(new TaiKhoan((String) row[7]));
                deThi.setTrangThai((String) row[5]);
                danhSachDeThi.add(deThi);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return danhSachDeThi;
    }
    @Override
    public boolean updatDeThi(DeThi deThi) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE DeThis SET linkFile = ?, monHoc = ?, soLuongCauHoi = ?, trangThai = ?, maNganHang = ?, maTaiKhoan = ?, tenDeThi = ? WHERE maDeThi = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1,deThi.getLinkFile())
                    .setParameter(2,deThi.getMonHoc())
                    .setParameter(3,deThi.getSoLuongCauHoi())
                    .setParameter(4,deThi.getTrangThai())
                    .setParameter(5,deThi.getNganHangDeThi().getMaNganHang())
                    .setParameter(6,deThi.getTaiKhoan().getMaTaiKhoan())
                    .setParameter(8,deThi.getMaDeThi())
                    .setParameter(7,deThi.getTenDeThi())
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
    public boolean deleteDeThi(String id) throws RemoteException{
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
    @Override
    public ArrayList<DeThi> getDanhSachDeThiCuaGiaoVien(String maTaiKhoan) throws RemoteException{
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
    @Override
    public ArrayList<DeThi> filterDeThiCuaGiaoVien(String maDeThi, String monHoc, String tenDeThi, String maTaiKhoan) throws RemoteException{
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
