import Dao.*;
import DB.CreateDB;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
        private static DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        private static EntityManager em;
        public static void main(String[] args) throws RemoteException {
                LocalDateTime now = LocalDateTime.now();
                em = CreateDB.createDB();

                IKetQuaHocTap_DAO IKetQuaHocTap_dao = new KetQuaHocTap_DAO(em);
                System.out.println(IKetQuaHocTap_dao.getDiemHocTapCuaSinhVien("TK21042025233255336","LH21042025233255773"));
        }
}
