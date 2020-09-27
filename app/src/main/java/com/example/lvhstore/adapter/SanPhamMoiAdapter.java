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
import java.util.List;

public class SanPhamMoiAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SanPham> sanPhamList;

    public SanPhamMoiAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
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
        ImageView imgHinhSanPhamMoi;
        TextView txtGia, txtMota;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtGia = (TextView) view.findViewById(R.id.textViewGiaSanPhamMoiNhat);
            holder.txtMota = (TextView) view.findViewById(R.id.textViewMoTaSanPhamMoiNhat);
            holder.imgHinhSanPhamMoi = (ImageView) view.findViewById(R.id.imageViewAnhSanPhamMoiNhat);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = sanPhamList.get(i);
        Picasso.get().load(sanPham.getHinhAnhSanPham()).placeholder(R.drawable.image).error(R.drawable.error).into(holder.imgHinhSanPhamMoi);
//        holder.imgHinhSanPhamMoi.setScaleType(ImageView.ScaleType.FIT_XY);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham())+"đ");
        holder.txtMota.setText(sanPham.getMotaSanPham());
        return view;
    }
}
