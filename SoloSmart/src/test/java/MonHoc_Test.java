import Dao.IMonHoc_DAO;
import Dao.MonHoc_DAO;
import Entity.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import service.RmiServiceLocator;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonHoc_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private IMonHoc_DAO monHocDAO = RmiServiceLocator.getMonHocDao();

    @BeforeAll
    void setUp() {
        // Tạo EntityManagerFactory từ persistence unit "test-pu"
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
    void testAddMonHoc_Success() throws RemoteException {
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMonHoc("MH001");
        monHoc.setTenMonHoc("Toán học");
        monHoc.setTrangThai("enable");

        boolean result = monHocDAO.addMonHoc(monHoc);
        assertTrue(result, "Thêm môn học không thành công!");

        // Kiểm tra lại dữ liệu trong cơ sở dữ liệu
        MonHoc retrieved = monHocDAO.getMonHoc("MH001");
        assertNotNull(retrieved, "Môn học không tồn tại trong cơ sở dữ liệu!");
        assertEquals("MH001", retrieved.getMaMonHoc());
        assertEquals("Toán học", retrieved.getTenMonHoc());
        assertEquals("enable", retrieved.getTrangThai());
    }

    @Test
    @Order(2)
    void testAddMonHoc_DuplicateId() throws RemoteException {
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMonHoc("MH001"); // ID đã tồn tại
        monHoc.setTenMonHoc("Lý học");
        monHoc.setTrangThai("enabled");

        boolean result = monHocDAO.addMonHoc(monHoc);
        assertFalse(result, "Hệ thống không ngăn chặn việc thêm trùng ID!");
    }

    @Test
    @Order(3)
    void testUpdateMonHoc_Success() throws RemoteException {
        // Lấy môn học hiện tại từ cơ sở dữ liệu
        MonHoc monHoc = monHocDAO.getMonHoc("MH001");
        assertNotNull(monHoc, "Không tìm thấy môn học để cập nhật!");

        // Cập nhật thông tin môn học
        monHoc.setTenMonHoc("Vật lý");
        boolean result = monHocDAO.updateMonHoc(monHoc);
        assertTrue(result, "Cập nhật môn học không thành công!");

        // Kiểm tra lại dữ liệu sau khi cập nhật
        MonHoc updatedMonHoc = monHocDAO.getMonHoc("MH001");
        assertNotNull(updatedMonHoc, "Môn học không tồn tại sau khi cập nhật!");
        assertEquals("Vật lý", updatedMonHoc.getTenMonHoc(), "Tên môn học không được cập nhật chính xác!");
    }
    @Test
    @Order(4)
    void testDeleteMonHoc_Success() throws RemoteException {
        // Lấy môn học hiện tại từ cơ sở dữ liệu
        MonHoc monHoc = monHocDAO.getMonHoc("MH001");
        assertNotNull(monHoc, "Không tìm thấy môn học để cập nhật!");

        // Cập nhật thông tin môn học
        monHoc.setTenMonHoc("Vật lý");
        boolean result = monHocDAO.deleteMonHoc(monHoc.getMaMonHoc());
        assertTrue(result, "Cập nhật môn học không thành công!");

        // Kiểm tra lại dữ liệu sau khi cập nhật
        MonHoc updatedMonHoc = monHocDAO.getMonHoc("MH001");
        assertNotNull(updatedMonHoc, "Môn học không tồn tại sau khi cập nhật!");
        assertEquals("disable", updatedMonHoc.getTrangThai(), "Tên môn học không được cập nhật chính xác!");
    }

}
