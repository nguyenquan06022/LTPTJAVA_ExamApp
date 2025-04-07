import Dao.*;
import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.MonHoc;
import DB.CreateDB;
import Entity.KetQuaHocTap;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class test {
        private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        private static EntityManager em;

        public static void main(String[] args) {
                LocalDateTime now = LocalDateTime.now();
                em = CreateDB.createDB();
                // System.out.println("DT"+df.format(now));
                // BaiKiemTra bkt= new BaiKiemTra();
                // LocalDateTime time= LocalDateTime.of(2025,1,23,10,30);
                BaiKiemTra_DAO dao = new BaiKiemTra_DAO(em);
                // System.out.println("EntityManager: " + dao);
                //
//                ArrayList<BaiKiemTra> dsMon = dao.getDanhSachBaiKiemTraTheoLop("LH25032025015344839");
                //
//                if (dsMon != null)
//                        dsMon.forEach(x -> System.out.println(x));
                // TaiKhoan_DAO taiKhoanDao = new TaiKhoan_DAO(em);
                // System.out.println(taiKhoanDao.getTaiKhoan("TK07042025153354984"));

                // taiKhoanDao.importTaiKhoanFromExcel("C:\\Users\\Admin\\Desktop\\taikhoan.xlsx");
//                 KetQuaHocTap_DAO ketQuaHocTap_dao = new KetQuaHocTap_DAO(em);
                // ketQuaHocTap_dao.importDanhSachTaiKhoanVaoLopHoc("C:\\Users\\Admin\\Desktop\\ds_tk_them_vao_lop.xlsx","LH07042025163902037");
                // LopHoc_DAO lopHoc_dao = new LopHoc_DAO(em);
                // System.out.println(lopHoc_dao.getDanhSachLopHocTheoTenLopHocCuaSinhVien("TK07042025163900406","Bachelor
                // of Psychology"));
//                 System.out.println(ketQuaHocTap_dao.getDanhSachKetQuaHocTap("LH07042025222803640"));
                 BaiKiemTra_DAO baiKiemTraDao = new BaiKiemTra_DAO(em);
                 baiKiemTraDao.getDanhSachBaiKiemTraCuaGiaoVienTheoLop("TK07042025222803300", "LH07042025222803640").forEach(x->System.out.println(x));
        }
}
