package Dao;

import Entity.BaiKiemTra;
import Entity.DeThi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class BaiKiemTra_DAO {
    private EntityManager em;

    public BaiKiemTra_DAO(EntityManager em) {
        this.em = em;
    }

    public boolean themBaiKiemTra(BaiKiemTra bkt){
        EntityTransaction tr = em.getTransaction();
        boolean isSuccess = false;
        try {
            tr.begin();
            String sql = "INSERT INTO BaiKiemTras values (mabaikiemtra,chophepxemdiem, chophepxemlai,heso,hienthidapan" +
                    "matkhaubaikiemtra,solanlambai,thangdiem,thoigianbatdau,thoigianketthuc,thoigianlambai,trangthai,madethi,malop)";
            em.createNativeQuery(sql)
                    .setParameter(1,bkt.getMaBaiKiemTra() )
                    .setParameter(2,bkt.isChoPhepXemDiem() )
                    .setParameter(3,bkt.isChoPhepXemLai() )
                    .setParameter(4,bkt.getHeSo() )
                    .setParameter(5,bkt.isHienThiDapAn() )
                    .setParameter(6,bkt.getMaBaiKiemTra() )
                    .setParameter(7,bkt.getSoLanLamBai() )
                    .setParameter(8,bkt.getThangDiem() )
                    .setParameter(9,bkt.getThoiGianBatDau() )
                    .setParameter(10,bkt.getThoiGianKetThuc() )
                    .setParameter(11,bkt.getThoiGianLamBai() )
                    .setParameter(12,bkt.getTrangThai() )
                    .setParameter(13,bkt.getDeThi().getMaDeThi() )
                    .setParameter(14,bkt.getLopHoc().getMaLop() )
                    .executeUpdate();
            tr.commit();
            isSuccess = true;
        } catch (Exception e) {
            tr.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }

    public BaiKiemTra getBaiKiemTra(String id) {
        BaiKiemTra baiKiemTra = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String sql = "SELECT * FROM BaiKiemTras WHERE maBaiKiemTra = :maBaiKiemTra";
            Object[] result = (Object[]) em.createNativeQuery(sql)
                    .setParameter("maBaiKiemTra", id)
                    .getSingleResult();

            if (result != null) {
                baiKiemTra = new BaiKiemTra();
                baiKiemTra.setMaBaiKiemTra((String) result[0]);
                baiKiemTra.setChoPhepXemDiem((Boolean) result[1]);
                baiKiemTra.setChoPhepXemLai((Boolean) result[2]);
                baiKiemTra.setHeSo((Float) result[3]);
                baiKiemTra.setHienThiDapAn((Boolean) result[4]);
                baiKiemTra.setMaBaiKiemTra((String) result[5]);
                baiKiemTra.setSoLanLamBai((Integer) result[6]);
                baiKiemTra.setThangDiem((Integer) result[7]);
                baiKiemTra.setThoiGianBatDau((LocalDateTime) result[8]);
                baiKiemTra.setThoiGianKetThuc((LocalDateTime) result[9]);
                baiKiemTra.setThoiGianLamBai((Integer) result[10]);
                baiKiemTra.setTrangThai((String) result[11]);
                baiKiemTra.setDeThi(new DeThi());
            }
            tr.commit();
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            throw new RuntimeException(e);
        }
        return baiKiemTra;
    }


}
