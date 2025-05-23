package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "MonHocs")
public class MonHoc implements Serializable {
    @Id
    @NonNull
    private String maMonHoc;
    @NonNull
    @Column(columnDefinition = "nvarchar(255)")
    private String tenMonHoc;
    @NonNull
    private String trangThai;

    @OneToMany(mappedBy = "monHoc")
    private Set<DeThi> deThi;

    @OneToMany(mappedBy = "monHoc")
    private Set<LopHoc> lopHoc;

    @OneToOne(mappedBy = "monHoc")
    private NganHangDeThi nganHangDeThi;

    
    public MonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }
    public MonHoc(String tenMonHoc, int field){
        if(field==2)
        this.tenMonHoc=tenMonHoc;
    }
    
    
}
