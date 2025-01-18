package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DeThis")
public class DeThi {
    @Id
    private String maDeThi;
    private int soLuongCauHoi;
    private String monHoc;
    private String linkFile;
    private String trangThai;

    @OneToMany(mappedBy = "deThi")
    private Set<BaiKiemTra> baiKiemTra;

    @OneToMany(mappedBy = "deThi")
    private Set<CauHoi> cauHoi;

    @ManyToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "maNganHang")
    private NganHangDeThi nganHangDeThi;
}

