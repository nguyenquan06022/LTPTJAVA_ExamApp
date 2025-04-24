import Dao.*;
import jakarta.persistence.EntityManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            EntityManager em = DB.CreateDB.createDB();
            Context context = new InitialContext();
            LocateRegistry.createRegistry(5881);

            InetAddress localHost = InetAddress.getLocalHost();
            String hostname = localHost.getHostName();
            System.setProperty("java.rmi.server.hostname", hostname);

            // Tạo đối tượng DAO và bind
            context.rebind("rmi://" + hostname + ":5881/TaiKhoanDao", new TaiKhoan_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/BaiKiemTraDao", new BaiKiemTra_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/CauHoiDao", new CauHoi_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/DeThiDao", new DeThi_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/DsCauTraLoiDao", new DsCauTraLoi_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/DsLuaChonDao", new DsLuaChon_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/KetQuaHocTapDao", new KetQuaHocTap_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/KetQuaKiemTraDao", new KetQuaKiemTra_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/LopHocDao", new LopHoc_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/MonHocDao", new MonHoc_DAO(em));
            context.rebind("rmi://" + hostname + ":5881/NganHangDeThiDao", new NganHangDeThi_DAO(em));

            System.out.println(">>>>> RMI Server ready to run on host: " + hostname);
        } catch (Exception e) {
            System.err.println("RMI Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
