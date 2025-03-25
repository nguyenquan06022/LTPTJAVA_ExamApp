package Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "TaiKhoans")
public class TaiKhoan {
    @Id
    @NonNull
    private String maTaiKhoan;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String tenTaiKhoan;
    
    @NonNull
    private String matKhau;
    @NonNull
    private String Ho;
    @NonNull
    private String Ten;
    @NonNull
    private String vaiTro;
    @NonNull
    private String trangThai;
    @NonNull
    private String dangOnline;
    @NonNull
    private String gioiTinh;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaHocTap> ketQuaHocTap;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaKiemTra> ketQuaKiemTra;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<DeThi> deThi;

    public TaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
}
