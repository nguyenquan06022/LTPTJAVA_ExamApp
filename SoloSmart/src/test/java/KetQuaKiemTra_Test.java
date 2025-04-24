import Dao.IKetQuaKiemTra_DAO;
import Entity.BaiKiemTra;
import Entity.KetQuaKiemTra;
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
public class KetQuaKiemTra_Test {

    private EntityManagerFactory emf;
    private EntityManager em;
    private IKetQuaKiemTra_DAO dao = RmiServiceLocator.getKetQuaKiemTraDao();

    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("mssql-pu");
        em = emf.createEntityManager();
    }

    @AfterAll
    public void teardown() {
        if (em != null)
            em.close();
        if (emf != null)
            emf.close();
    }

    @Test
    public void testThemKetQuaKiemTra() throws RemoteException {
        KetQuaKiemTra ketQua = new KetQuaKiemTra();
        ketQua.setMaKetQuaKiemTra(dao.generateMa());
        ketQua.setDiemCaoNhat(true);
        ketQua.setDiemSo((float) 9.5);
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setMaTaiKhoan("TK001");
        BaiKiemTra bkt = new BaiKiemTra();
        bkt.setMaBaiKiemTra("BKT001");
        ketQua.setBaiKiemTra(bkt);
        ketQua.setTaiKhoan(taikhoan);

        boolean result = dao.themKetQuaKiemTra(ketQua);
        assertTrue(result, "Thêm kết quả kiểm tra thất bại");
    }

    @Test
    public void testGetDanhSachKetQuaKiemTra() throws RemoteException {
        ArrayList<KetQuaKiemTra> danhSach = dao.getDanhSachKetQuaKiemTra("TK24022025173708720", "BKT24022025173716455");
        assertNotNull(danhSach, "Danh sách kết quả kiểm tra không được null");
        assertFalse(danhSach.isEmpty(), "Danh sách kết quả kiểm tra rỗng");
    }

    @Test
    public void testGetKetQuaKiemTra() throws RemoteException {
        KetQuaKiemTra kq= dao.getKetQuaKiemTra("KQKT24022025173720634");
        assertNotNull(kq);
        assertEquals("KQKT24022025173720634",kq.getMaKetQuaKiemTra());
    }

    @Test
    public void testUpdateKetQuaKiemTra() throws RemoteException {
        ArrayList<KetQuaKiemTra> danhSach = dao.getDanhSachKetQuaKiemTra("TK001", "BKT001");
        KetQuaKiemTra ketQua = danhSach.get(0);
        assertNotNull(ketQua, "Không tìm thấy kết quả kiểm tra với ID: ");
        ;

        ketQua.setDiemSo(5F);
        boolean result = dao.updateKetQuaKiemTra(ketQua);
        assertTrue(result, "Cập nhật kết quả kiểm tra thất bại");
        danhSach = dao.getDanhSachKetQuaKiemTra("TK001", "BKT001");
        KetQuaKiemTra updatedKetQua = danhSach.get(0);
        assertEquals(5, updatedKetQua.getDiemSo(), "Điểm số không được cập nhật đúng");
    }
}
