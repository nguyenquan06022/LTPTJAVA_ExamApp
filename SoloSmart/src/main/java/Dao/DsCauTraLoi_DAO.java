package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DsCauTraLoi_DAO extends UnicastRemoteObject implements IDsCauTraLoi_DAO {
    private EntityManager em;

    public DsCauTraLoi_DAO(EntityManager em) throws RemoteException {
        this.em = em;
    }

    public DsCauTraLoi_DAO() throws RemoteException {
        super();
    }

    @Override
    public boolean themCauTraLoiCuaSinhVien(String maketquakiemtra, String cauTraLoi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;

        try {
            tr.begin();
            String sql = "INSERT INTO  dsCauTraLoi (maKetQuaKiemTra, cauTraLoi) values (?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, maketquakiemtra)
                    .setParameter(2, cauTraLoi)
                    .executeUpdate();

            tr.commit();
            em.flush();
            isSuccess = true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
                e.printStackTrace();
            }
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }


    @Override
    public boolean themCauTraLoi(String maKetQuaKiemTra, String cauTraLoi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;

        try {
            if (!tr.isActive()) {
                tr.begin();
            }

            String sql = "INSERT INTO dsCauTraLoi(maKetQuaKiemTra, cauTraLoi) VALUES (?, ?)";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, maKetQuaKiemTra)
                    .setParameter(2, cauTraLoi)
                    .executeUpdate();

            if (result > 0) {
                if (tr.isActive()) {
                    tr.commit();
                }
                if (em.isOpen()) {
                    em.flush(); // optional: thường không cần nếu đã commit
                }
                isSuccess = true;
            } else {
                if (tr.isActive()) {
                    tr.rollback();
                }
            }
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
        }

        return isSuccess;
    }

    @Override
    public boolean updateCauTraLoi(String maketquakiemtra, String cauTraLoi, String cauTraLoiMoi) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "UPDATE dsCauTraLoi SET cauTraLoi = ? WHERE cauTraLoi = ? AND maKetQuaKiemTra = ?";
            int result = em.createNativeQuery(sql)
                    .setParameter(1, cauTraLoiMoi)
                    .setParameter(2, cauTraLoi)
                    .setParameter(3, maketquakiemtra)
                    .executeUpdate();

            if (result > 0) {
                tr.commit();
                isSuccess = true;
            } else {
                if (tr.isActive()) tr.rollback();
            }
        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            e.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public ArrayList<String> getDSCauTraLoi(String maKetQuaKiemTra) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        ArrayList<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT cauTraLoi FROM dsCauTraLoi WHERE maKetQuaKiemTra = ?";
            List<String> results = em.createNativeQuery(sql)
                    .setParameter(1, maKetQuaKiemTra)
                    .getResultList();
            dsLuaChon.addAll(results);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            throw new RuntimeException(e);
        }
        return dsLuaChon;
    }

    @Override
    public List<String> getDsCauTraLoiCuaSinhVien(String maTaiKhoan, String maBaiKiemTra) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        List<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = """
                SELECT tl.cauTraLoi, 
                       TRY_CAST(SUBSTRING(tl.cauTraLoi, 1, CHARINDEX('.', tl.cauTraLoi) - 1) AS INT) AS soThuTu
                FROM dsCauTraLoi tl 
                INNER JOIN KetQuaKiemTras kq ON tl.maKetQuaKiemTra = kq.maKetQuaKiemTra
                WHERE kq.maTaiKhoan = ? AND maBaiKiemTra = ?
                ORDER BY soThuTu
            """;
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, maTaiKhoan)
                    .setParameter(2, maBaiKiemTra)
                    .getResultList();

            for (Object[] row : results) {
                dsLuaChon.add((String) row[0]); // Lấy giá trị cauTraLoi
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) tr.rollback();
            throw new RuntimeException(e);
        }
        return dsLuaChon;
    }
}
