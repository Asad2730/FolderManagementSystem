package com.example.biit.Hod;


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

import com.example.biit.Db.Db;
import com.example.biit.R;

public class HodCoursesFrag extends Fragment {

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

        try{

            view = inflater.inflate(R.layout.courses_fragement,container,false);
            list = view.findViewById(R.id.courses_list);
            db = new Db(getContext());
             db.loadCoursesHod(list);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    contextClick.onItemSelected(i);
                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(), "HodCourseFrag:"+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

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

