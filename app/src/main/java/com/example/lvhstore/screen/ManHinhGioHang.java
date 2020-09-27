package com.example.lvhstore.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvhstore.R;
import com.example.lvhstore.adapter.GiaHangAdapter;

import java.text.DecimalFormat;

public class ManHinhGioHang extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtThongBao;
    static TextView txtTongTien;
    ListView listViewGioHang;
    Button btnThanhToan, btnTiepTucMuaHang;
    GiaHangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_gio_hang);
        AnhXa();
        ActionToolBar();
        CheckData();
        EventUltil();
        EventButton();
        EventListViewGioHang();
    }

    private void EventListViewGioHang() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhGioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm!");
                builder.setMessage("Bạn có chắc xóa sản phẩm nay không?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.mangGioHang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.mangGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.mangGioHang.size() <=0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongBao.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    //    bắt sự kiện cho các button trong màn hình
    private void EventButton() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGioHang.size()>0){
                    startActivity(new Intent(ManHinhGioHang.this, ManHinhThongTinKhachHang.class));
                }else{
                    Toast.makeText(ManHinhGioHang.this, "Không có sản phẩm để thành toán", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManHinhGioHang.this, MainActivity.class));
            }
        });
    }

    //    kiểm tra giỏ hàng có dữ liệu hay không
    private void CheckData() {
        if (MainActivity.mangGioHang.size() <= 0) {
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        } else {
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }
//    phương thức này chưa hiểu
    public static void EventUltil() {
        int tongTien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
            tongTien += MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongTien) + "đ");
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
        toolbar = (Toolbar) findViewById(R.id.toolBarManHinhGioHang);
        txtThongBao = (TextView) findViewById(R.id.textViewThongBaoManHinhGioHang);
        txtTongTien = (TextView) findViewById(R.id.textViewTongTienManHinhGioHang);
        btnThanhToan = (Button) findViewById(R.id.buttonThanhToanManHinhGioHang);
        btnTiepTucMuaHang = (Button) findViewById(R.id.buttonTiepTucMuaHang);
//        custom cho màn hình giỏ hàng
        listViewGioHang = (ListView) findViewById(R.id.listViewManHinhGioHang);
        adapter = new GiaHangAdapter(this, R.layout.dong_gio_hang, MainActivity.mangGioHang);
        listViewGioHang.setAdapter(adapter);
    }
}