package com.example.biit.Hod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.biit.Db.Db;
import com.example.biit.Teachers.CourseDetailFrag;
import com.example.biit.Teachers.CoursesFragment;
import com.example.biit.Classes.Helper;
import com.example.biit.R;
import com.example.biit.Upload.MyUploadsActivity;
import com.google.android.material.navigation.NavigationView;

public class HodActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,
        CoursesFragment.onItemClicked, HodCoursesFrag.onItemClicked {

    private DrawerLayout drawerLayout1,drawerLayout2;
    private ActionBarDrawerToggle actionBarDrawerToggle1,actionBarDrawerToggle2;
    private Toolbar toolbar1,toolbar2;
    private NavigationView navigationView1,navigationView2;
    private FragmentManager fragmentManager1,fragmentManager2;
    private FragmentTransaction fragmentTransaction1,fragmentTransaction2;
    private TabHost tabhost;

    //if Helper.folderItem != false load loadCourseDetail

    //make another activity & load from here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hod);
        //course Detail Frag
        new Db(getApplicationContext()).loadFolderContents();
       // new Db(getApplicationContext()).loadTopics_SubTopics();
        //CourseDetailFragHod

        loadInitialises();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout1.closeDrawer(GravityCompat.START);
        drawerLayout2.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(getApplicationContext(),MyUploadsActivity.class);

        switch (item.getItemId()){
            case R.id.menu_home:
                 loadTeacher();
                 loadHod();
                break;

            case R.id.menu_logout:
                Helper.logged_user = null;
                Helper.courses = null;
                this.finish();
                break;

            case R.id.menu_uploads:
                startActivity( new Intent(HodActivity.this, MyUploadsActivity.class));
                break;

            default:
                break;
        }
        return true;
    }




    private  void InitializeTeacher(){
        drawerLayout1 = findViewById(R.id.activity_drawer_layout);
        toolbar1 = findViewById(R.id.toolbar);
        navigationView1= findViewById(R.id.main_navigation);

        setSupportActionBar(toolbar1);
        actionBarDrawerToggle1 = new ActionBarDrawerToggle(this,drawerLayout1,toolbar1,R.string.open,R.string.close);
        drawerLayout1.addDrawerListener(actionBarDrawerToggle1);
        actionBarDrawerToggle1.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle1.syncState();
        navigationView1.setNavigationItemSelectedListener(this);
    }

    public void InitializeHod(){

        drawerLayout2= findViewById(R.id.activity_drawer_layout);
        toolbar2 = findViewById(R.id.toolbar);
        navigationView2 = findViewById(R.id.main_navigation);

        setSupportActionBar(toolbar2);
        actionBarDrawerToggle2 = new ActionBarDrawerToggle(this,drawerLayout2,toolbar2,R.string.open,R.string.close);
        drawerLayout2.addDrawerListener(actionBarDrawerToggle2);
        actionBarDrawerToggle2.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle2.syncState();
        navigationView2.setNavigationItemSelectedListener(this);
    }

    private void loadTeacher(){
        tabhost.getTabWidget().setVisibility(View.VISIBLE);
        loadFragment(new CoursesFragment(),R.id.content_frag,fragmentManager1,fragmentTransaction1);
    }

    private void loadHod(){

        tabhost.getTabWidget().setVisibility(View.VISIBLE);
        loadFragment(new HodCoursesFrag(),R.id.content_frag_2,fragmentManager2,fragmentTransaction2);
    }

    private void loadCourseDetail(){
        loadFragment(new CourseDetailFrag(),R.id.content_frag,fragmentManager1,fragmentTransaction1);
    }

    private void loadHodTeachers(){
        loadFragment(new CourseDetailFragHod(),R.id.content_frag_2,fragmentManager2,fragmentTransaction2);
    }

    private void loadFragment(Fragment fragment, int view,
                              FragmentManager fragmentManager, FragmentTransaction fragmentTransaction){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view,fragment);
        fragmentTransaction.commit();

    }

    private void loadInitialises(){
        try{
                tabhost = findViewById(R.id.content_manin_tab);
                // setting up the tab host
                tabhost.setup();

                TabHost.TabSpec spec = tabhost.newTabSpec("HOD");
                spec.setContent(R.id.hod);

                spec.setIndicator("HOD");

                tabhost.addTab(spec);

                spec = tabhost.newTabSpec("Teacher");
                spec.setContent(R.id.teacher);

                spec.setIndicator("Teacher");
                tabhost.addTab(spec);

                InitializeHod();

             InitializeTeacher();

            if(Helper.folderItem){
                loadCourseDetail();
            }
            else{
                loadTeacher();
                    loadHod();
            }

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ExHod:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onItemSelected(int index) {
        // tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
        tabhost.getTabWidget().setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(), "Index Selected is:"+Helper.hod_courses_list.get(index).getCourse_no(), Toast.LENGTH_SHORT).show();
        loadCourseDetail();
        //load teachers for specific course
        Helper.selectedCourseId = Helper.hod_courses_list.get(index).getCourse_no();
        //  Toast.makeText(getApplicationContext(), "cid:"+Helper.selectedCourseId, Toast.LENGTH_SHORT).show();
        loadHodTeachers();

    }

}