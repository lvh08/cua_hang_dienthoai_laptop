package com.example.lvhstore.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lvhstore.R;
import com.example.lvhstore.object.GioHang;
import com.example.lvhstore.object.QuangCao;
import com.example.lvhstore.object.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
public class ManHinhChiTietSanPham extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageViewAnhSanPham;
    TextView txtTenSanPham, txtGiaSanPham,  txtMoTaSanPham;
    Spinner spinner;
    Button btnThemGioHang;

    int giaSanPham = 0;
    int idSanPham = 0;
    String tenSanPham = "";
    String hinhAnhSanPham = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet_san_pham);
        AnhXa();
        ActionToolBar();
        NhanDuLieu();
        EventSpiner();
        EventButtonThemGioHang();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                startActivity(new Intent(ManHinhChiTietSanPham.this, ManHinhGioHang.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButtonThemGioHang() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGioHang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i <MainActivity.mangGioHang.size() ; i++) {
                        if(MainActivity.mangGioHang.get(i).getIdSanPhamGioHang()== idSanPham){
//                            lấy số lượng sẩn phẩm có sẵn trong giỏ hàng cộng thêm số lượng khi mình chọn mua ở spiner
                            MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(
                                    MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang()+sl);
//                            kiểm tra số lượng sản phẩm trong giỏ hàng và chỉ cho tối đa là 10 sản phẩm
                            if(MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang() >=10){
                                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(10);
                            }
//                            khi chọn xong số lượng thì phải tính giá cho tổng số lượng đó
                            MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(
                                    giaSanPham*MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang());
                        }
                    }
                    if(exists == false){
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        int giaMoiSanPham = giaSanPham*soLuong;
                        MainActivity.mangGioHang.add(new GioHang(idSanPham, tenSanPham, giaMoiSanPham, hinhAnhSanPham, soLuong));
                    }
                }else{
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    int giaMoiSanPham =giaSanPham*soLuong;
                    MainActivity.mangGioHang.add(new GioHang(idSanPham, tenSanPham, giaMoiSanPham, hinhAnhSanPham, soLuong));
                }
                startActivity(new Intent(ManHinhChiTietSanPham.this, ManHinhGioHang.class));
            }
        });
    }

    private void EventSpiner() {
        Integer[] soLuong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item, soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void NhanDuLieu() {

        Intent intent =getIntent();

        if(intent != null){
//            nhận data được truyền từ các màn hình khi click vào sản phẩm bất kì
            if(intent.hasExtra("dataSanPham")){
                SanPham sanPham = (SanPham) intent.getSerializableExtra("dataSanPham");
                Picasso.get().load(sanPham.getHinhAnhSanPham()).placeholder(R.drawable.image).error(R.drawable.error).into(imageViewAnhSanPham);
//        imageViewAnhSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
                txtTenSanPham.setText(sanPham.getTenSanPham());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtGiaSanPham.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + "đ");
                txtMoTaSanPham.setText(sanPham.getMotaSanPham());
//                những cái này phải lấy ra để lát sử dụng cho sự kiện thêm vào giỏ hàng
                idSanPham = sanPham.getId();
                giaSanPham = sanPham.getGiaSanPham();
                tenSanPham = sanPham.getTenSanPham();
                hinhAnhSanPham = sanPham.getHinhAnhSanPham();
            }
//            nhận data khi click vào quảng cáo
            if(intent.hasExtra("dataquangcao")){
                QuangCao quangCao = (QuangCao) intent.getSerializableExtra("dataquangcao");
                Picasso.get().load(quangCao.getAnhSanPham()).placeholder(R.drawable.image).error(R.drawable.error).into(imageViewAnhSanPham);
//        imageViewAnhSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
                txtTenSanPham.setText(quangCao.getTenSanPham());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtGiaSanPham.setText("Giá: " + decimalFormat.format(quangCao.getGiaSanPham()) + "đ");
                txtMoTaSanPham.setText(quangCao.getMoTa());
//                những cái này phải lấy ra để lát sử dụng cho sự kiện thêm vào giỏ hàng
                idSanPham = quangCao.getIdSanPham();
                giaSanPham = quangCao.getGiaSanPham();
                tenSanPham = quangCao.getTenSanPham();
                hinhAnhSanPham = quangCao.getAnhSanPham();
            }
        }
    }

    private void AnhXa() {
        imageViewAnhSanPham = (ImageView) findViewById(R.id.imageViewChiTietSanPham);
        txtTenSanPham = (TextView) findViewById(R.id.textViewChiTietTenSanPham);
        txtGiaSanPham = (TextView) findViewById(R.id.textViewChiTietGiaSanPham);
        txtMoTaSanPham = (TextView) findViewById(R.id.textViewChiTietMoTaSanPham);
        spinner = (Spinner) findViewById(R.id.spinnerChiTietSanPham);
        btnThemGioHang = (Button) findViewById(R.id.buttonDatMuaSanPham);
        toolbar = (Toolbar) findViewById(R.id.toolBarChiTietSanPham);
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

}