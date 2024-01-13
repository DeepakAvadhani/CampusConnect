package com.example.campusconnect.User.UI.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campusconnect.R;
import com.example.campusconnect.admin.Faculty.LecturerData;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewAdapter> {
    private List<LecturerData> list;
    private Context context;

    public FacultyAdapter(List<LecturerData> list, Context context,String category) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FacultyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlecturerlayout, parent, false);
        return new FacultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewAdapter holder, int position) {
        LecturerData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Glide.with(context).load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FacultyViewAdapter extends RecyclerView.ViewHolder {
        private TextView name, email, post;
        private ImageView imageView;
        public FacultyViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Lecturername);
            email = itemView.findViewById(R.id.lectureremail);
            post = itemView.findViewById(R.id.lecturerpost);
            imageView = itemView.findViewById(R.id.lecturerimageview);
        }
    }
}
