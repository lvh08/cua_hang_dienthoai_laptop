package com.example.lvhstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lvhstore.R;
import com.example.lvhstore.object.LoaiSanPham;

import java.util.List;

public class LoaiSanPhamAdapter extends BaseAdapter {
    private int[] anhLoaiSanPham = {R.drawable.dienthoai, R.drawable.laptop, R.drawable.lienhe, R.drawable.info};
    private Context context;
    private int layout;
    private List<LoaiSanPham> loaiSanPhamList;

    public LoaiSanPhamAdapter(Context context, int layout, List<LoaiSanPham> loaiSanPhamList) {
        this.context = context;
        this.layout = layout;
        this.loaiSanPhamList = loaiSanPhamList;
    }

    @Override
    public int getCount() {
        return loaiSanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txtLoaiSanPham;
        ImageView imgLoaiSanPham;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtLoaiSanPham = (TextView) view.findViewById(R.id.textViewLoaiSanPhamMhc);
            holder.imgLoaiSanPham = (ImageView) view.findViewById(R.id.imageViewLoaiSanPhamMhc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        LoaiSanPham loaiSanPham = loaiSanPhamList.get(i);
//        for (int j = 0; j <anhLoaiSanPham.length ; j++) {
//            holder.imgLoaiSanPham.setImageResource(anhLoaiSanPham[i]);
//        }
        holder.imgLoaiSanPham.setImageResource(loaiSanPham.getHinhAnh());
        holder.txtLoaiSanPham.setText(loaiSanPham.getTenLoaiSanPham());
        return view;
    }
}
