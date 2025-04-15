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
    @Column(columnDefinition = "nvarchar(255)")
    private String maTaiKhoan;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String tenTaiKhoan;
    
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String matKhau;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String Ho;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String Ten;
    @NonNull
    private String vaiTro;
    @NonNull
    private String trangThai;
    @NonNull
    private String dangOnline;
    @NonNull
    private String gioiTinh;
    @NonNull
    private String soDienThoai;
    @NonNull
    private String email;


    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaHocTap> ketQuaHocTap;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaKiemTra> ketQuaKiemTra;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<DeThi> deThi;

    @OneToMany(mappedBy = "giaoVien")
    private Set<LopHoc> lopHocs;

    public TaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
    
}
