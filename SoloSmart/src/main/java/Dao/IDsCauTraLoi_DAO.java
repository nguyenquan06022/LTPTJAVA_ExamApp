package Dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IDsCauTraLoi_DAO extends Remote, Serializable {
    boolean themCauTraLoi(String maketquakiemtra, String cauTraLoi) throws RemoteException;

    boolean updateCauTraLoi(String maketquakiemtra, String cauTraLoi, String cauTraLoiMoi) throws RemoteException;

    ArrayList<String> getDSCauTraLoi(String maKetQuaKiemTra) throws RemoteException;

    List<String> getDsCauTraLoiCuaSinhVien(String maTaiKhoan, String maBaiKiemTra) throws RemoteException;
}
