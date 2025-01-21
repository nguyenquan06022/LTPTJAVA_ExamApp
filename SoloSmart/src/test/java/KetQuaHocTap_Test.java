import Dao.KetQuaHocTap_DAO;
import Entity.KetQuaHocTap;
import Entity.LopHoc;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KetQuaHocTap_Test {
    private EntityManagerFactory emf;
    private EntityManager em;
    private KetQuaHocTap_DAO dao;

    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        dao = new KetQuaHocTap_DAO(em);
    }

    @AfterAll
    public void teardown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    void testThemKetQuaHocTap() {
        KetQuaHocTap ketQuaHocTap = new KetQuaHocTap();
        ketQuaHocTap.setDiemThuongKy(10);
        ketQuaHocTap.setDiemGiuaKy(9);
        ketQuaHocTap.setDiemCuoiKy((float)9.5);
        TaiKhoan tk= new TaiKhoan();
        tk.setMaTaiKhoan("TK001");
        LopHoc lop= new LopHoc();
        lop.setMaLop("DHKTMP18A");
        ketQuaHocTap.setLopHoc(lop);
        ketQuaHocTap.setTaiKhoan(tk);
        boolean result = dao.themKetQuaHocTap(ketQuaHocTap);
        assertTrue(result, "Thêm kết quả học tập không thành công.");
    }

    @Test
    void testGetKetQuaHocTap() {
        String maTaiKhoan = "TK001";
        String maLop = "DHKTMP18A";

        KetQuaHocTap ketQuaHocTap = dao.getKetQuaHocTap(maTaiKhoan, maLop);
        assertNotNull(ketQuaHocTap, "Kết quả học tập không được null.");
        assertEquals(maTaiKhoan, ketQuaHocTap.getTaiKhoan().getMaTaiKhoan(), "Mã tài khoản không đúng.");
        assertEquals(maLop, ketQuaHocTap.getLopHoc().getMaLop(), "Mã lớp không đúng.");
    }

    @Test
    void testCapNhatKetQuaHocTap() {
        KetQuaHocTap ketQuaHocTap = dao.getKetQuaHocTap("TK001","DHKTMP18A");
        ketQuaHocTap.setGPA((float)4.0);


        boolean result = dao.capNhatKetQuaHocTap(ketQuaHocTap);
        assertTrue(result, "Cập nhật kết quả học tập không thành công.");
        KetQuaHocTap resultKQ = dao.getKetQuaHocTap("TK001","DHKTMP18A");
        assertEquals(4.0,resultKQ.getGPA());
    }

    @Test
    void testGetDanhSachKetQuaHocTap() {
        String maLop = "DHKTMP18A";

        ArrayList<KetQuaHocTap> danhSachKetQua = dao.getDanhSachKetQuaHocTap(maLop);
        assertNotNull(danhSachKetQua, "Danh sách kết quả học tập không được null.");
        assertFalse(danhSachKetQua.isEmpty(), "Danh sách kết quả học tập bị rỗng.");
    }

    @AfterAll
    void cleanup() {
        // Clean up test data if necessary
    }
}
