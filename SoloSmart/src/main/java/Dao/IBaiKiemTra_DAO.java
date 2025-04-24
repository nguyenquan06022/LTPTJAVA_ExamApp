package Dao;

import Entity.BaiKiemTra;
import Entity.TaiKhoan;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IBaiKiemTra_DAO extends Remote, Serializable {
    String generateMa()throws RemoteException;

    boolean themBaiKiemTra(BaiKiemTra bkt) throws RemoteException;

    BaiKiemTra getBaiKiemTra(String id) throws RemoteException;

    BaiKiemTra getBaiKiemTra() throws RemoteException;

    List<BaiKiemTra> getBaiKiemTraTheoTaiKhoan(String id) throws RemoteException;

    ArrayList<BaiKiemTra> getDanhSachBaiKiemTra() throws RemoteException;

    ArrayList<BaiKiemTra> getDanhSachBaiKiemTraTheoLop(String maLop) throws RemoteException;

    boolean updateBaiKiemTra(BaiKiemTra baiKiemTra) throws RemoteException;

    boolean deleteBaiKiemTra(String id) throws RemoteException;

    // lấy ra danh sách bài kiểm tra của sinh viên theo lớp học
    ArrayList<BaiKiemTra> getDanhSachBaiKiemTraCuaSinhVienTheoLop(String maTaiKhoan, String maLop) throws RemoteException;

    // lấy ra danh sách bài kiểm tra theo lớp học của giáo viên
    ArrayList<BaiKiemTra> getDanhSachBaiKiemTraCuaGiaoVienTheoLop(String maTaiKhoan, String maLop) throws RemoteException;

    // Lấy ra danh sách tài khoản đã tham gia kiểm tra và điểm số của tài khoản đó trong bài kiểm tra đó
    Map<TaiKhoan, Float> getDsTaiKhoanThamGiaKiemTraVaDiemSo(String maBaiKiemTra) throws RemoteException;

    List<BaiKiemTra> getBaiKiemTraTheoTaiKhoanGV(String id) throws RemoteException;
}
