package com.example.lvhstore.object;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id;
    private String tenKhachHang;
    private String sdt;
    private String diaChi;

    public DonHang(int id, String tenKhachHang, String sdt, String diaChi) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
