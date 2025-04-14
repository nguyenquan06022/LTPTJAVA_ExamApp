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
//                KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
//                System.out.println(ketQuaKiemTraDao.getDsDiemTheoBaiKiemTra("LH08042025145310626","BKT08042025145318159"));

//                BaiKiemTra_DAO baiKiemTraDao = new BaiKiemTra_DAO(em);
//                for (Map.Entry<TaiKhoan,Float> entry : baiKiemTraDao.getDsTaiKhoanThamGiaKiemTraVaDiemSo("BKT08042025145318159").entrySet()) {
//                        System.out.println(entry.getKey() + " " + entry.getValue());
//                }

//                KetQuaKiemTra_DAO ketQuaKiemTraDao = new KetQuaKiemTra_DAO(em);
//                System.out.println(ketQuaKiemTraDao.tinhDiemChoSinhVien("TK10042025175716521","BKT10042025175726138"));

                DeThi_DAO deThiDao = new DeThi_DAO(em);
                System.out.println(deThiDao.getDanhSachDeThiCuaGiaoVien("TK10042025175717692"));
        }
}
