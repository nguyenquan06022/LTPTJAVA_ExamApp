import Dao.IBaiKiemTra_DAO;
import Dao.IDsCauTraLoi_DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import service.RmiServiceLocator;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DSCauTraLoi_Test {
    private EntityManagerFactory emf;
    private EntityManager em;
    private IDsCauTraLoi_DAO dao = RmiServiceLocator.getDsCauTraLoiDao();
    private IBaiKiemTra_DAO bkt_dao= RmiServiceLocator.getBaiKiemTraDao();
    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
    }

    @AfterAll
    public void teardown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testThemCauTraLoi() throws RemoteException {
        String maKetQuaKiemTra = bkt_dao.getDanhSachBaiKiemTraTheoLop("DHKTMP18A").get(0).getMaBaiKiemTra();
        String cauTraLoi = "A";

        boolean result = dao.themCauTraLoi(maKetQuaKiemTra, cauTraLoi);
        assertTrue(result, "Thêm câu trả lời không thành công.");
    }

    @Test
    @Order(2)
    void testUpdateCauTraLoi() throws RemoteException {
        String maKetQuaKiemTra = bkt_dao.getDanhSachBaiKiemTraTheoLop("DHKTMP18A").get(0).getMaBaiKiemTra();
        String cauTraLoi = "A";
        String cauTraLoiMoi = "1.A";

        boolean result = dao.updateCauTraLoi(maKetQuaKiemTra, cauTraLoi, cauTraLoiMoi);
        assertTrue(result, "Cập nhật câu trả lời không thành công.");
    }


    @Test
    @Order(3)
    void testGetDSCauTraLoi() throws RemoteException {
        String maKetQuaKiemTra = bkt_dao.getDanhSachBaiKiemTraTheoLop("DHKTMP18A").get(0).getMaBaiKiemTra();


        ArrayList<String> dsCauTraLoi = dao.getDSCauTraLoi(maKetQuaKiemTra);
        assertNotNull(dsCauTraLoi, "Danh sách câu trả lời không được null.");
        assertFalse(dsCauTraLoi.isEmpty(), "Danh sách câu trả lời bị rỗng.");
        assertTrue(dsCauTraLoi.contains("1.A"), "Danh sách không chứa câu trả lời mong muốn.");
    }

    @AfterAll
    void cleanup() {
        // Xóa dữ liệu test nếu cần thiết.
    }
}
