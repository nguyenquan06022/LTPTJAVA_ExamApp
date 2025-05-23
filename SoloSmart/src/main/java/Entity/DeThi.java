package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "DeThis")
public class DeThi implements Serializable {
    @NonNull
    @Id
    private String maDeThi;
    @NonNull
    private int soLuongCauHoi;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String monHoc;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String linkFile;
    @NonNull
    private String trangThai;

    @OneToMany(mappedBy = "deThi")
    private Set<BaiKiemTra> baiKiemTra;

    @OneToMany(mappedBy = "deThi")
    private Set<CauHoi> cauHoi;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "maNganHang")
    private NganHangDeThi nganHangDeThi;

    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String tenDeThi;

    public DeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }
}

