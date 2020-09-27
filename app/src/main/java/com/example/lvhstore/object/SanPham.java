package com.example.lvhstore.object;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int id;
    private String tenSanPham;
    private int giaSanPham;
    private String motaSanPham;
    private String hinhAnhSanPham;
    private int idLoaiSanPham;

    public SanPham(int id, String tenSanPham, int giaSanPham, String motaSanPham, String hinhAnhSanPham, int idLoaiSanPham) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.motaSanPham = motaSanPham;
        this.hinhAnhSanPham = hinhAnhSanPham;
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getMotaSanPham() {
        return motaSanPham;
    }

    public void setMotaSanPham(String motaSanPham) {
        this.motaSanPham = motaSanPham;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;
    }

    public int getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }
}
