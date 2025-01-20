package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "KetQuaHocTaps")
public class KetQuaHocTap {

    @Id
    @NonNull
    private float diemThuongKy;
    @Id
    @NonNull
    private float diemGiuaKy;
    @Id
    @NonNull
    private float diemCuoiKy;
    @NonNull
    private float GPA;

    @Id
    @ManyToOne
    @NonNull
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;

    @Id
    @ManyToOne
    @NonNull
    @JoinColumn(name = "maLop")
    private LopHoc lopHoc;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KetQuaHocTap that = (KetQuaHocTap) o;
        return Float.compare(diemThuongKy, that.diemThuongKy) == 0 && Float.compare(diemGiuaKy, that.diemGiuaKy) == 0 && Float.compare(diemCuoiKy, that.diemCuoiKy) == 0 && Objects.equals(taiKhoan, that.taiKhoan) && Objects.equals(lopHoc, that.lopHoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diemThuongKy, diemGiuaKy, diemCuoiKy, taiKhoan, lopHoc);
    }

    public float tinhDiemThuongKy() {
        return 0;
    }
    public float tinhDiemGiuaKy(){
        return 0;
    }
    public float tinhDiemCuoiKy(){
        return 0;
    }
    public float tinhGPA() {
        return 0;
    }
}
