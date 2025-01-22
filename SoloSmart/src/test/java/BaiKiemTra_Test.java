import Dao.BaiKiemTra_DAO;
import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.LopHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaiKiemTra_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private BaiKiemTra_DAO baiKiemTraDAO;

    @BeforeAll
    void setUp() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
        baiKiemTraDAO = new BaiKiemTra_DAO(em);
    }

    @AfterAll
    void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    @Order(1)
    void testAddBaiKiemTra_Success() {
        // Tạo đối tượng DeThi làm khóa ngoại
        DeThi deThi = new DeThi();
        deThi.setMaDeThi("DT001"); // Đảm bảo mã đề thi này tồn tại trong cơ sở dữ liệu

        // Tạo đối tượng LopHoc làm khóa ngoại
        LopHoc lopHoc = new LopHoc();
        lopHoc.setMaLop("DHKTMP18A"); // Đảm bảo mã lớp này tồn tại trong cơ sở dữ liệu

        // Tạo đối tượng BaiKiemTra
        BaiKiemTra baiKiemTra = new BaiKiemTra();
        baiKiemTra.setMaBaiKiemTra("BKT001");
        baiKiemTra.setChoPhepXemDiem(true);
        baiKiemTra.setChoPhepXemLai(true);
        baiKiemTra.setHeSo(10);
        baiKiemTra.setHienThiDapAn(true);
        baiKiemTra.setSoLanLamBai(3);
        baiKiemTra.setThangDiem(10);
        baiKiemTra.setThoiGianBatDau(LocalDateTime.now());
        baiKiemTra.setThoiGianKetThuc(LocalDateTime.now().plusHours(2));
        baiKiemTra.setThoiGianLamBai(90);
        baiKiemTra.setTrangThai("usable");
        baiKiemTra.setDeThi(deThi);
        baiKiemTra.setLopHoc(lopHoc);

        boolean result = baiKiemTraDAO.themBaiKiemTra(baiKiemTra);
        assertTrue(result, "Thêm bài kiểm tra không thành công!");

        // Kiểm tra dữ liệu sau khi thêm
        BaiKiemTra retrieved = baiKiemTraDAO.getBaiKiemTra("BKT001");
        assertNotNull(retrieved, "Không tìm thấy bài kiểm tra sau khi thêm!");
        assertEquals("BKT001", retrieved.getMaBaiKiemTra());
        assertTrue(retrieved.isChoPhepXemDiem());
    }

    @Test
    @Order(2)
    void testGetBaiKiemTra_Success() {
        // Lấy thông tin bài kiểm tra đã thêm
        BaiKiemTra baiKiemTra = baiKiemTraDAO.getBaiKiemTra("BKT001");
        assertNotNull(baiKiemTra, "Không tìm thấy bài kiểm tra!");
        assertEquals("BKT001", baiKiemTra.getMaBaiKiemTra());
//        assertEquals("usable", baiKiemTra.getTrangThai());
    }

    @Test
    @Order(3)
    void testGetDanhSachBaiKiemTra_Success() {
        // Lấy danh sách bài kiểm tra
        ArrayList<BaiKiemTra> danhSachBaiKiemTra = baiKiemTraDAO.getDanhSachBaiKiemTra();
        assertNotNull(danhSachBaiKiemTra, "Danh sách bài kiểm tra trả về null!");
        assertFalse(danhSachBaiKiemTra.isEmpty(), "Danh sách bài kiểm tra trống!");

        // Kiểm tra một bài kiểm tra trong danh sách
        BaiKiemTra baiKiemTra = danhSachBaiKiemTra.stream()
                .filter(bkt -> "BKT001".equals(bkt.getMaBaiKiemTra()))
                .findFirst()
                .orElse(null);
        assertNotNull(baiKiemTra, "Không tìm thấy bài kiểm tra với mã BKT001 trong danh sách!");
    }

    @Test
    @Order(4)
    void testUpdateBaiKiemTra_Success() {
        // Lấy bài kiểm tra cần cập nhật
        BaiKiemTra baiKiemTra = baiKiemTraDAO.getBaiKiemTra("BKT001");
        assertNotNull(baiKiemTra, "Không tìm thấy bài kiểm tra để cập nhật!");

        // Cập nhật thông tin
        baiKiemTra.setThangDiem(15);
        baiKiemTra.setMatKhauBaiKiemTra("j97");

        boolean result = baiKiemTraDAO.updateBaiKiemTra(baiKiemTra);
        assertTrue(result, "Cập nhật bài kiểm tra không thành công!");

        // Kiểm tra dữ liệu sau khi cập nhật
        BaiKiemTra updatedBaiKiemTra = baiKiemTraDAO.getBaiKiemTra("BKT001");
        assertNotNull(updatedBaiKiemTra, "Không tìm thấy bài kiểm tra sau khi cập nhật!");
        assertEquals(15, updatedBaiKiemTra.getThangDiem());
        assertEquals("j97", updatedBaiKiemTra.getMatKhauBaiKiemTra());
    }

    @Test
    @Order(5)
    void testDeleteBaiKiemTra_Success() {
        // Xóa bài kiểm tra
        boolean result = baiKiemTraDAO.deleteBaiKiemTra("BKT001");
        assertTrue(result, "Xóa bài kiểm tra không thành công!");

        // Kiểm tra trạng thái bài kiểm tra sau khi xóa
        BaiKiemTra deletedBaiKiemTra = baiKiemTraDAO.getBaiKiemTra("BKT001");
        assertNotNull(deletedBaiKiemTra, "Bài kiểm tra đã bị xóa khỏi cơ sở dữ liệu!");
        assertEquals("disable", deletedBaiKiemTra.getTrangThai(), "Trạng thái bài kiểm tra không được cập nhật đúng!");
    }
}
