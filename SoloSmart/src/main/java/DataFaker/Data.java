package DataFaker;

import DB.CreateDB;
import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Data {
    Faker faker = new Faker();
    private static EntityManager em= CreateDB.createDB();
    private static TaiKhoan_DAO taiKhoanDao = new TaiKhoan_DAO(em);
    private static MonHoc_DAO monHocDao = new MonHoc_DAO(em);
    private static LopHoc_DAO lopHocDao = new LopHoc_DAO(em);
    private static NganHangDeThi_DAO nganHangDeThiDao = new NganHangDeThi_DAO(em);
    private static DeThi_DAO deThiDao = new DeThi_DAO(em);
    private static CauHoi_DAO cauHoiDao = new CauHoi_DAO(em);
    private static DsLuaChon_DAO dsLuaChonDao = new DsLuaChon_DAO(em);
    private static BaiKiemTra_DAO baiKiemTraDao= new BaiKiemTra_DAO(em);
    private static KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
    private static NganHangDeThi_DAO nh_dao= new NganHangDeThi_DAO(em);

    public TaiKhoan TaiKhoanFaker() {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(taiKhoanDao.generateMa());
        tk.setTenTaiKhoan(faker.name().fullName());
        tk.setMatKhau(faker.internet().password(8, 16));
        tk.setVaiTro(faker.options().option("SV", "GV", "QTV"));
        tk.setTrangThai("enable");
        return tk;
    }
    public TaiKhoan TaiKhoanSVFaker() {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(taiKhoanDao.generateMa());
        tk.setTenTaiKhoan(faker.name().fullName());
        tk.setMatKhau(faker.internet().password(8, 16));
        tk.setVaiTro("SV");
        tk.setTrangThai("enable");
        return tk;
    }
    public TaiKhoan TaiKhoanGVFaker() {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(taiKhoanDao.generateMa());
        tk.setTenTaiKhoan(faker.name().fullName());
        tk.setMatKhau(faker.internet().password(8, 16));
        tk.setVaiTro("GV");
        tk.setTrangThai("enable");
        return tk;
    }
    public MonHoc MonHocFaker() {
        MonHoc mh = new MonHoc();
        mh.setMaMonHoc(monHocDao.generateMa());
        mh.setTenMonHoc(faker.educator().course());
        mh.setTrangThai("enable");
        return mh;
    }

    public LopHoc LopHocFaker(MonHoc mh) {
        LopHoc lopHoc = new LopHoc();
        lopHoc.setMaLop(lopHocDao.generateMa());
        lopHoc.setTenLop(faker.educator().course());
        lopHoc.setSiSo(faker.number().numberBetween(20, 50));
        lopHoc.setNamHoc(String.format("%d-%d", faker.number().numberBetween(2000, 2025), faker.number().numberBetween(2001, 2026)));
        lopHoc.setTrangThai("enable");
        lopHoc.setMonHoc(mh);
        return lopHoc;
    }

    public NganHangDeThi NganHangDeThiFaker(MonHoc mh) {
        NganHangDeThi nh = new NganHangDeThi();
        nh.setMaNganHang(nganHangDeThiDao.generateMa());
        nh.setTenNganHang("Ngân Hàng Đề Thi Môn "+ mh.getTenMonHoc());
        nh.setLoaiNganHang(faker.bool().bool());
        nh.setMonHoc(mh);
        return nh;
    }

    public DeThi DeThiFaker(TaiKhoan taiKhoan, NganHangDeThi nganHangDeThi, MonHoc mh) {
        DeThi deThi = new DeThi();
        deThi.setMaDeThi(deThiDao.generateMa());
        deThi.setSoLuongCauHoi(faker.number().numberBetween(10, 50));
        deThi.setMonHoc(mh.getTenMonHoc());
        deThi.setLinkFile(faker.internet().url());
        deThi.setTrangThai("enable");
        deThi.setTaiKhoan(taiKhoan);
        deThi.setNganHangDeThi(nganHangDeThi);
        return deThi;
    }

    public CauHoi CauHoiFaker(DeThi deThi) {
        CauHoi cauHoi = new CauHoi();
        cauHoi.setMaCauHoi(cauHoiDao.generateMa());
        String[] mucDoOptions = {"Nhận biết", "Thông hiểu", "Vận dụng", "Vận dụng cao"};
        cauHoi.setMucDo(faker.options().option(mucDoOptions));
        cauHoi.setCauHoi(faker.lorem().sentence());
        int kieuTraloi = faker.number().numberBetween(0, 2);
        cauHoi.setKieuTraLoi(kieuTraloi);
        String dapAn = null;
        String[] dsDapAn={"Đáp án 1","Đáp án 2","Đáp án 3","Đáp án 4"};
        if(kieuTraloi == 1) {
            dapAn = faker.options().option(dsDapAn);
        }else {
            // Nếu kiểu trả lời khác 1, chọn ngẫu nhiên số lượng đáp án không trùng lặp
            List<String> dsDapAnList = new ArrayList<>(Arrays.asList(dsDapAn));
            Collections.shuffle(dsDapAnList); // Trộn ngẫu nhiên các đáp án
            int num = faker.number().numberBetween(1, 5); // Số lượng đáp án muốn chọn

            // Lấy danh sách các đáp án và nối thành chuỗi
            dapAn = String.join(";", dsDapAnList.subList(0, num));
        };
        cauHoi.setDapAnDung(dapAn);
        cauHoi.setLoiGiai(faker.lorem().sentence());
        cauHoi.setTrangThai("enable");
        cauHoi.setDeThi(deThi);
        return cauHoi;
    }
    public void DsLuaChonFaker(CauHoi cauHoi){
        dsLuaChonDao.themLuaChon(cauHoi.getMaCauHoi(),"Đáp án 1");
        dsLuaChonDao.themLuaChon(cauHoi.getMaCauHoi(),"Đáp án 2");
        dsLuaChonDao.themLuaChon(cauHoi.getMaCauHoi(),"Đáp án 3");
        dsLuaChonDao.themLuaChon(cauHoi.getMaCauHoi(),"Đáp án 4");
    }
    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public BaiKiemTra BaiKiemTraFaker(DeThi deThi, LopHoc lopHoc){
        BaiKiemTra baiKiemTra= new BaiKiemTra();
        baiKiemTra.setMaBaiKiemTra(baiKiemTraDao.generateMa());
        baiKiemTra.setChoPhepXemDiem(faker.bool().bool());
        baiKiemTra.setChoPhepXemLai(faker.bool().bool());
        baiKiemTra.setHeSo(faker.options().option(0.2f,0.3f,0.5f));
        baiKiemTra.setHienThiDapAn(faker.bool().bool());
        baiKiemTra.setMatKhauBaiKiemTra(faker.internet().password());
        baiKiemTra.setSoLanLamBai(faker.number().numberBetween(1,10));
        baiKiemTra.setThangDiem(faker.options().option(4,10));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate= LocalDateTime.of(now.getYear(), faker.number().numberBetween(now.getMonthValue(),13),
                now.getDayOfMonth(), now.getHour(), now.getMinute());
        LocalDateTime endDate= startDate.plusDays(faker.number().numberBetween(1,6));
        baiKiemTra.setThoiGianBatDau(startDate);
        baiKiemTra.setThoiGianKetThuc(endDate);
        if(baiKiemTra.getHeSo()==0.2f){
            baiKiemTra.setThoiGianLamBai(faker.options().option(15,20,30,35));
        }
        else if(baiKiemTra.getHeSo()==0.3f){
            baiKiemTra.setThoiGianLamBai(faker.options().option(30,45,60,90));
        }
        else{
            baiKiemTra.setThoiGianLamBai(faker.options().option(60,90,120));
        }
        baiKiemTra.setTrangThai("enable");
        baiKiemTra.setDeThi(deThi);
        baiKiemTra.setLopHoc(lopHoc);
    return baiKiemTra;
    }
    public KetQuaKiemTra KetQuaKiemTraFaker(BaiKiemTra baiKiemTra,TaiKhoan taiKhoan) {
        KetQuaKiemTra ketQuaKiemTra= new KetQuaKiemTra();
        ketQuaKiemTra.setMaKetQuaKiemTra(ketQuaKiemTraDao.generateMa());
        ketQuaKiemTra.setDiemCaoNhat(true);
        ketQuaKiemTra.setDiemSo((float) faker.number().randomDouble(2, 0, 10));
        ketQuaKiemTra.setLanThu(1);
        ketQuaKiemTra.setThoiGianLamBai(faker.number().numberBetween(5,baiKiemTra.getThoiGianLamBai()));
        ketQuaKiemTra.setBaiKiemTra(baiKiemTra);
        ketQuaKiemTra.setTaiKhoan(taiKhoan);
    return  ketQuaKiemTra;
    }
    public void DSCauTraLoiFaker(KetQuaKiemTra ketQuaKiemTra){
        DsCauTraLoi_DAO dao= new DsCauTraLoi_DAO(em);
        String maBKT= ketQuaKiemTra.getBaiKiemTra().getMaBaiKiemTra();
        BaiKiemTra bkt= baiKiemTraDao.getBaiKiemTra(maBKT);
        DeThi deThi=deThiDao.getDeThi(bkt.getDeThi().getMaDeThi());
        String[] dapan={"Đáp án 1", "Đáp án 2", "Đáp án 3", "Đáp án 4"};
        for(int i=0;i<deThi.getSoLuongCauHoi();i++){
            dao.themCauTraLoi(ketQuaKiemTra.getMaKetQuaKiemTra(),i+faker.options().option(dapan));
        }
    }
    public void GenerateGV(int i) {
        for (int count = 0; count < i; count++) {
            // Tạo tài khoản giáo viên bằng Faker
            TaiKhoan tk = TaiKhoanGVFaker();
            try {
                taiKhoanDao.addTaiKhoan(tk); // Phương thức lưu tài khoản trong DAO
            } catch (Exception e) {
                System.err.println("Lỗi khi tạo tài khoản giáo viên: " + e.getMessage());
            }
        }
    }
    public void GenerateSV(int i) {
        for (int count = 0; count < i; count++) {
            // Tạo tài khoản giáo viên bằng Faker
            TaiKhoan tk = TaiKhoanSVFaker();
            try {
                taiKhoanDao.addTaiKhoan(tk); // Phương thức lưu tài khoản trong DAO
            } catch (Exception e) {
                System.err.println("Lỗi khi tạo tài khoản giáo viên: " + e.getMessage());
            }
        }
    }
    public void GenerateMonHoc(int soLuong) {
        Set<String> tenMonHocSet = new HashSet<>(); // Sử dụng Set để đảm bảo tên môn học không trùng nhau

        int count = 0;
        while (count < soLuong) {
            MonHoc mh = MonHocFaker();
            if (!tenMonHocSet.contains(mh.getTenMonHoc())) {
                tenMonHocSet.add(mh.getTenMonHoc()); // Thêm tên môn học vào Set
                try {
                    // Lưu môn học vào cơ sở dữ liệu thông qua DAO
                    monHocDao.addMonHoc(mh); // Phương thức lưu môn học trong DAO
                    count++;
                } catch (Exception e) {
                    System.err.println("Lỗi khi tạo môn học: " + e.getMessage());
                }
            }
        }
    }


    public static void main(String[] args) {
        Data data = new Data();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssql-pu");
        EntityManager em = emf.createEntityManager();



        try {
            em.getTransaction().begin();
//
            data.GenerateSV(80);
            data.GenerateGV(20);
            data.GenerateMonHoc(10);
            ArrayList<TaiKhoan> dsGV= taiKhoanDao.getDanhSachTaiKhoanGV();
            ArrayList<TaiKhoan> dsSV= taiKhoanDao.getDanhSachTaiKhoanSV();
            ArrayList<MonHoc> dsMH= monHocDao.getDanhSachMonHoc();

//            them mon hoc va ngan hang de thi
            for(int i=0; i<10; i++) {
                LopHoc lopHoc= data.LopHocFaker(dsMH.get(i));
                lopHocDao.addLopHoc(lopHoc);
                NganHangDeThi nhdt= data.NganHangDeThiFaker(dsMH.get(i));
                nh_dao.addNganHangDeThi(nhdt);
            }
            //them dethi tra va danh sach lua chon
            for(int i=0;i<20;i++){
                int num= data.faker.number().numberBetween(0,10);
                DeThi dethi= data.DeThiFaker(dsGV.get(num),new NganHangDeThi(),dsMH.get(num));
                deThiDao.addDeThi(dethi);
                for(int j=0;j<dethi.getSoLuongCauHoi()-1;j++){//thêm câu hỏi
                    CauHoi cauHoi=data.CauHoiFaker(dethi);
                    cauHoiDao.addCauHoi(cauHoi);
                    data.DsLuaChonFaker(cauHoi);
                }
            }
            //them bai kiem tra
            ArrayList<LopHoc> dsLopHoc= lopHocDao.getDanhSachLopHoc();
            for(int i=0;i<10;i++){
                MonHoc mon= monHocDao.getMonHoc(dsLopHoc.get(i).getMonHoc().getMaMonHoc());
               ArrayList<DeThi> dsDethiTheoMon= deThiDao.getDanhSachDeThiTheoMon(mon.getTenMonHoc());
               for(int j=0;j<dsDethiTheoMon.size();j++){
                    BaiKiemTra baiKiemTra=data.BaiKiemTraFaker(dsDethiTheoMon.get(j),dsLopHoc.get(i));
                    baiKiemTraDao.themBaiKiemTra(baiKiemTra);
               }
            }
            //tao ketquakiemtra
            for(int i=0;i<30;i++){
               int num= data.faker.number().numberBetween(0,10);

               ArrayList<BaiKiemTra> dsBKT= baiKiemTraDao.getDanhSachBaiKiemTraTheoLop(dsLopHoc.get(num).getMaLop());
               for(int j=0;j<dsBKT.size();j++){
                  KetQuaKiemTra ketQuaKiemTra=data.KetQuaKiemTraFaker(dsBKT.get(j),dsSV.get(i));
                  System.out.println(ketQuaKiemTra.getMaKetQuaKiemTra());
                  ketQuaKiemTraDao.themKetQuaKiemTra(ketQuaKiemTra);
                  data.DSCauTraLoiFaker(ketQuaKiemTra);
               }
            }

            //kết quả học tập được thêm và cập nhật bằng trigger trong sql khi có 1 kết quả kiểm tra được thêm vào


            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
