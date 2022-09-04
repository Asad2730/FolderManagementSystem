package com.example.biit.Hod.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Classes.Teacher;
import com.example.biit.Db.Db;
import com.example.biit.R;
import com.example.biit.Tasks.Task;
import com.example.biit.Tasks.TaskActivity;

import java.util.List;

public class HodTeachersMasterAdapter  extends ArrayAdapter {

    private TextView name,master,covered;
    private Context context;
    private List<Teacher> list;
    private String b1 ="Master";
    private String b2 = "Assign Master";
    private  String btn;

    public HodTeachersMasterAdapter(@NonNull Context context, List<Teacher> list) {
        super(context, R.layout.row_hod_teachers_master,list);
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try{

            convertView = inflater.inflate(R.layout.row_hod_teachers_master,parent,false);


            if(list.get(position).getMaster().equals("null") || list.get(position).getMaster().equals("n")){
                btn = b2;
            }else{
                btn = b1;
            }

            name = convertView.findViewById(R.id.row_teacherName);
            master = convertView.findViewById(R.id.row_master);
            covered = convertView.findViewById(R.id.row_covered);

            name.setText(list.get(position).getName());
            master.setText(btn);


            covered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "id:"+list.get(position).getTid()+"\n"
                            +list.get(position).getCid()+"\n"+list.get(position).getAid()
                            , Toast.LENGTH_SHORT).show();

                    new Db(getContext()).getCovered(list.get(position).getTid(),list.get(position).getCid());

                   Intent i = new Intent(context, TaskActivity.class);
                   context.startActivity(i);

                }
            });

            master.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView btn = (TextView) view;
                    //Toast.makeText(context, "clicked"+btn.getText().toString().equals(b2), Toast.LENGTH_SHORT).show();

                   if(btn.getText().toString().equals(b2)){
                       btn.setText(b1);
                   } else {
                       btn.setText(b2);
                   }

                    String tid = list.get(position).getTid();
                    String m = list.get(position).
                            getMaster().equals("null")||
                            list.get(position).getMaster().equals("n")?"y":"n";

                  // list.get(position).setMaster(m);
                   new Db(context).updateMaster(tid,m);
                }
            });


        }catch (Exception ex) {
            Toast.makeText(getContext(), "CoursesAdapter Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return  convertView;
    }
}
