package com.example.videoimageupload.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.videoimageupload.Model;
import com.example.videoimageupload.R;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        return super.instantiateItem(container, position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = layoutInflater.inflate(R.layout.mtaa_post_item,container,false);

        ImageView poster;
        TextView title,description;

        poster = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        description= view.findViewById(R.id.description);

        title.setText(models.get(position).getTitle());
        description.setText(models.get(position).getDescription());
        Glide.with(context).load(models.get(position).getImage_urk()).into(poster);

        container.addView(view,0);

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
