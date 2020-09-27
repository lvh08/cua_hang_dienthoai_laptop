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

public class Quanlydienthoai_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<SanPham> mangDienThoai;
    SanPhamAdapter adapter;
    ImageView imageViewAdd;

    SanPham dienThoaiListView;
    String getDienThoai = "https://lvh082000.000webhostapp.com/cuahangdienthoai/getDienThoaiDemo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlydienthoai_);
        AnhXa();
        ActionToolBar();
        GetDienThoai(getDienThoai);
        EventImageViewAdd();
        EventListView();
    }
    private void  DialogXoaDienThoai(){
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(Quanlydienthoai_Activity.this);
        dialogXoa.setMessage("Chắc chắn xóa điện thoại " +dienThoaiListView.getTenSanPham() + " này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                XoaDienThoai("https://lvh082000.000webhostapp.com/cuahangdienthoai/xoaSanPham.php");
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }
    private void XoaDienThoai(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("okok")){
                            Toast.makeText(Quanlydienthoai_Activity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            GetDienThoai(getDienThoai);
                        }else{
                            Toast.makeText(Quanlydienthoai_Activity.this, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Quanlydienthoai_Activity.this, "Lỗi code", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(dienThoaiListView.getId()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void EventListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dienThoaiListView = mangDienThoai.get(i);
                DialogDienThoai();
            }
        });
    }

    private void DialogDienThoai() {
        final Dialog dialogDienThoai =new Dialog(this);
        dialogDienThoai.setContentView(R.layout.dialog_san_pham);
        TextView txtTenDienThoai =(TextView) dialogDienThoai.findViewById(R.id.textViewQuanLyTenSanPham);
        TextView txtXoaDienThoai =(TextView) dialogDienThoai.findViewById(R.id.textViewQuanLyXoaSanPham);
        TextView txtSuaDienThoai =(TextView) dialogDienThoai.findViewById(R.id.textViewQuanLySuaSanPham);

        txtTenDienThoai.setText(dienThoaiListView.getTenSanPham());

        txtXoaDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogXoaDienThoai();
                dialogDienThoai.cancel();
            }
        });

        txtSuaDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quanlydienthoai_Activity.this, Updatesanpham_Activity.class);
                intent.putExtra("dataSanPham", dienThoaiListView);
                startActivity(intent);
                dialogDienThoai.cancel();
            }
        });
        dialogDienThoai.show();
    }

    private void EventImageViewAdd() {
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quanlydienthoai_Activity.this, Themsanpham_Activity.class));
            }
        });
    }

    private void GetDienThoai(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        ở đây xóa mảng tránh khi xóa bị lặp
                        mangDienThoai.clear();
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
                        Toast.makeText(Quanlydienthoai_Activity.this, "Lỗi!!!!!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhQuanLyDienThoai);
        listView  = (ListView) findViewById(R.id.listViewManHinhQuanLyDienThoai);
        mangDienThoai = new ArrayList<>();
        adapter = new SanPhamAdapter(this, R.layout.dong_san_pham,  mangDienThoai);
        listView.setAdapter(adapter);
        imageViewAdd = (ImageView) findViewById(R.id.imageViewThemThongTinDienThoai);
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
}