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
    @Column(columnDefinition = "nvarchar(255)")
    private String mucDo;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String cauHoi;
    @NonNull
    private int kieuTraLoi;

    @ElementCollection
    @CollectionTable(name = "dsLuaChon", joinColumns = @JoinColumn(name = "maCauHoi"))
    private List<LuaChons> dsLuaChon;

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

    public CauHoi(String maCauHoi,String mucDo,String cauHoi,int kieuTraLoi, List<LuaChons> dsLuaChon,String loiGiai,String trangThai,DeThi deThi) {
        this.maCauHoi = maCauHoi;
        this.mucDo = mucDo;
        this.cauHoi = cauHoi;
        this.kieuTraLoi = kieuTraLoi;
        this.dsLuaChon = dsLuaChon;
        this.loiGiai = loiGiai;
        this.trangThai = trangThai;
        this.deThi = deThi;
    }
}

