package com.example.lvhstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lvhstore.R;
import com.example.lvhstore.object.DonHang;
import com.example.lvhstore.object.SanPham;

import java.util.ArrayList;

public class DonHangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<DonHang> donHangArrayList;

    public DonHangAdapter(Context context, int layout, ArrayList<DonHang> donHangArrayList) {
        this.context = context;
        this.layout = layout;
        this.donHangArrayList = donHangArrayList;
    }

    @Override
    public int getCount() {
        return donHangArrayList.size();
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
        TextView txtTenKhachHang, txtSdtKhachHang, txtDiaChiKhachHang;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder.txtTenKhachHang = (TextView) view.findViewById(R.id.textViewTenKhachHangMhdh);
            viewHolder.txtSdtKhachHang = (TextView) view.findViewById(R.id.textViewSdtKhachHangMhdh);
            viewHolder.txtDiaChiKhachHang = (TextView) view.findViewById(R.id.textViewDiaChiKhachHangMhdh);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        DonHang donHang = donHangArrayList.get(i);

        viewHolder.txtTenKhachHang.setText(donHang.getTenKhachHang());
        viewHolder.txtSdtKhachHang.setText(donHang.getSdt());
        viewHolder.txtDiaChiKhachHang.setText(donHang.getDiaChi());

        return view;
    }
}
