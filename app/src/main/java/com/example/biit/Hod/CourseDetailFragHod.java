package com.example.biit.Hod;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;

import com.example.biit.Hod.Adapters.HodClosExpandableAdapter;
import com.example.biit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CourseDetailFragHod extends Fragment {

    private View view;

    private TabHost tabhost;

    private ListView teachersList;
    private ExpandableListView clos;
    private Db db;
    private List<String> cloGroup;
    private HashMap<String, List<String>> cloItems;
    private HodClosExpandableAdapter adapter;
    private ListView commonTopics;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Helper.folderItem = false;
        view = inflater.inflate(R.layout.courses_detail_frag_hod,container,false);

        commonTopics = view.findViewById(R.id.common_topic_list);
        new Db(getContext()).loadCommonTopics(commonTopics);

        loadTeachers();
        loadClos();
      /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after .5 seconds
                loadTeachers();
                loadClos();
             //   new LoadCommonTopics(getContext(),commonTopics);

            }
        }, 500);*/



        // initiating the tabhost
        tabhost = view.findViewById(R.id.tabHardCoded);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("Teachers");
        spec.setContent(R.id.tab1);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("Teachers");

        // adding the tab to tabhost
        tabhost.addTab(spec);

        // Code for adding Tab 2 to the tabhost
        spec = tabhost.newTabSpec("CLOS");
        spec.setContent(R.id.tab2);

        //to display tab2 contents
        // setting the name of the tab 1 as "Tab Two"
        spec.setIndicator("CLOS");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("C-Topics");
        spec.setContent(R.id.C_Topics);

        //to display tab2 contents
        // setting the name of the tab 1 as "Tab Two"
        spec.setIndicator("C-Topics");
        tabhost.addTab(spec);

        return view;
    }



    private void loadTeachers(){
        teachersList = view.findViewById(R.id.course_detail_frag_teachers);
        Helper.reload = teachersList; //for reload purpose after master update
        db = new Db(getContext());
        db.loadTeachersCoursesHod(teachersList);
    }

    private void loadClos(){

        try{
           // Toast.makeText(getContext(), "cid is:"+Helper.selectedCourseId, Toast.LENGTH_SHORT).show();
            clos = view.findViewById(R.id.course_detail_frag_clos);
            cloGroup = new ArrayList<>();
            cloItems = new HashMap<>();
            adapter = new HodClosExpandableAdapter(getContext(),cloGroup,cloItems);
            clos.setAdapter(adapter);
            setUpExpandableClosList();
        }catch (Exception ex){
            Toast.makeText(getContext(), "loadCloFragHod:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpExpandableClosList(){

        try{

            String[] headers = new String[]{"Clo1", "Clo2", "Clo3","Clo4","Clo5","Clo6"};

            //Update Weightage
            //for update button
            String[] child = new String[]{"Total","Mid Exams","Final Exams","Quizzes","Assignments", "Update Weightage"};

            for(String i:headers) {
                List<String> topics_child = new ArrayList<>();

                for (String y:child) {
                    topics_child.add(y);

                }
                cloGroup.add(i);
                cloItems.put(i,topics_child);

            }

            adapter.notifyDataSetChanged();
        }catch (Exception ex){
            Toast.makeText(getContext(), "setUpExpandableClosListFragHod:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    }
