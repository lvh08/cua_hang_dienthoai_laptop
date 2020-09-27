package com.example.lvhstore.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.adapter.SanPhamAdapter;
import com.example.lvhstore.adapter.SanPhamMoiAdapter;
import com.example.lvhstore.object.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManHinhDienThoai extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewDienThoai;
    ArrayList<SanPham> mangDienThoai;
    SanPhamAdapter adapter;
    EditText edtTimKiemDienThoai;
    SanPham sanPhamChitiet;
    String tenDienThoai = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dien_thoai);
        AnhXa();
        ActionToolBar();
        GetDienThoai("https://lvh082000.000webhostapp.com/cuahangdienthoai/getDienThoaiDemo.php");
        EventListViewDienThoai();
        EventEditText();
    }

    private void EventEditText() {
        edtTimKiemDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtTimKiemDienThoai.getText().toString();
                if(s.length() >=2){
                    s = s.toLowerCase();
//                    tiến hành kiểm tra và update lại gridView
                    tenDienThoai = s;
                    tienHanhKiemTra();
                }else{
                    adapter.updateSanPham(mangDienThoai);
                }
            }
        });
    }

    private void tienHanhKiemTra(){
        final ArrayList<SanPham> mangTenDienThoai;
        if(tenDienThoai.length() >=2){
            mangTenDienThoai = new ArrayList<>();
            for (SanPham dt : mangDienThoai) {
                String tenDienThoaiNhap = dt.getTenSanPham().toLowerCase();
                if(tenDienThoaiNhap.indexOf(tenDienThoai) >=0 ){
                    mangTenDienThoai.add(dt);
                }
            }
//            sau khi tìm kiếm tên thì mảng những sản phẩm được tìm kiếm theo tên nó sẽ được lưu vào một mảng mới
//            thì ở đây mình bắt sự kiện cho listView chứa mảng những cái điện thoại mới<=> mảng điện thoại được
//            tìm kiếm theo tên
            listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    sanPhamChitiet = mangTenDienThoai.get(i);
                    Intent intent = new Intent(ManHinhDienThoai.this, ManHinhChiTietSanPham.class);
                    intent.putExtra("dataSanPham", sanPhamChitiet);
                    startActivity(intent);
                }
            });
        }else{
            mangTenDienThoai = mangDienThoai;
        }
        adapter.updateSanPham(mangTenDienThoai);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                startActivity(new Intent(ManHinhDienThoai.this, ManHinhGioHang.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventListViewDienThoai() {
        listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sanPhamChitiet = mangDienThoai.get(i);
                Intent intent = new Intent(ManHinhDienThoai.this, ManHinhChiTietSanPham.class);
                intent.putExtra("dataSanPham", sanPhamChitiet);
                startActivity(intent);
            }
        });
    }

    private void GetDienThoai(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                mangDienThoai.add(new SanPham(
                                        object.getInt("id"),
                                        object.getString("tensanpham"),
                                        object.getInt("giasanpham"),
                                        object.getString("mota"),
                                        object.getString("anhsanpham"),
                                        object.getInt("idloaisanpham")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinhDienThoai.this, "Lỗi!!!!!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        edtTimKiemDienThoai = (EditText) findViewById(R.id.editTextTimKiemDienThoai);
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhDienThoai);
        listViewDienThoai = (ListView) findViewById(R.id.listViewManHinhDienThoai);
        mangDienThoai = new ArrayList<>();
        adapter = new SanPhamAdapter(this, R.layout.dong_san_pham, mangDienThoai);
        listViewDienThoai.setAdapter(adapter);
    }
}