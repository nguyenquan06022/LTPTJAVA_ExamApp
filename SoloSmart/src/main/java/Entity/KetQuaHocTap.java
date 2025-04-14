package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "KetQuaHocTaps")
public class KetQuaHocTap {

    private Float diemThuongKy;
    private Float diemGiuaKy;
    private Float diemCuoiKy;
    private Float diemTBMon;

    public KetQuaHocTap(float diemThuongKy, float diemGiuaKy, float diemCuoiKy, float diemTBMon, TaiKhoan taiKhoan, LopHoc lopHoc) {
        this.diemThuongKy = diemThuongKy;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTBMon = diemTBMon;
        this.taiKhoan = taiKhoan;
        this.lopHoc = lopHoc;
    }

    public KetQuaHocTap(LopHoc lopHoc,TaiKhoan taiKhoan) {
        this.lopHoc = lopHoc;
        this.taiKhoan = taiKhoan;
    }

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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KetQuaHocTap that = (KetQuaHocTap) o;
    return Objects.equals(taiKhoan.getMaTaiKhoan(), that.taiKhoan.getMaTaiKhoan()) &&
           Objects.equals(lopHoc.getMaLop(), that.lopHoc.getMaLop());
}

@Override
public int hashCode() {
    return Objects.hash(taiKhoan.getMaTaiKhoan(), lopHoc.getMaLop());
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
