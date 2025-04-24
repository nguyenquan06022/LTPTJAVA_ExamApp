package Dao;

import Entity.CauHoi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICauHoi_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean addCauHoi(CauHoi cauHoi) throws RemoteException;

    CauHoi getCauHoi(String id) throws RemoteException;

    ArrayList<CauHoi> getDanhSachCauHoi() throws RemoteException;

    boolean updateCauHoi(CauHoi cauHoi) throws RemoteException;

    boolean deleteCauHoi(String id) throws RemoteException;

    // getdscauhoi theo madethi
    ArrayList<CauHoi> getDsCauHoiTheoDeThi(String maDeThi) throws RemoteException;
}
