package Dao;

import Entity.MonHoc;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMonHoc_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean addMonHoc(MonHoc monHoc) throws RemoteException;

    MonHoc getMonHoc(String id) throws RemoteException;

    ArrayList<MonHoc> getDanhSachMonHoc() throws RemoteException;

    ArrayList<MonHoc> getDanhSachMonHocTheoTen(String ten) throws RemoteException;

    String getTenMonHocTheoBaiKiemTra(String id) throws RemoteException;

    boolean updateMonHoc(MonHoc monHoc) throws RemoteException;

    boolean deleteMonHoc(String id) throws RemoteException;

    ArrayList<MonHoc> getDanhSachMonHocCuaSinhVien(String maTaiKhoan) throws RemoteException;

    ArrayList<MonHoc> getDanhSachMonHocCuaGiaoVien(String maTaiKhoan) throws RemoteException;
}
