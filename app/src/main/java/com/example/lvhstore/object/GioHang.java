package com.example.lvhstore.object;

public class GioHang {
    private int idSanPhamGioHang;
    private String tenSanPhamGioHang;
    private int giaSanPhamGioHang;
    private String anhSanPhamGioHang;
    private int soLuongSanPhamGioHang;

    public GioHang(int idSanPhamGioHang, String tenSanPhamGioHang, int giaSanPhamGioHang, String anhSanPhamGioHang, int soLuongSanPhamGioHang) {
        this.idSanPhamGioHang = idSanPhamGioHang;
        this.tenSanPhamGioHang = tenSanPhamGioHang;
        this.giaSanPhamGioHang = giaSanPhamGioHang;
        this.anhSanPhamGioHang = anhSanPhamGioHang;
        this.soLuongSanPhamGioHang = soLuongSanPhamGioHang;
    }

    public int getIdSanPhamGioHang() {
        return idSanPhamGioHang;
    }

    public void setIdSanPhamGioHang(int idSanPhamGioHang) {
        this.idSanPhamGioHang = idSanPhamGioHang;
    }

    public String getTenSanPhamGioHang() {
        return tenSanPhamGioHang;
    }

    public void setTenSanPhamGioHang(String tenSanPhamGioHang) {
        this.tenSanPhamGioHang = tenSanPhamGioHang;
    }

    public int getGiaSanPhamGioHang() {
        return giaSanPhamGioHang;
    }

    public void setGiaSanPhamGioHang(int giaSanPhamGioHang) {
        this.giaSanPhamGioHang = giaSanPhamGioHang;
    }

    public String getAnhSanPhamGioHang() {
        return anhSanPhamGioHang;
    }

    public void setAnhSanPhamGioHang(String anhSanPhamGioHang) {
        this.anhSanPhamGioHang = anhSanPhamGioHang;
    }

    public int getSoLuongSanPhamGioHang() {
        return soLuongSanPhamGioHang;
    }

    public void setSoLuongSanPhamGioHang(int soLuongSanPhamGioHang) {
        this.soLuongSanPhamGioHang = soLuongSanPhamGioHang;
    }
}
