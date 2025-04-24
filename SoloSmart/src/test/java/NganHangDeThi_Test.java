import Dao.INganHangDeThi_DAO;
import Dao.NganHangDeThi_DAO;
import Entity.MonHoc;
import Entity.NganHangDeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NganHangDeThi_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private INganHangDeThi_DAO nganHangDeThiDAO;

    @BeforeAll
    void setUp() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        nganHangDeThiDAO = new NganHangDeThi_DAO(em);
    }

    @AfterAll
    void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testAddNganHangDeThi_Success() {
        // Tạo đối tượng MonHoc làm khóa ngoại
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMonHoc("MH001"); // Đảm bảo môn học này tồn tại trong cơ sở dữ liệu

        // Tạo đối tượng NganHangDeThi
        NganHangDeThi nganHangDeThi = new NganHangDeThi();
        nganHangDeThi.setMaNganHang("NHDT001");
        nganHangDeThi.setLoaiNganHang(true); // Ví dụ loại ngân hàng là true
        nganHangDeThi.setTenNganHang("Ngân hàng đề thi Vật Lý");
        nganHangDeThi.setMonHoc(monHoc);

        boolean result = nganHangDeThiDAO.addNganHangDeThi(nganHangDeThi);
        assertTrue(result, "Thêm ngân hàng đề thi không thành công!");

        // Kiểm tra dữ liệu trong cơ sở dữ liệu
        NganHangDeThi retrieved = nganHangDeThiDAO.getNganHangDeThi("NHDT001");
        assertNotNull(retrieved, "Không tìm thấy ngân hàng đề thi sau khi thêm!");
        assertEquals("NHDT001", retrieved.getMaNganHang());
        assertEquals("Ngân hàng đề thi Vật Lý", retrieved.getTenNganHang());
        assertTrue(retrieved.isLoaiNganHang(), "Loại ngân hàng không đúng!");
        assertEquals("MH001", retrieved.getMonHoc().getMaMonHoc());
    }

    @Test
    @Order(2)
    void testUpdateNganHangDeThi_Success() {
        // Lấy ngân hàng đề thi đã thêm
        NganHangDeThi nganHangDeThi = nganHangDeThiDAO.getNganHangDeThi("NHDT001");
        assertNotNull(nganHangDeThi, "Không tìm thấy ngân hàng đề thi để cập nhật!");

        // Cập nhật thông tin ngân hàng đề thi
        nganHangDeThi.setLoaiNganHang(false); // Cập nhật loại ngân hàng
        nganHangDeThi.setTenNganHang("Ngân hàng đề thi Vật Lý - Cập Nhật");

        boolean result = nganHangDeThiDAO.updateNganHangDeThi(nganHangDeThi);
        assertTrue(result, "Cập nhật ngân hàng đề thi không thành công!");

        // Kiểm tra dữ liệu sau khi cập nhật
        NganHangDeThi updatedNganHangDeThi = nganHangDeThiDAO.getNganHangDeThi("NHDT001");
        assertNotNull(updatedNganHangDeThi, "Không tìm thấy ngân hàng đề thi sau khi cập nhật!");
        assertEquals("Ngân hàng đề thi Vật Lý - Cập Nhật", updatedNganHangDeThi.getTenNganHang());
        assertFalse(updatedNganHangDeThi.isLoaiNganHang(), "Loại ngân hàng không được cập nhật đúng!");
    }

    @Test
    @Order(3)
    void testGetNganHang(){
        NganHangDeThi nganHangDeThi= nganHangDeThiDAO.getNganHangDeThi("NHDT001");
        assertNotNull(nganHangDeThi);
    }
}
