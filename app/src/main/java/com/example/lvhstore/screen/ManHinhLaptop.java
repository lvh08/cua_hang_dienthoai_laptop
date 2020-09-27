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
import com.example.lvhstore.object.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManHinhLaptop extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewLaptop;
    ArrayList<SanPham> mangLaptop;
    SanPhamAdapter adapter;
    SanPham sanPhamChiTiet;
    EditText edtTimKiemLaptop;
    String tenLaptop = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_laptop);
        AnhXa();
        ActionToolBar();
        GetLaptop("https://lvh082000.000webhostapp.com/cuahangdienthoai/getLaptopDemo.php");
        EventListViewLaptop();
        EventEditText();
    }

    private void EventEditText() {
        edtTimKiemLaptop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtTimKiemLaptop.getText().toString();
                if(s.length() >=2){
                    s = s.toLowerCase();
//                    tiến hành kiểm tra và update lại gridView
                    tenLaptop = s;
                    tienHanhKiemTra();
                }else{
                    adapter.updateSanPham(mangLaptop);
                }
            }
        });
    }
    private void tienHanhKiemTra(){
        final ArrayList<SanPham> mangTenLaptop;
        if(tenLaptop.length() >=2){
            mangTenLaptop = new ArrayList<>();
            for (SanPham lt : mangLaptop) {
                String tenLaptopNhap = lt.getTenSanPham().toLowerCase();
                if(tenLaptopNhap.indexOf(tenLaptop) >=0 ){
                    mangTenLaptop.add(lt);
                }
            }
            listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    sanPhamChiTiet = mangTenLaptop.get(i);
                    Intent intent = new Intent(ManHinhLaptop.this, ManHinhChiTietSanPham.class);
                    intent.putExtra("dataSanPham", sanPhamChiTiet);
                    startActivity(intent);
                }
            });
        }else{
            mangTenLaptop = mangLaptop;
        }
        adapter.updateSanPham(mangTenLaptop);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                startActivity(new Intent(ManHinhLaptop.this, ManHinhGioHang.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventListViewLaptop() {
        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sanPhamChiTiet = mangLaptop.get(i);
                Intent intent = new Intent(ManHinhLaptop.this, ManHinhChiTietSanPham.class);
                intent.putExtra("dataSanPham", sanPhamChiTiet);
                startActivity(intent);
            }
        });
    }

    private void GetLaptop(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                mangLaptop.add(new SanPham(
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
                        Toast.makeText(ManHinhLaptop.this, "Lỗi!!!!!", Toast.LENGTH_SHORT).show();
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
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhLaptop);
        listViewLaptop = (ListView) findViewById(R.id.listViewManHinhLaptop);
        mangLaptop = new ArrayList<>();
        adapter = new SanPhamAdapter(this, R.layout.dong_san_pham, mangLaptop);
        listViewLaptop.setAdapter(adapter);
        edtTimKiemLaptop = (EditText) findViewById(R.id.editTextTimKiemLaptop);
    }
}