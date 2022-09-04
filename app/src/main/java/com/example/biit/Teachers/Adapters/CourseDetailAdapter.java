package com.example.biit.Teachers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseDetailAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listGroup;
    private HashMap<String,List<String>> listItems;
    private TextView header,childFolder;
    private int folder_clo;
    private LinearLayout layout;
    private ListView childClo;
    private Contents_CloItemAdapter adapter;
    private String loadCloOrContents;


    public CourseDetailAdapter(Context context,List<String> group, HashMap<String,
            List<String>> items,int folderOr_clo,String loadCloOrContents){
        this.context = context;
        this.listGroup = group;
        this.listItems = items;
        this.folder_clo = folderOr_clo;
        this.loadCloOrContents = loadCloOrContents;


    }

    @Override
    public int getGroupCount() {
        return this.listGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listItems.get(listGroup.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {

        return this.listItems.get(this.listGroup.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String data = (String) getGroup(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.course_detail_expandable_list_header,null);
        }


        header = view.findViewById(R.id.list_header);
        header.setText(data);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String data = (String) getChild(i,i1);
        //Toast.makeText(context, "headers:"+data, Toast.LENGTH_SHORT).show();
        if(view == null){

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.course_detail_expandable_list_item,null);
        }

        if(this.folder_clo == 1){
          //  Toast.makeText(context, "data:"+data, Toast.LENGTH_SHORT).show();
            layout = view.findViewById(R.id.clo);
            childFolder = view.findViewById(R.id.list_folder_item);
            childFolder.setText(data);
        }
        else if(this.folder_clo == 2){

              List<String> list = new ArrayList<>();
               layout = view.findViewById(R.id.folder);
               childClo = view.findViewById(R.id.list_clo);
               //updateClo = view.findViewById(R.id.updateClo);

                //to get value of headers
                 String headers = (String) getGroup(i);
                 List<String> header = new ArrayList<>();
                if(headers.isEmpty() || header.size() == 0){
                    header.add(headers);

                }else{
                    boolean contains = header.contains(headers);
                    if(!contains){
                        header.add(headers);
                    }
                }

                list.add((String) getChild(i,i1));
                adapter = new Contents_CloItemAdapter(context,list,loadCloOrContents,header);
                childClo.setAdapter(adapter);
                adapter.notifyDataSetChanged();

        }

        layout.setVisibility(View.GONE);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
