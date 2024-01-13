package com.example.campusconnect.User.Notes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {
    private Context context;
    private List<EbookData> list;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebookitemlayout, parent, false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        holder.pdfname.setText(list.get(holder.getAdapterPosition()).getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PdfViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(holder.getAdapterPosition()).getPdfUrl());
                context.startActivity(intent);
            }
        });
        holder.pdfdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(holder.getAdapterPosition()).getPdfUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {
        private TextView pdfname;
        private ImageView pdfdownload;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfname = itemView.findViewById(R.id.pdftextview);
            pdfdownload = itemView.findViewById(R.id.pdfdownload);
        }
    }
}
