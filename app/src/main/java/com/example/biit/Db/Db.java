package com.example.biit.Db;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biit.Classes.CheckedValues;
import com.example.biit.Classes.Clos;
import com.example.biit.Classes.Courses;
import com.example.biit.Classes.Upload;
import com.example.biit.CommonTopics.CommonTopicAdapter;
import com.example.biit.CommonTopics.CommonTopics;

import com.example.biit.Classes.Data;
import com.example.biit.Classes.Helper;

import com.example.biit.Classes.Teacher;
import com.example.biit.Hod.Adapters.HodTeachersMasterAdapter;
import com.example.biit.Tasks.Task;
import com.example.biit.Teachers.Adapters.CloTableViewAdapter;
import com.example.biit.Teachers.Adapters.CourseDetailAdapter;
import com.example.biit.Teachers.Adapters.CoursesAdapter;
import com.example.biit.Teachers.CourseDetailFrag;
import com.example.biit.Teachers.TeacherActivity;
import com.example.biit.Upload.adapter.PdfAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Db {

   // public static final  String local = "localhost";
    //http://192.168.224.86/FypApi/api/My/viewAll
    public static final String ip ="192.168.225.86";
    private final String url ="http://"+ip+"/FypApi/api/My/";
    private final String login_user = url+"viewAll";
    private final String user_courses = url+"getCoursesForLoggedUser?id=";
    private final String folder_contents = url+"fetchFolderContents";
    private final String fetchTopics_SubTopics = url+"fetchTopics_and_subTopics?cid=";
    private final String fetch_Courses = url+"getCoursesName";
    private  final String fetchTeachers = url+"getTeachers?cid=";
    private final String insertClos = url+"insertClos";
    private final String getClos = url+"getClos?cid=";
    private final String insertPdf = url+"UploadFile";
    private final String updateMaster = url+"updateMaster";
    private final String loadCommonTopics = url +"commonTopics";
    private final String fetchUploads = url +"fetchMyUploads?tid=";
    private  final String cvr = url +"getCovered?tid=";

    private boolean userMatched;

    private RequestQueue queue;
    private Context context;

    public static List<String> topic;
    public static List<Task> task;


    public Db(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

  public void coveredTopics(String tid){
           topic  = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, cvr + Helper.logged_user, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int  i=0;i<response.length();i++) {
                            try {
                                topic.add(response.getJSONObject(i).getString("subtopic"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },null);

        queue.add(req);
  }

    public void loginUser(String id,String password){
     userMatched = false;
        try{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, login_user, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    if (id.equals(response.getJSONObject(i).getString("Emp_no"))
                                     && password.equals("123")) {
                                          userMatched = true;
                                          Helper.logged_user = id;
                                          String master = response.getJSONObject(i).getString("master");
                                          if(master.equals("y")){
                                              Helper.isMaster = true;
                                            coveredTopics(Helper.logged_user);
                                          }
                                         Intent intent = new Intent(context, TeacherActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                         context.startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(!userMatched) {
                                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }

                        }
                    },null);

            queue.add(request);


        }catch (Exception ex){
            Toast.makeText(context, "Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void loadCourses(ListView list){
        try{

            if( Helper.courses != null){
                //Toast.makeText(context,"Clearing..",Toast.LENGTH_LONG).show();
                Helper.courses.clear();
            }else{
                Helper.courses = new ArrayList<>();
            }


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    user_courses+Helper.logged_user, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for(int i=0;i<response.length();i++){
                                try {

                                    Courses courses = new Courses();
                                    courses.setId(response.getJSONObject(i).getString("EMP_NO"));
                                    courses.setCourse_no(response.getJSONObject(i).getString("COURSE_NO"));
                                    courses.setDiscipline(response.getJSONObject(i).getString("DISCIPLINE"));
                                    courses.setSection(response.getJSONObject(i).getString("SECTION"));
                                    courses.setSemC(response.getJSONObject(i).getString("SemC"));
                                    courses.setSos(response.getJSONObject(i).getString("SOS"));
                                    courses.setLec1(response.getJSONObject(i).getString("LEC1_DT"));
                                    courses.setLec2(response.getJSONObject(i).getString("LEC2_DT"));
                                    courses.setLec3(response.getJSONObject(i).getString("LEC3_DT"));

                                    courses.setSemester_no(response.getJSONObject(i).getString("SEMESTER_NO"));

                                    Helper.courses.add(courses);

                                   CoursesAdapter adapter = new CoursesAdapter(context,Helper.courses,"teacher");
                                    list.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },null);


            queue.add(jsonArrayRequest);

        }catch (Exception ex){

            Toast.makeText(context, "CoursesFragment exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


   public void loadFolderContents() {

       try {
           if (Helper.headersFolderArr != null) {
               Helper.headersFolderArr.clear();
           } else {
               Helper.headersFolderArr = new ArrayList<>();

           }
           JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, folder_contents, null,
                   new Response.Listener<JSONArray>() {
                       @Override
                       public void onResponse(JSONArray response) {
                           CourseDetailAdapter adapter;
                           Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
                           for (int i = 0; i < response.length(); i++) {
                               try {

                                   Data d = new Data();
                                   d.setId(response.getJSONObject(i).getString("id"));
                                   d.setDescription(response.getJSONObject(i).getString("description"));
                                   d.setFlag(response.getJSONObject(i).getString("flag"));
                                   Helper.headersFolderArr.add(d);

                                //Toast.makeText(context.getApplicationContext(),
                                  //         "Data:"+Helper.headersFolderArr.get(i).getDescription(), Toast.LENGTH_SHORT).show();
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }

                       }
                   }, null);
           queue.add(request);

       } catch (Exception ex) {
           Toast.makeText(context.getApplicationContext(), "loadFolderContents:" + ex.getMessage(),
                   Toast.LENGTH_SHORT).show();
       }
     }


       public void loadTopics_SubTopics(){

           try {
               if( Helper.topics != null ){
                   Helper.topics.clear();
               }else {
                   Helper.topics = new ArrayList<>();

               }

               if(Helper.subtopics != null){
                   Helper.subtopics.clear();
               }else{
                   Helper.subtopics = new ArrayList<>();
               }

               JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                       fetchTopics_SubTopics + Helper.course_clicked_code,
                       null,
                       new Response.Listener<JSONArray>() {
                           @Override
                           public void onResponse(JSONArray response) {

                               for (int i = 0; i < response.length(); i++) {
                                   try {
                                       Data d = new Data();
                                       d.setName(response.getJSONObject(i).getString("name"));
                                       d.setWeeks(response.getJSONObject(i).getString("weeks"));
                                       JSONArray title = response.getJSONObject(i).getJSONArray("title");
                                       d.setTid(response.getJSONObject(i).getString("tid"));
                                      // d.title = new ArrayList<>();

                                       Data d2 = new Data();
                                       d2.title = new ArrayList<>();
                                       d2.setTid(d.getTid());
                                       for (int y = 0; y < title.length(); y++) {
                                          String subTitles = title.get(y).toString();
                                           d2.title.add(subTitles);

                                       }

                                       Helper.subtopics.add(d2);
                                       Helper.topics.add(d);


                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                       Toast.makeText(context.getApplicationContext(),
                                               "Ex:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }

                           }
                       }, null);
               queue.add(request);

           }catch (Exception ex){
               Toast.makeText(context.getApplicationContext(), "loadFolderContents:"+ex.getMessage(),
                       Toast.LENGTH_SHORT).show();
           }

       }


       public  void loadCoursesHod(ListView list){
        try {


            if( Helper.hod_courses_list != null){
                //Toast.makeText(context,"Clearing..",Toast.LENGTH_LONG).show();
                Helper.hod_courses_list.clear();
            }else{
                Helper.hod_courses_list = new ArrayList<>();
            }
            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, fetch_Courses, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for(int i=0;i< response.length();i++){
                                try {
                                    String course_no = response.getJSONObject(i).getString("Course_no");
                                    String course_name = response.getJSONObject(i).getString("Course_desc");
                                    Courses c = new Courses();
                                    c.setCourse_no(course_no);
                                    c.setCourse_desc(course_name);

                                    Helper.hod_courses_list.add(c);
                                    CoursesAdapter adapter = new CoursesAdapter(context,Helper.hod_courses_list,"Hod");
                                    list.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },null);

            queue.add(arr);
        }catch (Exception ex){
            Toast.makeText(context.getApplicationContext(), "LoadCoursesHod:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

       }


//hod side teachers for master folder
    public  void loadTeachersCoursesHod(ListView list){
        try {

          //  Toast.makeText(context.getApplicationContext(), "called", Toast.LENGTH_SHORT).show();
            if( Helper.hod_teachers_list != null){
                //Toast.makeText(context,"Clearing..",Toast.LENGTH_LONG).show();
                Helper.hod_teachers_list.clear();
            }else{
                Helper.hod_teachers_list = new ArrayList<>();
            }
            JsonArrayRequest arr = new JsonArrayRequest(Request.Method.GET, fetchTeachers+Helper.selectedCourseId, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for(int i=0;i< response.length();i++){
                                try {

                                    String course_no = response.getJSONObject(i).getString("cid");
                                    String name = response.getJSONObject(i).getString("name");
                                    String tid = response.getJSONObject(i).getString("tid");
                                    String master = response.getJSONObject(i).getString("master");
                                    String aid = response.getJSONObject(i).getString("aid");

                                    Teacher t = new Teacher();
                                    t.setCid(course_no);
                                    t.setName(name);
                                    t.setTid(tid);
                                    t.setMaster(master);
                                    t.setAid(aid);

                                    Helper.hod_teachers_list.add(t);
                                    HodTeachersMasterAdapter adapter = new HodTeachersMasterAdapter(context,Helper.hod_teachers_list);
                                    list.setAdapter(adapter);
                                   adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },null);

            queue.add(arr);
        }catch (Exception ex){
            Toast.makeText(context.getApplicationContext(), "LoadCoursesHod:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void updateCloWeightage(String cid,String clo,String mid,String finals,String q,String a){

        try{
           double total = Double.parseDouble(mid)+Double.parseDouble(finals)+Double.parseDouble(q) +Double.parseDouble(a);

           if(total <= 100.00) {

               JSONObject obj = new JSONObject();
               obj.put("cid", cid);
               obj.put("des", clo);
               obj.put("mids", mid);
               obj.put("finals", finals);
               obj.put("q", q);
               obj.put("a", a);

               JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, insertClos, obj, new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       Toast.makeText(context.getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(context.getApplicationContext(), "Rows Effected 1", Toast.LENGTH_SHORT).show();
                   }
               });

               queue.add(request);

           }else{
               Toast.makeText(context.getApplicationContext(), "Weightage cant be more than 100%"+total, Toast.LENGTH_SHORT).show();
           }

        }catch (Exception ex){
            Toast.makeText(context, "updateCloWeightage:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void loadCloTable(String cid,ListView list){

        try{
           // cid = "CS-452";
          //  Toast.makeText(context,"cid.."+cid,Toast.LENGTH_LONG).show();
            if( Helper.closList != null){
              //  Toast.makeText(context,"Clearing.."+cid,Toast.LENGTH_LONG).show();
                Helper.closList.clear();
            }else{
                Helper.closList = new ArrayList<>();
            }

            JsonArrayRequest obj = new JsonArrayRequest(Request.Method.GET, getClos+cid, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    CloTableViewAdapter adapter;
                    for(int i=0;i< response.length();i++){
                        Clos c = new Clos();
                        try {
                            c.setMids(response.getJSONObject(i).getString("mids"));
                            c.setFinals(response.getJSONObject(i).getString("finals"));
                            c.setA(response.getJSONObject(i).getString("a"));
                            c.setQ(response.getJSONObject(i).getString("q"));
                            c.setDes(response.getJSONObject(i).getString("des"));

                            Helper.closList.add(c);
                            adapter = new CloTableViewAdapter(context,Helper.closList);
                            list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            },null);

            queue.add(obj);
        }catch (Exception ex){
            Toast.makeText(context, "loadCloTableDb:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


   public void updateMaster(String tid,String master){

        try {

            JSONObject object = new JSONObject();
             object.put("Emp_no",tid);
             object.put("master",master);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                    updateMaster, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show();
                            loadTeachersCoursesHod(Helper.reload);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
                    loadTeachersCoursesHod(Helper.reload);
                }
            });

            queue.add(request);



        }catch (Exception ex){
            Toast.makeText(context, "updateMasterDb:", Toast.LENGTH_SHORT).show();
        }
   }


    public void updateContents_clos(Map<String, CheckedValues> map, String type, String topic,String d){

         String urls;
        try{

            for (Map.Entry<String, CheckedValues> i : map.entrySet()) {

                JSONObject object = new JSONObject();
                 if(type.equals("Contents")){
                     urls=url+"myContents";
                     object.put("subtopic",i.getKey());
                     object.put("week",i.getValue().getVal());
                 }else {
                     urls=url+"myClos";
                     object.put("clo",i.getValue().getVal());
                 }

                object.put("cid",i.getValue().getCid());
                object.put("tid",i.getValue().getTid());
                object.put("section",i.getValue().getSection()); //for clos should not send section
                object.put("topic",topic);
                Log.d("Map:",i.getValue().getVal());


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urls+d, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "Data saved success", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "saved successfully", Toast.LENGTH_SHORT).show();
                    }
                });



                queue.add(request);
            }
        }catch (Exception ex){
            Toast.makeText(context, "Db-update-Contents_clos:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void loadCommonTopics(ListView list){

        if( Helper.commonTopics != null){

            Helper.commonTopics.clear();
        }else{
            Helper.commonTopics = new ArrayList<>();
        }

        try{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, loadCommonTopics, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    CommonTopicAdapter adapter;

                     for(int i=0;i<response.length();i++){
                        CommonTopics common = new CommonTopics();
                         try {
                             common.setSubtopic(response.getJSONObject(i).getString("topic"));
                             Helper.commonTopics.add(common);
                             adapter = new CommonTopicAdapter(context,Helper.commonTopics);
                             list.setAdapter(adapter);
                             adapter.notifyDataSetChanged();

                            // Toast.makeText(context, "v is "+common.getSubtopic(), Toast.LENGTH_SHORT).show();
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }
                }
            },null);

            queue.add(request);

        }catch (Exception ex){

            Toast.makeText(context, "DbCommonTopics:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


   public void getUploads(ListView list){
        try{

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, fetchUploads+Helper.logged_user, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<Upload> uploads = new ArrayList<>();
                            PdfAdapter adapter;
                            Upload u;
                            for (int i=0;i<response.length();i++){
                                try {
                                   u= new Upload();
                                    u.setCid(response.getJSONObject(i).getString("cid"));
                                    u.setTid(response.getJSONObject(i).getString("url"));
                                    u.setName(response.getJSONObject(i).getString("name"));
                                    u.setDes(response.getJSONObject(i).getString("des"));
                                    uploads.add(u);
                                    adapter = new PdfAdapter(context,uploads);
                                    list.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    },null);

            queue.add(request);
        }catch (Exception e){
            Toast.makeText(context, "DbGetUploads:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
   }



   public void getCovered(String tid,String cid){

        try{
            String my = url+"getCovered?tid=" + tid + "&cid=" + cid;
            Db.task = new ArrayList<>();

            JsonArrayRequest j = new JsonArrayRequest(Request.Method.GET, my, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            Task t = new Task();
                            t.setTopic(response.getJSONObject(i).getString("topic"));
                            t.setSubtopic(response.getJSONObject(i).getString("subtopic"));

                           // Toast.makeText(context, "topic:"+t.getTopic(), Toast.LENGTH_SHORT).show();
                            Db.task.add(t);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(j);

        }catch (Exception ex){
            Toast.makeText(context, "ex:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
   }


   public  static  List<Task> my;
   public  void getData(){
       try{

           my = new ArrayList<>();

           JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url + "tsk", null, new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {


                   for (int i = 0; i < response.length(); i++) {

                       try {
                           Task t1 = new Task();
                           t1.setTopic(response.getJSONObject(i).getString("topic"));
                           t1.setSubtopic(response.getJSONObject(i).getString("subtopic"));
                           my.add(t1);

                       } catch (Exception ex) {

                       }
                   }
               }
           },null);

           queue.add(req);
       }catch (Exception ex){
           Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
       }
   }



}





    /*
    public void uploadPdf(String name, String url, String cid, String des){

        try{

            try {
                JSONObject obj = new JSONObject();
                obj.put("name",name);
                obj.put("url",url);
                obj.put("cid",cid);
                obj.put("des",des);
                //  obj.putOpt("file",b);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, insertPdf, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(context, "Data Saved!", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Pdf uploaded!", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }catch (Exception e){
            Toast.makeText(context, "upload PdfDb:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

/
     */

