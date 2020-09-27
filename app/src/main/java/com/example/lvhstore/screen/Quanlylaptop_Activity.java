package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.adapter.SanPhamAdapter;
import com.example.lvhstore.object.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quanlylaptop_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<SanPham> mangLaptop;
    SanPhamAdapter adapter;
    ImageView imageViewAdd;

    SanPham laptopListView;
    String getLaptop = "https://lvh082000.000webhostapp.com/cuahangdienthoai/getLaptopDemo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlylaptop_);
        AnhXa();
        ActionToolBar();
        GetLaptop(getLaptop);
        EventImageViewAdd();
        EventListView();
    }

    private void  DialogXoaLaptop(){
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(Quanlylaptop_Activity.this);
        dialogXoa.setMessage("Chắc chắn xóa điện thoại " + laptopListView.getTenSanPham() + " này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                XoaLaptop("https://lvh082000.000webhostapp.com/cuahangdienthoai/xoaSanPham.php");
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }
    private void XoaLaptop(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("okok")){
                            Toast.makeText(Quanlylaptop_Activity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            GetLaptop(getLaptop);
                        }else{
                            Toast.makeText(Quanlylaptop_Activity.this, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Quanlylaptop_Activity.this, "Lỗi code", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(laptopListView.getId()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void EventListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                laptopListView = mangLaptop.get(i);
                DialogDienThoai();
            }
        });
    }

    private void DialogDienThoai() {
        final Dialog dialogLaptop =new Dialog(this);
        dialogLaptop.setContentView(R.layout.dialog_san_pham);
        TextView txtTenLaptop =(TextView) dialogLaptop.findViewById(R.id.textViewQuanLyTenSanPham);
        TextView txtXoaLaptop =(TextView) dialogLaptop.findViewById(R.id.textViewQuanLyXoaSanPham);
        TextView txtSuaLaptop =(TextView) dialogLaptop.findViewById(R.id.textViewQuanLySuaSanPham);

        txtTenLaptop.setText(laptopListView.getTenSanPham());

        txtXoaLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogXoaLaptop();
                dialogLaptop.cancel();
            }
        });

        txtSuaLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quanlylaptop_Activity.this, Updatesanpham_Activity.class);
                intent.putExtra("dataSanPham", laptopListView);
                startActivity(intent);
                dialogLaptop.cancel();
            }
        });
        dialogLaptop.show();
    }
    private void EventImageViewAdd() {
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quanlylaptop_Activity.this, Themsanpham_Activity.class));
            }
        });
    }

    private void GetLaptop(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mangLaptop.clear();
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
                        Toast.makeText(Quanlylaptop_Activity.this, "Lỗi!!!!!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
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
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhQuanLyLaptop);
        listView  = (ListView) findViewById(R.id.listViewManHinhQuanLyLaptop);
        mangLaptop = new ArrayList<>();
        adapter = new SanPhamAdapter(this, R.layout.dong_san_pham,  mangLaptop);
        listView.setAdapter(adapter);
        imageViewAdd = (ImageView) findViewById(R.id.imageViewThemThongTinLaptop);
    }
}