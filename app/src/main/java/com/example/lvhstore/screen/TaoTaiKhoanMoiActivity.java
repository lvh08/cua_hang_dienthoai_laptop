package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;

import java.util.HashMap;
import java.util.Map;

public class TaoTaiKhoanMoiActivity extends AppCompatActivity {
    EditText edtTaiKhoan, edtMatKhau, edtSdt;
    Button btnXacNhanTao, btnHuyTao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan_moi);
        AnhXa();
        EventButton();
    }

    private void InsertNguoiDung(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Integer.parseInt(response) > 0) {
                            Toast.makeText(TaoTaiKhoanMoiActivity.this, "Tạo mới tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("taikhoan", edtTaiKhoan.getText().toString().trim());
                params.put("matkhau", edtMatKhau.getText().toString().trim());
                params.put("sdt", edtSdt.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        edtTaiKhoan = (EditText) findViewById(R.id.editTextNhapTaiKhoan);
        edtMatKhau = (EditText) findViewById(R.id.editTextNhapMatKhau);
        edtSdt = (EditText) findViewById(R.id.editTextNhapSoDienThoai);
        btnXacNhanTao = (Button) findViewById(R.id.buttonXacNhanTaoTaiKhoanMoi);
        btnHuyTao = (Button) findViewById(R.id.buttonHuyTaoTaiKhoan);
    }

    private void EventButton() {
        btnXacNhanTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edtTaiKhoan.getText().toString().trim();
                String mk = edtMatKhau.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                if (tk.matches("") || mk.equals("") || sdt.length() == 0) {
                    Toast.makeText(TaoTaiKhoanMoiActivity.this, "Hãy nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT).show();
                } else {
                    InsertNguoiDung("https://lvh082000.000webhostapp.com/cuahangdienthoai/insertNguoiDung.php");
                    startActivity(new Intent(TaoTaiKhoanMoiActivity.this, ManHinhDangNhap.class));
                }
            }
        });
        btnHuyTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}