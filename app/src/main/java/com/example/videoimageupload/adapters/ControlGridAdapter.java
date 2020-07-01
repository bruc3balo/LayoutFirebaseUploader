package com.example.videoimageupload.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.videoimageupload.R;


public class ControlGridAdapter extends BaseAdapter {
    Context context;
    int incidence_list[];
   // String titles [];
    LayoutInflater inflter;
    private  LinearLayout backG;

    public ControlGridAdapter(Context applicationContext, int[] incidence_list) {
        this.context = applicationContext;
        this.incidence_list = incidence_list;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return incidence_list.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.control_grid_image, null); // inflate the layout
        ImageView icon = view.findViewById(R.id.responce_pic_grid); // get the reference of ImageView
        backG = view.findViewById(R.id.gridPicBG);

        //setBGColor(i);

        try {
            Glide.with(context).load(incidence_list[i]).into(icon);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Image failed to load", Toast.LENGTH_SHORT).show();
            icon.setImageResource(R.drawable.error);
        }

       // TextView title = view.findViewById(R.id.responce_name_grid); // title tv
     //   title.setText(titles[i]);

        return view;
    }


    @SuppressLint("ResourceAsColor")
    private void setBGColor(int i){
        switch (i){
            case 0:
                backG.setBackgroundColor(Color.RED);
                break;
            case 1:
                backG.setBackgroundColor(R.color.niceBlue);
                break;
            case 2:
                backG.setBackgroundColor(Color.CYAN);
                break;
            case  3:
                backG.setBackgroundColor(Color.YELLOW);
                break;
            default:break;
        }

    }



}
