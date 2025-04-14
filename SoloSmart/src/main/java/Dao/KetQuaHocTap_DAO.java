package Dao;

import Entity.KetQuaHocTap;
import Entity.LopHoc;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KetQuaHocTap_DAO {
    private EntityManager em;

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    public String generateMa() {
        LocalDateTime now = LocalDateTime.now();
        return "KQHT" + df.format(now);
    }

    public KetQuaHocTap_DAO() {
    }

    public KetQuaHocTap_DAO(EntityManager em) {
        this.em = em;
    }

    // Thêm kết quả học tập
    public boolean themKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
    EntityTransaction tr = em.getTransaction();
    try {
        tr.begin();
        // Dùng native SQL để thêm kết quả học tập vào cơ sở dữ liệu
        String sql = "INSERT INTO KetQuaHocTaps (diemCuoiKy, diemGiuaKy, diemTBMon, diemThuongKy, maTaiKhoan, maLop) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        em.createNativeQuery(sql)
          .setParameter(1, ketQuaHocTap.getDiemCuoiKy())
          .setParameter(2, ketQuaHocTap.getDiemGiuaKy())
          .setParameter(3, ketQuaHocTap.getDiemTBMon())
          .setParameter(4, ketQuaHocTap.getDiemThuongKy())
          .setParameter(5, ketQuaHocTap.getTaiKhoan().getMaTaiKhoan())
          .setParameter(6, ketQuaHocTap.getLopHoc().getMaLop())
          .executeUpdate();
        tr.commit();
        return true;
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException("Lỗi khi thêm kết quả học tập", e);
    }
}
    
    public boolean xoaKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
    EntityTransaction tr = em.getTransaction();
    try {
        tr.begin();
        // Dùng native SQL để thêm kết quả học tập vào cơ sở dữ liệu
        String sql = "delete KetQuaHocTaps " +
                     "where maLop= ? and maTaiKhoan=?";
        em.createNativeQuery(sql)
          .setParameter(1, ketQuaHocTap.getLopHoc().getMaLop())
          .setParameter(2, ketQuaHocTap.getTaiKhoan().getMaTaiKhoan())
          .executeUpdate();
        tr.commit();
        return true;
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException("Lỗi khi thêm kết quả học tập", e);
    }
}

    // Lấy kết quả học tập dựa trên mã tài khoản và mã lớp
    public KetQuaHocTap getKetQuaHocTap(String maTaiKhoan, String maLop) {
    EntityTransaction tr = em.getTransaction();
    KetQuaHocTap ketQuaHocTap = null;
    try {
        tr.begin();
        // Dùng native SQL để lấy kết quả học tập
        String sql = "SELECT * FROM KetQuaHocTaps WHERE maTaiKhoan = ? AND maLop = ?";
        List<Object[]> results = em.createNativeQuery(sql)
                                   .setParameter(1, maTaiKhoan)
                                   .setParameter(2, maLop)
                                   .getResultList();

        if (!results.isEmpty()) {
            Object[] result = results.get(0);  // Chỉ lấy kết quả đầu tiên (nếu có)
            ketQuaHocTap = new KetQuaHocTap();
            ketQuaHocTap.setDiemCuoiKy((Float) result[0]);
            ketQuaHocTap.setDiemGiuaKy((Float) result[1]);
            ketQuaHocTap.setDiemTBMon((Float) result[2]);
            ketQuaHocTap.setDiemThuongKy((Float) result[3]);
            ketQuaHocTap.setTaiKhoan(new TaiKhoan((String) result[4]));
            ketQuaHocTap.setLopHoc(new LopHoc((String) result[5]));
        }

        tr.commit();
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException("Lỗi khi lấy kết quả học tập", e);
    }
    return ketQuaHocTap;
}


    // Cập nhật kết quả học tập
    public boolean capNhatKetQuaHocTap(KetQuaHocTap ketQuaHocTap) {
    EntityTransaction tr = em.getTransaction();
    try {
        tr.begin();
        // Dùng native SQL để cập nhật kết quả học tập
        String sql = "UPDATE KetQuaHocTaps SET diemCuoiKy = ?, diemGiuaKy = ?, diemTBMon = ?, diemThuongKy = ? " +
                     "WHERE maTaiKhoan = ? AND maLop = ?";
        em.createNativeQuery(sql)
          .setParameter(1, ketQuaHocTap.getDiemCuoiKy())
          .setParameter(2, ketQuaHocTap.getDiemGiuaKy())
          .setParameter(3, ketQuaHocTap.getDiemTBMon())
          .setParameter(4, ketQuaHocTap.getDiemThuongKy())
          .setParameter(5, ketQuaHocTap.getTaiKhoan().getMaTaiKhoan())
          .setParameter(6, ketQuaHocTap.getLopHoc().getMaLop())
          .executeUpdate();
        tr.commit();
        return true;
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        throw new RuntimeException("Lỗi khi cập nhật kết quả học tập", e);
    }
}


    // Lấy danh sách kết quả học tập dựa trên mã lớp
    public ArrayList<KetQuaHocTap> getDanhSachKetQuaHocTap(String maLop) {
        EntityTransaction tr = em.getTransaction();
        ArrayList<KetQuaHocTap> danhSachKetQua = new ArrayList<>();
        try {
            tr.begin();
            // Sử dụng cú pháp JPQL
           String sql = "SELECT * FROM KetQuaHocTaps WHERE maLop = ?";
            List<Object[]> results = em.createNativeQuery(sql)
                                       .setParameter(1, maLop)
                                       .getResultList();
            for(Object[] result: results){
                KetQuaHocTap ketQuaHocTap= new KetQuaHocTap();
                ketQuaHocTap.setDiemCuoiKy((Float)result[0]);
                ketQuaHocTap.setDiemGiuaKy((Float)result[1]);
                ketQuaHocTap.setDiemTBMon((Float)result[2]);
                ketQuaHocTap.setDiemThuongKy((Float)result[3]);
                ketQuaHocTap.setTaiKhoan(new TaiKhoan((String) result[4]));
                ketQuaHocTap.setLopHoc(new LopHoc((String) result[5]));
                
                danhSachKetQua.add(ketQuaHocTap);
            }
            
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy danh sách kết quả học tập", e);
        }
        return danhSachKetQua;
    }

    //thêm sinh viên vào lớp học
    public void importDanhSachTaiKhoanVaoLopHoc(String filePath,String maLop) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ header
                Row row = sheet.getRow(i);
                String maTaiKhoan = row.getCell(0).getStringCellValue();
                KetQuaHocTap ketQuaHocTap = new KetQuaHocTap(new LopHoc(maLop), new TaiKhoan(maTaiKhoan));
                themKetQuaHocTap(ketQuaHocTap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
