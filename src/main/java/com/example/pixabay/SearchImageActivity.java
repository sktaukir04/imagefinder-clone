package com.example.pixabay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchImageActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    CardView cardView;
    LinearLayout linearLayout;

    private RecyclerView recyclerView;
    private WallpaperAdapter wallpaperAdapter;
    private List<WallpaperModel> wallpaperModelList = new ArrayList<>();

    private String mygetImageslink="https://pixabay.com/api/";
    private String myApi = "16572993-c855c922b0f2a449b298ad9da";

//    String URL="https://pixabay.com/api/?key="+myApi+"&q=night";

    private boolean isScrolling=false;
    private int currenttItems,totalItems,scrolloutItems;
    int pageNumber=1,per_page=50;

    String data;
    String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_image);


        linearLayout=findViewById(R.id.linearlayout);
        cardView=findViewById(R.id.card);

        progressDialog=new ProgressDialog(SearchImageActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Search Data....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(SearchImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        },3000);

         data = getIntent().getStringExtra("searchdata");
         // experiment purpose
        data1 = getIntent().getStringExtra("loadimages");

        recyclerView=(RecyclerView)findViewById(R.id.homeRecyclerview);

        fetchData();
        wallpaperAdapter=new WallpaperAdapter(wallpaperModelList,getApplicationContext());

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currenttItems=gridLayoutManager.getChildCount();
                totalItems=gridLayoutManager.getItemCount();
                scrolloutItems=gridLayoutManager.findFirstVisibleItemPosition();
//                currenttItems=linearLayoutManager.getChildCount();
//                totalItems=linearLayoutManager.getItemCount();
//                scrolloutItems=linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currenttItems+scrolloutItems==totalItems)  ){
                    isScrolling=false;
                    fetchData();
                }

            }
        });

        recyclerView.setAdapter(wallpaperAdapter);
        wallpaperAdapter.notifyDataSetChanged();

    }

    private void fetchData(){
        data = getIntent().getStringExtra("searchdata");
        data1=getIntent().getStringExtra("loadimages");
        String URL="https://pixabay.com/api/?key="+myApi+"&q="+data;
        StringRequest request=new StringRequest(Request.Method.GET, URL+"&page="+pageNumber+"&per_page="+per_page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("hits");

                    int length= jsonArray.length();

                    for (int i=0;i<length;i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String image = jsonObject1.getString("largeImageURL");
                        String user = jsonObject1.getString("user");
                        int likes = jsonObject1.getInt("likes");
                        int download=jsonObject1.getInt("downloads");

                        String sLikes= String.valueOf(likes);
                        String sDownload=String.valueOf(download);

                        wallpaperModelList.add(new WallpaperModel(id,image,user,sLikes,sDownload));
//                        wallpaperModelList.add(new WallpaperModel(id,image,user,likes,download));

                    }
                    wallpaperAdapter.notifyDataSetChanged();
                    pageNumber++;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Images Not Found",Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("key",myApi);
                return map;
            }
        };

        Context context=getApplicationContext();
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);

    }


}