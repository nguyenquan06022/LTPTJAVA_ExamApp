package Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BaiKiemTras")
public class BaiKiemTra {
    @Id
    private String maBaiKiemTra;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private int thoiGianLamBai;
    private String matKhauBaiKiemTra;
    private boolean choPhepXemDiem;
    private boolean choPhepXemLai;
    private boolean hienThiDapAn;
    private int soLanLamBai;
    private int thangDiem;
    private float heSo;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "maDeThi")
    private DeThi deThi;

    @OneToMany(mappedBy = "baiKiemTra")
    private List<KetQuaKiemTra> ketQuaKiemTra;

    @ManyToOne
    @JoinColumn(name = "maLop")
    private LopHoc lopHoc;
}

