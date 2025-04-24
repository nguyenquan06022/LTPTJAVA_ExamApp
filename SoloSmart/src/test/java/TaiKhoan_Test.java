
import Dao.ITaiKhoan_DAO;
import Dao.TaiKhoan_DAO;
import Entity.TaiKhoan;
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
public class TaiKhoan_Test {
    private EntityManagerFactory emf;
    private EntityManager em;
    private ITaiKhoan_DAO taiKhoanDAO = RmiServiceLocator.getTaiKhoanDao();

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
    void testAddTaiKhoan_Success() throws RemoteException {
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setMaTaiKhoan("TK001");
        taiKhoan.setMatKhau("123456");
        taiKhoan.setTenTaiKhoan("Nguyen Van A");
        taiKhoan.setTrangThai("enable");
        taiKhoan.setVaiTro("SV");

        boolean result = taiKhoanDAO.addTaiKhoan(taiKhoan);
        assertTrue(result, "Thêm tài khoản không thành công!");

        // Kiểm tra lại dữ liệu
        TaiKhoan retrieved = taiKhoanDAO.getTaiKhoan("TK001");
        assertNotNull(retrieved, "Không tìm thấy tài khoản sau khi thêm!");
        assertEquals("TK001", retrieved.getMaTaiKhoan());
        assertEquals("Nguyen Van A", retrieved.getTenTaiKhoan());
    }

    @Test
    @Order(2)
    void testAddDanhSachTaiKhoan_Success() throws RemoteException {
        ArrayList<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();

        TaiKhoan tk1 = new TaiKhoan();
        tk1.setMaTaiKhoan("TK002");
        tk1.setMatKhau("123456");
        tk1.setTenTaiKhoan("Nguyen Van B");
        tk1.setTrangThai("enable");
        tk1.setVaiTro("SV");
        danhSachTaiKhoan.add(tk1);

        TaiKhoan tk2 = new TaiKhoan();
        tk2.setMaTaiKhoan("TK003");
        tk2.setMatKhau("123456");
        tk2.setTenTaiKhoan("Nguyen Van C");
        tk2.setTrangThai("enable");
        tk2.setVaiTro("SV");
        danhSachTaiKhoan.add(tk2);

        TaiKhoan tk3 = new TaiKhoan();
        tk3.setMaTaiKhoan("TK004");
        tk3.setMatKhau("123456");
        tk3.setTenTaiKhoan("Le Thi D");
        tk3.setTrangThai("enable");
        tk3.setVaiTro("GV");
        danhSachTaiKhoan.add(tk3);

        for (TaiKhoan tk : danhSachTaiKhoan) {
            assertTrue(taiKhoanDAO.addTaiKhoan(tk), "Thêm tài khoản " + tk.getMaTaiKhoan() + " không thành công!");
        }

        // Kiểm tra lại
        assertNotNull(taiKhoanDAO.getTaiKhoan("TK002"));
        assertNotNull(taiKhoanDAO.getTaiKhoan("TK003"));
        assertNotNull(taiKhoanDAO.getTaiKhoan("TK004"));
    }

    @Test
    @Order(3)
    void testUpdateTaiKhoan_Success() throws RemoteException {
        TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoan("TK002");
        assertNotNull(taiKhoan, "Không tìm thấy tài khoản để cập nhật!");

        taiKhoan.setTenTaiKhoan("Nguyễn Lâm Anh Thư");
        boolean result = taiKhoanDAO.updateTaiKhoan(taiKhoan);
        assertTrue(result, "Cập nhật tài khoản không thành công!");

        // Kiểm tra lại
        TaiKhoan updatedTaiKhoan = taiKhoanDAO.getTaiKhoan("TK002");
        assertNotNull(updatedTaiKhoan, "Không tìm thấy tài khoản sau khi cập nhật!");
        assertEquals("Nguyễn Lâm Anh Thư", updatedTaiKhoan.getTenTaiKhoan());
    }
    @Test
    @Order(4)
    void testDeleteTaiKhoan_Success() throws RemoteException {
        boolean result = taiKhoanDAO.deleteTaiKhoan("TK003");
        assertTrue(result, "Xóa tài khoản không thành công!");

        // Kiểm tra lại trạng thái
        TaiKhoan deletedTaiKhoan = taiKhoanDAO.getTaiKhoan("TK003");
        assertNotNull(deletedTaiKhoan, "Tài khoản đã bị xóa khỏi cơ sở dữ liệu!");
        assertEquals("disable", deletedTaiKhoan.getTrangThai(), "Trạng thái tài khoản không đúng sau khi xóa!");
    }
}
