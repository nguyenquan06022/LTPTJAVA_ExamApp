import Dao.LopHoc_DAO;
import Entity.LopHoc;
import Entity.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LopHoc_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private LopHoc_DAO lopHocDAO;

    @BeforeAll
    void setUp() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        lopHocDAO = new LopHoc_DAO(em);
    }

    @AfterAll
    void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testAddLopHoc_Success() {
        // Tạo đối tượng MonHoc làm khóa ngoại
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMonHoc("MH001"); // Đảm bảo môn học này tồn tại trong cơ sở dữ liệu

        // Tạo đối tượng LopHoc
        LopHoc lopHoc = new LopHoc();
        lopHoc.setMaLop("DHKTMP18A");
        lopHoc.setNamHoc("2025");
        lopHoc.setSiSo(30);
        lopHoc.setTenLop("Đại học Kỹ Thuật Phần Mềm 18A");
        lopHoc.setTrangThai("enable");
        lopHoc.setMonHoc(monHoc);

        boolean result = lopHocDAO.addLopHoc(lopHoc);
        assertTrue(result, "Thêm lớp học không thành công!");

        // Kiểm tra dữ liệu trong cơ sở dữ liệu
        LopHoc retrieved = lopHocDAO.getLopHoc("DHKTMP18A");
        assertNotNull(retrieved, "Không tìm thấy lớp học sau khi thêm!");
        assertEquals("DHKTMP18A", retrieved.getMaLop());
        assertEquals("2025", retrieved.getNamHoc());
        assertEquals(30, retrieved.getSiSo());
        assertEquals("Đại học Kỹ Thuật Phần Mềm 18A", retrieved.getTenLop());
    }

    @Test
    @Order(2)
    void testUpdateLopHoc_Success() {
        // Lấy lớp học đã thêm
        LopHoc lopHoc = lopHocDAO.getLopHoc("DHKTMP18A");
        assertNotNull(lopHoc, "Không tìm thấy lớp học để cập nhật!");

        // Cập nhật thông tin lớp học
        lopHoc.setNamHoc("2026");
        lopHoc.setSiSo(35);
        lopHoc.setTenLop("Đại học Kỹ Thuật Phần Mềm 18B");

        boolean result = lopHocDAO.updateLopHoc(lopHoc);
        assertTrue(result, "Cập nhật lớp học không thành công!");

        // Kiểm tra dữ liệu sau khi cập nhật
        LopHoc updatedLopHoc = lopHocDAO.getLopHoc("DHKTMP18A");
        assertNotNull(updatedLopHoc, "Không tìm thấy lớp học sau khi cập nhật!");
        assertEquals("2026", updatedLopHoc.getNamHoc());
        assertEquals(35, updatedLopHoc.getSiSo());
        assertEquals("Đại học Kỹ Thuật Phần Mềm 18B", updatedLopHoc.getTenLop());
    }

    @Test
    @Order(3)
    void testDeleteLopHoc_Success() {
        // Xóa lớp học
        boolean result = lopHocDAO.deleteLopHoc("DHKTMP18A");
        assertTrue(result, "Xóa lớp học không thành công!");

        // Kiểm tra trạng thái của lớp học sau khi xóa
        LopHoc deletedLopHoc = lopHocDAO.getLopHoc("DHKTMP18A");
        assertNotNull(deletedLopHoc, "Lớp học đã bị xóa khỏi cơ sở dữ liệu!");
        assertEquals("disable", deletedLopHoc.getTrangThai(), "Trạng thái lớp học không đúng sau khi xóa!");
    }
}
