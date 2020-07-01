package com.example.videoimageupload.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.example.videoimageupload.Model;
import com.example.videoimageupload.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.LinkedList;

public class SliderAdapter2 extends RecyclerView.Adapter<SliderAdapter2.SliderViewHolder> {

    private Context context;
    private LinkedList<Model> sliderItems;
    private ViewPager2 viewPager2;
    private int position;
    Integer[] colors = new Integer[]{R.color.dresscode,R.color.eventsicon,R.color.mtaaicon,R.color.semiBlack,R.color.tbBlue,R.color.niceBlue};



    public SliderAdapter2(Context context, LinkedList<Model> modelList, ViewPager2 viewPager2) {
        this.sliderItems = modelList;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mtaa_post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.title.setText(sliderItems.get(position).getTitle());
        holder.description.setText(sliderItems.get(position).getDescription());
        Glide.with(context).load(sliderItems.get(position).getImage_urk()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public int getCurrentPosition() {
        return position;
    }

    private void updatePosition  (int new_position){
        position = new_position;
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{
        private TextView title,description;
        private RoundedImageView poster;
     //   private RecyclerView userPostRv;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            description= itemView.findViewById(R.id.description);
       //   userPostRv = itemView.findViewById(R.id.userPostRv);
        }

     /*   void setDetails(Model models){
            title.setText(models.getTitle());
            description.setText(models.getDescription());
            Glide.with(context).load(models.getImage_urk()).into(poster);
        }*/
    }


}
