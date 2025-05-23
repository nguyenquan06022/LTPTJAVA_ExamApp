import Dao.IKetQuaHocTap_DAO;
import Dao.KetQuaHocTap_DAO;
import Entity.KetQuaHocTap;
import Entity.LopHoc;
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
public class KetQuaHocTap_Test {
    private EntityManagerFactory emf;
    private EntityManager em;
    private IKetQuaHocTap_DAO dao = RmiServiceLocator.getKetQuaHocTapDao();

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
    void testThemKetQuaHocTap() throws RemoteException {
        KetQuaHocTap ketQuaHocTap = new KetQuaHocTap();
        ketQuaHocTap.setDiemThuongKy(10f);
        ketQuaHocTap.setDiemGiuaKy(9f);
        ketQuaHocTap.setDiemCuoiKy((float)9.5);
        TaiKhoan tk= new TaiKhoan();
        tk.setMaTaiKhoan("TK23012025025419647");
        LopHoc lop= new LopHoc();
        lop.setMaLop("LH23012025025420273");
        ketQuaHocTap.setLopHoc(lop);
        ketQuaHocTap.setTaiKhoan(tk);
        boolean result = dao.themKetQuaHocTap(ketQuaHocTap);
        assertTrue(result, "Thêm kết quả học tập không thành công.");
    }

    @Test
    void testGetKetQuaHocTap() throws RemoteException {
        String maTaiKhoan = "TK001";
        String maLop = "DHKTMP18A";

        KetQuaHocTap ketQuaHocTap = dao.getKetQuaHocTap(maTaiKhoan, maLop);
        assertNotNull(ketQuaHocTap, "Kết quả học tập không được null.");
        assertEquals(maTaiKhoan, ketQuaHocTap.getTaiKhoan().getMaTaiKhoan(), "Mã tài khoản không đúng.");
        assertEquals(maLop, ketQuaHocTap.getLopHoc().getMaLop(), "Mã lớp không đúng.");
    }

    @Test
    void testCapNhatKetQuaHocTap() throws RemoteException {
        KetQuaHocTap ketQuaHocTap = dao.getKetQuaHocTap("TK001","DHKTMP18A");
        ketQuaHocTap.setDiemTBMon((float)4.0);


        boolean result = dao.capNhatKetQuaHocTap(ketQuaHocTap);
        assertTrue(result, "Cập nhật kết quả học tập không thành công.");
        KetQuaHocTap resultKQ = dao.getKetQuaHocTap("TK001","DHKTMP18A");
        assertEquals(4f,resultKQ.getDiemTBMon());
    }

    @Test
    void testGetDanhSachKetQuaHocTap() throws RemoteException {
        String maLop = "DHKTMP18A";

        ArrayList<KetQuaHocTap> danhSachKetQua = dao.getDanhSachKetQuaHocTap(maLop);
        assertNotNull(danhSachKetQua, "Danh sách kết quả học tập không được null.");
        assertFalse(danhSachKetQua.isEmpty(), "Danh sách kết quả học tập bị rỗng.");
    }
    @Test
    void testUpdate() throws RemoteException {
        KetQuaHocTap kq= dao.getKetQuaHocTap("TK23012025025419647","LH23012025025420273");
        kq.setDiemTBMon(10f);
        boolean rs= dao.capNhatKetQuaHocTap(kq);
        assertTrue(rs);
        kq= dao.getKetQuaHocTap("TK23012025025419647","LH23012025025420273");
        assertEquals(10,kq.getDiemTBMon());
    }
    @AfterAll
    void cleanup() {
        // Clean up test data if necessary
    }
}
