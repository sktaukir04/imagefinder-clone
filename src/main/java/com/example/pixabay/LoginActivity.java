package com.example.pixabay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView textView,textView1,animtext1;
    private EditText editText,editText1;
    private Button button;
    public static String SHARED_PREF_NAME = "username";
    public static String KEY_NAME = "key_username";
    public static String KEY_EMAIL = "key_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView=findViewById(R.id.userdatatext);
        editText = findViewById(R.id.edittextuser);
        editText1 = findViewById(R.id.emailedittext);
        textView1 = findViewById(R.id.useremailtext);
        button =(Button) findViewById(R.id.userbtn);
        animtext1 = (TextView)findViewById(R.id.welometext1);

        runAnimation();

        displayName();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
                String uname = editText.getText().toString().trim();
                String uemail = editText1.getText().toString().trim();
                if (uname.toString().trim().length()!=0&&uemail.toString().trim().length()!=0){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textView.setText("");
                textView1.setText("");
                return true;
            }
        });


    }
        private void runAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animationltr);
        animation.reset();
        animtext1.clearAnimation();
        animtext1.startAnimation(animation);
    }
    private void displayName(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sp.getString(KEY_NAME,null);
        String email = sp.getString(KEY_EMAIL,null);
        if (name!=null){
            textView.setText("Welcome " + name);
            textView1.setText("Email : "+email);
        }

    }

    private void saveName(){
        String uname,uemail;
         uname = editText.getText().toString().trim();
         uemail = editText1.getText().toString().trim();
//        Log.d("user", uname);
//        Log.d("email", uemail);
        if (uname.isEmpty()){
            editText.setError("oops! No Name");
            editText.requestFocus();
        }if (uemail.isEmpty()){
            editText1.setError("Enter Email");
            editText1.requestFocus();
        }
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(KEY_NAME,uname);
        e.putString(KEY_EMAIL,uemail);
        e.apply();
//        editText.setText("");
        displayName();

    }

    @Override
    public void onBackPressed() {
        Intent a =new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}