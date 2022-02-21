package com.example.pixabay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.pixabay.LoginActivity.KEY_EMAIL;
import static com.example.pixabay.LoginActivity.KEY_NAME;
import static com.example.pixabay.LoginActivity.SHARED_PREF_NAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainFragment.onFragmentSelected {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ProgressDialog progressDialog;


    TextView textView;
    private Button searchimg;
    private EditText editText;
    private RecyclerView recyclerView;
    private WallpaperAdapter wallpaperAdapter;
    private List<WallpaperModel> wallpaperModelList = new ArrayList<>();

private String mygetImageslink="https://pixabay.com/api/";
String[] myKeys={"16572993-c855c922b0f2a449b298ad9da","19700086-0b617f97a6ab6282bdb0039ed","19700138-87f7b3c7e04262c608ab8a553",
        "19700166-755fab644a5d0504f005fdb77","19700195-077cf3f972def9b30f502099d","19700218-6579e031f088c12609435637d"};
private String myApi = "16572993-c855c922b0f2a449b298ad9da";

String URL="https://pixabay.com/api/?key="+myApi+"&safesearch="+true;

private boolean isScrolling=false;
private int currenttItems,totalItems,scrolloutItems;
int pageNumber=1,per_page=50;

//    android:theme="@style/Theme.Pixabay"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.white);
//        textView=findViewById(R.id.mail);


        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation_view);
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sp.getString(KEY_NAME,null);
        String email = sp.getString(KEY_EMAIL,null);
        View headerView = navigationView.getHeaderView(0);
        TextView tv = (TextView)headerView.findViewById(R.id.textViewheader);
        TextView ev = (TextView)headerView.findViewById(R.id.mail);
        ImageButton imageButton = (ImageButton)headerView.findViewById(R.id.editdata);
        Log.d("shared data", name + email);
        tv.setText(name);
        ev.setText(email);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vi = inflater.inflate(R.layout.nav_header,null);
//        TextView tv = (TextView)vi.findViewById(R.id.textViewheader);
//        TextView ev = (TextView)vi.findViewById(R.id.mail);
//        ev.setText(email);
//        tv.setText(name);
//        Log.i("username", name);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);   //<---Hamburger Icon Enabled


        actionBarDrawerToggle.syncState();

//        fragmentManager=getSupportFragmentManager();
//        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.container_main,new MainFragment());
//        fragmentTransaction.commit();

        recyclerView=(RecyclerView)findViewById(R.id.homeRecyclerview);
        searchimg=findViewById(R.id.searchimg);
        editText=findViewById(R.id.editsearch);
//        editText.setText(name);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                   SearchResults();
                    return true;
                }

                return false;
            }
        });




        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (s.toString().trim().length()==0){
//                    searchimg.setEnabled(false);
//                }else {
//                    searchimg.setEnabled(true);
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()==0){
                    searchimg.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Enter any text to search ....!", Toast.LENGTH_SHORT).show();
                }else {
                    searchimg.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               Toast toast= Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT);
               toast.show();
                final Handler handler=new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       toast.cancel();
                    }
                },500);
            }
        });

        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchValue=editText.getText().toString();

                fetchData();
                Intent intent = new Intent(getApplicationContext(),SearchImageActivity.class);
                intent.putExtra("searchdata",SearchValue);
                startActivity(intent);

            }
        });

//        wallpaperModelList.add(new WallpaperModel("1","https://pbs.twimg.com/profile_images/1276800887671803904/Jh3hauD3_400x400.jpg"));

        fetchData();
        wallpaperAdapter=new WallpaperAdapter(wallpaperModelList,getApplicationContext());

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false );
//        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
//                    for (int i=0;i<myKeys.length;i++){
//                        myApi=myKeys[i];
//                        if (i>=myKeys.length){
//                            i=0;
//                        }
//                        fetchData();
//                        Log.d("testT", myApi);
//                    }

                    fetchData();
                }

            }



            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currenttItems=gridLayoutManager.getChildCount();
                totalItems=gridLayoutManager.getItemCount();
                scrolloutItems=gridLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currenttItems+scrolloutItems==totalItems)  ){
                    isScrolling=false;
//                    for (int i=0;i<myKeys.length;i++){
//                        myApi=myKeys[i];
//                        if (i>=myKeys.length){
//                            i=0;
//                        }
//                        fetchData();
//                    }

                    fetchData();
                }                        Log.d("testT", myApi);


            }
        });


        recyclerView.setAdapter(wallpaperAdapter);
        wallpaperAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.clearFocus();
    }

    private void fetchData(){
//        while (pageNumber<10){
//            myApi=myKeys[1];
//        }


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
                        //testing purpose
                        String user= jsonObject1.getString("user");
                        int likes = jsonObject1.getInt("likes");
                        int download=jsonObject1.getInt("downloads");
                        String sLikes= String.valueOf(likes);
                        String sDownload=String.valueOf(download);

                        wallpaperModelList.add(new WallpaperModel(id,image,user,sLikes,sDownload));

                    }
                    wallpaperAdapter.notifyDataSetChanged();
                    pageNumber++;
                    for (int i=0;i<=5;i++) {
                        myApi = myKeys[i];
                        Log.d("nayaapi", myApi);
                    }

//                    if (pageNumber==5){
//                        myApi=myKeys[1];
//                        Log.d("newapi", myApi);
//                    }else if (pageNumber==10){
//                        myApi=myKeys[2];
//                        Log.d("newapi", myApi);
//
//                    }else if (pageNumber==15){
//                        myApi=myKeys[3];
//                        Log.d("newapi", myApi);
//
//                    }else if (pageNumber==20){
//                        myApi=myKeys[4];
//                        Log.d("newapi", myApi);
//
//                    }else if (pageNumber==25){
//                        myApi=myKeys[5];
//                        Log.d("newapi", myApi);
//
//                    }else if (pageNumber==30){
//                        myApi=myKeys[6];
//                        Log.d("newapi", myApi);
//
//                    }
//                    else {
//                        myApi=myKeys[0];
//                    }
//                    myApi=myKeys[0];


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Images Not Found",Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId()==R.id.home){
//            fragmentManager=getSupportFragmentManager();
//            fragmentTransaction=fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.container_main,new MainFragment());
//            fragmentTransaction.commit();
            Toast.makeText(getApplicationContext(),"Must implement fragments..!",Toast.LENGTH_SHORT).show();

        }
        if (item.getItemId()==R.id.photos){
//            fragmentManager=getSupportFragmentManager();
//            fragmentTransaction=fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_main,new MainFragment());
//            fragmentTransaction.commit();
//            Intent intent=new Intent(getApplicationContext(),DisplayCategory.class);
//            String val="animals";
//            intent.putExtra("loadimages",val);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"worked..!",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this,LoginActivity.class);
//            startActivity(intent);
        }
        if (item.getItemId()==R.id.about){
//            fragmentManager=getSupportFragmentManager();
//            fragmentTransaction=fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_main,new FragmentThird());
//            fragmentTransaction.commit();
            Intent intent=new Intent(getApplicationContext(),Categories.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"worked..!",Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void SearchResults(){
        String value= editText.getText().toString();
        Intent intent=new Intent(getApplicationContext(),SearchImageActivity.class);
        intent.putExtra("searchdata",value);
        startActivity(intent);
    }

    @Override
    public void onButtonSelected() {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_main,new MainFragment());
        fragmentTransaction.commit();
    }

//

//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else{
//            super.onBackPressed();
//        }
//
//    }
}