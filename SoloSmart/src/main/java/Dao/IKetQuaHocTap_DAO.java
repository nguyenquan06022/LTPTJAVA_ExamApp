package Dao;

import Entity.KetQuaHocTap;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface IKetQuaHocTap_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    // Thêm kết quả học tập
    boolean themKetQuaHocTap(KetQuaHocTap ketQuaHocTap) throws RemoteException;

    boolean xoaKetQuaHocTap(KetQuaHocTap ketQuaHocTap) throws RemoteException;

    // Lấy kết quả học tập dựa trên mã tài khoản và mã lớp
    KetQuaHocTap getKetQuaHocTap(String maTaiKhoan, String maLop) throws RemoteException;

    // Cập nhật kết quả học tập
    boolean capNhatKetQuaHocTap(KetQuaHocTap ketQuaHocTap) throws RemoteException;

    // Lấy danh sách kết quả học tập dựa trên mã lớp
    ArrayList<KetQuaHocTap> getDanhSachKetQuaHocTap(String maLop) throws RemoteException;

    // thêm sinh viên vào lớp học
    void importDanhSachTaiKhoanVaoLopHoc(String filePath, String maLop) throws RemoteException;

    Map<String, Float> getDiemHocTapCuaSinhVien(String maTaiKhoan, String maLop) throws RemoteException;
}
