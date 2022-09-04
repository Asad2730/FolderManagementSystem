package com.example.biit.Upload;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UploadService {
    @Multipart
    @POST("My/Upload") //My is name of controller and upload is name of method in API
    Call<ResponseBody> Upload(
            @Part MultipartBody.Part photo
    );


    @Multipart
    @POST("My/convertPdf") //My is name of controller and upload is name of method in API
    Call<ResponseBody> UploadPdf(
            @Part MultipartBody.Part photo,
           @Query("cid") String cid,
            @Query("des")  String des,
            @Query("tid") String tid
    );
}