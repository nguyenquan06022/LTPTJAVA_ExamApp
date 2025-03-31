package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "LopHocs")
public class LopHoc {
    @Id
    @NonNull
    private String maLop;

    @Column(columnDefinition = "nvarchar(255)")
    @NonNull
    private String tenLop;
    @NonNull
    private int siSo;
    @NonNull
    private String namHoc;
    @NonNull
    private String trangThai;

    @OneToMany(mappedBy = "lopHoc")
    private List<KetQuaHocTap> ketQuaHocTap;

    @OneToMany(mappedBy = "lopHoc")
    private Set<BaiKiemTra> baiKiemTra;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "maMonHoc")
    private MonHoc monHoc;

    @ManyToOne
    @JoinColumn(name = "maGiaoVien") // Khóa ngoại trỏ đến maTaiKhoan của giáo viên
    private TaiKhoan giaoVien;


    public LopHoc(String maLop) {
        this.maLop = maLop;
    }
}
