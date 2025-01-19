package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NganHangDeThis")
public class NganHangDeThi {
    @Id
    private String maNganHang;

    private String tenNganHang;
    private boolean loaiNganHang;

    @OneToOne
    @JoinColumn(name = "maMonHoc")
    private MonHoc monHoc;

    @OneToMany(mappedBy = "nganHangDeThi")
    private Set<DeThi> deThi;
}

