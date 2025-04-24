package Dao;

import Entity.NganHangDeThi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface INganHangDeThi_DAO extends Remote, Serializable {
    String generateMa() throws RemoteException;

    boolean addNganHangDeThi(NganHangDeThi nganHangDeThi) throws RemoteException;

    NganHangDeThi getNganHangDeThi(String id) throws RemoteException;

    ArrayList<NganHangDeThi> getDanhSachNganHangDeThi() throws RemoteException;

    boolean updateNganHangDeThi(NganHangDeThi nganHangDeThi) throws RemoteException;
}
