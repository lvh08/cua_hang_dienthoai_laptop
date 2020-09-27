package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.object.SanPham;

import java.util.HashMap;
import java.util.Map;

public class Updatesanpham_Activity extends AppCompatActivity {
    EditText edtTen, edtGia, edtMota, edtAnh, edtId;
    Button btnSua, btnHuy;
    int id = 0;
    String urlUpdate = "https://lvh082000.000webhostapp.com/cuahangdienthoai/updateSanPham.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatesanpham_);
        Intent intent = getIntent();
        SanPham sanPham = (SanPham) intent.getSerializableExtra("dataSanPham");
        AnhXa();

        id = sanPham.getId();
        edtTen.setText(sanPham.getTenSanPham());
        edtGia.setText(sanPham.getGiaSanPham()+"");
        edtMota.setText(sanPham.getMotaSanPham());
        edtAnh.setText(sanPham.getHinhAnhSanPham());
        edtId.setText(sanPham.getIdLoaiSanPham()+"");

        EventButton();
    }

    private void EventButton() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTen.getText().toString().trim();
                String gia = edtGia.getText().toString().trim();
                String mota = edtMota.getText().toString().trim();
                String anh = edtAnh.getText().toString().trim();
                String idloaisp = edtId.getText().toString().trim();
                if(ten.matches("") || gia.equals("") || mota.length()==0 ||
                        anh.matches("") || idloaisp.equals("")){
                    Toast.makeText(Updatesanpham_Activity.this, "Hãy nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT).show();
                }else if(idloaisp.equals("1") || idloaisp.equals("2")){
                    CapNhatSanPham(urlUpdate);
                }else{
                    Toast.makeText(Updatesanpham_Activity.this, "Id loại sản phẩm chỉ được nhập 1 hoặc 2" + "\n" + "1: Điện thoại" + "\n" + "2: Laptop", Toast.LENGTH_SHORT).show();
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

    private void CapNhatSanPham(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("okok")){
                            Toast.makeText(Updatesanpham_Activity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Updatesanpham_Activity.this, Admin_Activity.class));
                        }else{
                            Toast.makeText(Updatesanpham_Activity.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Updatesanpham_Activity.this, "Lỗi code!!!!!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id",String.valueOf(id));
                params.put("tensanpham", edtTen.getText().toString().trim());
                params.put("giasanpham", edtGia.getText().toString().trim());
                params.put("mota", edtMota.getText().toString().trim());
                params.put("anhsanpham", edtAnh.getText().toString().trim());
                params.put("idloaisanpham", edtId.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {
        edtTen = (EditText) findViewById(R.id.editTextSuaThongTinTenSanPhamMHUpdate);
        edtGia = (EditText) findViewById(R.id.editTextSuaThongTinGiaSanPhamMHUpdate);
        edtMota = (EditText) findViewById(R.id.editTextSuaThongTinMoTaSanPhamMHUpdate);
        edtAnh = (EditText) findViewById(R.id.editTextSuaThongTinAnhSanPHamMHUpdate);
        edtId = (EditText) findViewById(R.id.editTextSuaThongTinIdLoaiSanPhamMHUpdate);
        btnHuy = (Button) findViewById(R.id.buttonHuySuaThongTinSanPhamMHUpdate);
        btnSua = (Button) findViewById(R.id.buttonSuaThongTinSanPhamMHUpdate);
    }
}