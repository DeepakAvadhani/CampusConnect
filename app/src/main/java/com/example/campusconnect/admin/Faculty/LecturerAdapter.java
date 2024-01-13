package com.example.campusconnect.admin.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.LecturerViewAdapter> {
    private List<LecturerData> list;
    private Context context;
    private String category;

    public LecturerAdapter(List<LecturerData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public LecturerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturerlayout,parent,false);
        return new LecturerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LecturerViewAdapter holder, int position) {
        LecturerData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {

            e.printStackTrace();
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateFacultyActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LecturerViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,post;
        private Button update;
        private ImageView imageView;
        public LecturerViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Lecturername);
            email=itemView.findViewById(R.id.lectureremail);
            post=itemView.findViewById(R.id.lecturerpost);
            update=itemView.findViewById(R.id.lecturerupdatebutton);
            imageView=itemView.findViewById(R.id.lecturerimageview);
        }
    }
}
