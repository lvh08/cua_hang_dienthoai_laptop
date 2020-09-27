package com.example.lvhstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lvhstore.R;
import com.example.lvhstore.adapter.QuangCaoAdapter;
import com.example.lvhstore.object.QuangCao;
import com.example.lvhstore.screen.ManHinhChiTietSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Fragment_Quang_Cao extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ArrayList<QuangCao> quangCaoArrayList;
    QuangCaoAdapter quangCaoAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quang_cao, container, false);
        GetData("https://lvh082000.000webhostapp.com/cuahangdienthoai/getQuangCao.php");
        AnhXa();
        return view;
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                quangCaoArrayList.add(new QuangCao(
                                        object.getInt("idquangcao"),
                                        object.getString("hinhquangcao"),
                                        object.getInt("idsanpham"),
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
                        quangCaoAdapter.notifyDataSetChanged();
//                        thêm những lớp này mục đích để hiện thi các dấu chấm trong lướt qua lướt lại
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                currentItem = viewPager.getCurrentItem();
                                currentItem++;
                                if (currentItem >= viewPager.getAdapter().getCount()) {
                                    currentItem = 0;
                                }
                                viewPager.setCurrentItem(currentItem, true);
                                handler.postDelayed(runnable, 6000);
                            }
                        };
                        handler.postDelayed(runnable, 6000);
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
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.indicatorDefault);
        quangCaoArrayList = new ArrayList<>();
        quangCaoAdapter = new QuangCaoAdapter(getActivity(), quangCaoArrayList);
        viewPager.setAdapter(quangCaoAdapter);
        circleIndicator.setViewPager(viewPager);
    }
}
