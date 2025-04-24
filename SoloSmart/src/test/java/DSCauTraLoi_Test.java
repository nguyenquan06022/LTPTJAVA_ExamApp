import Dao.BaiKiemTra_DAO;
import Dao.DsCauTraLoi_DAO;
import Dao.IDsCauTraLoi_DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DSCauTraLoi_Test {
    private EntityManagerFactory emf;
    private EntityManager em;
    private IDsCauTraLoi_DAO dao;
    private BaiKiemTra_DAO bkt_dao=new BaiKiemTra_DAO();
    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        dao = new DsCauTraLoi_DAO(em);
    }

    @AfterAll
    public void teardown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testThemCauTraLoi() {
        String maKetQuaKiemTra = bkt_dao.getDanhSachBaiKiemTraTheoLop("DHKTMP18A").get(0).getMaBaiKiemTra();
        String cauTraLoi = "A";

        boolean result = dao.themCauTraLoi(maKetQuaKiemTra, cauTraLoi);
        assertTrue(result, "Thêm câu trả lời không thành công.");
    }

    @Test
    @Order(2)
    void testUpdateCauTraLoi() {
        String maKetQuaKiemTra = bkt_dao.getDanhSachBaiKiemTraTheoLop("DHKTMP18A").get(0).getMaBaiKiemTra();
        String cauTraLoi = "A";
        String cauTraLoiMoi = "1.A";

        boolean result = dao.updateCauTraLoi(maKetQuaKiemTra, cauTraLoi, cauTraLoiMoi);
        assertTrue(result, "Cập nhật câu trả lời không thành công.");
    }


    @Test
    @Order(3)
    void testGetDSCauTraLoi() {
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
