package com.example.pixabay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kotlin.reflect.KParameter;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    List<WallpaperModel> wallpaperModelList;
    Context context;

    public WallpaperAdapter(List<WallpaperModel> wallpaperModelList, Context context) {
        this.wallpaperModelList = wallpaperModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(context).inflate(R.layout.wall_item_layout,parent,false);
     return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(wallpaperModelList.get(position).getImgLink()).into(holder.imageView);
        holder.textView.setText(wallpaperModelList.get(position).getUser());
        holder.Likes.setText(wallpaperModelList.get(position).getLikes());
        holder.Downloads.setText(wallpaperModelList.get(position).getDownloads());

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Get screen size
                Toast.makeText(context,"Keep it up",Toast.LENGTH_SHORT).show();
//                Dialog mDialog = new Dialog(context);
//                mDialog.setContentView(R.layout.popup_dialog);
//                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                mDialog.show();


                return true;
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kya bolta", "onClick: ok hai sab");
                Intent i = new Intent(context,FullWallpaperActivity.class);
                i.putExtra("img",wallpaperModelList.get(position).getImgLink());
                i.putExtra("user",wallpaperModelList.get(position).getUser());
                i.putExtra("likes",wallpaperModelList.get(position).getLikes());
                i.putExtra("downloads",wallpaperModelList.get(position).getDownloads());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return wallpaperModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView,Likes,Downloads;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            textView=itemView.findViewById(R.id.user);
            Likes=itemView.findViewById(R.id.likes);
            Downloads=itemView.findViewById(R.id.downloads);
        }
    }
}
