import Dao.KetQuaKiemTra_DAO;
import Entity.BaiKiemTra;
import Entity.KetQuaKiemTra;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KetQuaKiemTra_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private KetQuaKiemTra_DAO dao;

    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        dao = new KetQuaKiemTra_DAO(em);
    }

    @AfterAll
    public void teardown() {
        if (em != null)
            em.close();
        if (emf != null)
            emf.close();
    }

    @Test
    public void testThemKetQuaKiemTra() {
        KetQuaKiemTra ketQua = new KetQuaKiemTra();
        ketQua.setMaKetQuaKiemTra(dao.generateMa());
        ketQua.setDiemCaoNhat(true);
        ketQua.setDiemSo((float) 9.5);
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setMaTaiKhoan("TK001");
        BaiKiemTra bkt = new BaiKiemTra();
        bkt.setMaBaiKiemTra("BKT001");
        ketQua.setBaiKiemTra(bkt);
        ketQua.setTaiKhoan(taikhoan);

        boolean result = dao.themKetQuaKiemTra(ketQua);
        assertTrue(result, "Thêm kết quả kiểm tra thất bại");
    }

    @Test
    public void testGetDanhSachKetQuaKiemTra() {
        ArrayList<KetQuaKiemTra> danhSach = dao.getDanhSachKetQuaKiemTra("TK001", "BKT001");
        assertNotNull(danhSach, "Danh sách kết quả kiểm tra không được null");
        assertFalse(danhSach.isEmpty(), "Danh sách kết quả kiểm tra rỗng");
        assertEquals("TK001", danhSach.get(0).getTaiKhoan().getMaTaiKhoan());
        assertEquals("BKT001", danhSach.get(0).getBaiKiemTra().getMaBaiKiemTra());
    }

    @Test
    public void testUpdateKetQuaKiemTra() {
        ArrayList<KetQuaKiemTra> danhSach = dao.getDanhSachKetQuaKiemTra("TK001", "BKT001");
        KetQuaKiemTra ketQua = danhSach.get(0);
        assertNotNull(ketQua, "Không tìm thấy kết quả kiểm tra với ID: ");
        ;

        ketQua.setDiemSo(5F);
        boolean result = dao.updateKetQuaKiemTra(ketQua);
        assertTrue(result, "Cập nhật kết quả kiểm tra thất bại");
        danhSach = dao.getDanhSachKetQuaKiemTra("TK001", "BKT001");
        KetQuaKiemTra updatedKetQua = danhSach.get(0);
        assertEquals(5, updatedKetQua.getDiemSo(), "Điểm số không được cập nhật đúng");
    }
}
