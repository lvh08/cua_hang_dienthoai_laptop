package com.example.lvhstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lvhstore.R;
import com.example.lvhstore.object.GioHang;
import com.example.lvhstore.screen.MainActivity;
import com.example.lvhstore.screen.ManHinhGioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class GiaHangAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GioHang> gioHangList;

    public GiaHangAdapter(Context context, int layout, List<GioHang> gioHangList) {
        this.context = context;
        this.layout = layout;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        return gioHangList.size();
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
        TextView txtTenSanPham,txtGiaSanPham;
        ImageView imgAnhSanPham;
        Button btnTru, btnGiaTri, btnCong;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtTenSanPham = (TextView) view.findViewById(R.id.textViewTenSanPhamManHinhGioHang);
            holder.txtGiaSanPham = (TextView) view.findViewById(R.id.textViewGiaSanPhamManHinhGioHang);
            holder.imgAnhSanPham = (ImageView) view.findViewById(R.id.imageSanPhamManHinhGioHang);
            holder.btnTru = (Button) view.findViewById(R.id.buttonTruGioHang);
            holder.btnGiaTri = (Button) view.findViewById(R.id.buttonGiaTriGioHang);
            holder.btnCong = (Button) view.findViewById(R.id.buttonCongGioHang);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = gioHangList.get(i);
        Picasso.get().load(gioHang.getAnhSanPhamGioHang()).placeholder(R.drawable.image).error(R.drawable.error).into(holder.imgAnhSanPham);
//        holder.imgAnhSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.txtTenSanPham.setText(gioHang.getTenSanPhamGioHang());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá: " + decimalFormat.format(gioHang.getGiaSanPhamGioHang()) + "đ");
        holder.btnGiaTri.setText(gioHang.getSoLuongSanPhamGioHang() + "");
        final int soLuong = Integer.parseInt(holder.btnGiaTri.getText().toString());
        if (soLuong >= 10) {
            holder.btnCong.setVisibility(View.INVISIBLE);
            holder.btnTru.setVisibility(View.VISIBLE);
        } else if (soLuong <= 1) {
            holder.btnTru.setVisibility(View.INVISIBLE);
        } else if (soLuong >= 1) {
            holder.btnCong.setVisibility(View.VISIBLE);
            holder.btnTru.setVisibility(View.VISIBLE);
        }

        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMoi = Integer.parseInt(holder.btnGiaTri.getText().toString()) + 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang();
                int giaHienTai = MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(soLuongMoi);
                int giaMoiNhat = (giaHienTai * soLuongMoi) / soLuongHienTai;
                MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtGiaSanPham.setText("Giá: " + decimalFormat.format(giaMoiNhat) + "đ");
                ManHinhGioHang.EventUltil();
                if (soLuongMoi > 9) {
                    holder.btnCong.setVisibility(View.INVISIBLE);
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnGiaTri.setText(String.valueOf(soLuongMoi));
                }else{
                    holder.btnCong.setVisibility(View.VISIBLE);
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnGiaTri.setText(String.valueOf(soLuongMoi));
                }
            }
        });
        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMoi = Integer.parseInt(holder.btnGiaTri.getText().toString()) - 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang();
                int giaHienTai = MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(soLuongMoi);
                int giaMoiNhat = (giaHienTai * soLuongMoi) / soLuongHienTai;
                MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtGiaSanPham.setText("Giá: " + decimalFormat.format(giaMoiNhat) + "₫");
                ManHinhGioHang.EventUltil();
                if (soLuongMoi < 2) {
                    holder.btnCong.setVisibility(View.VISIBLE);
                    holder.btnTru.setVisibility(View.INVISIBLE);
                    holder.btnGiaTri.setText(String.valueOf(soLuongMoi));
                }else{
                    holder.btnCong.setVisibility(View.VISIBLE);
                    holder.btnTru.setVisibility(View.VISIBLE);
                    holder.btnGiaTri.setText(String.valueOf(soLuongMoi));
                }
            }
        });
        return view;
    }
}
