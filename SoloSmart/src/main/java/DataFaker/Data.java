package DataFaker;

import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

public class Data {
    Faker faker = new Faker();
    TaiKhoan_DAO taiKhoanDao = new TaiKhoan_DAO();
    MonHoc_DAO monHocDao = new MonHoc_DAO();
    LopHoc_DAO lopHocDao = new LopHoc_DAO();
    NganHangDeThi_DAO nganHangDeThiDao = new NganHangDeThi_DAO();
    DeThi_DAO deThiDao = new DeThi_DAO();
    CauHoi_DAO cauHoiDao = new CauHoi_DAO();

    public TaiKhoan TaiKhoanFaker() {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(taiKhoanDao.generateMa());
        tk.setTenTaiKhoan(faker.name().fullName());
        tk.setMatKhau(faker.internet().password(8, 16));
        tk.setVaiTro(faker.options().option("SV", "GV", "QTV"));
        tk.setTrangThai(faker.bool().bool() ? "enable" : "disable");
        return tk;
    }

    public MonHoc MonHocFaker() {
        MonHoc mh = new MonHoc();
        mh.setMaMonHoc(monHocDao.generateMa());
        mh.setTenMonHoc(faker.name().fullName());
        mh.setTrangThai(faker.bool().bool() ? "enable" : "disable");
        return mh;
    }

    public LopHoc LopHocFaker(MonHoc mh) {
        LopHoc lopHoc = new LopHoc();
        lopHoc.setMaLop(lopHocDao.generateMa());
        lopHoc.setTenLop(faker.educator().course());
        lopHoc.setSiSo(faker.number().numberBetween(20, 50));
        lopHoc.setNamHoc(String.format("%d-%d", faker.number().numberBetween(2000, 2025), faker.number().numberBetween(2001, 2026)));
        lopHoc.setTrangThai(faker.bool().bool() ? "enable" : "disable");
        lopHoc.setMonHoc(mh);
        return lopHoc;
    }

    public NganHangDeThi NganHangDeThiFaker(MonHoc mh) {
        NganHangDeThi nh = new NganHangDeThi();
        nh.setMaNganHang(nganHangDeThiDao.generateMa());
        nh.setTenNganHang(faker.name().fullName());
        nh.setLoaiNganHang(faker.bool().bool());
        nh.setMonHoc(mh);
        return nh;
    }

    public DeThi DeThiFaker(TaiKhoan taiKhoan, NganHangDeThi nganHangDeThi) {
        DeThi deThi = new DeThi();
        deThi.setMaDeThi(deThiDao.generateMa());
        deThi.setSoLuongCauHoi(faker.number().numberBetween(10, 100));
        deThi.setMonHoc(faker.educator().course());
        deThi.setLinkFile(faker.internet().url());
        deThi.setTrangThai(faker.bool().bool() ? "enable" : "disable");
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
        if(kieuTraloi == 1) {
            dapAn = "A";
        }else dapAn = "1.4.6";
        cauHoi.setDapAnDung(dapAn);
        cauHoi.setLoiGiai(faker.lorem().sentence());
        cauHoi.setTrangThai(faker.bool().bool() ? "enable" : "disable");
        cauHoi.setDeThi(deThi);
        return cauHoi;
    }

    public static void main(String[] args) {
        Data data = new Data();
        TaiKhoan taiKhoan = data.TaiKhoanFaker();
        MonHoc monHoc = data.MonHocFaker();
        LopHoc lopHoc = data.LopHocFaker(monHoc);
        NganHangDeThi nganHangDeThi = data.NganHangDeThiFaker(monHoc);
        DeThi deThi = data.DeThiFaker(taiKhoan,nganHangDeThi);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssql-pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(taiKhoan);
            em.persist(monHoc);
            em.persist(lopHoc);
            em.persist(nganHangDeThi);
            em.persist(deThi);
            for(int i=0; i<10; i++) {
                CauHoi cauHoi = data.CauHoiFaker(deThi);
                em.persist(cauHoi);
            }

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
