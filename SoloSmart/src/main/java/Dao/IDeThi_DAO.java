package Dao;

import Entity.DeThi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IDeThi_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean addDeThi(DeThi deThi) throws RemoteException;

    // get de thi theo ma roi dung ma do lay ra danh sách caâu hỏi
    DeThi getDeThi(String id) throws RemoteException;

    ArrayList<DeThi> getDanhSachDeThi() throws RemoteException;

    ArrayList<DeThi> getDanhSachDeThiTheoMon(String mon) throws RemoteException;

    ArrayList<DeThi> getDanhSachDeThiTheoMonCuaGV(String maTaiKhoan, String mon) throws RemoteException;

    boolean updatDeThi(DeThi deThi) throws RemoteException;

    boolean deleteDeThi(String id) throws RemoteException;

    // lấy ra danh sách đề thi của giáo viên đó tạo
    ArrayList<DeThi> getDanhSachDeThiCuaGiaoVien(String maTaiKhoan) throws RemoteException;

    // filter dethi của giáo viên
    ArrayList<DeThi> filterDeThiCuaGiaoVien(String maDeThi, String monHoc, String tenDeThi, String maTaiKhoan) throws RemoteException;
}
