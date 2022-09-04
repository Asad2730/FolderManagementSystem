package com.example.biit.Upload.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.biit.Classes.Teacher;
import com.example.biit.Classes.Upload;
import com.example.biit.Db.Db;
import com.example.biit.R;
import com.example.biit.Upload.DownloadImageTask;

import java.util.List;

public class PdfAdapter extends ArrayAdapter {

    private Context context;
    private List<Upload> list;
    private TextView name,open,delete;
    private WebView imageView;

    public PdfAdapter(@NonNull Context context, List<Upload> list){
        super(context, R.layout.row_pdf,list);
         this.context = context;
         this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try{

            convertView = inflater.inflate(R.layout.row_pdf,parent,false);
            name = convertView.findViewById(R.id.name);
            open = convertView.findViewById(R.id.open);
            delete = convertView.findViewById(R.id.delete);
            imageView = convertView.findViewById(R.id.img);
           // WebSettings  webSettings = imageView.getSettings();
           // webSettings.setJavaScriptEnabled(true);
            //imageView.setVisibility(View.GONE);

            name.setText(list.get(position).getName());
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // imageView.setVisibility(View.VISIBLE);
                    String name = list.get(position).getName();
                    String path = "http://"+Db.ip+"/FypApi/Content/Uploads/"+name;
                    Toast.makeText(context, "p:"+path, Toast.LENGTH_SHORT).show();
                    imageView.loadUrl(path);
                    //new DownloadImageTask(imageView).execute(path);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = list.get(position).getName();
                    Toast.makeText(context, "p:"+position, Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex) {
            Toast.makeText(getContext(), "PdfAdapter Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("PdfAdapter:",ex.getMessage());
        }

        return  convertView;
    }
}
