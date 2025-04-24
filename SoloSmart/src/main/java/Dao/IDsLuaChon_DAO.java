package Dao;

import Entity.LuaChons;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IDsLuaChon_DAO extends Remote, Serializable {
    boolean themLuaChon(String maCauHoi, String luaChon, boolean dapAnDung) throws RemoteException;

    boolean capNhatLuaChon(String maCauHoi, String luaChonCu, String luaChonMoi, boolean dapAnDung) throws RemoteException;

    String getLuaChon(String maCauHoi, String luaChon) throws RemoteException;

    ArrayList<String> getDSLuaChon(String maCauHoi) throws RemoteException;

    boolean xoaLuaChon(String maCauHoi, String luaChon) throws RemoteException;

    ArrayList<LuaChons> getDSLuaChonTheoDeThi(String maDeThi) throws RemoteException;

    ArrayList<LuaChons> getDSLuaChonTheoMa(String maCauHoi) throws RemoteException;
}
