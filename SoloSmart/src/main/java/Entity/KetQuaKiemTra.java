package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "KetQuaKiemTras")
public class KetQuaKiemTra implements Serializable {

    @Id
    @NonNull
    private String maKetQuaKiemTra;
    @NonNull
    private float diemSo;
    @NonNull
    private int thoiGianLamBai;
    @NonNull
    private int lanThu;

    @ElementCollection
    @CollectionTable(name = "dsCauTraLoi", joinColumns = @JoinColumn(name = "maKetQuaKiemTra"))
    @Column(name = "cauTraLoi", nullable = false, columnDefinition = "nvarchar(255)")
    private Set<String> dsCauTraLoi;
    @NonNull
    private boolean diemCaoNhat;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "maBaiKiemTra")
    private BaiKiemTra baiKiemTra;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "maTaiKhoan")
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
