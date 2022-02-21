package com.example.pixabay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FullWallpaperActivity extends AppCompatActivity {

    private PhotoView photoView;
    private String imgLink,Suser,Slikes,Sdownloads;
    private Button button1,button2,button3;
    private ProgressDialog progressBar;
    private String filename;

    TextView likes,username,downloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_wallpaper);

        imgLink=getIntent().getStringExtra("img");
        Suser=getIntent().getStringExtra("user");
        Slikes=getIntent().getStringExtra("likes");
        Sdownloads=getIntent().getStringExtra("downloads");

        likes=findViewById(R.id.text1);
        username=findViewById(R.id.text2);
        downloads=findViewById(R.id.text3);

        likes.setText(Slikes);
        username.setText(Suser);
        downloads.setText(Sdownloads);

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),Slikes+" Peoples have liked it.",Toast.LENGTH_SHORT).show();
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),Suser+" is the owner of this pic.",Toast.LENGTH_SHORT).show();
            }
        });
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"No of downloads is "+Sdownloads+".",Toast.LENGTH_SHORT).show();
            }
        });


        photoView=findViewById(R.id.full_screen_img);
        photoView.setImageURI(Uri.parse(imgLink));

        Glide.with(this).load(imgLink).into(photoView);
//        photoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"hmmm",Toast.LENGTH_SHORT).show();
//            }
//        });

        button1=findViewById(R.id.download_wallpaper_btn);
        button2=findViewById(R.id.setas_homescreen_btn);
        button3=findViewById(R.id.setas_lockscreeen_btn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWallpaper();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //homescreen
                progressBar= new ProgressDialog(FullWallpaperActivity.this);
                progressBar.setCancelable(false);
                progressBar.setMessage("File Loading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

                final Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WallpaperManager wallpaperManager=WallpaperManager.getInstance(FullWallpaperActivity.this);
                        Bitmap bitmap=((BitmapDrawable)photoView.getDrawable()).getBitmap();
                        try {
                            wallpaperManager.setBitmap(bitmap);
//                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//                                wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
                                Toast.makeText(getApplicationContext(),"Wallpaper Set...!",Toast.LENGTH_SHORT).show();
//
//                            }else {
//                                Toast.makeText(getApplicationContext(),"Must Have Android 9 or Above",Toast.LENGTH_SHORT).show();
//                            }
                            progressBar.dismiss();

                        }catch (IOException e){
                            e.printStackTrace();
                        }


                    }
                },2000);


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lockscreen
                progressBar= new ProgressDialog(FullWallpaperActivity.this);
                progressBar.setCancelable(false);
                progressBar.setMessage("File Loading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

             final Handler handler=new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     WallpaperManager wallpaperManager=WallpaperManager.getInstance(FullWallpaperActivity.this);
                     Bitmap bitmap=((BitmapDrawable)photoView.getDrawable()).getBitmap();
                     try {
                         wallpaperManager.setBitmap(bitmap);
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                                wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
                                Toast.makeText(getApplicationContext(),"Wallpaper Set...!",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getApplicationContext(),"Must Have Android 9 or Above",Toast.LENGTH_SHORT).show();
                            }
                         progressBar.dismiss();
                     }catch (IOException e){
                         e.printStackTrace();
                     }

                 }
             },2000);


            }
        });

    }
    private void downloadWallpaper(){
        progressBar= new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("File Downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DownloadWallpaperEvent(imgLink);
            }
        },2000);

    }

    private void DownloadWallpaperEvent(String imgLink) {

        File download=this.getDir("Taukir Files", Context.MODE_APPEND);
        if (!download.exists()){
            download.mkdirs();
        }

        filename= UUID.randomUUID().toString();

        DownloadManager downloadManager=(DownloadManager)this.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(imgLink);
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
//        request.setDestinationInExternalPublicDir("Taukir Files/",filename+".jpg");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS+""+File.separator,filename+".jpg");
        downloadManager.enqueue(request);
        Toast.makeText(this,"Download Complete",Toast.LENGTH_SHORT).show();
        progressBar.dismiss();

    }
}