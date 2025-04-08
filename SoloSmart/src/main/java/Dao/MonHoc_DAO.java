package Dao;

import Entity.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MonHoc_DAO {
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "MH" + df.format(now);
    }

    public MonHoc_DAO() {
    }

    public MonHoc_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean addMonHoc(MonHoc monHoc) {
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO MonHocs (maMonHoc,tenMonHoc,trangThai) VALUES (?, ?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, monHoc.getMaMonHoc())
                    .setParameter(2, monHoc.getTenMonHoc())
                    .setParameter(3, monHoc.getTrangThai())
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }

    public MonHoc getMonHoc(String id) {
        MonHoc monHoc = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maMonHoc,tenMonHoc,trangThai from MonHocs WHERE maMonHoc like ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, "%"+id+"%")
                    .getSingleResult();
            if (result != null) {
                monHoc = new MonHoc();
                monHoc.setMaMonHoc((String) result[0]);
                monHoc.setTenMonHoc((String) result[1]);
                monHoc.setTrangThai((String) result[2]);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return monHoc;
    }

    public ArrayList<MonHoc> getDanhSachMonHoc() {
        ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maMonHoc,tenMonHoc,trangThai from MonHocs where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();
            for (Object[] row : results) {
                MonHoc monHoc = new MonHoc();
                monHoc.setMaMonHoc((String) row[0]);
                monHoc.setTenMonHoc((String) row[1]);
                monHoc.setTrangThai((String) row[2]);
                danhSachMonHoc.add(monHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachMonHoc;
    }
    public ArrayList<MonHoc> getDanhSachMonHocTheoTen(String ten) {
        ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maMonHoc,tenMonHoc,trangThai from MonHocs where (tenMonHoc like ? or mamonhoc like ?) and trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, "%"+ten+"%")
                    .setParameter(2, "%"+ten+"%")
                    .getResultList();
            for (Object[] row : results) {
                MonHoc monHoc = new MonHoc();
                monHoc.setMaMonHoc((String) row[0]);
                monHoc.setTenMonHoc((String) row[1]);
                monHoc.setTrangThai((String) row[2]);
                danhSachMonHoc.add(monHoc);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachMonHoc;
    }
    
    public String getTenMonHocTheoBaiKiemTra(String id) {
    EntityTransaction tr = em.getTransaction();
    try {
        tr.begin();
        String sql = "SELECT mh.tenMonHoc " +
                     "FROM BaiKiemTras bkt " +
                     "INNER JOIN LopHocs lh ON lh.maLop = bkt.maLop " +
                     "INNER JOIN MonHocs mh ON mh.maMonHoc = lh.maMonHoc " +
                     "WHERE bkt.maBaiKiemTra = :id";
                     
        Object result = em.createNativeQuery(sql)
                          .setParameter("id", id) // Gán giá trị tham số
                          .getSingleResult(); // Lấy kết quả duy nhất

        tr.commit();

        return result != null ? result.toString() : null; // Trả về kết quả dạng String

    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException(e);
    }
}


    public boolean updateMonHoc(MonHoc monHoc) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE MonHocs " +
                    "SET tenMonHoc = ?, trangThai = ? " +
                    "WHERE maMonHoc = ?";

            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1,monHoc.getTenMonHoc())
                    .setParameter(2,monHoc.getTrangThai())
                    .setParameter(3, monHoc.getMaMonHoc())
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

    public boolean deleteMonHoc(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE MonHocs SET trangThai = 'disable' WHERE maMonHoc = ?";
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
