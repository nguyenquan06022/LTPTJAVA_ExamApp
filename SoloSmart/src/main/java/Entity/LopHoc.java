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
@Table(name = "LopHocs")
public class LopHoc implements Serializable {
    @Id
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
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

    public LopHoc(String maLop, String tenLop, int siSo, String namHoc, String trangThai, MonHoc monHoc, TaiKhoan giaoVien) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.siSo = siSo;
        this.namHoc = namHoc;
        this.trangThai = trangThai;
        this.monHoc = monHoc;
        this.giaoVien = giaoVien;
    }
    
    

    public LopHoc(String maLop) {
        this.maLop = maLop;
    }
}
