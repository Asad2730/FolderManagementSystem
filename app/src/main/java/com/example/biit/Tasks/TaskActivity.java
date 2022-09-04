package com.example.biit.Tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.biit.Classes.Data;
import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.Hod.Adapters.HodTeachersMasterAdapter;
import com.example.biit.R;
import com.example.biit.Teachers.MyTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private ListView list;
    private List<String> header;
    private  HashMap<String, List<String>> items;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_task);

            list = findViewById(R.id.task_list);
            //Db.my
            MyTask ad = new MyTask(getApplicationContext(),Db.task);
            list.setAdapter(ad);
            ad.notifyDataSetChanged();
           //t initListData();

        }catch (Exception ex){

        }

    }


    private void  initListData(){
        try {
            header = new ArrayList<>();
            items = new HashMap<>();
            List<String> hd = new ArrayList<>();

            for (Task t:Db.task){

             hd.add( t.getTopic());
            }

            Toast.makeText(getApplicationContext(), hd.size()+"   "+Db.task.size(), Toast.LENGTH_SHORT).show();

                for(String s : hd) {
                    List<String> topics_child = new ArrayList<>();

                    for (Task y : Db.task) {
                        if(s.equals(y.getTopic())) {
                            topics_child.add(y.getSubtopic());
                            Toast.makeText(this, "y:", Toast.LENGTH_SHORT).show();
                        }
                    }

                    header.add(s);
                    items.put(s, topics_child);
                }




            Toast.makeText(this, header.size()+""+items.size(), Toast.LENGTH_SHORT).show();
            adapter = new TaskAdapter(getApplicationContext(),header,items);
            //list.setAdapter(adapter);

           adapter.notifyDataSetChanged();
        }catch (Exception ex){
           // Toast.makeText(getApplicationContext(), "initListData:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}


   /* String[] itemsForClo = new String[]{"BasicCodeStructure of Code", "Header files",
            "cout statement,insertion operators","Data types","main function","arthmetic operators","main function",
            "Integer Data type","Char Data type","Unary Operator"};*/