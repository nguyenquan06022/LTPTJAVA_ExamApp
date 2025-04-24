package Dao;

import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class TaiKhoan_DAO extends UnicastRemoteObject implements ITaiKhoan_DAO {
    private ArrayList<TaiKhoan> dsTaiKhoanVuaThem;
    private EntityManager em;
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

    @Override
    public String removeVietnameseAccent(String input) throws RemoteException {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replace("đ", "d")
                .replace("Đ", "D");
    }

    @Override
    public String generateMa() throws RemoteException{
        LocalDateTime now = LocalDateTime.now();
        return "TK" + df.format(now);
    }

    @Override
    public String generatePassword(String ten, String ho, int vaiTro) throws RemoteException{

        String[] hoParts = ho.trim().split("\\s+");
        StringBuilder chuCaiDau = new StringBuilder();
        for (String part : hoParts) {
            if (!part.isEmpty()) {
                chuCaiDau.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        return removeVietnameseAccent(ten.toLowerCase()) + chuCaiDau.toString().toUpperCase() + "@"
                + ((vaiTro == 0) ? "SV" : (vaiTro == 1) ? "GV" : "AD") + LocalDateTime.now().getYear();
    }
    @Override
    public String generatedTenTaiKhoan(int role) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        int x=0;
        try {
            tr.begin();
            String sql = "select count(*) from TaiKhoans where vaiTro like ?";
            Object rs=em.createNativeQuery(sql).setParameter(1, (role==0)?"SV":(role==1)?"GV":"AD").getSingleResult();
            x = ((Number) rs).intValue();
            tr.commit();
        } catch (Exception e) {
        }
        DateTimeFormatter dfTK = DateTimeFormatter.ofPattern("yy");
        return dfTK.format(LocalDateTime.now())+role+String.format("%05d",x);
    }
    public TaiKhoan_DAO() throws RemoteException{
        super();
    }

    public TaiKhoan_DAO(EntityManager em) throws RemoteException{
        this.em = em;
    }

    @Override
    public boolean addTaiKhoan(TaiKhoan taiKhoan) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO TaiKhoans (maTaiKhoan,matKhau,tenTaiKhoan,trangThai,vaiTro,dangOnline,gioiTinh,ho,ten,soDienThoai,email) VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?)";
            em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMaTaiKhoan())
                    .setParameter(2, taiKhoan.getMatKhau())
                    .setParameter(3, taiKhoan.getTenTaiKhoan())
                    .setParameter(4, taiKhoan.getTrangThai())
                    .setParameter(5, taiKhoan.getVaiTro())
                    .setParameter(6, taiKhoan.getDangOnline())
                    .setParameter(7, taiKhoan.getGioiTinh())
                    .setParameter(8, taiKhoan.getHo())
                    .setParameter(9, taiKhoan.getTen())
                    .setParameter(10, taiKhoan.getSoDienThoai())
                    .setParameter(11, taiKhoan.getEmail())
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
        }
        return isSuccess;
    }
    
    @Override
    public TaiKhoan getTaiKhoan(String id) throws RemoteException{
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline,gioiTinh,ho,ten,soDienThoai,email FROM TaiKhoans WHERE maTaiKhoan = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
                taiKhoan.setSoDienThoai((String) result[9]);
                taiKhoan.setEmail((String) (String) result[10]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            return null;
        }

        return taiKhoan;
    }
    
    @Override
    public TaiKhoan getTaiKhoanByMailPhone(String id) throws RemoteException{
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline,gioiTinh,ho,ten,soDienThoai,email "
                    + "FROM TaiKhoans WHERE (email = ? or sodienthoai like ?) and trangthai = 'enable'";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .setParameter(2, id)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
                taiKhoan.setSoDienThoai((String) result[9]);
                taiKhoan.setEmail((String) (String) result[10]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            return null;
        }

        return taiKhoan;
    }
    @Override
    public TaiKhoan getTaiKhoanByName(String id) throws RemoteException{
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline,gioiTinh,ho,ten,soDienThoai,email "
                    + "FROM TaiKhoans WHERE tentaikhoan = ? and trangthai = 'enable'";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
                taiKhoan.setSoDienThoai((String) result[9]);
                taiKhoan.setEmail((String) (String) result[10]);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            return null;
        }

        return taiKhoan;
    }
    @Override
    public ArrayList<TaiKhoan> getDanhSachTaiKhoan() throws RemoteException{
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh,ho,ten,soDienThoai,email FROM TaiKhoans where trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                taiKhoan.setSoDienThoai((String) row[9]);
                taiKhoan.setEmail((String) row[10]);
                danhSachTaiKhoan.add(taiKhoan);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachTaiKhoan;
    }

    @Override
    public ArrayList<TaiKhoan> getDanhSachTaiKhoanGV() throws RemoteException{
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro,dangOnline,gioiTinh,ho,ten,soDienThoai,email FROM TaiKhoans where trangThai = 'enable' and vaitro='GV'";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                taiKhoan.setSoDienThoai((String) row[9]);
                taiKhoan.setEmail((String) row[10]);
                danhSachTaiKhoan.add(taiKhoan);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachTaiKhoan;
    }

    @Override
    public ArrayList<TaiKhoan> getDanhSachTaiKhoanSV() throws RemoteException{
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro,dangOnline,gioiTinh,ho,ten,soDienThoai,email FROM TaiKhoans where trangThai = 'enable' and vaitro='SV'\n"
                    +
                    "ORDER BY ten ASC";
            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                taiKhoan.setSoDienThoai((String) row[9]);
                taiKhoan.setEmail((String) row[10]);
                danhSachTaiKhoan.add(taiKhoan);
            }

            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }

        return danhSachTaiKhoan;
    }

    @Override
    public boolean updateTaiKhoan(TaiKhoan taiKhoan) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE TaiKhoans SET matKhau = ?, tenTaiKhoan = ?, trangThai = ?, vaiTro = ?, dangOnline = ?, gioiTinh = ?, ho = ?, ten = ?,soDienThoai = ?,email = ? WHERE maTaiKhoan = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMatKhau())
                    .setParameter(2, taiKhoan.getTenTaiKhoan())
                    .setParameter(3, taiKhoan.getTrangThai())
                    .setParameter(4, taiKhoan.getVaiTro())
                    .setParameter(5, taiKhoan.getDangOnline())
                    .setParameter(6, taiKhoan.getGioiTinh())
                    .setParameter(7, taiKhoan.getHo())
                    .setParameter(8, taiKhoan.getTen())
                    .setParameter(9, taiKhoan.getSoDienThoai())
                    .setParameter(10, taiKhoan.getEmail())
                    .setParameter(11, taiKhoan.getMaTaiKhoan())
                    .executeUpdate();
            tr.commit();

            return updatedRows > 0;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteTaiKhoan(String id) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();

            String sql = "UPDATE TaiKhoans SET trangThai = 'disable' WHERE maTaiKhoan = ?";

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
    public TaiKhoan dangNhap(String userName, String password) throws RemoteException{
        TaiKhoan taiKhoan = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh, ho, ten,soDienThoai,email FROM TaiKhoans WHERE tenTaiKhoan = ? AND matKhau = ?";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter(1, userName)
                    .setParameter(2, password)
                    .getSingleResult();
            if (result != null) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) result[0]);
                taiKhoan.setMatKhau((String) result[1]);
                taiKhoan.setTenTaiKhoan((String) result[2]);
                taiKhoan.setTrangThai((String) result[3]);
                taiKhoan.setVaiTro((String) result[4]);
                taiKhoan.setDangOnline((String) result[5]);
                taiKhoan.setGioiTinh((String) result[6]);
                taiKhoan.setHo((String) result[7]);
                taiKhoan.setTen((String) result[8]);
                taiKhoan.setSoDienThoai((String) result[9]);
                taiKhoan.setEmail((String) result[10]);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
        }

        return taiKhoan;
    }

    @Override
    public boolean updateTrangThaiOnline(TaiKhoan taiKhoan) throws RemoteException{
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "UPDATE TaiKhoans\n" +
                    "SET dangOnline = CASE \n" +
                    "                    WHEN dangOnline = 'offline' THEN 'online' \n" +
                    "                    ELSE 'offline' \n" +
                    "                 END\n" +
                    "WHERE maTaiKhoan = ?";
            int updatedRows = em.createNativeQuery(sql)
                    .setParameter(1, taiKhoan.getMaTaiKhoan())
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

    // thêm danh sách tài khoản từ file Excel
    @Override
    public void importTaiKhoanFromExcel(String filePath) throws RemoteException{
        try (FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            if (dsTaiKhoanVuaThem == null)
                dsTaiKhoanVuaThem = new ArrayList<>();
            else
                dsTaiKhoanVuaThem.clear();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ header
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String ho = formatter.formatCellValue(row.getCell(0));
                String ten = formatter.formatCellValue(row.getCell(1));
                String gioiTinh = formatter.formatCellValue(row.getCell(2));
                String vaiTro = formatter.formatCellValue(row.getCell(3));
                String soDienThoai = formatter.formatCellValue(row.getCell(4));
                String email = formatter.formatCellValue(row.getCell(5));

                TaiKhoan tk = new TaiKhoan();
                tk.setMaTaiKhoan(generateMa());
                tk.setHo(ho);
                tk.setTen(ten);
                tk.setGioiTinh(gioiTinh);
                tk.setVaiTro(vaiTro);
                tk.setTrangThai("enable");
                tk.setDangOnline("offline");
                tk.setTenTaiKhoan(ho + " " + ten);
                tk.setMatKhau(UUID.randomUUID().toString().substring(0, 8)); // mật khẩu ngẫu nhiên
                tk.setSoDienThoai(soDienThoai);
                tk.setEmail(email);

                boolean res = addTaiKhoan(tk);
                if (res)
                    dsTaiKhoanVuaThem.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<TaiKhoan> getDanhSachTaiKhoanFromExcel(String filePath) throws RemoteException{
        try (FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            ArrayList<TaiKhoan> list = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String maTaiKhoan = row.getCell(0).getStringCellValue();
                TaiKhoan tk = getTaiKhoan(maTaiKhoan);
                list.add(tk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } 

    // export file excel danh sách tài khoản vua them
    @Override
    public void exportDsTaiKhoanVuaThemToExcel(String filePath) throws RemoteException{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TaiKhoanVuaThem");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] columns = { "Họ", "Tên", "Giới Tính", "Vai Trò", "Tên Tài Khoản", "Mật Khẩu" };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Ghi dữ liệu
        int rowNum = 1;
        for (TaiKhoan tk : dsTaiKhoanVuaThem) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(tk.getHo());
            row.createCell(1).setCellValue(tk.getTen());
            row.createCell(2).setCellValue(tk.getGioiTinh());
            row.createCell(3).setCellValue(tk.getVaiTro());
            row.createCell(4).setCellValue(tk.getTenTaiKhoan());
            row.createCell(5).setCellValue(tk.getMatKhau());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi vào file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Xuất file Excel thành công tại: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi ghi file Excel!");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public ArrayList<TaiKhoan> searchTaiKhoanTheoMaVaTheoTen(String maTaiKhoan, String ten) throws RemoteException{
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "select maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh, ho, ten,soDienThoai,email \n" +
            "from TaiKhoans where Ten like ? and maTaiKhoan like ? and trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, '%'+ten+'%')
                    .setParameter(2, '%'+maTaiKhoan+'%')
                    .getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                taiKhoan.setSoDienThoai((String) row[9]);
                taiKhoan.setEmail((String) row[10]);
                danhSachTaiKhoan.add(taiKhoan);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachTaiKhoan;
    }
    
    @Override
    public ArrayList<TaiKhoan> filterTaiKhoan(String gioiTinh, String vaiTro, String key) throws RemoteException{
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "Select maTaiKhoan, matKhau, tenTaiKhoan, trangThai, vaiTro, dangOnline, gioiTinh, ho, ten,soDienThoai,email from TaiKhoans\n" +
            "where (maTaiKhoan like ? or Ten like ?) and gioiTinh like ? and vaiTro like ?\n" +
            "and trangThai = 'enable'";
            List<Object[]> results = em.createNativeQuery(sql)
                    .setParameter(1, '%'+key+'%')
                    .setParameter(2, '%'+key+'%')
                    .setParameter(3, "%"+gioiTinh+"%")
                    .setParameter(4, "%"+vaiTro+"%")
                    .getResultList();

            for (Object[] row : results) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setMaTaiKhoan((String) row[0]);
                taiKhoan.setMatKhau((String) row[1]);
                taiKhoan.setTenTaiKhoan((String) row[2]);
                taiKhoan.setTrangThai((String) row[3]);
                taiKhoan.setVaiTro((String) row[4]);
                taiKhoan.setDangOnline((String) row[5]);
                taiKhoan.setGioiTinh((String) row[6]);
                taiKhoan.setHo((String) row[7]);
                taiKhoan.setTen((String) row[8]);
                taiKhoan.setSoDienThoai((String) row[9]);
                taiKhoan.setEmail((String) row[10]);
                danhSachTaiKhoan.add(taiKhoan);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return danhSachTaiKhoan;
    }
}
