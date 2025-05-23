package Dao;

import Entity.DeThi;
import Entity.LuaChons;
import Entity.NganHangDeThi;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DsLuaChon_DAO extends UnicastRemoteObject implements IDsLuaChon_DAO {
    private EntityManager em;

    public DsLuaChon_DAO() throws RemoteException {
        super();
    }

    public DsLuaChon_DAO(EntityManager em) throws RemoteException {
        this.em = em;
    }

    @Override
    public boolean themLuaChon(String maCauHoi, String luaChon, boolean dapAnDung) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO dsLuaChon(macauhoi, dapandung, luachon) values (?, ?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, dapAnDung)
                    .setParameter(3, luaChon)
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
    public boolean capNhatLuaChon(String maCauHoi, String luaChonCu, String luaChonMoi, boolean dapAnDung) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "UPDATE dsLuaChon SET luaChon = ?, dapAnDung = ? WHERE maCauHoi = ? and luaChon = ?";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, luaChonMoi)
                    .setParameter(2, dapAnDung)
                    .setParameter(3, maCauHoi)
                    .setParameter(4,luaChonCu)
                    .executeUpdate();
            tr.commit();
            isSuccess = result > 0;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }
    @Override
    public String getLuaChon(String maCauHoi, String luaChon) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        String ketQua = null;
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = ? AND luaChon = ?";
            ketQua = (String) em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, luaChon)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy lựa chọn", e);
        }
        return ketQua;
    }
    @Override
    public ArrayList<String> getDSLuaChon(String maCauHoi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        ArrayList<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT luaChon FROM dsLuaChon WHERE maCauHoi = ?";
            // Sử dụng NativeQuery trả về danh sách các đối tượng
            List<String> rs = em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .getResultList();
            dsLuaChon = new ArrayList<>(rs);


            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách lựa chọn", e);
        }
        return dsLuaChon;
    }

    @Override
    public boolean xoaLuaChon(String maCauHoi, String luaChon) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "DELETE FROM dsLuaChon WHERE maCauHoi = ? AND luaChon = ?";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, maCauHoi)
                    .setParameter(2, luaChon)
                    .executeUpdate();
            tr.commit();
            isSuccess = result > 0; // Kiểm tra xem có bản ghi nào bị xóa không
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa lựa chọn", e);
        }
        return isSuccess;
    }

    @Override
    public ArrayList<LuaChons> getDSLuaChonTheoDeThi(String maDeThi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        ArrayList<LuaChons> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT dslc.maCauHoi, dslc.dapAnDung, dslc.luaChon FROM CauHois ch join dsLuaChon dslc\n" +
                    "ON ch.maCauHoi = dslc.maCauHoi JOIN DeThis dt\n" +
                    "ON dt.maDeThi = ch.maDeThi\n" +
                    "WHERE dt.maDeThi = ? AND ch.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maDeThi)
                    .getResultList();
            for (Object[] row : results) {
                LuaChons luaChons = new LuaChons();
                luaChons.setLuaChon((String) row[2]);
                luaChons.setDapAnDung((boolean) row[1]);
                dsLuaChon.add(luaChons);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách lựa chọn", e);
        }
        return dsLuaChon;
    }
    @Override
    public ArrayList<LuaChons> getDSLuaChonTheoMa(String maCauHoi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        ArrayList<LuaChons> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT dslc.maCauHoi, dslc.dapAnDung, dslc.luaChon FROM CauHois ch join dsLuaChon dslc\n" +
                    "ON ch.maCauHoi = dslc.maCauHoi \n" +
                    "WHERE dslc.macauhoi = ? AND ch.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maCauHoi)
                    .getResultList();
            for (Object[] row : results) {
                LuaChons luaChons = new LuaChons();
                luaChons.setLuaChon((String) row[2]);
                luaChons.setDapAnDung((boolean) row[1]);
                dsLuaChon.add(luaChons);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách lựa chọn", e);
        }
        return dsLuaChon;
    }
}
