package com.example.biit.Teachers.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biit.R;
import com.example.biit.Upload.UploadActivity;

import java.util.HashMap;
import java.util.List;

public class FolderAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listGroup;
    private HashMap<String, List<String>> listItems;
    private TextView header, childFolder;
    private String des;
    private LinearLayout layout;


    public FolderAdapter(Context context, List<String> group, HashMap<String,
            List<String>> items,String des) {
        this.context = context;
        this.listGroup = group;
        this.listItems = items;
         this.des = des;
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

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_adapter_header, null);
        }


        header = view.findViewById(R.id.list_header_folder);
        header.setText(data);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String data = (String) getChild(i, i1);

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.folder_adapter_child, null);
        }

        childFolder = view.findViewById(R.id.list_folder_child);
        childFolder.setText(data);
        childFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   String d= des+","+getGroup(i).toString();
                    showDialog(d);
                }catch (Exception ex){
                    Toast.makeText(context,"Ex FolderAdapter:"+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {

       // Toast.makeText(context, "i:"+i+" "+"i1:"+i1, Toast.LENGTH_SHORT).show();
        return true;
    }



    private void showDialog(String d){

        Intent i = new Intent(context, UploadActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_buttons,null);
        Button best = view.findViewById(R.id.alert_best);
        Button worst = view.findViewById(R.id.alert_worst);
        Button average = view.findViewById(R.id.alert_average);

        builder.setView(view);
       // builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Best"+d, Toast.LENGTH_SHORT).show();
                i.putExtra("des",d+",best");
                context.startActivity(i);
                dialog.dismiss();
            }
        });

        worst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Worst"+d, Toast.LENGTH_SHORT).show();
                i.putExtra("des",d+",worst");
                context.startActivity(i);
                dialog.dismiss();

            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Average"+d, Toast.LENGTH_SHORT).show();
                i.putExtra("des",d+",average");
                context.startActivity(i);
                dialog.dismiss();
            }
        });


        dialog.show();

    }

}
