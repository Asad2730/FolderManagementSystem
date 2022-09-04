package com.example.biit.Hod.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.R;

import java.util.HashMap;
import java.util.List;

public class HodClosExpandableAdapter  extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listGroup;
    private HashMap<String, List<String>> listItems;
    private TextView header, childFolder;
    private EditText weightage;
    private TextView total;
   // private List<String> mids,finals,q1,q2,q3,a1,a2,a3 = new ArrayList<>();
   String mids="0",finals="0",q="0",a="0";



    public HodClosExpandableAdapter(Context context, List<String> group, HashMap<String,
            List<String>> items){

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
       // Toast.makeText(context, "i:"+i+" i1:"+i1, Toast.LENGTH_SHORT).show();
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
            view = inflater.inflate(R.layout.hod_clos_adapter_child, null);

           // weightage.setText("0");
        }
        weightage = view.findViewById(R.id.hod_clo_adapter_child_items_input);
        total = view.findViewById(R.id.total);
        childFolder = view.findViewById(R.id.hod_clo_adapter_child_items);

        if(i1 == 0){
            total.setText("");
            weightage.setVisibility(View.GONE);
            total.setVisibility(View.VISIBLE);
            childFolder.setVisibility(View.GONE);
            total.setGravity(Gravity.CENTER);
            double ttl = Double.parseDouble(finals) + Double.parseDouble(mids) + Double.parseDouble(q) + Double.parseDouble(a);
            if(ttl <= 100.00) {
                total.setText(ttl+"");
            }
            else {
                total.setText("Max limit exceeded");
                //Toast.makeText(context, "Max limit exceeded", Toast.LENGTH_SHORT).show();
            }

        }

       if(i1 == 5){
           total.setTextSize(14);
           weightage.setVisibility(View.GONE);
           total.setVisibility(View.GONE);
           childFolder.setGravity(Gravity.CENTER);
           childFolder.setTextSize(16);
           childFolder.setTextColor(Color.parseColor("#0000FF"));
           childFolder.setEnabled(true);
           childFolder.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String cid = Helper.selectedCourseId;
                   String clo = "Clo"+(i+1);
                   Toast.makeText(context, clo+" -Data:"+mids+" "+finals+" "+ q+ " "+a, Toast.LENGTH_SHORT).show();
                   //Toast.makeText(context, "i"+i, Toast.LENGTH_SHORT).show();
                  new Db(context).updateCloWeightage(cid,clo,mids,finals,q,a);

               }
           });

       }
       if(i1 != 0 && i1 != 5){

           weightage.setVisibility(View.VISIBLE);
           total.setVisibility(View.GONE);
           childFolder.setGravity(Gravity.CENTER_VERTICAL);
           childFolder.setTextSize(14);
           childFolder.setTextColor(Color.parseColor("#000000"));
          childFolder.setEnabled(false);

          //weightage.setText("0");
          weightage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView textView, int ind, KeyEvent keyEvent) {
                 // Toast.makeText(context, "v:"+textView.getText().toString()+" i1:"+i1, Toast.LENGTH_SHORT).show();
                  if(textView.getText().toString().isEmpty()){
                      //do nothing
                  }else {
                      save(i1,textView.getText().toString());

                  }
                  return false;
              }
          });



       }
        childFolder.setText(data);


        return view;
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        //Toast.makeText(context, "i:"+i+" i1:"+i1, Toast.LENGTH_SHORT).show();
        return false;
    }


    private void save(int pos,String val){
       // mids="0";finals="0";q1="0";q2="0";q3="0";a1="0";a2="0";a3="0";
        //Toast.makeText(context, "pos is "+ pos, Toast.LENGTH_SHORT).show();
        switch (pos){
            case 0:mids = val; break;
            case 1:finals = val; break;
            case 2:q = val; break;
            case 3:a = val; break;
            default:break;
        }


    }
}
