package com.example.lvhstore.object;

import java.io.Serializable;

public class QuangCao implements Serializable {
    private int idQuangCao;
    private String hinhQuangCao;
    private int idSanPham;
    private String tenSanPham;
    private int giaSanPham;
    private String moTa;
    private String anhSanPham;
    private int idLoaiSanPham;

    public QuangCao(int idQuangCao, String hinhQuangCao, int idSanPham, String tenSanPham, int giaSanPham, String moTa, String anhSanPham, int idLoaiSanPham) {
        this.idQuangCao = idQuangCao;
        this.hinhQuangCao = hinhQuangCao;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.moTa = moTa;
        this.anhSanPham = anhSanPham;
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public int getIdQuangCao() {
        return idQuangCao;
    }

    public void setIdQuangCao(int idQuangCao) {
        this.idQuangCao = idQuangCao;
    }

    public String getHinhQuangCao() {
        return hinhQuangCao;
    }

    public void setHinhQuangCao(String hinhQuangCao) {
        this.hinhQuangCao = hinhQuangCao;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public int getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }
}
