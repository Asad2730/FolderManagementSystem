package com.example.biit.Teachers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Classes.Clos;
import com.example.biit.R;

import java.util.List;

public class CloTableViewAdapter extends ArrayAdapter {

    private Context context;
    private List<Clos> list;
    private TextView des,mids,finals,q,a;

    public CloTableViewAdapter(@NonNull Context context, List<Clos> list) {
        super(context, R.layout.clo_table_view_adapter,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         try{

             convertView = inflater.inflate(R.layout.clo_table_view_adapter,parent,false);


             des = convertView.findViewById(R.id.adapter_des);
             mids = convertView.findViewById(R.id.adapter_mids);
             finals = convertView.findViewById(R.id.adapter_finals);
             q = convertView.findViewById(R.id.adapter_q);
             a = convertView.findViewById(R.id.adapter_a);

             des.setText(list.get(position).getDes());
             mids.setText("Mid Exams: "+list.get(position).getMids());
             finals.setText("Final Exams: "+list.get(position).getFinals());
             q.setText("Quizzes: "+list.get(position).getQ());
             a.setText("Assignments: "+list.get(position).getA());



         }catch (Exception ex){
             Toast.makeText(context, "CloTableGetView:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
         }
        return convertView;
    }
}
