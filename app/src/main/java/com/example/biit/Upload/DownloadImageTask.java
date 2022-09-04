package com.example.biit.Upload;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public  class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {
    ImageView imgv;
    public DownloadImageTask(ImageView img){
        this.imgv=img;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmp=null;
        String url=urls[0];
        try{
            InputStream is=new java.net.URL(url).openStream();
            bmp= BitmapFactory.decodeStream(is);


        }catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }
    protected  void onPostExecute(Bitmap res){
        imgv.setImageBitmap(res);
    }
}
