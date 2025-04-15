import Dao.*;
import Entity.*;
import DB.CreateDB;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class test {
        private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        private static EntityManager em;

        public static void main(String[] args) {
                LocalDateTime now = LocalDateTime.now();
                em = CreateDB.createDB();
                // KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
                // System.out.println(ketQuaKiemTraDao.getDsDiemTheoBaiKiemTra("LH08042025145310626","BKT08042025145318159"));

                // BaiKiemTra_DAO baiKiemTraDao = new BaiKiemTra_DAO(em);
                // for (Map.Entry<TaiKhoan,Float> entry :
                // baiKiemTraDao.getDsTaiKhoanThamGiaKiemTraVaDiemSo("BKT08042025145318159").entrySet())
                // {
                // System.out.println(entry.getKey() + " " + entry.getValue());
                // }

                // KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
                // System.out.println(ketQuaKiemTraDao.tinhDiemChoSinhVien("TK10042025175716521","BKT10042025175726138"));

                // DeThi_DAO deThiDao = new DeThi_DAO(em);
                // System.out.println(deThiDao.filterDeThiCuaGiaoVien("","","","TK14042025101852237"));

                // TaiKhoan_DAO taiKhoanDao = new TaiKhoan_DAO(em);
                // taiKhoanDao.importTaiKhoanFromExcel("C:\\Users\\Admin\\Desktop\\taikhoan.xlsx");
                //
                // taiKhoanDao.exportDsTaiKhoanVuaThemToExcel("C:\\Users\\Admin\\Desktop\\export.xlsx");
                LopHoc_DAO lopHocDao = new LopHoc_DAO(em);
                System.out.println(lopHocDao.filterLopHocCuaSinhVien("","","","","TK14042025233131460"));
        }
}
