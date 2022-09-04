package com.example.biit.Tasks;

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
import java.util.HashMap;
import java.util.List;


public class TaskAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listGroup;
    private HashMap<String,List<String>> listItems;
    private TextView header,childFolder;

    public TaskAdapter(Context context,List<String> group, HashMap<String, List<String>> items){
        this.context = context;
        this.listGroup = group;
        this.listItems = items;
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
            view = inflater.inflate(R.layout.task_header,null);
        }


        header = view.findViewById(R.id.header);
        header.setText(data);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String data = (String) getChild(i,i1);
        //Toast.makeText(context, "i"+data, Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_item,null);
            childFolder = view.findViewById(R.id.item);
            childFolder.setText(data);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
