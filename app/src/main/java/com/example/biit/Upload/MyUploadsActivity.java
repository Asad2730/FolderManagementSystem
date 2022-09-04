package com.example.biit.Upload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.biit.Db.Db;
import com.example.biit.R;

public class MyUploadsActivity extends AppCompatActivity {

    private ListView list;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_uploads);
            list=findViewById(R.id.pdf_list);
            back = findViewById(R.id.back);
            new Db(getApplicationContext()).getUploads(list);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }catch (Exception ex){
            Toast.makeText(this, "MyUploadsActivityEx:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("UploadActivity:",ex.getMessage());
        }


    }
}