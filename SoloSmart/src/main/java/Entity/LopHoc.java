package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LopHocs")
public class LopHoc {
    @Id
    private String maLop;

    private String tenLop;
    private int siSo;
    private String namHoc;
    private String trangThai;

    @OneToMany(mappedBy = "lopHoc")
    private List<KetQuaHocTap> ketQuaHocTap;

    @OneToMany(mappedBy = "lopHoc")
    private Set<BaiKiemTra> baiKiemTra;

    @ManyToOne
    @JoinColumn(name = "maMonHoc")
    private MonHoc monHoc;
}
