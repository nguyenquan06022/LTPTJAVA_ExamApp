package Dao;

import Entity.KetQuaKiemTra;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IKetQuaKiemTra_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean themKetQuaKiemTra(KetQuaKiemTra ketQua) throws RemoteException;

    KetQuaKiemTra getKetQuaKiemTra(String id) throws RemoteException;

    ArrayList<KetQuaKiemTra> getDanhSachKetQuaKiemTra(String maTaiKhoan, String maBaiKiemTra) throws RemoteException;

    KetQuaKiemTra getKetQuaKiemTra(String maBaiKiemTra, String maTaiKhoan) throws RemoteException;

    boolean updateKetQuaKiemTra(KetQuaKiemTra ketQua) throws RemoteException;

    // thống kê điểm theo bài kiểm tra
    ArrayList<Float> getDsDiemTheoBaiKiemTra(String maLop, String maBaiKiemTra) throws RemoteException;

    // tính điểm sinh viên cho bài kiểm tra theo mã sinh viên và mã bài kiểm tra
    float tinhDiemChoSinhVien(String maSinhVien, String maBaiKiemTra) throws RemoteException;

    //cập nhật điểm cao nhất
    boolean updateDiemCaoNhatChoBaiKiemTraCuaSinhVien(String maTaiKhoan, String maBaiKiemTra) throws RemoteException;
}
