package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.object.Admin;
import com.example.lvhstore.object.NguoiDung;
import com.google.android.gms.common.util.CollectionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ManHinhDangNhap extends AppCompatActivity {
    CheckBox checkBox;
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnTaoMoiTaiKhoan;
    String urlGetData = "https://lvh082000.000webhostapp.com/cuahangdienthoai/getNguoiDung.php";
    ArrayList<NguoiDung> nguoiDungArrayList;
    Admin admin;

    //    khởi tạo các biến để lưu thông tin đăng nhập
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        AnhXa();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        ở đây chú ý là vị trí phương thức trong hàm onCreat đặc biệt là nên để hàm ánh xạ ở đầu tiên
//        ảnh hưởng đến chương trình chạy
        loadData();//lấy dữ liệu đã lưu nếu đã có
        GetData(urlGetData);
        EventButton();
        GetAdmin("https://lvh082000.000webhostapp.com/cuahangdienthoai/getAdmin.php");

    }

    private void GetAdmin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            admin = new Admin(
                                    object.getString("taikhoan"),
                                    object.getString("matkhau"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putBoolean(REMEMBER, checkBox.isChecked());
        editor.commit();
    }

    private void loadData() {
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            edtTaiKhoan.setText(sharedpreferences.getString(USERNAME, ""));
            edtMatKhau.setText(sharedpreferences.getString(PASS, ""));
            checkBox.setChecked(true);
        } else
            checkBox.setChecked(false);

    }

    private void EventButton() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                kiểm tra có check lưu mật khẩu hay không
                if (checkBox.isChecked()) {
                    saveData(edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString());
                } else {
                    clearData();
                }
//                kiểm tra đăng nhập
                if (edtTaiKhoan.getText().toString().equals("") || edtMatKhau.getText().toString().equals("")) {
                    Toast.makeText(ManHinhDangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if(edtTaiKhoan.getText().toString().equals("admin") && edtMatKhau.getText().toString().equals("admin")){
                    startActivity(new Intent(ManHinhDangNhap.this, Admin_Activity.class));
                }else{
//                    tạo biến đếm giúp lưu số lượng tài khoản trong mảng nguoidung
//                    tức là nếu mình chạy hết mảng mà tài khoản và mật khẩu nhập không đúng thì biến đếm
//                    của mình khởi tạo sẽ bị trừ về bằng 0
                    int dem = nguoiDungArrayList.size();
                    for (int i = 0; i < nguoiDungArrayList.size(); i++) {
                        String tk = edtTaiKhoan.getText().toString();
                        String mk = edtMatKhau.getText().toString();
                        if (tk.equals(nguoiDungArrayList.get(i).getTaiKhoan()) && mk.equals(nguoiDungArrayList.get(i).getMatKhau())) {
                            Toast.makeText(ManHinhDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ManHinhDangNhap.this, MainActivity.class);
                            startActivity(intent);
                            edtTaiKhoan.setText("");
                            edtMatKhau.setText("");
                            finish();
                        } else {
                            dem--;
                        }
                    }
//                    khi tài khoản và mật khẩu nhập không đúng thì vòng for ở trên nó đã chạy hết mảng người dùng
//                    và nó sẽ giảm biến đếm = 0
                    if (dem == 0) {
                        Toast.makeText(ManHinhDangNhap.this, "Tài khoản hoặc mật khẩu sai. Hãy thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnTaoMoiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManHinhDangNhap.this, TaoTaiKhoanMoiActivity.class));
            }
        });
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                nguoiDungArrayList.add(new NguoiDung(
                                        object.getString("taikhoan"),
                                        object.getString("matkhau")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinhDangNhap.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        edtTaiKhoan = (EditText) findViewById(R.id.edtTaiKhoan);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        nguoiDungArrayList = new ArrayList<>();
        btnTaoMoiTaiKhoan = (Button) findViewById(R.id.buttonTaoTaiKhoanMoi);
        checkBox = (CheckBox) findViewById(R.id.checkBoxLuuTaiKhoan);
    }
}