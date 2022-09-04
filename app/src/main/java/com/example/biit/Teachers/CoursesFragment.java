package com.example.biit.Teachers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.R;

public class CoursesFragment extends Fragment {

    private ListView list;
    private View view;
    private Db db;


    public interface  onItemClicked{
        public void onItemSelected(int index);
    }

    private onItemClicked contextClick;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.courses_fragement,container,false);
        list = view.findViewById(R.id.courses_list);
        db = new Db(getContext());
        db.loadCourses(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String section= Helper.courses.get(i).getDiscipline()+"-"+Helper.courses.get(i).getSemC()+" "
                +Helper.courses.get(i).getSection();

        String header = Helper.courses.get(i).getCourse_no()+" | "+section;

        String courseCode = Helper.courses.get(i).getCourse_no();
        Helper.course_clicked = header;
        Helper.course_clicked_code = courseCode;
        new Db(getContext()).loadTopics_SubTopics();
        Toast.makeText(getContext(), "this:"+courseCode, Toast.LENGTH_SHORT).show();
        contextClick.onItemSelected(i);
    }
     });
     return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onItemClicked){
            contextClick = (onItemClicked) context;
        }
        else {
            throw new ClassCastException("must implement listener");
        }
    }
}

