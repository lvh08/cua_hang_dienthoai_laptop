package com.example.lvhstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lvhstore.R;
import com.example.lvhstore.object.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<SanPham> sanPhamList;

    public SanPhamAdapter(Context context, int layout, ArrayList<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    public void updateSanPham(ArrayList<SanPham> mangSanPham){
        this.sanPhamList = mangSanPham;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return sanPhamList.size();
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
        ImageView imgAnhSanPham;
        TextView txtGiaSanPham, txtMoTaSanPham;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.imgAnhSanPham = (ImageView) view.findViewById(R.id.imageViewHinhAnhSanPham);
            holder.txtGiaSanPham = (TextView) view.findViewById(R.id.textViewGiaSanPham);
            holder.txtMoTaSanPham = (TextView) view.findViewById(R.id.textViewMoTaSanPham);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = sanPhamList.get(i);
        Picasso.get().load(sanPham.getHinhAnhSanPham()).placeholder(R.drawable.image).error(R.drawable.error).into(holder.imgAnhSanPham);
//        holder.imgAnhSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham())+"đ");
        holder.txtMoTaSanPham.setText(sanPham.getMotaSanPham());
        return view;
    }
}
