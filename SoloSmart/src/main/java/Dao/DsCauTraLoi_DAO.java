package Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class DsCauTraLoi_DAO {
    private EntityManager em;
    public DsCauTraLoi_DAO(EntityManager em) {
        this.em = em;
    }

    public DsCauTraLoi_DAO() {
    }

    public boolean themCauTraLoi(String maketquakiemtra, String cauTraLoi){
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO dsCauTraLoi values (?, ?)";
            em.createNativeQuery(sql)
                    .setParameter(1, maketquakiemtra)
                    .setParameter(2, cauTraLoi)
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }

    public boolean updateCauTraLoi(String maketquakiemtra, String cauTraLoi, String cauTraLoiMoi){
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "update dsCauTraLoi set cautraloi = ? where cauTraLoi = ? and maketquakiemtra = ?";
            em.createNativeQuery(sql)
                    .setParameter(1, cauTraLoiMoi)
                    .setParameter(2, cauTraLoi)
                    .setParameter(3, maketquakiemtra)
                    .executeUpdate();

            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }

    public ArrayList<String> getDSCauTraLoi(String maKetQuaKiemTra) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "SELECT cauTraLoi FROM dsCauTraLoi WHERE maketquakiemtra = ?";
            List<String> results = em.createNativeQuery(sql)
                    .setParameter(1, maKetQuaKiemTra) // Sử dụng tham số vị trí
                    .getResultList();
            dsLuaChon.addAll(results);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return dsLuaChon;
    }

    // lấy ra danh sách cau trả lời cua sinh vien theo mataikhoan va mabaikiemtra
    public List<String> getDsCauTraLoiCuaSinhVien(String maTaiKhoan,String maBaiKiemTra) {
        EntityTransaction tr = em.getTransaction();
        List<String> dsLuaChon = new ArrayList<>();
        try {
            tr.begin();
            String sql = "select cauTraLoi from dsCauTraLoi dsctl join KetQuaKiemTras kqkt\n" +
                    "on dsctl.maKetQuaKiemTra = kqkt.maKetQuaKiemTra join TaiKhoans tk\n" +
                    "on tk.maTaiKhoan = kqkt.maTaiKhoan\n" +
                    "where maBaiKiemTra = ? and tk.maTaiKhoan = ?";
            List<String> results = em.createNativeQuery(sql)
                    .setParameter(1, maBaiKiemTra)
                    .setParameter(2,maTaiKhoan)// Sử dụng tham số vị trí
                    .getResultList();
            dsLuaChon.addAll(results);
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return dsLuaChon;
    }
}
