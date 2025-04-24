import Dao.DeThi_DAO;
import Dao.IDeThi_DAO;
import Entity.DeThi;
import Entity.NganHangDeThi;
import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeThi_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private IDeThi_DAO deThiDAO;

    @BeforeAll
    void setUp() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        deThiDAO = new DeThi_DAO(em);
    }

    @AfterAll
    void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testAddDeThi_Success() {
        // Tạo đối tượng NganHangDeThi và TaiKhoan làm khóa ngoại
        NganHangDeThi nganHangDeThi = new NganHangDeThi();
        nganHangDeThi.setMaNganHang("NHDT001"); // Đảm bảo mã ngân hàng này tồn tại trong DB

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setMaTaiKhoan("TK001"); // Đảm bảo mã tài khoản này tồn tại trong DB

        // Tạo đối tượng DeThi
        DeThi deThi = new DeThi();
        deThi.setMaDeThi("DT001");
        deThi.setLinkFile("link/to/file.pdf");
        deThi.setMonHoc("Toán");
        deThi.setSoLuongCauHoi(1);
        deThi.setTrangThai("enable");
        deThi.setNganHangDeThi(nganHangDeThi);
        deThi.setTaiKhoan(taiKhoan);

        boolean result = deThiDAO.addDeThi(deThi);
        assertTrue(result, "Thêm đề thi không thành công!");

        // Kiểm tra dữ liệu sau khi thêm
        DeThi retrieved = deThiDAO.getDeThi("DT001");
        assertNotNull(retrieved, "Không tìm thấy đề thi sau khi thêm!");
        assertEquals("DT001", retrieved.getMaDeThi());
        assertEquals("Toán", retrieved.getMonHoc());
    }

    @Test
    @Order(2)
    void testGetDeThi_Success() {
        // Lấy thông tin đề thi đã thêm
        DeThi deThi = deThiDAO.getDeThi("DT001");
        assertNotNull(deThi, "Không tìm thấy đề thi!");
        assertEquals("DT001", deThi.getMaDeThi());
        assertEquals("Toán", deThi.getMonHoc());
    }

    @Test
    @Order(3)
    void testGetDanhSachDeThi_Success() {
        // Lấy danh sách đề thi
        ArrayList<DeThi> danhSachDeThi = deThiDAO.getDanhSachDeThi();
        assertNotNull(danhSachDeThi, "Danh sách đề thi trả về null!");
        assertFalse(danhSachDeThi.isEmpty(), "Danh sách đề thi trống!");

        // Kiểm tra một đề thi trong danh sách
        DeThi deThi = danhSachDeThi.stream()
                .filter(dt -> "DT001".equals(dt.getMaDeThi()))
                .findFirst()
                .orElse(null);
        assertNotNull(deThi, "Không tìm thấy đề thi với mã DT001 trong danh sách!");
    }

    @Test
    @Order(4)
    void testUpdateDeThi_Success() {
        // Lấy đề thi cần cập nhật
        DeThi deThi = deThiDAO.getDeThi("DT001");
        assertNotNull(deThi, "Không tìm thấy đề thi để cập nhật!");

        // Cập nhật thông tin
        deThi.setLinkFile("updated/link/to/file.pdf");
        deThi.setMonHoc("Văn");
        deThi.setSoLuongCauHoi(40);

        boolean result = deThiDAO.updatDeThi(deThi);
        assertTrue(result, "Cập nhật đề thi không thành công!");

        // Kiểm tra dữ liệu sau khi cập nhật
        DeThi updatedDeThi = deThiDAO.getDeThi("DT001");
        assertNotNull(updatedDeThi, "Không tìm thấy đề thi sau khi cập nhật!");
        assertEquals("updated/link/to/file.pdf", updatedDeThi.getLinkFile());
        assertEquals("Văn", updatedDeThi.getMonHoc());
        assertEquals(40, updatedDeThi.getSoLuongCauHoi());
    }

    @Test
    @Order(5)
    void testDeleteDeThi_Success() {
        // Xóa đề thi
        boolean result = deThiDAO.deleteDeThi("DT001");
        assertTrue(result, "Xóa đề thi không thành công!");

        // Kiểm tra trạng thái đề thi sau khi xóa
        DeThi deletedDeThi = deThiDAO.getDeThi("DT001");
        assertNotNull(deletedDeThi, "Đề thi đã bị xóa khỏi cơ sở dữ liệu!");
        assertEquals("disable", deletedDeThi.getTrangThai(), "Trạng thái đề thi không được cập nhật đúng!");
    }
}
