package com.example.biit.Teachers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Classes.CheckedValues;
import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.R;

import java.util.List;

class Contents_CloItemAdapter extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener {

    private CheckBox txt;
    private Spinner ddlContents;
    private Context context;
    private List<String>items,headers;

    private String[] itemsForContents = new String[]{"Week 1", "Week 2", "Week 3","Week 3","Week 4","Week 5","Week 6","Week 7","Week 8"};
    private String load;
    private ArrayAdapter<String> adapter;
    private String d = "s";


    public Contents_CloItemAdapter(@NonNull Context context, List<String> items,String load,List<String> headers) {
        super(context, R.layout.expandable_contents_tab_item,items);
        this.context = context;
        this.items = items;
        this.load = load;
        this.headers = headers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try{
            convertView = inflater.inflate(R.layout.expandable_contents_tab_item,parent,false);



            ddlContents = convertView.findViewById(R.id.ddl_clo);
            txt = convertView.findViewById(R.id.list_clo_item);
            Boolean con=false;

            for(String s: Db.topic){
                if(s.equals(items.get(position))){
                    con = true;
                }
            }
            txt.setText(items.get(position));

            if(load.equals("Contents")) {
                //layout for when list appears not mandatory
               // adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                txt.setOnCheckedChangeListener(this);
                txt.setChecked(con);

            }
            if(load.equals("CLos")){
              //  adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemsForClo);
                ddlContents.setVisibility(View.GONE);
                txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        CheckBox c = (CheckBox)  compoundButton;
                        String s = c.getText().toString();
                        if(c.isChecked()) {
                            Helper.deleteFromMap(s, Helper.clos);
                            Helper.insertIntoMap(s, setMapObject(s), Helper.clos);
                        }else {
                            Helper.deleteFromMap(s, Helper.clos);
                        }
                    }
                });
            }


            update(position);
        }catch (Exception ex) {
            Toast.makeText(getContext(), "Clo Item Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return  convertView;
    }




    private  void update(int pos){

        Helper.updateContents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Helper.contents.isEmpty()) {
                    Toast.makeText(context, ""+headers.get(0), Toast.LENGTH_SHORT).show();
                    new Db(getContext()).updateContents_clos(Helper.contents,"Contents",headers.get(0),d);
                }

            }
        });


        Helper.updateClos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Helper.clos.isEmpty()) {
                    d = "";
                    new Db(getContext()).updateContents_clos(Helper.clos,"Clos",headers.get(0),d);
                }

            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        try {
            CheckBox c = (CheckBox) compoundButton;
            //Toast.makeText(context, "b:"+b, Toast.LENGTH_SHORT).show();
            if(!b){
                d = "d";
            }

            if (c.isChecked()) {

                adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, itemsForContents);
                ddlContents.setAdapter(adapter);
                // ddlContents.setSelection(position,false);
                ddlContents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (c.isChecked()) {
                            Spinner s = (Spinner) adapterView;
                            String k = c.getText().toString();
                            String v = s.getSelectedItem().toString();
                            Helper.deleteFromMap(k,Helper.contents);

                            Helper.insertIntoMap(k, setMapObject(v), Helper.contents);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                //ddlContents.performContextClick();

            } else {
             Helper.deleteFromMap(c.getText().toString(),Helper.contents);
            }

        }catch (Exception ex){
            Toast.makeText(getContext(), "onCheckedChanged:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private CheckedValues setMapObject(String value){
        String[] code_section = Helper.course_clicked.split("\\|");
        CheckedValues obj = new CheckedValues();
        obj.setVal(value);
        obj.setTid(Helper.logged_user);
        obj.setCid(code_section[0]);
        obj.setSection(code_section[1]);
        return  obj;
    }

}
