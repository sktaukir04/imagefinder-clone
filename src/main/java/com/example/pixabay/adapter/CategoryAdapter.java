package com.example.pixabay.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pixabay.R;
import com.example.pixabay.SearchImageActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    String names[];
    int images[];
    Context context;
    public CategoryAdapter(Context c,String places[],int img[]){
        context=c;
        names=places;
        images=img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.categoryholdlayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.places.setText(names[position]);
        holder.images.setImageResource(images[position]);


        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();
                int temp=position;
                Intent intent=new Intent(context, SearchImageActivity.class);
                String value;
                switch (temp){
                    case 0:
                        value="animals";
                        break;
                    case 1:
                        value="background";
                        break;
                    case 2:
                        value="Buildings";
                        break;
                    case 3:
                        value="Business";
                        break;
                    case 4:
                        value="Computers";
                        break;
                    case 5:
                        value = "Education";
                        break;
                    case 6:
                        value = "Fashion";
                        break;
                    case 7:
                        value = "Feelings";
                        break;
                    case 8:
                        value = "Food";
                        break;
                    case 9:
                        value = "Health";
                        break;
                    case 10:
                        value = "Industry";
                        break;
                    case 11:
                        value = "Music";
                        break;
                    case 12:
                        value = "City";
                        break;
                    case 13:
                        value = "Clothes";
                        break;
                    case 14:
                        value = "Sea";
                        break;
                    case 15:
                        value="Mountains";
                        break;
                    case 16:
                        value="Train";
                        break;
                    case 17:
                        value="Aircraft";
                        break;
                    case 18:
                        value="Travel";
                        break;
                    case 19:
                        value="Waterfall";
                        break;
                    case 20:
                        value="Night";
                        break;
                    case 21:
                        value="Ships";
                        break;
                    default:
                        value="";

                }
//                intent.putExtra("loadimages",value);
                intent.putExtra("searchdata",value);
                context.startActivity(intent);


            }
        });

        holder.places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(context, holder.places.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();

                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                },500);

//                Intent intent= new Intent(context, SearchImageActivity.class);
//                String temp=holder.places.getText().toString();
//                intent.putExtra("img",temp);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView places;
        ImageView images;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);
             places=itemView.findViewById(R.id.placenames);
             images=itemView.findViewById(R.id.imgview);
         }
     }
}
