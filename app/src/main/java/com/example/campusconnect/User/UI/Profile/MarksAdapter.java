package com.example.campusconnect.User.UI.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewAdapter> {
    Context context;
    ArrayList<MarksHelper> list;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference, reference1, dbref;

    public MarksAdapter(Context context, ArrayList<MarksHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MarksViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.markslayout, parent, false);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference1 = FirebaseDatabase.getInstance().getReference("Marks");
        return new MarksViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksViewAdapter holder, @SuppressLint("RecyclerView") int position) {
//        String Uid = user.getUid();
//        dbref=reference.child(Uid);
//        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserHelper helper = snapshot.getValue(UserHelper.class);
//                if(helper!=null){
//                    String regno = helper.getRegno();
//                    dbref = reference1.child(regno);
//                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                            MarksHelper helper1= snapshot1.getValue(MarksHelper.class);
//                            String course = helper1.getCourse();
//                            String sem = helper1.getSem();
//                            if(course=="BCA" && sem=="1st SEM"){
//                                holder.subject1.setText("Kannada");
//                                holder.subject2.setText("Mathematics-1");
//                                holder.subject3.setText("Accounting 1");
//                                holder.subject4.setText("Basic Electricals");
//                                holder.subject5.setText("Computer Concepts");
//                                holder.subject6.setText("Indian Constitution");
//                                holder.subject7.setText("");
//                                holder.marks1.setText(helper1.getSub1());
//                                holder.marks2.setText(helper1.getSub2());
//                                holder.marks3.setText(helper1.getSub3());
//                                holder.marks4.setText(helper1.getSub4());
//                                holder.marks5.setText(helper1.getSub5());
//                                holder.marks6.setText(helper1.getSub6());
//                                holder.marks7.setText(helper1.getSub7());
//                                String m = holder.marks7.getText().toString();
//                                if(m.equals("0")){
//                                    holder.marks7.setVisibility(View.GONE);
//                                    holder.subject7.setVisibility(View.GONE);
//                                }
//                                holder.total.setText("120");
//                                int tot = Integer.parseInt(holder.total.getText().toString());
//                                int mk1 = Integer.parseInt(holder.marks1.getText().toString());
//                                int mk2 = Integer.parseInt(holder.marks2.getText().toString());
//                                int mk3 = Integer.parseInt(holder.marks3.getText().toString());
//                                int mk4 = Integer.parseInt(holder.marks4.getText().toString());
//                                int mk5 = Integer.parseInt(holder.marks5.getText().toString());
//                                int mk6 = Integer.parseInt(holder.marks6.getText().toString());
//                                int mk7 = Integer.parseInt(holder.marks7.getText().toString());
//                                int total = mk1 + mk2 + mk3 + mk4 + mk5 + mk6 + mk7;
//                                int k = 100;
//                                double perc = total * 100 / tot;
//                                holder.percent.setText("" + perc);
//                                holder.totalmarks.setText("" + total);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(context, "Error : "+error, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context, "Error : "+error, Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.subject1.setText("Kannada");
        holder.subject2.setText("Mathematics-1");
        holder.subject3.setText("Accounting 1");
        holder.subject4.setText("Basic Electricals");
        holder.subject5.setText("Computer Concepts");
        holder.subject6.setText("Indian Constitution");
holder.subject7.setText("0");
        MarksHelper doom = list.get(position);
        holder.marks1.setText(doom.getSub1());
        holder.marks2.setText(doom.getSub2());
        holder.marks3.setText(doom.getSub3());
        holder.marks4.setText(doom.getSub4());
        holder.marks5.setText(doom.getSub5());
        holder.marks6.setText(doom.getSub6());
        holder.marks7.setText(doom.getSub7());
        holder.total.setText("140");
        int tot = Integer.parseInt(holder.total.getText().toString());
        int mk1 = Integer.parseInt(holder.marks1.getText().toString());
        int mk2 = Integer.parseInt(holder.marks2.getText().toString());
        int mk3 = Integer.parseInt(holder.marks3.getText().toString());
        int mk4 = Integer.parseInt(holder.marks4.getText().toString());
        int mk5 = Integer.parseInt(holder.marks5.getText().toString());
        int mk6 = Integer.parseInt(holder.marks6.getText().toString());
        int mk7 = Integer.parseInt(holder.marks7.getText().toString());
        int total = mk1 + mk2 + mk3 + mk4 + mk5 + mk6 + mk7;
        int k = 100;
        double perc = total * 100 / tot;
        holder.percent.setText("" + perc);
        holder.totalmarks.setText("" + total);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MarksViewAdapter extends RecyclerView.ViewHolder {
        private TextView subject1, subject3, subject4, subject5, subject6, subject7, subject2, marks1, marks2, marks3, marks4, marks5, marks6, marks7;
        private TextView total, totalmarks, percent;

        public MarksViewAdapter(@NonNull View itemView) {
            super(itemView);
            subject1 = itemView.findViewById(R.id.subject1);
            subject2 = itemView.findViewById(R.id.subject2);
            subject3 = itemView.findViewById(R.id.subject3);
            subject4 = itemView.findViewById(R.id.subject4);
            subject5 = itemView.findViewById(R.id.subject5);
            subject6 = itemView.findViewById(R.id.subject6);
            subject7 = itemView.findViewById(R.id.subject7);
            marks1 = itemView.findViewById(R.id.sub1marks);
            marks2 = itemView.findViewById(R.id.sub2marks);
            marks3 = itemView.findViewById(R.id.sub3marks);
            marks4 = itemView.findViewById(R.id.sub4marks);
            marks5 = itemView.findViewById(R.id.sub5marks);
            marks6 = itemView.findViewById(R.id.sub6marks);
            marks7 = itemView.findViewById(R.id.sub7marks);
            total = itemView.findViewById(R.id.total);
            totalmarks = itemView.findViewById(R.id.totalmarks);
            percent = itemView.findViewById(R.id.percentage);
        }
    }
}
