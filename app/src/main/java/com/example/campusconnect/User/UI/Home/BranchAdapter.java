package com.example.campusconnect.User.UI.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.campusconnect.R;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private Context context;
    private List<BranchModel> list;

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branchitemlayout,container,false);
        ImageView branchicon;
        TextView branchtitle,branchdesc;
        branchicon = view.findViewById(R.id.branchicon);
        branchtitle = view.findViewById(R.id.branchtitle);
        branchdesc = view.findViewById(R.id.branchdescription);
        branchicon.setImageResource(list.get(position).getImage());
        branchtitle.setText(list.get(position).getTitle());
        branchdesc.setText(list.get(position).getDescription());
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
