import Dao.CauHoi_DAO;
import Dao.ICauHoi_DAO;
import Entity.CauHoi;
import Entity.DeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import service.RmiServiceLocator;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CauHoi_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ICauHoi_DAO cauHoiDAO = RmiServiceLocator.getCauHoiDao();

    @BeforeAll
    void setUp() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
    }

    @AfterAll
    void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testAddCauHoi_Success() throws RemoteException {
        // Tạo đối tượng DeThi làm khóa ngoại
        DeThi deThi = new DeThi();
        deThi.setMaDeThi("DT001"); // Đảm bảo mã đề thi này tồn tại trong cơ sở dữ liệu

        // Tạo đối tượng CauHoi
        CauHoi cauHoi = new CauHoi();
        cauHoi.setMaCauHoi("CH001");
        cauHoi.setCauHoi("Câu hỏi thử nghiệm?");
        cauHoi.setKieuTraLoi(1); // Ví dụ kiểu trả lời là 1
        cauHoi.setLoiGiai("Giải thích cho câu hỏi");
        cauHoi.setMucDo("Thông hiểu");
        cauHoi.setTrangThai("enable");
        cauHoi.setDeThi(deThi);

        boolean result = cauHoiDAO.addCauHoi(cauHoi);
        assertTrue(result, "Thêm câu hỏi không thành công!");

        // Kiểm tra dữ liệu sau khi thêm
        CauHoi retrieved = cauHoiDAO.getCauHoi("CH001");
        assertNotNull(retrieved, "Không tìm thấy câu hỏi sau khi thêm!");
        assertEquals("CH001", retrieved.getMaCauHoi());
        assertEquals("Câu hỏi thử nghiệm?", retrieved.getCauHoi());
    }

    @Test
    @Order(2)
    void testGetCauHoi_Success() throws RemoteException {
        // Lấy thông tin câu hỏi đã thêm
        CauHoi cauHoi = cauHoiDAO.getCauHoi("CH001");
        assertNotNull(cauHoi, "Không tìm thấy câu hỏi!");
        assertEquals("CH001", cauHoi.getMaCauHoi());
        assertEquals("Câu hỏi thử nghiệm?", cauHoi.getCauHoi());
    }

    @Test
    @Order(3)
    void testGetDanhSachCauHoi_Success() throws RemoteException {
        // Lấy danh sách câu hỏi
        ArrayList<CauHoi> danhSachCauHoi = cauHoiDAO.getDanhSachCauHoi();
        assertNotNull(danhSachCauHoi, "Danh sách câu hỏi trả về null!");
        assertFalse(danhSachCauHoi.isEmpty(), "Danh sách câu hỏi trống!");

        // Kiểm tra một câu hỏi trong danh sách
        CauHoi cauHoi = danhSachCauHoi.stream()
                .filter(ch -> "CH001".equals(ch.getMaCauHoi()))
                .findFirst()
                .orElse(null);
        assertNotNull(cauHoi, "Không tìm thấy câu hỏi với mã CH001 trong danh sách!");
    }

    @Test
    @Order(4)
    void testUpdateCauHoi_Success() throws RemoteException {
        // Lấy câu hỏi cần cập nhật
        CauHoi cauHoi = cauHoiDAO.getCauHoi("CH001");
        assertNotNull(cauHoi, "Không tìm thấy câu hỏi để cập nhật!");

        // Cập nhật thông tin
        cauHoi.setCauHoi("Câu hỏi đã được cập nhật?");
        cauHoi.setKieuTraLoi(2);

        boolean result = cauHoiDAO.updateCauHoi(cauHoi);
        assertTrue(result, "Cập nhật câu hỏi không thành công!");

        // Kiểm tra dữ liệu sau khi cập nhật
        CauHoi updatedCauHoi = cauHoiDAO.getCauHoi("CH001");
        assertNotNull(updatedCauHoi, "Không tìm thấy câu hỏi sau khi cập nhật!");
        assertEquals("Câu hỏi đã được cập nhật?", updatedCauHoi.getCauHoi());
        assertEquals(2, updatedCauHoi.getKieuTraLoi());
    }

    @Test
    @Order(5)
    void testDeleteCauHoi_Success() throws RemoteException {
        // Xóa câu hỏi
        boolean result = cauHoiDAO.deleteCauHoi("CH001");
        assertTrue(result, "Xóa câu hỏi không thành công!");

        // Kiểm tra trạng thái câu hỏi sau khi xóa
        CauHoi deletedCauHoi = cauHoiDAO.getCauHoi("CH001");
        assertNotNull(deletedCauHoi, "Câu hỏi đã bị xóa khỏi cơ sở dữ liệu!");
        assertEquals("disable", deletedCauHoi.getTrangThai(), "Trạng thái câu hỏi không được cập nhật đúng!");
    }
}
