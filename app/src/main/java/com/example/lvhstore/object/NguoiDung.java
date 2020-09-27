package com.example.lvhstore.object;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String taiKhoan;
    private String matKhau;

    public NguoiDung(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }


    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
