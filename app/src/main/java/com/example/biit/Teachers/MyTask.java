package com.example.biit.Teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Db.Db;
import com.example.biit.R;
import com.example.biit.Tasks.Task;

import java.util.List;

public class MyTask extends ArrayAdapter {

      private CheckBox txt;
      private Context context;
      private TextView tw;
      List<Task> list;
    public MyTask(@NonNull Context context,List<Task> list) {
        super(context, R.layout.task_header,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       // Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_header,parent,false);
            txt = convertView.findViewById(R.id.header);
            tw = convertView.findViewById(R.id.dr);

            if(position == 0){
                Toast.makeText(context,txt.getText(),Toast.LENGTH_LONG).show();
                txt.setChecked(true);
                txt.setText(list.get(position).getTopic());
            }
             else{
                 txt.setVisibility(View.GONE);
            }
            tw.setText(list.get(position).getSubtopic());

            tw.setVisibility(View.GONE);
        }catch (Exception ex) {
            Toast.makeText(getContext(), " Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
//            new Db().
        }

        return  convertView;
    }
}
