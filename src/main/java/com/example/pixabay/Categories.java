package com.example.pixabay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pixabay.adapter.CategoryAdapter;

public class Categories extends AppCompatActivity {
    RecyclerView recyclerView;

    String names[];
    int categoryImages[]={R.drawable.animals,R.drawable.backgrounds,R.drawable.buildings,R.drawable.business,R.drawable.computers,R.drawable.education,
                        R.drawable.fashion,R.drawable.feelings,R.drawable.foods,R.drawable.health,R.drawable.industry,R.drawable.music,
                        R.drawable.city,R.drawable.clothes,R.drawable.sea,R.drawable.mountains,R.drawable.trains,R.drawable.aircraft,R.drawable.travel,
                        R.drawable.waterfall,R.drawable.night,R.drawable.ship};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        recyclerView=findViewById(R.id.recycler);
        names = getResources().getStringArray(R.array.names);

        CategoryAdapter myadapter= new CategoryAdapter(this,names,categoryImages);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}