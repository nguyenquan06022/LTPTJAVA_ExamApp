import Dao.BaiKiemTra_DAO;
import Dao.DeThi_DAO;
import Dao.MonHoc_DAO;
import Entity.BaiKiemTra;
import Entity.DeThi;
import Entity.MonHoc;
import DB.CreateDB;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class test {
    private static DateTimeFormatter df= DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
    private static EntityManager em;
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        em=CreateDB.createDB();
        System.out.println("DT"+df.format(now));
        BaiKiemTra bkt= new BaiKiemTra();
        LocalDateTime time= LocalDateTime.of(2025,1,23,10,30);
        BaiKiemTra_DAO dao= new BaiKiemTra_DAO(em);
        System.out.println("EntityManager: " + dao);
        
        ArrayList<BaiKiemTra> dsMon= dao.getDanhSachBaiKiemTraTheoLop("LH25032025015344839");

        if(dsMon!=null ) dsMon.forEach(x->System.out.println(x));
    }
}
