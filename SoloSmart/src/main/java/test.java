import Dao.*;
import Entity.*;
import DB.CreateDB;
import GUI.Main_GUI;
import com.sun.tools.javac.Main;
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

                KetQuaHocTap_DAO ketQuaHocTap_dao = new KetQuaHocTap_DAO(em);
                System.out.println(ketQuaHocTap_dao.getDiemHocTapCuaSinhVien("TK21042025233255336","LH21042025233255773"));
        }
}
