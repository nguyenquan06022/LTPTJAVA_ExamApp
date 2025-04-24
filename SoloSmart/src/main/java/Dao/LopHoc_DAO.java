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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LopHoc_DAO extends UnicastRemoteObject implements ILopHoc_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String generateMa() throws RemoteException {
        LocalDateTime now = LocalDateTime.now();
        return "LH" + df.format(now);
    }

    public LopHoc_DAO() throws RemoteException{
        super();
    }

    public LopHoc_DAO(EntityManager em) throws RemoteException{
        this.em = em;
    }

    @Override
    public boolean addLopHoc(LopHoc lopHoc) throws RemoteException{
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

    @Override
    public LopHoc getLopHoc(String id) throws RemoteException{
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
                lopHoc.setMaLop((String) result[0]);
                lopHoc.setNamHoc((String) result[1]);
                lopHoc.setSiSo((Integer) result[2]);
                lopHoc.setTenLop((String) result[3]);
                lopHoc.setTrangThai((String) result[4]);
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

    @Override
    public ArrayList<LopHoc> getDanhSachLopHoc() throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maLop,namHoc,siSo,tenLop,trangThai,maMonHoc,maGiaoVien from LopHocs where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();
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

    @Override
    public ArrayList<LopHoc> getDanhSachLopHocByKey(String name) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maLop,namHoc,siSo,tenLop,lh.trangThai,lh.maMonHoc,lh.maGiaoVien from LopHocs lh "

                    + "where lh.trangThai = 'enable' "
                    + "and (malop like ? or tenlop like ? )";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, '%' + name + '%')
                    .setParameter(2, '%' + name + '%')
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

    @Override
    public boolean updateLopHoc(LopHoc lopHoc) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE LopHocs SET namHoc = ?, siSo = ?, tenLop = ?, trangThai = ?, maMonHoc = ?, maGiaoVien = ? WHERE maLop = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, lopHoc.getNamHoc())
                    .setParameter(2, lopHoc.getSiSo())
                    .setParameter(3, lopHoc.getTenLop())
                    .setParameter(4, lopHoc.getTrangThai())
                    .setParameter(5, lopHoc.getMonHoc().getMaMonHoc())
                    .setParameter(7, lopHoc.getMaLop())
                    .setParameter(6, lopHoc.getGiaoVien().getMaTaiKhoan())
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
    public boolean deleteLopHoc(String id) throws RemoteException{
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

    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoNamHocCuaSinhVien(String maTaiKhoan, String namHoc) throws RemoteException{
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

    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoTenLopHocCuaSinhVien(String maTaiKhoan, String tenLopHoc) throws RemoteException{
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
                    .setParameter(1, tenLopHoc) // Set tenLopHoc vào tham số đầu tiên
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
    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoNamHoc(String namHoc) throws RemoteException{
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
    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoTenMonHoc(String tenMonHoc) throws RemoteException{
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
    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoGV(String magv) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select * from LopHocs  \n" +
                    "where magiaovien = ? and trangThai = 'enable' order by tenlop asc";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, magv)
                    .getResultList();

            for (Object[] row : results) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMaLop((String) row[0]);
                lopHoc.setNamHoc((String) row[1]);
                lopHoc.setSiSo((Integer) row[2]);
                lopHoc.setTenLop((String) row[3]);
                lopHoc.setTrangThai((String) row[4]);
                lopHoc.setMonHoc(new MonHoc((String) row[6]));
                lopHoc.setGiaoVien(new TaiKhoan((String) row[5]));
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
    // lọc lớp học theo tên lớp của admin JtextField
    @Override
    public ArrayList<LopHoc> getDanhSachLopHocTheoTenLop(String tenLop) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select * from LopHocs lh\n" +
                    "where lh.tenLop like ? and lh.trangThai = 'enable'";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, "%" + tenLop + "%")
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

    // filter lớp học của giáo viên
    @Override
    public ArrayList<LopHoc> filterLopHocCuaGiaoVien(String maLop, String tenLop, String tenMonHoc, String namHoc,
                                                     String maGiaoVien) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select lh.maLop, lh.namHoc, lh.siSo, lh.tenLop, lh.trangThai,lh.maMonHoc,lh.maGiaoVien from LopHocs lh join MonHocs mh\n"
                    +
                    "on lh.maMonHoc = mh.maMonHoc\n" +
                    "where (lh.maLop LIKE ? or lh.tenLop LIKE ?) and mh.tenMonHoc LIKE ? and lh.namHoc LIKE ? and lh.trangThai = 'enable' and lh.maGiaoVien = ?";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, "%" + maLop + "%")
                    .setParameter(2, "%" + tenLop + "%")
                    .setParameter(3, "%" + tenMonHoc + "%")
                    .setParameter(4, "%" + namHoc + "%")
                    .setParameter(5, maGiaoVien)
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
    
    @Override
    public ArrayList<LopHoc> getDsLopHocCuaSinhVien(String maTaiKhoan) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT lh.maLop,lh.namHoc,lh.siSo,lh.tenLop,lh.trangThai,lh.maMonHoc, lh.maGiaoVien FROM LopHocs lh JOIN KetQuaHocTaps kqht \n" +
                    "ON kqht.maLop = lh.maLop JOIN TaiKhoans tk\n" +
                    "ON tk.maTaiKhoan = kqht.maTaiKhoan\n" +
                    "WHERE lh.trangThai = 'enable' and kqht.maTaiKhoan = ?";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1,maTaiKhoan)
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
    
    @Override
    public List<String> getDSNamHocGV(String maGV) throws RemoteException{
        List<String> list= new ArrayList<>();
         EntityTransaction tr = em.getTransaction();
         try {
            tr.begin();
            String sql = """
                         SELECT namHoc
                         FROM (
                             SELECT DISTINCT namHoc
                             FROM LopHocs
                             WHERE maGiaoVien = ?
                             AND trangThai = 'enable'
                         ) AS temp
                         ORDER BY CAST(LEFT(namHoc, 4) AS INT) DESC;
                         """;
            list = em.createNativeQuery(sql)
                    .setParameter(1,maGV)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
         return list;
    }
    // lọc lớp học theo tiêu chi của sinh viên
    @Override
    public ArrayList<LopHoc> filterLopHocCuaSinhVien(String maLop, String tenLop, String tenMonHoc, String namHoc,
                                                     String maSinhVien) throws RemoteException{
        ArrayList<LopHoc> danhSachLopHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select lh.maLop, lh.namHoc, lh.siSo, lh.tenLop, lh.trangThai,lh.maMonHoc,lh.maGiaoVien from LopHocs lh join MonHocs mh\n" +
                            "on lh.maMonHoc = mh.maMonHoc join KetQuaHocTaps kqht\n" +
                            "on kqht.maLop = lh.maLop join TaiKhoans tk\n" +
                            "on tk.maTaiKhoan = kqht.maTaiKhoan\n" +
                            "where (lh.maLop LIKE ? or lh.tenLop LIKE ?) and mh.tenMonHoc LIKE ? and lh.namHoc LIKE ? and lh.trangThai = 'enable' and tk.maTaiKhoan = ?";

            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, "%" + maLop + "%")
                    .setParameter(2, "%" + tenLop + "%")
                    .setParameter(3, "%" + tenMonHoc + "%")
                    .setParameter(4, "%" + namHoc + "%")
                    .setParameter(5, maSinhVien)
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
    
    
    @Override
    public List<String> getDSNamHocSV(String maSV) throws RemoteException{
        List<String> list= new ArrayList<>();
         EntityTransaction tr = em.getTransaction();
         try {
            tr.begin();
            String sql = "SELECT DISTINCT namHoc FROM LopHocs lh join KetQuaHocTaps kqht\n" +
            "ON lh.maLop = kqht.maLop join TaiKhoans tk\n" +
            "ON tk.maTaiKhoan = kqht.maTaiKhoan\n" +
            "where tk.maTaiKhoan = ? and lh.trangThai = 'enable'";
            list = em.createNativeQuery(sql)
                    .setParameter(1,maSV)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
         return list;
    }
}
