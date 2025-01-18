package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KetQuaHocTaps")
public class KetQuaHocTap {

    @Id
    private float diemThuongKy;
    @Id
    private float diemGiuaKy;
    @Id
    private float diemCuoiKy;
    private float GPA;

    @Id
    @ManyToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;

    @Id
    @ManyToOne
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
