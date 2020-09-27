package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.adapter.DonHangAdapter;
import com.example.lvhstore.object.DonHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Quanlydonhang_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<DonHang> donHangArrayList;
    DonHangAdapter adapter;
    DonHang donHangListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlydonhang_);
        AnhXa();
        ActionToolBar();
        GetDonHang("https://lvh082000.000webhostapp.com/cuahangdienthoai/getDonHang.php");
//        EventListViewDonHang();
    }

    private void EventListViewDonHang() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                donHangListView = donHangArrayList.get(i);
                DialogDonHang();
            }
        });
    }
    private void DialogDonHang(){
        final Dialog dialogDonHang = new Dialog(this);
        dialogDonHang.setContentView(R.layout.dialog_don_hang);
        TextView txtTenDonHang = (TextView) dialogDonHang.findViewById(R.id.textViewTenChiTietDonHang);
        TextView txtChiTiet = (TextView) dialogDonHang.findViewById(R.id.textViewChiTietDonHang);
        TextView txtXoa = (TextView) dialogDonHang.findViewById(R.id.textViewXoaDonHang);

        txtTenDonHang.setText(donHangListView.getTenKhachHang());

        txtChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Quanlydonhang_Activity.this, "xóa", Toast.LENGTH_SHORT).show();
            }
        });
        dialogDonHang.show();
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

    private void GetDonHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                donHangArrayList.add(new DonHang(
                                        object.getInt("id"),
                                        object.getString("tenkhachhang"),
                                        object.getString("sdt"),
                                        object.getString("diachi")
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

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhQuanLyDonHang);
        listView = (ListView) findViewById(R.id.listViewManHinhQuanLyDonHang);
        donHangArrayList = new ArrayList<>();
        adapter = new DonHangAdapter(this, R.layout.dong_don_hang, donHangArrayList);
        listView.setAdapter(adapter);
    }
}