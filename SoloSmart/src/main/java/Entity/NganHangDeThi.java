package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "NganHangDeThis")
public class NganHangDeThi {
    @NonNull
    @Id
    private String maNganHang;

    @Column(columnDefinition = "nvarchar(255)")
    @NonNull
    private String tenNganHang;
    @NonNull
    private boolean loaiNganHang;

    @OneToOne
    @JoinColumn(name = "maMonHoc")
    @NonNull
    private MonHoc monHoc;

    @OneToMany(mappedBy = "nganHangDeThi")
    private Set<DeThi> deThi;

    public NganHangDeThi(String maNganHang) {
        this.maNganHang = maNganHang;
    }
}

