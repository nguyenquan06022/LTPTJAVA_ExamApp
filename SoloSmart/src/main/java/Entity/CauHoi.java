package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "CauHois")
public class CauHoi {
    @Id
    @NonNull
    private String maCauHoi;
    @NonNull
    private String mucDo;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String cauHoi;
    @NonNull
    private int kieuTraLoi;

    @ElementCollection
    @CollectionTable(name="dsLuaChon", joinColumns = @JoinColumn(name="maCauHoi"))
    @Column(name="luaChon", nullable = false,columnDefinition = "nvarchar(255)")
    private List<String> dsLuaChon;

    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String dapAnDung;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String loiGiai;
    @NonNull
    private String trangThai;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "maDeThi")
    private DeThi deThi;

    public CauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }
}

