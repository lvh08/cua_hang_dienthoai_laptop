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

public class Themsanpham_Activity extends AppCompatActivity {
    EditText edtTen, edtGia, edtMota, edtAnh, edtIdLoaiSanhPham;
    Button btnXacNhan, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham_);
        AnhXa();
        EventButton();
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTen.getText().toString();
                String gia = edtGia.getText().toString();
                String mota = edtMota.getText().toString();
                String anh = edtAnh.getText().toString();
                String idloaisanpham = edtIdLoaiSanhPham.getText().toString();
                if (ten.isEmpty() || gia.isEmpty() || mota.isEmpty()
                        || anh.isEmpty() || idloaisanpham.isEmpty()) {
                    Toast.makeText(Themsanpham_Activity.this,
                            "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (idloaisanpham.equals("1") || idloaisanpham.equals("2")) {
                    InsertSanPhamMoi("https://lvh082000.000webhostapp.com/cuahangdienthoai/insertSanPhamMoi.php");
                } else {
                    Toast.makeText(Themsanpham_Activity.this, "Id loại sản phẩm chỉ được nhập 1 hoặc 2" + "\n" + "1: Điện thoại" + "\n" + "2: Laptop", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void InsertSanPhamMoi(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().toString().equals("okok")) {
                            Toast.makeText(Themsanpham_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Themsanpham_Activity.this, Admin_Activity.class));
                        } else {
                            Toast.makeText(Themsanpham_Activity.this, "Lỗi thêm!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Themsanpham_Activity.this, "Lỗi code", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tensanpham", edtTen.getText().toString().trim());
                params.put("giasanpham", edtGia.getText().toString().trim());
                params.put("mota", edtMota.getText().toString().trim());
                params.put("anhsanpham", edtAnh.getText().toString().trim());
                params.put("idloaisanpham", edtIdLoaiSanhPham.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        edtTen = (EditText) findViewById(R.id.editTextNhapTenSanPhamMHTSPM);
        edtGia = (EditText) findViewById(R.id.editTextNhapGiaSanPhamMHTSPM);
        edtMota = (EditText) findViewById(R.id.editTextNhapMoTaSanPhamMHTSPM);
        edtAnh = (EditText) findViewById(R.id.editTextNhapAnhSanPHamMHTSPM);
        edtIdLoaiSanhPham = (EditText) findViewById(R.id.editTextNhapIdLoaiSanPhamMHTSPM);
        btnXacNhan = (Button) findViewById(R.id.buttonXacNhanThemSanPhamMoiMHTSPM);
        btnHuy = (Button) findViewById(R.id.buttonHuyThemSanPhamMoiMHTSPM);
    }
}