package com.example.lvhstore.object;

public class LoaiSanPham {
    private int id;
    private String tenLoaiSanPham;
    private int hinhAnh;

    public LoaiSanPham(int id, String tenLoaiSanPham, int hinhAnh) {
        this.id = id;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
