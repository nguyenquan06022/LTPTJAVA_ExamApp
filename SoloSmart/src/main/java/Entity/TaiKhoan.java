package Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TaiKhoans")
public class TaiKhoan {
    @Id
    private String maTaiKhoan;

    private String tenTaiKhoan;
    private String matKhau;
    private String vaiTro;
    private String trangThai;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaHocTap> ketQuaHocTap;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<KetQuaKiemTra> ketQuaKiemTra;

    @OneToMany(mappedBy = "taiKhoan")
    private Set<DeThi> deThi;
}
