package com.example.lvhstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.lvhstore.R;
import com.example.lvhstore.object.QuangCao;
import com.example.lvhstore.screen.ManHinhChiTietSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuangCaoAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<QuangCao> quangCaoArrayList;

    public QuangCaoAdapter(Context context, ArrayList<QuangCao> quangCaoArrayList) {
        this.context = context;
        this.quangCaoArrayList = quangCaoArrayList;
    }

    @Override
    public int getCount() {
        return quangCaoArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_quang_cao, null);

        ImageView imageView = view.findViewById(R.id.imageViewHinhQuangCao);
        Picasso.get().load(quangCaoArrayList.get(position).getHinhQuangCao()).into(imageView);
//        bắt sự kiện chio quảng cáo
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManHinhChiTietSanPham.class);
                intent.putExtra("dataquangcao", quangCaoArrayList.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
