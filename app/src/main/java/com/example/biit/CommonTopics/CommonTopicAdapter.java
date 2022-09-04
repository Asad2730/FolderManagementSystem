package com.example.biit.CommonTopics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Db.Db;
import com.example.biit.R;

import java.util.List;

public class CommonTopicAdapter extends ArrayAdapter {

    private TextView topic;
    private Context context;
    private List<CommonTopics> list;

    public CommonTopicAdapter(@NonNull Context context, List<CommonTopics> list) {
        super(context, R.layout.row_common_topics, list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // Toast.makeText(context, "get View called:"+list.size(), Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try{

            convertView = inflater.inflate(R.layout.row_common_topics,parent,false);

            topic = convertView.findViewById(R.id.topic);
            topic.setText(" * "+list.get(position).getSubtopic());
           // Toast.makeText(context, "v:"+list.get(position).getSubtopic(), Toast.LENGTH_SHORT).show();


        }catch (Exception ex) {
            Toast.makeText(getContext(), "CommonTopicsAdapter Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return  convertView;

    }
}
