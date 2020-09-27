package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lvhstore.R;

public class Admin_Activity extends AppCompatActivity {
    TextView txtDienThoai, txtLaptop, txtDonHang, txtDangXuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);
        AnhXa();
        EventTextView();
    }

    private void EventTextView() {
        txtDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Activity.this, Quanlydienthoai_Activity.class));
            }
        });

        txtLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Activity.this, Quanlylaptop_Activity.class));
            }
        });

        txtDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Activity.this, Quanlydonhang_Activity.class));
            }
        });
        txtDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Activity.this, ManHinhDangNhap.class));
                finish();
            }
        });
    }

    private void AnhXa() {
        txtDienThoai = (TextView) findViewById(R.id.textViewQuanLyDienThoai);
        txtLaptop = (TextView) findViewById(R.id.textViewQuanLyLaptop);
        txtDonHang = (TextView) findViewById(R.id.textViewQuanLyDonHang);
        txtDangXuat = (TextView) findViewById(R.id.textViewDangXuatManHinhAdmin);
    }
}