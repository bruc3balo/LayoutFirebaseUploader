package com.example.videoimageupload.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.videoimageupload.Model;
import com.example.videoimageupload.R;

import java.util.LinkedList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private Context context;
    private LinkedList<Model> sliderItems;
    private LinkedList<Model> emptyItems = new LinkedList<>();
    private ViewPager2 viewPager2;
    private int position;
    Integer[] colors = new Integer[]{R.color.dresscode,R.color.eventsicon,R.color.mtaaicon,R.color.semiBlack,R.color.tbBlue,R.color.niceBlue};
    private SliderAdapter2 sliderAdapter2;


    public SliderAdapter(Context context,LinkedList<Model> modelList, ViewPager2 viewPager2) {
        this.sliderItems = modelList;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, int position) {

        holder.secondViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        sliderAdapter2 = new SliderAdapter2(context,sliderItems,viewPager2); ///list for individuals data
        holder.secondViewPager.setAdapter(sliderAdapter2);
        holder.secondViewPager.setPadding(10,10,10,10);
        holder.secondViewPager.setClipToPadding(false);
        holder.secondViewPager.setClipChildren(false);
        holder.secondViewPager.setOffscreenPageLimit(3);
        holder.secondViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });

        holder.secondViewPager.setPageTransformer(compositePageTransformer);

        holder.secondViewPager.registerOnPageChangeCallback( new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(context, "adapter : "+holder.secondViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public int getCurrentPosition() {
        return position;
    }

    private void updatePosition (int new_position){
        position = new_position;
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager2 secondViewPager;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            secondViewPager = itemView.findViewById(R.id.secondViewPager2);
        }

    }

    public int getPosition() {
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
        return position;
    }
}
