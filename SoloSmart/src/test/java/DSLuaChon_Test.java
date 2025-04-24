import Dao.DsLuaChon_DAO;
import Dao.IDsLuaChon_DAO;
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
public class DSLuaChon_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private IDsLuaChon_DAO dsLuaChonDAO = RmiServiceLocator.getDsLuaChonDao();

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
    void testThemLuaChon() throws RemoteException {
        String maCauHoi = "CH001";

        // Thêm 4 lựa chọn
        boolean isAdded1 = dsLuaChonDAO.themLuaChon(maCauHoi, "Đáp án 1", true);
        boolean isAdded2 = dsLuaChonDAO.themLuaChon(maCauHoi, "Đáp án 2", false);
        boolean isAdded3 = dsLuaChonDAO.themLuaChon(maCauHoi, "Đáp án 3", false);
        boolean isAdded4 = dsLuaChonDAO.themLuaChon(maCauHoi, "Đáp án 4", false);

        assertTrue(isAdded1, "Không thêm được Đáp án 1!");
        assertTrue(isAdded2, "Không thêm được Đáp án 2!");
        assertTrue(isAdded3, "Không thêm được Đáp án 3!");
        assertTrue(isAdded4, "Không thêm được Đáp án 4!");
    }

    @Test
    @Order(2)
    void testGetDSLuaChon() throws RemoteException {
        String maCauHoi = "CH001";

        // Lấy danh sách lựa chọn
        ArrayList<String> dsLuaChon = dsLuaChonDAO.getDSLuaChon(maCauHoi);
        assertNotNull(dsLuaChon, "Danh sách lựa chọn trả về null!");
        assertEquals(4, dsLuaChon.size(), "Danh sách lựa chọn không đúng số lượng!");
        assertTrue(dsLuaChon.contains("Đáp án 1"), "Không tìm thấy Đáp án 1!");
        assertTrue(dsLuaChon.contains("Đáp án 2"), "Không tìm thấy Đáp án 2!");
        assertTrue(dsLuaChon.contains("Đáp án 3"), "Không tìm thấy Đáp án 3!");
        assertTrue(dsLuaChon.contains("Đáp án 4"), "Không tìm thấy Đáp án 4!");
    }

    @Test
    @Order(3)
    void testCapNhatLuaChon() throws RemoteException {
        String maCauHoi = "CH001";
        String luaChonCu = "Đáp án 1";
        String luaChonMoi = "Đáp án A";

        // Cập nhật lựa chọn
        boolean isUpdated = dsLuaChonDAO.capNhatLuaChon(maCauHoi, luaChonCu, luaChonMoi,true);

        assertTrue(isUpdated, "Không cập nhật được lựa chọn!");

        // Kiểm tra lại danh sách sau khi cập nhật
        ArrayList<String> dsLuaChon = dsLuaChonDAO.getDSLuaChon(maCauHoi);

        assertNotNull(dsLuaChon, "Danh sách lựa chọn sau khi cập nhật trả về null!");
        assertTrue(dsLuaChon.contains(luaChonMoi), "Không tìm thấy lựa chọn mới sau khi cập nhật!");
        assertFalse(dsLuaChon.contains(luaChonCu), "Vẫn tồn tại lựa chọn cũ sau khi cập nhật!");
    }
    @Test
    @Order(4) // Đảm bảo thứ tự thực thi nếu có sử dụng @TestMethodOrder
    void testXoaLuaChon() throws RemoteException {
        String maCauHoi = "CH001"; // Mã câu hỏi giả lập
        String luaChon = "Đáp án 4"; // Lựa chọn cần xóa



        // Kiểm tra xóa lựa chọn
        boolean isDeleted = dsLuaChonDAO.xoaLuaChon(maCauHoi, luaChon);
        assertTrue(isDeleted, "Xóa thành công!");

        // Lấy lại danh sách các lựa chọn sau khi xóa
        ArrayList<String> dsLuaChon = dsLuaChonDAO.getDSLuaChon(maCauHoi);
        assertEquals(3,dsLuaChon.size());
    }

}
