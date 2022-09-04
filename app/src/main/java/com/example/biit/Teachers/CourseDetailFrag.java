package com.example.biit.Teachers;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.biit.Classes.Data;
import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.R;
import com.example.biit.Teachers.Adapters.CourseDetailAdapter;
import com.example.biit.Upload.UploadActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CourseDetailFrag extends Fragment {

    private View view;
    private Button update_clo, updateContent;
    private ExpandableListView folder, Contents, Clos;
    private List<String> listFolderGroup, listContentsGroup, listCloGroup;
    private HashMap<String, List<String>> listFolderItems, listContentsItems, listCloItems;

    private CourseDetailAdapter adapterFolder, adapterClo, adapterContents;

    private TextView courseClickedFolder, courseClickedContent, cloClickedContent;

    private TabHost tabhost;

    private ListView cloTable;
   // private CloTableViewAdapter adapterTable;

    private ListView commonTopics;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        try {

           //++++
            new Db(getContext()).loadTopics_SubTopics();

            view = inflater.inflate(R.layout.course_detail_frag, container, false);

            commonTopics = view.findViewById(R.id.c_list);



            Helper.contents = new HashMap<>();
            Helper.clos = new HashMap<>();

            if(Helper.contents != null){
                Helper.contents.clear();
            }
            if(Helper.clos != null){
                Helper.clos.clear();
            }


            //loading in both activities Hod & Teacher for better performance
           // new Db(getContext()).loadFolderContents();
           // new Db(getContext()).loadTopics_SubTopics();
            new Db(getContext()).loadCommonTopics(commonTopics);

            Helper.folderItem = false;


            // initiating the tabhost
            tabhost = view.findViewById(R.id.tab_host_course_detail);

            // setting up the tab host
            tabhost.setup();

            // Code for adding Tab 1 to the tabhost
            TabHost.TabSpec spec = tabhost.newTabSpec("Folder");
            spec.setContent(R.id.Folder);

            // setting the name of the tab 1 as "Tab One"
            spec.setIndicator("Folder");

            // adding the tab to tabhost
            tabhost.addTab(spec);

            // Code for adding Tab 2 to the tabhost
            spec = tabhost.newTabSpec("Content");
            spec.setContent(R.id.Contents);

            //to display tab2 contents
            // setting the name of the tab 1 as "Tab Two"
            spec.setIndicator("Content");
            // adding the tab to tabhost
            tabhost.addTab(spec);

            if(Helper.isMaster) {
                // Code for adding Tab 2 to the tabhost
                spec = tabhost.newTabSpec("Clos");
                spec.setContent(R.id.Clos);
                //to display tab2 contents
                // setting the name of the tab 1 as "Tab Two"
                spec.setIndicator("Clos");
                tabhost.addTab(spec);
            }

            spec = tabhost.newTabSpec("Table");
            spec.setContent(R.id.clos_table_layout);

            //to display tab2 contents
            // setting the name of the tab 1 as "Tab Two"
            spec.setIndicator("Table");
            tabhost.addTab(spec);


            //common Topics
            spec = tabhost.newTabSpec("C-Topics");
            spec.setContent(R.id.C_Topics);

            //to display tab2 contents
            // setting the name of the tab 1 as "Tab Two"
            spec.setIndicator("C-Topics");
            tabhost.addTab(spec);


            //handlers
            loadFolder(view);
           // loadContents(view);

            //Toast.makeText(getContext(), "cid:"+Helper.course_clicked_code, Toast.LENGTH_SHORT).show();
          //  new Db(getContext()).loadTopics_SubTopics();
               // loadContents(view);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 1 seconds
                    //course Detail Frag
                    if(Helper.isMaster)
                    {
                        loadClos(view);
                    }
                    loadContents(view);

                }
            }, 1000);


            cloTable = view.findViewById(R.id.clo_table);
            new Db(getContext()).loadCloTable(Helper.selectedCourseId,cloTable);

            update_clo = view.findViewById(R.id.course_detail_update_clo);
            updateContent = view.findViewById(R.id.course_detail_update_content);


            if(!Helper.isMaster){

                update_clo.setVisibility(View.GONE);
            }

            Helper.updateClos = update_clo;
            Helper.updateContents = updateContent;

        } catch (Exception ex) {
            Toast.makeText(getContext(), "CourseDetailFrag:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }


//master logic
    private void loadFolder(View view) {

        try {
            courseClickedFolder = view.findViewById(R.id.course_detail_header_folder);
            courseClickedFolder.setText(Helper.course_clicked + " | Master FOLDER");
            folder = view.findViewById(R.id.course_folder_list);
            listFolderGroup = new ArrayList<>();
            listFolderItems = new HashMap<>();
            adapterFolder = new CourseDetailAdapter(getContext(), listFolderGroup, listFolderItems, 1, "");
            folder.setAdapter(adapterFolder);
            initListData(1);

            folder.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    //Toast.makeText(getContext(), "Clicked:"+i+" & "+i1, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getContext(), "Clicked:"+headersArr[i], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), FolderActivity.class);
                    Intent uploads = new Intent(getContext(), UploadActivity.class);

                    switch (Helper.headersFolderArr.get(i).getDescription()) {
                        case "Course Content":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "Week Plan":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "Attendance":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "Notes and Manual":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "Questions":
                            upload(Helper.headersFolderArr.get(i).getDescription(),intent);
                            break;

                        case "Solutions":
                            upload(Helper.headersFolderArr.get(i).getDescription(),intent);
                            break;

                        case "Samples":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "Result":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;

                        case "FCR":
                            upload(Helper.headersFolderArr.get(i).getDescription(),uploads);
                            break;
                        default:
                            break;
                    }

                    return true;
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception DetailFrag:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadContents(View view) {

        try {
            courseClickedContent = view.findViewById(R.id.course_detail_header_Contents);
            courseClickedContent.setText(Helper.course_clicked + " | Contents");

            Contents = view.findViewById(R.id.course_Contents_list);

            listContentsGroup = new ArrayList<>();
            listContentsItems = new HashMap<>();

            adapterContents = new CourseDetailAdapter(getContext(), listContentsGroup, listContentsItems,
                    2, "Contents");
            Contents.setAdapter(adapterContents);


            initListData(3);

            Contents.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    return true;
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception DetailFrag 2:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void loadClos(View view) {

      try{
        cloClickedContent = view.findViewById(R.id.course_detail_header_Clos);
        cloClickedContent.setText(Helper.course_clicked + " | CLOS");

        Clos = view.findViewById(R.id.course_Clos_list);

        listCloGroup = new ArrayList<>();
        listCloItems = new HashMap<>();

        adapterClo = new CourseDetailAdapter(getContext(), listCloGroup, listCloItems, 2, "CLos");
        Clos.setAdapter(adapterClo);

        initListData(2);

        Clos.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return true;
            }
        });

        if(!Helper.isMaster){
            Clos.setVisibility(View.GONE);
            cloClickedContent.setVisibility(View.GONE);
        }

    }catch(Exception ex)

    {
        Toast.makeText(getContext(), "Exception DetailFrag 2:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

}


    private void  initListData(int op){
        try {
            //folders
        if(op == 1) {
           // Toast.makeText(getContext(), "called:"+ Helper.headersFolderArr.size(), Toast.LENGTH_SHORT).show();
            for (Data i : Helper.headersFolderArr) {
                listFolderGroup.add(i.getDescription());
            }
            for (int i = 0; i < listFolderGroup.size(); i++)
                listFolderItems.put(listFolderGroup.get(i), Collections.singletonList("Add Files"));



            adapterFolder.notifyDataSetChanged();
        }
            //clos
                if(op == 2) {

                     String[] itemsForClo = new String[]{"Clo1", "Clo2", "Clo3","Clo4","Clo5","Clo6"};
                    for(Data i:Helper.topics) {
                        List<String> topics_child = new ArrayList<>();

                        for (String y:itemsForClo) { topics_child.add(y); }

                        listCloGroup.add(i.getName());
                        listCloItems.put(i.getName(),topics_child);

                       // Toast.makeText(getContext(), "here"+listCloGroup.size(), Toast.LENGTH_SHORT).show();

                    }


                    adapterClo.notifyDataSetChanged();
                }

                //Contents
            if(op == 3) {
               // Toast.makeText(getContext(), "l:"+Helper.topics.size(), Toast.LENGTH_SHORT).show();
                for(Data i:Helper.topics) {
                    List<String> topics_child = new ArrayList<>();

                    for (Data y:Helper.subtopics) {
                        if (y.getTid().equals(i.getTid())) {
                            for (String s : y.title) {
                                topics_child.add(s);
                            }

                            listContentsGroup.add(i.getName());
                            listContentsItems.put(i.getName(),topics_child);
                        }
                    }

                }

               /*
                if(Helper.topics.size() <= 0){
                    CardView cardView = view.findViewById(R.id.cardContents);
                    cardView.setVisibility(View.INVISIBLE);
                }*/
                adapterContents.notifyDataSetChanged();
            }


        }catch (Exception ex){
            if(op == 3){
                new Db(getContext()).loadTopics_SubTopics();
                loadContents(view);

            }
            if(op == 2){
                loadClos(view);
            }
            Toast.makeText(getContext(), "initListData:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //upload
    private void upload(String des,Intent i){

        i.putExtra("des",des);
        startActivity(i);
    }

}
