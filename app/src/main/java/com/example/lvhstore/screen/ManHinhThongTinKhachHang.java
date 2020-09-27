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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManHinhThongTinKhachHang extends AppCompatActivity {
    EditText edtTen, edtSdt, edtDiaChi;
    Button btnXacNhan, btnHuy;
    String insertChiTietDonHang = "https://lvh082000.000webhostapp.com/cuahangdienthoai/insertChiTietDonHang.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_thong_tin_khach_hang);
        AnhXa();
        EventButton();
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ht = edtTen.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                if (ht.matches("") || sdt.equals("") || diaChi.length() == 0) {
                    Toast.makeText(ManHinhThongTinKhachHang.this, "Hãy nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT).show();
                } else {
                    InsertDonHang("https://lvh082000.000webhostapp.com/cuahangdienthoai/insertDonHang.php");
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

    private void InsertDonHang(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String madonhang) {
//                        kiểm tra xem đã thêm thành công đơn hàng hay chưa
//                        kiểm tra ở đây là mình phải đối chiếu với file php mình đã tạo
                        if (Integer.parseInt(madonhang) > 0) {
//                            Toast.makeText(ManHinhThongTinKhachHang.this, "Thêm thông tin đơn hàng thành công", Toast.LENGTH_SHORT).show();
//                            thêm xong đơn hàng thì mình thêm chi tiết đơn hàng ngày owr trong Response này luôn
//                            cách thêm tương tụ như thêm đơn hàng
                            RequestQueue queue = Volley.newRequestQueue(ManHinhThongTinKhachHang.this);
                            StringRequest request = new StringRequest(Request.Method.POST, insertChiTietDonHang,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
//                                            kiểm tra thêm chi tiết đơn hàng thành công hay chưa
//                                            kiểm tra ở đây là dối chiều với file php mình đã tạo
                                            if (response.equals("1")) {
                                                MainActivity.mangGioHang.clear();
                                                Toast.makeText(ManHinhThongTinKhachHang.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ManHinhThongTinKhachHang.this, MainActivity.class));
                                                Toast.makeText(ManHinhThongTinKhachHang.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ManHinhThongTinKhachHang.this, "Dữ liệu giỏ hàng bị lỗi", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ManHinhThongTinKhachHang.this, "Lỗi code thêm chii tiết đơn hàng", Toast.LENGTH_SHORT).show();
                                            Log.d("AAAAA", "Lỗi\n" + error.toString());
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
//                                    ở đây giở hàng của mình là một mảng bao gồm nhiều sản phẩm mà
//                                    mỗi sản phẩm là ứng với một object, mà cách để thêm được dữ
//                                    liệu vào bảng chitietdonhang là phải lấy thông tin từ những
//                                    đối tượng trong mảng giỏ hàng và chuyển đổi nó thành JsonArray
//                                    để web có thể hiểu được

                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
//                                            madonhang ở đây là cái mã khi mình thêm thành công một
//                                            đơn hàng
                                            jsonObject.put("madonhang", madonhang);
                                            jsonObject.put("masanpham", MainActivity.mangGioHang.get(i).getIdSanPhamGioHang());
                                            jsonObject.put("tensanpham", MainActivity.mangGioHang.get(i).getTenSanPhamGioHang());
                                            jsonObject.put("giasanpham", MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang());
                                            jsonObject.put("soluongsanpham", MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
//                                    khi xong thì phải chuyển cái mảng jsonarray của mình về chuỗi
                                    hashMap.put("json", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(request);
                        } else {
                            Toast.makeText(ManHinhThongTinKhachHang.this, "Lỗi thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinhThongTinKhachHang.this, "Lỗi code", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tenkhachhang", edtTen.getText().toString().trim());
                params.put("sdt", edtSdt.getText().toString().trim());
                params.put("diachi", edtDiaChi.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {

        edtTen = (EditText) findViewById(R.id.editTextNhapTenKhachHang);
        edtSdt = (EditText) findViewById(R.id.editTextNhapSoDienThoai);
        edtDiaChi = (EditText) findViewById(R.id.editTextNhapDiaChiKhachHang);
        btnXacNhan = (Button) findViewById(R.id.buttonXacNhanThongTinKhachHang);
        btnHuy = (Button) findViewById(R.id.buttonHuyThongTinKhachHang);
    }
}