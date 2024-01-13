package com.example.campusconnect.User.UI.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.campusconnect.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private int[] images;
    private String[] text;
    private SliderAdapter adapter;
    private SliderView sliderView;
    private ViewPager viewPager;
    private BranchAdapter adapter1;
    private List<BranchModel> list;
    private ImageView map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        sliderView = view.findViewById(R.id.imageslider);
        map=view.findViewById(R.id.map);
        images = new int[]{R.drawable.no1, R.drawable.no2, R.drawable.no3, R.drawable.no4};
        adapter = new SliderAdapter(images, text);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.startAutoCycle();
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        list = new ArrayList<BranchModel>();
        list.add(new BranchModel(R.drawable.ic_bca,"Bachelor of Computer Application","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"));
        list.add(new BranchModel(R.drawable.ic_bba,"Bachelor of Business Administration","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,)"));
        adapter1 = new BranchAdapter(getContext(),list);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter1);
        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0,0?q=Shree Guru Sudhindra College,Bhatkal");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

}