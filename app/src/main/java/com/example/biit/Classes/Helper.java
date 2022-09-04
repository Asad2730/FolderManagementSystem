package com.example.biit.Classes;

import android.widget.Button;
import android.widget.ListView;

import com.example.biit.CommonTopics.CommonTopics;

import java.util.List;
import java.util.Map;

public class Helper {


    public final static String HOD = "BIIT179";
    public static String logged_user ;
    public static String course_clicked;
    public static String course_clicked_code;
    public static List<Courses> courses;
    public static List<Data> headersFolderArr;
    public static List<Data> topics;
    public static List<Data> subtopics;
    public static boolean folderItem = false;
    public static List<Courses> hod_courses_list;

    public static List<Teacher> hod_teachers_list;
    public static  String selectedCourseId;
    public static List<Clos> closList;


    public static Boolean isMaster = false;

    public static List<CommonTopics> commonTopics;

    //Course DetailFrag //for reload purpose after updating master
    public static ListView reload;


    //CourseDetailFrag//for-Contents_CloItemAdapter
    public  static Button updateContents,updateClos;

    //Contents_CloItemAdapter *
    public static Map<String, CheckedValues> contents = null,clos =null;

   //  public  static Bitmap b;

    public static void insertIntoMap(String key,CheckedValues values, Map<String,CheckedValues> map){
        map.put(key,values);
    }

    public static void deleteFromMap(String key, Map<String,CheckedValues> map){
        boolean contains = map.containsKey(key);
        if (contains) {
           map.remove(key);
        }
    }

    //* Contents_CloItemAdapter
}
