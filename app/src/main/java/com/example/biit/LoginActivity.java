package com.example.biit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biit.Classes.Helper;
import com.example.biit.Db.Db;
import com.example.biit.Hod.HodActivity;
import com.example.biit.Teachers.TeacherActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText id,password;
    private Button login;
    private Db db;
    private  Handler handler;
    private Intent intent;
    private final String hod="BIIT179";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        getSupportActionBar().hide();
        id = findViewById(R.id.login_id);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login);
        db = new Db(getApplicationContext());

       Helper.isMaster = true;

       /// Helper.logged_user = "BIIT216";
       // startActivity(new Intent(this, TeacherActivity.class));

          Helper.logged_user = Helper.HOD;
          db.getData();
          db.coveredTopics("BIIT179");
          startActivity(new Intent(this, HodActivity.class));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(checkFields() != ""){
                   Toast.makeText(getApplicationContext(), checkFields(), Toast.LENGTH_SHORT).show();
               }
                else{
                    String userid = id.getText().toString().toUpperCase();
                 if(userid.equals(Helper.HOD) && password.getText().toString().equals("123")) {
                     Helper.logged_user = Helper.HOD;
                     Helper.isMaster = true;
                     startActivity(new Intent(getApplicationContext(),HodActivity.class));
                 }else{
                     db.loginUser(userid,password.getText().toString());
                 }

                id.setText("");
                password.setText("");
                }
            }
        });
    }

    private String checkFields(){
        String result = "";
        if(id.getText().toString().isEmpty())
            result+= " id field cannot be empty ";
        if(password.getText().toString().isEmpty())
            result+= " password field cannot be empty ";
       return result ;
    }


}