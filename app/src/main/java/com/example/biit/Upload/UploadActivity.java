package com.example.biit.Upload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biit.Db.Db;
import com.example.biit.Classes.Helper;
import com.example.biit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class UploadActivity extends AppCompatActivity {

    private Intent uploadPdf;
    private String pdfFile;
    private String des;
    private FloatingActionButton camera;
    private Button upload;
    private TextView back;

   private Bitmap pdfBitmap;
   private Uri filepath;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView img;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private  Uri imageUri;
    private Bitmap photoBitmap;

    private String real;

    private  String cid,tid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        try {

            des = getIntent().getStringExtra("des");
            cid = Helper.course_clicked_code;
            tid = Helper.logged_user;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.upload_alert,null);
            builder.setView(view);
            builder.setCancelable(false);
            camera = view.findViewById(R.id.camera);
            upload = view.findViewById(R.id.fileManager);
            back = view.findViewById(R.id.back);

            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPdf(1);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
           // Toast.makeText(getApplicationContext(), "des:"+des, Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "ExUpload:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    //upload pdf
    private void uploadPdf(int requestCode){
       // String url= "https://docs.google.com/gview?embedded=true&url=https://url.pdf";
       uploadPdf = new Intent(Intent.ACTION_CREATE_DOCUMENT);
         uploadPdf.addCategory(Intent.CATEGORY_OPENABLE);

        //uploadPdf.setDataAndType(Uri.parse(url), "application/pdf");
       // File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        //Uri path = Uri.fromFile(file);
        uploadPdf.setType("application/pdf");
        //uploadPdf.setAction(uploadPdf.ACTION_GET_CONTENT);
        //uploadPdf = new Intent(Intent.ACTION_VIEW);
        //uploadPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //uploadPdf.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //ploadPdf.setDataAndType(path, "application/pdf");
        startActivityForResult(Intent.createChooser(uploadPdf,"PDF FILE SELECT"),requestCode);
    }

    public static String getRealPathFromUri(Context ctx, Uri uri) {
        String  picturePath = "";

        String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("", "picturePath : " + picturePath);
            cursor.close();
        }
        return picturePath;
    }

    private void uploadFile(String path){

        try{

            //  final ProgressDialog progress = new ProgressDialog(getContext());
            //  progress.setTitle("File is Loading...");
            //   progress.show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            builder.setMessage("pdf:"+pdfFile);
            builder.setTitle("Upload File");


            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //db data.toString() as url name pdfFile , cid as Helper.selectedCourse //check and des as  what


                    File file = new File(path);
                   // File file = new File(real);

                    RequestBody photoContent = RequestBody.create(MediaType.parse("*/*"), file);

                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);

                    UploadService uploadService = APIClient.getClient().create(UploadService.class);

                    uploadService.Upload(photo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                Toast.makeText(UploadActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(UploadActivity.this, "  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            back.setText(t.getMessage());
                        }
                    });
                   // new Db(getApplicationContext()).uploadPdf(pdfFile,data.toString(), Helper.course_clicked_code,des);
                   // finish();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                    finish();
                }
            });

            //   progress.dismiss();
            builder.show();
            // dialog.show();
            // Toast.makeText(getContext(), "pdf:"+data+" - "+pdfFile, Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "uploadFileEx:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    //image upload
    private void saveImageAlert(Bitmap bitmap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.save_image_alert,null);
        builder.setView(view);
        builder.setCancelable(false);
        img = view.findViewById(R.id.image);
        img.setImageBitmap(bitmap);

        TextView save = view.findViewById(R.id.save);
        TextView cancel = view.findViewById(R.id.cancel);
        AlertDialog alertDialog = builder.create();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
                File file = new File(imageUri.toString());
                RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoContent);

                UploadService uploadService = APIClient.getClient().create(UploadService.class);

                uploadService.UploadPdf(photo,cid,des,tid).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            Toast.makeText(UploadActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(UploadActivity.this, "  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              alertDialog.dismiss();
            }
        });


        alertDialog.show();

    }



    private String saveImage() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String fname = sdf.format(new Date());
        File file = new File(directory, fname + ".jpg");
        if (file.exists())
            file.delete();
        imageUri = Uri.parse(file.toString());
        Log.d("path", file.toString());
        FileOutputStream fos = null;
        try {

                fos = new FileOutputStream(file);
                photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();


        } catch (java.io.IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Ex:"+e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        return file.toString();

    }



    private void uploadFile2(String path,File f){

        try{

            //  final ProgressDialog progress = new ProgressDialog(getContext());
            //  progress.setTitle("File is Loading...");
            //   progress.show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            builder.setMessage("pdf:"+pdfFile);
            builder.setTitle("Upload File");


            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //db data.toString() as url name pdfFile , cid as Helper.selectedCourse //check and des as  what

                    //File file = new File(path);
                   // File file = new File(real);

                    RequestBody photoContent = RequestBody.create(MediaType.parse("*/*"), f);

                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", f.getName(), photoContent);

                    UploadService uploadService = APIClient.getClient().create(UploadService.class);

                    uploadService.Upload(photo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                Toast.makeText(UploadActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(UploadActivity.this, "  " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            back.setText(t.getMessage());
                        }
                    });
                    // new Db(getApplicationContext()).uploadPdf(pdfFile,data.toString(), Helper.course_clicked_code,des);
                    // finish();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                    finish();
                }
            });

            //   progress.dismiss();
            builder.show();
            // dialog.show();
            // Toast.makeText(getContext(), "pdf:"+data+" - "+pdfFile, Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "uploadFileEx:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData() != null){
            String path = data.getData().getPath();
            
            String fileName = path.substring(path.lastIndexOf('/') + 1);

            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //uploadFile(fileName);
            File[] files = folder.listFiles();
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    //  newfilepickerreader(path, f);
                    //  uploadFile2(path,f);
                    break;
                } else {
                    // EmpSalary(path, f);
                }

            }

            Uri uri = data.getData();
            filepath = uri;
            //  real =  getRealPathFromUri(getApplicationContext(),data.getData());

            //  String path1 =  new FilePdf().getFilePathFromURI(UploadActivity.this,uri);


            try {

                pdfBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //Helper.b = pdfBitmap;
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "e:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            pdfFile = data.getDataString().substring(data.getDataString().lastIndexOf("/")+1);
            // uploadFile(path1);
            //pdf.uploadFile(data.getData(),pdfFile);
        }else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            photoBitmap = (Bitmap) extras.get("data");
            saveImageAlert(photoBitmap);

        }
        else{
            finish();
        }
    }



}