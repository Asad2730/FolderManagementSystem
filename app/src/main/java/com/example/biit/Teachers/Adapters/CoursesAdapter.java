package com.example.biit.Teachers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Classes.Courses;
import com.example.biit.Db.Db;
import com.example.biit.R;

import java.util.List;

public class CoursesAdapter extends ArrayAdapter {


    private TextView courseCode,section,courseName,covered;
    private Context context;
    private List<Courses> list;
    private String option;

    public CoursesAdapter(@NonNull Context context, List<Courses> list,String option) {
        super(context, R.layout.row_courses,list);
        this.context = context;
        this.list = list;
        this.option = option;
      //  Toast.makeText(context, "len:"+list.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Toast.makeText(context, "sss", Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try{
            convertView = inflater.inflate(R.layout.row_courses,parent,false);

            courseCode = convertView.findViewById(R.id.row_course_code);
            section = convertView.findViewById(R.id.row_discipline_section);
            courseName = convertView.findViewById(R.id.row_course_name);
            covered = convertView.findViewById(R.id.row_covered);

           // Toast.makeText(context, "l:"+list.get(position).getName(), Toast.LENGTH_SHORT).show();
            if(option.equals("teacher")) {
               // covered.setVisibility(View.GONE);
                courseName.setVisibility(View.GONE);
                courseCode.setText(list.get(position).getCourse_no());
                String sectionDetail = list.get(position).getDiscipline() + "-" + list.get(position).getSemC() + " "
                        + list.get(position).getSection();

                section.setText(sectionDetail);
            }

            else {

                courseCode.setVisibility(View.GONE);
                section.setVisibility(View.GONE);
               // courseCode.setText(list.get(position).getCourse_no());
                courseName.setText(list.get(position).getCourse_desc());
            }

        }catch (Exception ex) {
            Toast.makeText(getContext(), "CoursesAdapter Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
//            new Db().
        }

        return  convertView;
    }
}
