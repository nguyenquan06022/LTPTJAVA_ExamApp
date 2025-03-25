import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TestCRUD {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssql-pu");
    private static EntityManager em = emf.createEntityManager();
    private static final Scanner sc = new Scanner(System.in);

    private static BaiKiemTra_DAO baiKiemTraDao = new BaiKiemTra_DAO(em);
    private static CauHoi_DAO cauHoiDao = new CauHoi_DAO(em);
    private static DeThi_DAO deThiDao = new DeThi_DAO(em);
    private static KetQuaHocTap_DAO ketQuaHocTapDao = new KetQuaHocTap_DAO(em);
    private static KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
    private static LopHoc_DAO lopHocDao = new LopHoc_DAO(em);
    private static MonHoc_DAO monHocDao = new MonHoc_DAO(em);
    private static NganHangDeThi_DAO nganHangDeThiDao = new NganHangDeThi_DAO(em);
    private static TaiKhoan_DAO taiKhoanDao = new TaiKhoan_DAO(em);
    private static DsLuaChon_DAO dsLuaChonDao = new DsLuaChon_DAO(em);
    private static DsCauTraLoi_DAO dsCauTraLoi_dao = new DsCauTraLoi_DAO(em);


    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("----Menu----");
            System.out.println("1. TaiKhoan");
            System.out.println("2. MonHoc");
            System.out.println("3. LopHoc");
            System.out.println("4. NganHang");
            System.out.println("5. DeThi");
            System.out.println("6. CauHoi");
            System.out.println("7. dsLuaChon");
            System.out.println("8. BaiKiemTra");
            System.out.println("9. KetQuaKiemTra");
            System.out.println("10. DsCauTraLoi");
            System.out.println("11. KetQuaHocTap");
            System.out.println("0. Thoat");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: {
                    TaiKhoanMenu();
                    break;
                }
                case 2: {
                    MonHocMenu();
                    break;
                }
                case 3: {
                    LopHocMenu();
                    break;
                }
                case 4: {
                    NganHangMenu();
                    break;
                }
                case 5: {
                    DeThiMenu();
                    break;
                }
                case 6: {
                    CauHoiMenu();
                    break;
                }
                case 7: {
                    DsLuaChonMenu();
                    break;
                }
                case 8: {
                    BaiKiemTraMenu();
                    break;
                }
                case 9: {
                    KetQuaKiemTraMenu();
                    break;
                }
                case 10: {
                    DsCauTraLoiMenu();
                    break;
                }
                case 11: {
                    KetQuaHocTapMenu();
                    break;
                }
                case 0: {
                    System.out.println("Thoat chuong trinh.");
                    break;
                }
                default: {
                    System.out.println("Lua chon khong hop le.");
                }
            }
        } while (choice != 0);
    }

    public static void TaiKhoanMenu() {
        subMenu("TaiKhoan");
    }

    public static void MonHocMenu() {
        subMenu("MonHoc");
    }

    public static void LopHocMenu() {
        subMenu("LopHoc");
    }

    public static void NganHangMenu() {
        subMenu("NganHang");
    }

    public static void DeThiMenu() {
        subMenu("DeThi");
    }

    public static void CauHoiMenu() {
        subMenu("CauHoi");
    }

    public static void DsLuaChonMenu() {
        subMenu("dsLuaChon");
    }

    public static void BaiKiemTraMenu() {
        subMenu("BaiKiemTra");
    }

    public static void KetQuaKiemTraMenu() {
        subMenu("KetQuaKiemTra");
    }

    public static void DsCauTraLoiMenu() {
        subMenu("dsCauTraLoi");
    }

    public static void KetQuaHocTapMenu() {
        subMenu("KetQuaHocTap");
    }

    public static void subMenu(String entityName) {
        int choice;
        do {
            System.out.println("----" + entityName + "----");
            System.out.println("1. Add");
            System.out.println("2. Get");
            System.out.println("3. GetAll");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Exit");
            choice = Integer.parseInt(sc.nextLine());
            if(choice == 0) {
                return;
            }else {
                handleCRUD(choice, entityName);
            }
        } while (choice != 0);
    }

    public static void handleCRUD(int choice, String entityName) {
        switch (entityName) {
            case "TaiKhoan": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ten tai khoan");
                        String tenTaiKhoan = sc.nextLine();
                        System.out.println("Nhap mat khau");
                        String matKhau = sc.nextLine();
                        System.out.println("Nhap ho");
                        String ho = sc.nextLine();
                        System.out.println("Nhap ten");
                        String ten = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap vai tro (SV,GV,QTV)");
                        String vaiTro = sc.nextLine();
                        System.out.println("Nhap trang thai online (online,offline)");
                        String dangOnline = sc.nextLine();
                        System.out.println("Nhap gioi tinh(Nam,Nu)");
                        String gioiTinh = sc.nextLine();
                        boolean res = taiKhoanDao.addTaiKhoan(new TaiKhoan(taiKhoanDao.generateMa(),tenTaiKhoan,matKhau,ho,ten,vaiTro,trangThai,dangOnline,gioiTinh));
                        if(res) System.out.println("Them tai khoan thanh cong");
                        else System.out.println("Them tai khoan that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma tai khoan can tim");
                        String maTaiKhoan = sc.nextLine();
                        TaiKhoan res = taiKhoanDao.getTaiKhoan(maTaiKhoan);
                        if(res != null) {
                            System.out.println(res);
                        }else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach tai khoan");
                        System.out.println(taiKhoanDao.getDanhSachTaiKhoan());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma tai khoan can cap nhat");
                        String maTaiKhoan = sc.nextLine();
                        System.out.println("Nhap ten tai khoan");
                        String tenTaiKhoan = sc.nextLine();
                        System.out.println("Nhap mat khau");
                        String matKhau = sc.nextLine();
                        System.out.println("Nhap ho");
                        String ho = sc.nextLine();
                        System.out.println("Nhap ten");
                        String ten = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap vai tro (SV,GV,QTV)");
                        String vaiTro = sc.nextLine();
                        System.out.println("Nhap trang thai online (online,offline)");
                        String dangOnline = sc.nextLine();
                        System.out.println("Nhap gioi tinh(Nam,Nu)");
                        String gioiTinh = sc.nextLine();
                        boolean res = taiKhoanDao.updateTaiKhoan(new TaiKhoan(maTaiKhoan,tenTaiKhoan,matKhau,ho,ten,vaiTro,trangThai,dangOnline,gioiTinh));
                        if(res) System.out.println("Cap nhat thanh cong");
                        else System.out.println("Cap nhat that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma tai khoan can xoa");
                        String maTaiKhoan = sc.nextLine();
                        boolean res = taiKhoanDao.deleteTaiKhoan(maTaiKhoan);
                        if(res) System.out.println("Xoa tai khoan thanh cong");
                        else System.out.println("Xoa tai khoan that bai");
                        break;
                    }
                }
                break;
            }
            case "MonHoc": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ten mon hoc");
                        String tenMonHoc = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        boolean res = monHocDao.addMonHoc(new MonHoc(monHocDao.generateMa(),tenMonHoc,trangThai));
                        if(res) System.out.println("Them mon hoc thanh cong");
                        else System.out.println("Them mon hoc that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma mon hoc can tim");
                        String maMonHoc = sc.nextLine();
                        MonHoc res = monHocDao.getMonHoc(maMonHoc);
                        if(res != null) System.out.println(res);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach mon hoc");
                        System.out.println(monHocDao.getDanhSachMonHoc());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma mon hoc can cap nhat");
                        String maMonHoc = sc.nextLine();
                        System.out.println("Nhap ten mon hoc");
                        String tenMonHoc = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        boolean res = monHocDao.updateMonHoc(new MonHoc(maMonHoc,tenMonHoc,trangThai));
                        if(res) System.out.println("Sua mon hoc thanh cong");
                        else System.out.println("Sua mon hoc that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma mon hoc can xoa");
                        String maMonHoc = sc.nextLine();
                        boolean res = monHocDao.deleteMonHoc(maMonHoc);
                        if(res) System.out.println("Xoa mon hoc thanh cong");
                        else System.out.println("Xoa mon hoc that bai");
                        break;
                    }
                }
                break;
            }
            case "LopHoc": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ten lop");
                        String tenLop = sc.nextLine();
                        System.out.println("Nhap nam hoc (yyyy - yyyy)");
                        String namHoc = sc.nextLine();
                        System.out.println("Nhap si so");
                        int siSo = Integer.parseInt(sc.nextLine());
                        System.out.println("nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma mon hoc");
                        String maMonHoc = sc.nextLine();
                        boolean res = lopHocDao.addLopHoc(new LopHoc(lopHocDao.generateMa(),tenLop,siSo,namHoc,trangThai,new MonHoc(maMonHoc)));
                        if(res) System.out.println("Them lop hoc thanh cong");
                        else System.out.println("Them lop hoc that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma lop hoc can tim");
                        String maLopHoc = sc.nextLine();
                        LopHoc lopHoc = lopHocDao.getLopHoc(maLopHoc);
                        if(lopHoc != null) System.out.println(lopHoc);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach lop hoc");
                        System.out.println(lopHocDao.getDanhSachLopHoc());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma lop hoc can cap nhat");
                        String maLopHoc = sc.nextLine();
                        System.out.println("Nhap ten lop");
                        String tenLop = sc.nextLine();
                        System.out.println("Nhap nam hoc (yyyy - yyyy)");
                        String namHoc = sc.nextLine();
                        System.out.println("Nhap si so");
                        int siSo = Integer.parseInt(sc.nextLine());
                        System.out.println("nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma mon hoc");
                        String maMonHoc = sc.nextLine();
                        boolean res = lopHocDao.updateLopHoc(new LopHoc(maLopHoc,tenLop,siSo,namHoc,trangThai,new MonHoc(maMonHoc)));
                        if(res) System.out.println("Sua lop hoc thanh cong");
                        else System.out.println("Sua lop hoc that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma lop hoc can xoa");
                        String maLopHoc = sc.nextLine();
                        boolean res = lopHocDao.deleteLopHoc(maLopHoc);
                        if(res) System.out.println("Xoa lop hoc thanh cong");
                        else System.out.println("Xoa lop hoc that bai");
                        break;
                    }
                }
                break;
            }
            case "NganHang": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ten ngan hang");
                        String tenNganHang = sc.nextLine();
                        System.out.println("Nhap loai ngan hang(true,false)");
                        boolean loaiNganHang = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap ma mon hoc");
                        String maMonHoc = sc.nextLine();
                        boolean res = nganHangDeThiDao.addNganHangDeThi(new NganHangDeThi(nganHangDeThiDao.generateMa(),tenNganHang,loaiNganHang,new MonHoc(maMonHoc)));
                        if(res) System.out.println("Them ngan hang thanh cong");
                        else System.out.println("Them ngan hang that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma ngan hang");
                        String maNganHang = sc.nextLine();
                        NganHangDeThi nganHangDeThi = nganHangDeThiDao.getNganHangDeThi(maNganHang);
                        if (nganHangDeThi != null) System.out.println(nganHangDeThi);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach ngan hang de thi");
                        System.out.println(nganHangDeThiDao.getDanhSachNganHangDeThi());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma ngan hang can cap nhat");
                        String maNganHang = sc.nextLine();
                        System.out.println("Nhap ten ngan hang");
                        String tenNganHang = sc.nextLine();
                        System.out.println("Nhap loai ngan hang(true,false)");
                        boolean loaiNganHang = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap ma mon hoc");
                        String maMonHoc = sc.nextLine();
                        boolean res = nganHangDeThiDao.updateNganHangDeThi(new NganHangDeThi(maNganHang,tenNganHang,loaiNganHang,new MonHoc(maMonHoc)));
                        if(res) System.out.println("Cap nhat ngan hang thanh cong");
                        else System.out.println("Cap nhat ngan hang that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Khong the xoa ngan hang de thi");
                        break;
                    }
                }
                break;
            }
            case "DeThi": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ten file");
                        String linkFile = sc.nextLine();
                        System.out.println("Nhap mon hoc");
                        String monHoc = sc.nextLine();
                        System.out.println("Nhap so luong cau hoi");
                        int soLuongCauHoi = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma ngan hang de thi");
                        String maNganHang = sc.nextLine();
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        boolean res = deThiDao.addDeThi(new DeThi(deThiDao.generateMa(),soLuongCauHoi,monHoc,linkFile,trangThai,new TaiKhoan(maTaiKhoan),new NganHangDeThi(maNganHang)));
                        if(res) System.out.println("Them de thi thanh cong");
                        else System.out.println("Them de thi that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma de thi can tim");
                        String maDeThi = sc.nextLine();
                        DeThi res = deThiDao.getDeThi(maDeThi);
                        if(res!=null) System.out.println(res);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach de thi");
                        System.out.println(deThiDao.getDanhSachDeThi());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma de thi can cap nhat");
                        String maDeThi = sc.nextLine();
                        System.out.println("Nhap ten file");
                        String linkFile = sc.nextLine();
                        System.out.println("Nhap mon hoc");
                        String monHoc = sc.nextLine();
                        System.out.println("Nhap so luong cau hoi");
                        int soLuongCauHoi = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma ngan hang de thi");
                        String maNganHang = sc.nextLine();
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        boolean res = deThiDao.updatDeThi(new DeThi(maDeThi,soLuongCauHoi,monHoc,linkFile,trangThai,new TaiKhoan(maTaiKhoan),new NganHangDeThi(maNganHang)));
                        if(res) System.out.println("Sua de thi thanh cong");
                        else System.out.println("Sua de thi that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma de thi can xoa");
                        String maDeThi = sc.nextLine();
                        boolean res = deThiDao.deleteDeThi(maDeThi);
                        if(res) System.out.println("Xoa de thi thanh cong");
                        else System.out.println("Xoa de thi that bai");
                        break;
                    }
                }
                break;
            }
            case "CauHoi": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap cau hoi");
                        String cauHoi = sc.nextLine();
                        System.out.println("Nhap kieu tra loi (0,1)");
                        int kieuTraLoi = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap loi giai");
                        String loiGiai = sc.nextLine();
                        System.out.println("Nhap muc do (Nhan biet,Thong hieu, Van dung, Van dung cao)");
                        String mucDo = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma de thi");
                        String maDeThi = sc.nextLine();
                        boolean res = cauHoiDao.addCauHoi(new CauHoi(cauHoiDao.generateMa(),mucDo,cauHoi,kieuTraLoi,loiGiai,trangThai,new DeThi(maDeThi)));
                        if(res) System.out.println("Them cau hoi thanh cong");
                        else System.out.println("Them cau hoi that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma cau hoi can tim");
                        String maCauHoi = sc.nextLine();
                        CauHoi cauHoi = cauHoiDao.getCauHoi(maCauHoi);
                        if(cauHoi!=null) System.out.println(cauHoi);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Danh sach cau hoi");
                        System.out.println(cauHoiDao.getDanhSachCauHoi());
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma cau hoi can cap nhat");
                        String maCauHoi = sc.nextLine();
                        System.out.println("Nhap cau hoi");
                        String cauHoi = sc.nextLine();
                        System.out.println("Nhap dap an dung");
                        String dapAnDung = sc.nextLine();
                        System.out.println("Nhap kieu tra loi (0,1)");
                        int kieuTraLoi = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap loi giai");
                        String loiGiai = sc.nextLine();
                        System.out.println("Nhap muc do (Nhan biet,Thong hieu, Van dung, Van dung cao)");
                        String mucDo = sc.nextLine();
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma de thi");
                        String maDeThi = sc.nextLine();
                        boolean res = cauHoiDao.updateCauHoi(new CauHoi(maCauHoi,mucDo,cauHoi,kieuTraLoi,loiGiai,trangThai,new DeThi(maDeThi)));
                        if(res) System.out.println("Sua cau hoi thanh cong");
                        else System.out.println("Sua cau hoi that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma cau hoi can xoa");
                        String maCauHoi = sc.nextLine();
                        boolean res = cauHoiDao.deleteCauHoi(maCauHoi);
                        if(res) System.out.println("Xoa cau hoi thanh cong");
                        else System.out.println("Xoa cau hoi that bai");
                        break;
                    }
                }
                break;
            }
            case "dsLuaChon": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap lua chon");
                        String luaChon = sc.nextLine();
                        System.out.println("Nhap ma cau hoi");
                        String maCauHoi = sc.nextLine();
                        System.out.println("Co phai dap an dung? 1: phải/0: không");
                        int dapAnDung= sc.nextInt();
                        boolean res = dsLuaChonDao.themLuaChon(maCauHoi,luaChon, dapAnDung==1);
                        if(res) System.out.println("Them lua chon thanh cong");
                        else System.out.println("Them lua chon that bai");
                        break;
                    }
                    case 2: {

                        break;
                    }
                    case 3: {
                        System.out.println("Nhap ma cau hoi");
                        String maCauHoi = sc.nextLine();
                        System.out.println("Danh sach lua chon");
                        System.out.println(dsLuaChonDao.getDSLuaChon(maCauHoi));
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma cau hoi can cap nhat");
                        String maCauHoi = sc.nextLine();
                        System.out.println("Nhap lua chon cu");
                        String luaChonCu= sc.nextLine();
                        System.out.println("Nhap lua chon moi");
                        String luaChonMoi= sc.nextLine();
                        boolean res = dsLuaChonDao.capNhatLuaChon(maCauHoi,luaChonCu,luaChonMoi);
                        if(res) System.out.println("Cap nhat lua chon thanh cong");
                        else System.out.println("Cap nhat lua chon that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma cau hoi can xoa");
                        String maCauHoi = sc.nextLine();
                        System.out.println("Nhap lua chon");
                        String luaChon = sc.nextLine();
                        boolean res = dsLuaChonDao.xoaLuaChon(maCauHoi,luaChon);
                        if(res) System.out.println("Xoa lua chon thanh cong");
                        else System.out.println("Xoa lua chon that bai");
                        break;
                    }
                }
                break;
            }
            case "BaiKiemTra": {
                switch (choice) {
                    case 1: {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        System.out.println("Nhap cho phep xem diem (true,false)");
                        boolean choPhepXemDiem = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap cho phep xem lai (true,false)");
                        boolean choPhepXemLai = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap he so");
                        float heSo = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap hien thi dap an");
                        boolean hienThiDapAn = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap mat khau bai kiem tra");
                        String matKhauBaiKiemTra = sc.nextLine();
                        System.out.println("Nhap so lan lam bai");
                        int soLanLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thang diem");
                        int thangDiem = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thoi gian bat dau (dd/MM/yyyy HH:mm:ss)");
                        LocalDateTime thoiGianBatDau = LocalDateTime.parse(sc.nextLine(), formatter);
                        System.out.println("Nhap thoi gian ket thuc (dd/MM/yyyy HH:mm:ss)");
                        LocalDateTime thoiGianKetThuc = LocalDateTime.parse(sc.nextLine(), formatter);
                        System.out.println("Nhap thoi gian lam bai");
                        int thoiGianLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma de thi");
                        String maDeThi = sc.nextLine();
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        boolean res = baiKiemTraDao.themBaiKiemTra(new BaiKiemTra(baiKiemTraDao.generateMa(),thoiGianBatDau,thoiGianKetThuc,thoiGianLamBai,matKhauBaiKiemTra,choPhepXemDiem,choPhepXemLai,
                                hienThiDapAn,soLanLamBai,thangDiem,heSo,trangThai,new DeThi(maDeThi),new LopHoc(maLop)));
                        if(res) System.out.println("Them bai kiem tra thanh cong");
                        else System.out.println("Them bai kiem tra that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma bai kiem tra");
                        String maBaiKiemTra = sc.nextLine();
                        BaiKiemTra baiKiemTra = baiKiemTraDao.getBaiKiemTra(maBaiKiemTra);
                        if (baiKiemTra != null) System.out.println(baiKiemTra);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        System.out.println("Danh sach bai kiem tra theo lop");
                        System.out.println(baiKiemTraDao.getDanhSachBaiKiemTraTheoLop(maLop));
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma bai kiem tra can sua");
                        String maBaiKiemTra = sc.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        System.out.println("Nhap cho phep xem diem (true,false)");
                        boolean choPhepXemDiem = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap cho phep xem lai (true,false)");
                        boolean choPhepXemLai = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap he so");
                        float heSo = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap hien thi dap an");
                        boolean hienThiDapAn = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap mat khau bai kiem tra");
                        String matKhauBaiKiemTra = sc.nextLine();
                        System.out.println("Nhap so lan lam bai");
                        int soLanLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thang diem");
                        int thangDiem = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thoi gian bat dau (dd/MM/yyyy HH:mm:ss)");
                        LocalDateTime thoiGianBatDau = LocalDateTime.parse(sc.nextLine(), formatter);
                        System.out.println("Nhap thoi gian ket thuc (dd/MM/yyyy HH:mm:ss)");
                        LocalDateTime thoiGianKetThuc = LocalDateTime.parse(sc.nextLine(), formatter);
                        System.out.println("Nhap thoi gian lam bai");
                        int thoiGianLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap trang thai (enable,disable)");
                        String trangThai = sc.nextLine();
                        System.out.println("Nhap ma de thi");
                        String maDeThi = sc.nextLine();
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        boolean res = baiKiemTraDao.updateBaiKiemTra(new BaiKiemTra(maBaiKiemTra,thoiGianBatDau,thoiGianKetThuc,thoiGianLamBai,matKhauBaiKiemTra,choPhepXemDiem,choPhepXemLai,
                                hienThiDapAn,soLanLamBai,thangDiem,heSo,trangThai,new DeThi(maDeThi),new LopHoc(maLop)));
                        if(res) System.out.println("Sua bai kiem tra thanh cong");
                        else System.out.println("Sua bai kiem tra that bai");
                        break;
                    }
                    case 5: {
                        System.out.println("Nhap ma bai kiem tra can xoa");
                        String maBaiKiemTra = sc.nextLine();
                        boolean res = baiKiemTraDao.deleteBaiKiemTra(maBaiKiemTra);
                        if(res) System.out.println("Xoa bai kiem tra thanh cong");
                        else System.out.println("Xoa bai kiem tra that bai");
                        break;
                    }
                }
                break;
            }
            case "KetQuaKiemTra": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap diem cao nhat (true,false)");
                        boolean diemCaoNhat = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap diem so");
                        float diemSo = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap lan thu");
                        int lanThu = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thoi gian lam bai (phut)");
                        int thoiGianLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap ma bai kiem tra");
                        String maBaiKiemTra = sc.nextLine();
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        boolean res = ketQuaKiemTraDao.themKetQuaKiemTra(new KetQuaKiemTra(ketQuaKiemTraDao.generateMa(),diemSo,thoiGianLamBai,lanThu,diemCaoNhat,new BaiKiemTra(maBaiKiemTra),new TaiKhoan(maTaiKhoan)));
                        if(res) System.out.println("Them ket qua kiem tra thanh cong");
                        else System.out.println("Them ket qua kiem tra that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma ket qua kiem tra");
                        String maKetQuaKiemTra = sc.nextLine();
                        KetQuaKiemTra ketQuaKiemTra = ketQuaKiemTraDao.getKetQuaKiemTra(maKetQuaKiemTra);
                        if (ketQuaKiemTra!=null) System.out.println(ketQuaKiemTra);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        System.out.println("Nhap ma bai kiem tra");
                        String maBaiKiemTra = sc.nextLine();
                        System.out.println("Danh sach ket qua bai kiem tra");
                        System.out.println(ketQuaKiemTraDao.getDanhSachKetQuaKiemTra(maTaiKhoan,maBaiKiemTra));
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma ket qua kiem tra can cap nhat");
                        String maKetQuaKiemTra = sc.nextLine();
                        System.out.println("Nhap diem cao nhat (true,false)");
                        boolean diemCaoNhat = Boolean.parseBoolean(sc.nextLine());
                        System.out.println("Nhap diem so");
                        float diemSo = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap lan thu");
                        int lanThu = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap thoi gian lam bai (phut)");
                        int thoiGianLamBai = Integer.parseInt(sc.nextLine());
                        System.out.println("Nhap ma bai kiem tra");
                        String maBaiKiemTra = sc.nextLine();
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        boolean res = ketQuaKiemTraDao.updateKetQuaKiemTra(new KetQuaKiemTra(maKetQuaKiemTra,diemSo,thoiGianLamBai,lanThu,diemCaoNhat,new BaiKiemTra(maBaiKiemTra),new TaiKhoan(maTaiKhoan)));
                        if(res) System.out.println("Sua ket qua kiem tra thanh cong");
                        else System.out.println("Sua ket qua kiem tra that bai");
                        break;
                    }
                    case 5: {
                        break;
                    }
                }
                break;
            }
            case "dsCauTraLoi": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap ma ket qua kiem tra");
                        String maKetQuaKiemTra = sc.nextLine();
                        System.out.println("Nhap cau tra loi");
                        String cauTraLoi = sc.nextLine();
                        boolean res = dsCauTraLoi_dao.themCauTraLoi(maKetQuaKiemTra,cauTraLoi);
                        if(res) System.out.println("Them cau tra loi thanh cong");
                        else System.out.println("Them cau tra loi that bai");
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        System.out.println("Nhap ma ket qua kiem tra");
                        String maKetQuaKiemTra = sc.nextLine();
                        System.out.println("Danh sach cau tra loi");
                        System.out.println(dsCauTraLoi_dao.getDSCauTraLoi(maKetQuaKiemTra));
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap ma ket qua kiem tra");
                        String maKetQuaKiemTra = sc.nextLine();
                        System.out.println("Nhap cau tra loi");
                        String cauTraLoi = sc.nextLine();
                        System.out.println("Nhap cau tra loi moi");
                        String cauTraLoiMoi = sc.nextLine();
                        boolean res = dsCauTraLoi_dao.updateCauTraLoi(maKetQuaKiemTra,cauTraLoi,cauTraLoiMoi);
                        if(res) System.out.println("Sua cau tra loi thanh cong");
                        else System.out.println("Sua cau tra loi that bai");
                        break;
                    }
                    case 5: {
                        break;
                    }
                }
                break;
            }
            case "KetQuaHocTap": {
                switch (choice) {
                    case 1: {
                        System.out.println("Nhap diem thuong ky");
                        float diemThuongKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap diem giua ky");
                        float diemGiuaKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap diem cuoi ky");
                        float diemCuoiKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap GPA");
                        float GPA = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        boolean res = ketQuaHocTapDao.themKetQuaHocTap(new KetQuaHocTap(diemThuongKy,diemGiuaKy,diemCuoiKy,GPA,new TaiKhoan(maTaiKhoan),new LopHoc(maLop)));
                        if(res) System.out.println("Them ket qua hoc tap thanh cong");
                        else System.out.println("Them ket qua hoc tap that bai");
                        break;
                    }
                    case 2: {
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        KetQuaHocTap ketQuaHocTap = ketQuaHocTapDao.getKetQuaHocTap(maTaiKhoan,maLop);
                        if(ketQuaHocTap != null) System.out.println(ketQuaHocTap);
                        else System.out.println("Khong tim thay");
                        break;
                    }
                    case 3: {
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        System.out.println("Danh sach ket qua hoc tap");
                        System.out.println(ketQuaHocTapDao.getDanhSachKetQuaHocTap(maLop));
                        break;
                    }
                    case 4: {
                        System.out.println("Nhap diem thuong ky");
                        float diemThuongKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap diem giua ky");
                        float diemGiuaKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap diem cuoi ky");
                        float diemCuoiKy = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap GPA");
                        float GPA = Float.parseFloat(sc.nextLine());
                        System.out.println("Nhap ma tai khoan");
                        String maTaiKhoan = sc.nextLine();
                        System.out.println("Nhap ma lop");
                        String maLop = sc.nextLine();
                        boolean res = ketQuaHocTapDao.capNhatKetQuaHocTap(new KetQuaHocTap(diemThuongKy,diemGiuaKy,diemCuoiKy,GPA,new TaiKhoan(maTaiKhoan),new LopHoc(maLop)));
                        if(res) System.out.println("Sua ket qua hoc tap thanh cong");
                        else System.out.println("Sua ket qua hoc tap that bai");
                        break;
                    }
                    case 5: {
                        break;
                    }
                }
                break;
            }
        }return;
    }

}
