package com.example.biit.Teachers;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.Hod.HodCoursesFrag;
import com.example.biit.R;
import com.example.biit.Upload.MyUploadsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class TeacherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,
        CoursesFragment.onItemClicked, HodCoursesFrag.onItemClicked {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        try{

            //course Detail Frag
            new Db(getApplicationContext()).loadFolderContents();


            InitializeTeacher();
            if(Helper.folderItem){

                loadCourseDetail();
            }
            else{
                loadTeacher();
            }

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ExTeacher:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onItemSelected(int index) {

        loadCourseDetail();
    }

    private  void InitializeTeacher(){
        drawerLayout = findViewById(R.id.activity_teacher_drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView= findViewById(R.id.main_navigation);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadTeacher(){
        loadFragment(new CoursesFragment());
    }

    private void loadCourseDetail(){
        loadFragment(new CourseDetailFrag());
    }

    private void loadFragment(Fragment fragment){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frag,fragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.menu_home:
                loadTeacher();
                break;

            case R.id.menu_logout:
                Helper.logged_user = null;
                Helper.courses = null;
                this.finish();
                break;

            case R.id.menu_uploads:
                startActivity( new Intent(TeacherActivity.this, MyUploadsActivity.class));
                 break;

            default:
                break;
        }
        return true;
    }


}