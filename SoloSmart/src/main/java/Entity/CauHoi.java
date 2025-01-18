package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CauHois")
public class CauHoi {
    @Id
    private String maCauHoi;

    private String mucDo;
    private String cauHoi;
    private int kieuTraLoi;

    @ElementCollection
    @CollectionTable(name="dsLuaChon", joinColumns = @JoinColumn(name="maCauHoi"))
    @Column(name="luaChon", nullable = false)
    private List<String> dsLuaChon;

    private String dapAnDung;
    private String loiGiai;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "maNganHang")
    private NganHangDeThi nganHangDeThi;

    @ManyToOne
    @JoinColumn(name = "maDeThi")
    private DeThi deThi;
}

