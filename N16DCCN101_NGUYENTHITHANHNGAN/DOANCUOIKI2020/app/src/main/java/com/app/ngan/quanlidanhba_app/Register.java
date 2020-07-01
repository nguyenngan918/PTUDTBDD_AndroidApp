package com.app.ngan.quanlidanhba_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngan.quanlidanhba_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    TextView haveLogin;
    EditText userName,pass,comfirm_pass;
    LinearLayout signup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.LightMode);
        }
        else setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setWidget();

        haveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();
            }
        });
    }

    void setWidget(){
        haveLogin = findViewById(R.id.have_login);
        userName = findViewById(R.id.userName);
        pass = findViewById(R.id.pass);
        comfirm_pass = findViewById(R.id.comfirm_pass);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

    }
    private void DangKy(){
        String email = userName.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String comfirm_password = comfirm_pass.getText().toString().trim();

        if(email.equals("")||password.equals("")){
            Toast.makeText(getApplicationContext(),R.string.err_null_1,Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(comfirm_password)){
            Toast.makeText(getApplicationContext(),R.string.confirm_pass,Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            mDatabaseRef = FirebaseDatabase.getInstance().getReference(user.getUid());
                            User u = new User();
                            u.setEmail(user.getEmail());
                            u.setLinkAvatar("https://firebasestorage.googleapis.com/v0/b/quanlidanhba.appspot.com/o/meoFace.jpg?alt=media&token=0c17c2e5-0267-4e66-a47c-22f53a8e58b4");
                            u.setName("User new");
                            mDatabaseRef.setValue(u);
                            Toast.makeText(getApplicationContext(),R.string.register_succ,Toast.LENGTH_LONG).show();
                            Intent intent =  new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(),R.string.err + " " +task.getException(),Toast.LENGTH_LONG).show();
                            Log.e("AAA",task.getException().toString());
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });

    }
}
