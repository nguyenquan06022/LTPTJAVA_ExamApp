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
@Table(name = "KetQuaKiemTras")
public class KetQuaKiemTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Nếu muốn tự động tạo id duy nhất
    private Long maKetQuaKiemTra;

    private float diemSo;
    private int thoiGianLamBai;
    private int lanThu;

    @ElementCollection
    @CollectionTable(name = "dsCauTraLoi", joinColumns = @JoinColumn(name = "maKetQuaKiemTra"))
    @Column(name = "cauTraLoi", nullable = false)
    private Set<String> dsCauTraLoi;

    private boolean diemCaoNhat;

    @ManyToOne
    @JoinColumn(name = "maBaiKiemTra", nullable = false)
    private BaiKiemTra baiKiemTra;

    @ManyToOne
    @JoinColumn(name = "maTaiKhoan", nullable = false)
    private TaiKhoan taiKhoan;

    public void capNhatDiemCaoNhat() {
        diemCaoNhat = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KetQuaKiemTra that = (KetQuaKiemTra) o;
        return Objects.equals(maKetQuaKiemTra, that.maKetQuaKiemTra) && Objects.equals(baiKiemTra, that.baiKiemTra) && Objects.equals(taiKhoan, that.taiKhoan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKetQuaKiemTra, baiKiemTra, taiKhoan);
    }
}
