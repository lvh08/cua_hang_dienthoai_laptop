package com.example.lvhstore.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.adapter.LoaiSanPhamAdapter;
import com.example.lvhstore.adapter.SanPhamMoiAdapter;
import com.example.lvhstore.object.GioHang;
import com.example.lvhstore.object.LoaiSanPham;
import com.example.lvhstore.object.NguoiDung;
import com.example.lvhstore.object.SanPham;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayoutMhc;
    Toolbar toolbarMhc;
//    ViewFlipper viewFlipperMhc;
    GridView gridViewSanPhamMoiMhc;
    NavigationView navigationViewMhc;
    ListView listViewMenuMhc;
//    custom listView menu loại sản phẩm
    ArrayList<LoaiSanPham> loaiSanPhamArrayList;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    private int[] anhLoaiSanPham = {R.drawable.dienthoai, R.drawable.laptop};
//    custom gridView sản phẩm mới nhất
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamMoiAdapter sanPhamMoiAdapter;
//    tạo một đối tượng sanpham để intent sang màn hình khác
    SanPham sanPhamChiTiet;
//    tạo một mảng giỏ hàng để lưu dữ liệu trong mảng giỏ hàng
    public static ArrayList<GioHang> mangGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ActionBar();
//        ActionViewFlipper();
//        phương thức lấy dữ liệu của loại sản phẩm trên webservice về
        GetLoaiSanPham("https://lvh082000.000webhostapp.com/cuahangdienthoai/getLoaiSanPhamDemo.php");
//        phương thức lấy dữ liệu của sản phẩm mới nhất trên webservice về
        GetSanPhamMoiNhat("https://lvh082000.000webhostapp.com/cuahangdienthoai/getSanPhamMoiDemo.php");
//        phương thức xử lý sự kiện cho listView menu loại sản phẩm
        EventListViewMenuLoaiSanPham();
        EventGridViewSanPhamMoiNhat();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                startActivity(new Intent(MainActivity.this, ManHinhGioHang.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventGridViewSanPhamMoiNhat() {
        gridViewSanPhamMoiMhc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sanPhamChiTiet = sanPhamArrayList.get(i);
                Intent intent = new Intent(MainActivity.this, ManHinhChiTietSanPham.class);
                intent.putExtra("dataSanPham", sanPhamChiTiet);
                startActivity(intent);
            }
        });
    }

    private void EventListViewMenuLoaiSanPham() {
        listViewMenuMhc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(MainActivity.this, ManHinhDienThoai.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, ManHinhLaptop.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, ManHinhLienHe.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ManHinhThongTin.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, ManHinhDangNhap.class));
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //    tạo phương thức lấy dữ liệu của các sản phẩm mới nhất
    private void GetSanPhamMoiNhat(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                sanPhamArrayList.add(new SanPham(
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
                        sanPhamMoiAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!!!!!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

//    tạo phương thức lấy dữ  liệu của loại sản phẩm
    private void GetLoaiSanPham(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                loaiSanPhamArrayList.add(new LoaiSanPham(
                                        object.getInt("id"),
                                        object.getString("tenloaisanpham"),
                                        anhLoaiSanPham[i]
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loaiSanPhamArrayList.add(new LoaiSanPham(0, "Liên hệ ", R.drawable.lienhe));
                        loaiSanPhamArrayList.add(new LoaiSanPham(0, "Thông tin ", R.drawable.info));
                        loaiSanPhamArrayList.add(new LoaiSanPham(0, "Đăng xuất ", R.drawable.dangxuat));
                        loaiSanPhamAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!!!!!!!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //    tạo menu giỏ hàng để khi click vào nó sẽ chuyển đến màn hính giỏ hàng
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
//    tạo phương thức cho viewFlipper để gán hình ảnh và tạo animation trong viewFlipper
//    private void ActionViewFlipper() {
//        ArrayList<String> mangQuangCao =new ArrayList<>();
//        mangQuangCao.add("https://cdn.tgdd.vn/2020/08/banner/Note10plus-800-300-800x300-2.png");
//        mangQuangCao.add("https://cdn.tgdd.vn/2020/08/banner/800-300-800x300-36.png");
//        mangQuangCao.add("https://cdn.tgdd.vn/2020/08/banner/800-300-800x300-27.png");
//        mangQuangCao.add("https://cdn.tgdd.vn/2020/08/banner/reno4-chung-800-300-800x300-1.png");
//        for (int i = 0; i <mangQuangCao.size() ; i++) {
//            ImageView imageView = new ImageView(this);
//            Picasso.get().load(mangQuangCao.get(i).toString()).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipperMhc.addView(imageView);
//        }
//        viewFlipperMhc.setFlipInterval(5000);
//        viewFlipperMhc.setAutoStart(true);
//        Animation animation_slide_in = AnimationUtils.loadAnimation(
//                this, R.anim.slide_in_right);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(
//                this, R.anim.sllide_out_right);
//        viewFlipperMhc.setInAnimation(animation_slide_in);
//        viewFlipperMhc.setOutAnimation(animation_slide_out);
//    }
//    tạo phương thức cho toolBar
    private void ActionBar() {
        setSupportActionBar(toolbarMhc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMhc.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarMhc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutMhc.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        drawerLayoutMhc = (DrawerLayout) findViewById(R.id.drawableLayoutMhc);
        toolbarMhc = (Toolbar) findViewById(R.id.toolBarMhc);
//        viewFlipperMhc = (ViewFlipper) findViewById(R.id.viewFlipperMhc);
        navigationViewMhc = (NavigationView) findViewById(R.id.navigationViewMhc);
//        ánh xạ cho custom listview menu loại sản phẩm
        listViewMenuMhc = (ListView) findViewById(R.id.listViewMenuMhc);
        loaiSanPhamArrayList = new ArrayList<>();
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(this, R.layout.dong_loai_san_pham, loaiSanPhamArrayList);
        listViewMenuMhc.setAdapter(loaiSanPhamAdapter);
//        ánh xạ cho custom gridView sản phẩm mới nhất
        gridViewSanPhamMoiMhc = (GridView) findViewById(R.id.gridViewSanPhamMoiNhatMhc);
        sanPhamArrayList = new ArrayList<>();
        sanPhamMoiAdapter = new SanPhamMoiAdapter(this, R.layout.dong_san_pham_moi, sanPhamArrayList);
        gridViewSanPhamMoiMhc.setAdapter(sanPhamMoiAdapter);

        if(mangGioHang !=null){

        }else{
            mangGioHang = new ArrayList<>();
        }
    }
}