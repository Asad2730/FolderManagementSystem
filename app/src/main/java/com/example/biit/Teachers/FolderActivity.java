package com.example.biit.Teachers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.biit.R;
import com.example.biit.Teachers.Adapters.FolderAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private ExpandableListView list1,list2,list3;
    private Button back;
    private List<String> group1,group2,group3;
    private HashMap<String ,List<String>> item1,item2,item3;
    private TabHost tabhost;

    private String des;

    private FolderAdapter adapter1,adapter2,adapter3;

    private String[] header1 = {"Quizzes"};
    private String[] header2 = {"Assignments"};
    private String[] header3 = {"Mid-Term","Final-Term"};
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        des = getIntent().getStringExtra("des");
        Toast.makeText(getApplicationContext(), "des:"+des, Toast.LENGTH_SHORT).show();

        list1 = findViewById(R.id.folder_list1);
        list2 = findViewById(R.id.folder_list2);
        list3 = findViewById(R.id.folder_list3);

        back = findViewById(R.id.folder_back);

        tabhost = findViewById(R.id.folderTabHost);
        // setting up the tab host

        context = FolderActivity.this;
        tabhost.setup();


        TabHost.TabSpec spec = tabhost.newTabSpec("Quizzes");
        spec.setContent(R.id.tab1);

        spec.setIndicator("Quizzes");

        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Assignments");
        spec.setContent(R.id.tab2);

        spec.setIndicator("Assignments");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Exam");
        spec.setContent(R.id.tab3);

        spec.setIndicator("Exam");
        tabhost.addTab(spec);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Helper.folderItem = true; //finish were ever this is
                finish();
            }
        });


        //load tabs that u want to
        loadTab1();
        loadTab2();
        loadTab3();

    }

    private void loadTab1(){

        try {
            group1 = new ArrayList<>();
            item1 = new HashMap<>();
            adapter1 = new FolderAdapter(context,group1,item1,des);
            list1.setAdapter(adapter1);
            initListData(1);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ex:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadTab2(){

        try {
            group2 = new ArrayList<>();
            item2 = new HashMap<>();
            adapter2 = new FolderAdapter(context,group2,item2,des);
            list2.setAdapter(adapter2);
            initListData(2);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ex:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadTab3(){

        try {
            group3 = new ArrayList<>();
            item3 = new HashMap<>();
            adapter3 = new FolderAdapter(context,group3,item3,des);
            list3.setAdapter(adapter3);
            initListData(3);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ex:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void  initListData(int op){

        try {
            if(op == 1){
                for (String i : header1)
                    group1.add(i);

                for (int i = 0; i < group1.size(); i++)
                    item1.put(group1.get(i), Collections.singletonList("Upload File"));

                //adapterFolder.notifyDataSetChanged();
            }
            if(op == 2){

                for (String i : header2)
                    group2.add(i);

                for (int i = 0; i < group2.size(); i++)
                    item2.put(group2.get(i), Collections.singletonList("Upload File"));
            }

            if(op ==3){

                for (String i : header3)
                    group3.add(i);

                for (int i = 0; i < group3.size(); i++)
                    item3.put(group3.get(i), Collections.singletonList("Upload File"));
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "FolderEx:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}