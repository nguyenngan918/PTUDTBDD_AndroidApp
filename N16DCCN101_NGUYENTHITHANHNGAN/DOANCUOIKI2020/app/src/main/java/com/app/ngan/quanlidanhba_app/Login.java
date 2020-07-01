package com.app.ngan.quanlidanhba_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Login extends AppCompatActivity {

    RelativeLayout btn_login;
    TextView register;
    EditText userName,pass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.LightMode);
        }
        else setTheme(R.style.DarkTheme);
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setWidget();
        userName = findViewById(R.id.userName);
        pass  = findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent home =  new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(home);
               DangNhap();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });
    }

    void setWidget(){
        btn_login = findViewById(R.id.login);
        register = findViewById(R.id.textView2);
    }
    private void DangNhap(){
        Log.e("mau nen", R.attr.gradient_1 + "");
        String email = userName.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if(email.equals("")||password.equals("")){
            Toast.makeText(Login.this,"Login name or password not allow null",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),R.string.err_null_1,Toast.LENGTH_LONG).show();
            return;
        }
        //Log.e("AAAA",email + password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("abc", "onComplete: ");

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(getApplicationContext(), R.string.done + " "+task.getResult()+"  "+user.getUid(),Toast.LENGTH_LONG).show();
                            Toast.makeText(Login.this,"Login success",Toast.LENGTH_SHORT).show();

                            Intent home =  new Intent(getApplicationContext(),MainActivity.class);
                            home.putExtra("Uiid",user.getUid());
                            Constants.Uiid = user.getUid();
                            startActivity(home);
                            finish();
                        } else {
                            Log.e("AAA",task.getException().toString());
                            Toast.makeText(Login.this,"Login fail",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),R.string.err + " "+ task.getException(),Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        //Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
//        Intent intent = new Intent(DetailUser.this,DetailUser.class);
//        startActivity(intent);

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language =  prefs.getString("My_Lang", "vi");
        setLocale(language);
    }
}
