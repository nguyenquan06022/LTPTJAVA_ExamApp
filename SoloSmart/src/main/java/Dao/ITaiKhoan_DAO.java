package Dao;

import Entity.TaiKhoan;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ITaiKhoan_DAO extends Remote, Serializable {
    String removeVietnameseAccent(String input) throws RemoteException;

    String generateMa() throws RemoteException;

    String generatePassword(String ten, String ho, int vaiTro) throws RemoteException;

    String generatedTenTaiKhoan(int role) throws RemoteException;

    boolean addTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    TaiKhoan getTaiKhoan(String id) throws RemoteException;

    TaiKhoan getTaiKhoanByMailPhone(String id) throws RemoteException;

    TaiKhoan getTaiKhoanByName(String id) throws RemoteException;

    ArrayList<TaiKhoan> getDanhSachTaiKhoan() throws RemoteException;

    ArrayList<TaiKhoan> getDanhSachTaiKhoanGV() throws RemoteException;

    ArrayList<TaiKhoan> getDanhSachTaiKhoanSV() throws RemoteException;

    boolean updateTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    boolean deleteTaiKhoan(String id) throws RemoteException;

    TaiKhoan dangNhap(String userName, String password) throws RemoteException;

    boolean updateTrangThaiOnline(TaiKhoan taiKhoan) throws RemoteException;

    // thêm danh sách tài khoản từ file Excel
    void importTaiKhoanFromExcel(String filePath) throws RemoteException;

    ArrayList<TaiKhoan> getDanhSachTaiKhoanFromExcel(String filePath) throws RemoteException;

    // export file excel danh sách tài khoản vua them
    void exportDsTaiKhoanVuaThemToExcel(String filePath) throws RemoteException;

    ArrayList<TaiKhoan> searchTaiKhoanTheoMaVaTheoTen(String maTaiKhoan, String ten) throws RemoteException;

    ArrayList<TaiKhoan> filterTaiKhoan(String gioiTinh, String vaiTro, String key) throws RemoteException;
}
