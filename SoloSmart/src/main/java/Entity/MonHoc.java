package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MonHocs")
public class MonHoc {
    @Id
    private String maMonHoc;

    private String tenMonHoc;
    private String trangThai;

    @OneToMany(mappedBy = "monHoc")
    private Set<DeThi> deThi;

    @OneToMany(mappedBy = "monHoc")
    private Set<LopHoc> lopHoc;

    @OneToOne
    @JoinColumn(name = "maNganHang", unique = true)
    private NganHangDeThi nganHangDeThi;
}
