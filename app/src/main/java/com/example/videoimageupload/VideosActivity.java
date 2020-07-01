package com.example.videoimageupload;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.videoimageupload.adapters.ControlGridAdapter;
import com.example.videoimageupload.adapters.SliderAdapter;

import java.util.LinkedList;

public class VideosActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    //Adapter adapter;
    RelativeLayout vpBg;
    LinkedList<Model> modelList = new LinkedList<>();
    SliderAdapter sliderAdapter;
    Integer[] colors = new Integer[]{R.color.dresscode,R.color.eventsicon,R.color.mtaaicon,R.color.semiBlack,R.color.tbBlue,R.color.niceBlue};

    private int [] control_ops = new int[]{R.drawable.pause,R.drawable.play,R.drawable.forward,R.drawable.ic_stop_svgrepo_com};
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

        setContentView(R.layout.activity_videos);

       new Handler().post(new Runnable() {
           @Override
           public void run() {
               modelList.add(new Model("https://www.ecopetit.cat/wpic/mpic/60-605928_mozzie-rainbow-six-siege-4k-rainbow-six-siege.jpg","Mozzie on a bike","Hey look at me, I'm cool"));
               modelList.add(new Model("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/020e1179-46f8-43ff-9c44-4280cde630ec/ddcca82-c18ba2fe-8d5f-4c8f-90a5-eb69b5a8a555.jpg/v1/fill/w_1231,h_649,q_70,strp/rainbow_six_siege__2019__wallpaper_hd_4k_by_sahibdm_ddcca82-pre.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9Njc1IiwicGF0aCI6IlwvZlwvMDIwZTExNzktNDZmOC00M2ZmLTljNDQtNDI4MGNkZTYzMGVjXC9kZGNjYTgyLWMxOGJhMmZlLThkNWYtNGM4Zi05MGE1LWViNjliNWE4YTU1NS5qcGciLCJ3aWR0aCI6Ijw9MTI4MCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.OH_W3uifsPMNqp6JYYv_ZHhDC4lrr1DsCA27V1muOJ4","No Running in the hallway","Operation Clash is tosic and i will peek forever is coming soon"));
               modelList.add(new Model("https://www.jakpost.travel/wimages/large/47-470139_alibi-tom-clancys-rainbow-six-siege-video-game.jpg","Do you have an alibi?","Illusions are fatal, If you waver, you're mine! if you pick wrong , you're mine!"));
               modelList.add(new Model("https://data.1freewallpapers.com/download/rainbow-six-siege-operation-shifting-tides-2019-4k.jpg","Kali","Peek me, I double dare you"));
           }
       });
        // adapter= new Adapter(modelList,this);

        vpBg = findViewById(R.id.vpBg);

        //ViewPager2
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        sliderAdapter = new SliderAdapter(this,modelList,viewPager2);
        viewPager2.setAdapter(sliderAdapter);
        viewPager2.setPadding(10,200,10,200);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(VideosActivity.this, ""+viewPager2.getCurrentItem(), Toast.LENGTH_SHORT).show();
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        //ControlGrid
        GridView controlGrid = findViewById(R.id.controlGrid);
        ControlGridAdapter controlGridAdapter = new ControlGridAdapter(this, control_ops);
        controlGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        controlGrid.setGravity(Gravity.FILL);
        controlGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final int TIME_OUT = 200;
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 3:
                    case 1:
                    case 2:
                        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryVDark));
                        new Handler().postDelayed(new Runnable() {@Override public void run() { view.setBackgroundColor(Color.TRANSPARENT); }},TIME_OUT);
                        break;

                    default:break;
                }
            }
        });
        controlGrid.setAdapter(controlGridAdapter);


    }

    public void toggleControls(View view) {
        CardView controlCard = findViewById(R.id.controlCard);
        if (controlCard.getVisibility() == View.VISIBLE) {
            controlCard.setVisibility(View.GONE);
        } else {
            controlCard.setVisibility(View.VISIBLE);
        }
    }

}