package Dao;

import Entity.LopHoc;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ILopHoc_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean addLopHoc(LopHoc lopHoc) throws RemoteException;

    LopHoc getLopHoc(String id) throws RemoteException;

    ArrayList<LopHoc> getDanhSachLopHoc() throws RemoteException;

    ArrayList<LopHoc> getDanhSachLopHocByKey(String name) throws RemoteException;

    boolean updateLopHoc(LopHoc lopHoc) throws RemoteException;

    boolean deleteLopHoc(String id) throws RemoteException;

    ArrayList<LopHoc> getDanhSachLopHocTheoNamHocCuaSinhVien(String maTaiKhoan, String namHoc) throws RemoteException;

    ArrayList<LopHoc> getDanhSachLopHocTheoTenLopHocCuaSinhVien(String maTaiKhoan, String tenLopHoc) throws RemoteException;

    // get danh sach lop hoc theo nam hoc cua admin Jcombobox
    ArrayList<LopHoc> getDanhSachLopHocTheoNamHoc(String namHoc) throws RemoteException;

    // get danh sach lop hoc theo ten monHoc cua admin Jcombobox
    ArrayList<LopHoc> getDanhSachLopHocTheoTenMonHoc(String tenMonHoc) throws RemoteException;

    ArrayList<LopHoc> getDanhSachLopHocTheoGV(String magv) throws RemoteException;

    // lọc lớp học theo tên lớp của admin JtextField
    ArrayList<LopHoc> getDanhSachLopHocTheoTenLop(String tenLop) throws RemoteException;

    // filter lớp học của giáo viên
    ArrayList<LopHoc> filterLopHocCuaGiaoVien(String maLop, String tenLop, String tenMonHoc, String namHoc,
                                              String maGiaoVien) throws RemoteException;

    ArrayList<LopHoc> getDsLopHocCuaSinhVien(String maTaiKhoan) throws RemoteException;

    List<String> getDSNamHocGV(String maGV) throws RemoteException;

    // lọc lớp học theo tiêu chi của sinh viên
    ArrayList<LopHoc> filterLopHocCuaSinhVien(String maLop, String tenLop, String tenMonHoc, String namHoc,
                                              String maSinhVien) throws RemoteException;

    List<String> getDSNamHocSV(String maSV) throws RemoteException;
}
