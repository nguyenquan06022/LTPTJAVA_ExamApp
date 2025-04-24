package Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "BaiKiemTras")
public class BaiKiemTra implements Serializable {
    @Id
    @NonNull
    private String maBaiKiemTra;
    @NonNull
    private LocalDateTime thoiGianBatDau;
    @NonNull
    private LocalDateTime thoiGianKetThuc;
    @NonNull
    private int thoiGianLamBai;
    @NonNull
    private String matKhauBaiKiemTra;
    @NonNull
    private boolean choPhepXemDiem;
    @NonNull
    private boolean choPhepXemLai;
    @NonNull
    private boolean hienThiDapAn;
    @NonNull
    private int soLanLamBai;
    @NonNull
    private int thangDiem;
    @NonNull
    private float heSo;
    @NonNull
    private String trangThai;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "maDeThi")
    private DeThi deThi;

    @OneToMany(mappedBy = "baiKiemTra")
    private List<KetQuaKiemTra> ketQuaKiemTra;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "maLop")
    private LopHoc lopHoc;

    public BaiKiemTra(String maBaiKiemTra) {
        this.maBaiKiemTra = maBaiKiemTra;
    }
}

