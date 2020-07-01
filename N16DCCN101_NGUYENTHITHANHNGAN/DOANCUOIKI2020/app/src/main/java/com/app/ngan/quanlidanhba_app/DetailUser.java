package com.app.ngan.quanlidanhba_app;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngan.quanlidanhba_app.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailUser extends AppCompatActivity {
    private ImageView linkAvatar;
    private TextView change, language;
    private EditText name, email, birthday, work, relationship,lname;
    private RadioButton male, female;
    private RadioGroup rG_gioiTinh;
    private Switch darkMode;
    public Uri selectedImage;
    private static final int PICK_IMAGE_REQUEST =1;
    private static int RESULT_LOAD_IMAGE = 1;
    private User user;
    private UploadTask uploadTask; private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private int flat = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.LightMode);
        }
        else setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_user);
        loadLocale();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("UserBundle");
        user =  (User) bundle.getSerializable("User");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Log.e("data",user.getId()+" no ở đây");
        setWidget();
        Picasso.get().load(user.getLinkAvatar()).into(linkAvatar);
        name.setText(user.getName());

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            darkMode.setChecked(true);
        }
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        linkAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DetailUser.this, "language", Toast.LENGTH_SHORT).show();
                showChangeLangDialog();
            }
        });
    }

    private void setThemeApp() {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.LightMode);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            darkMode.setChecked(true);
        }
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });
    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(), DetailUser.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("User", user);
        i.putExtra("UserBundle", bundle);
        startActivity(i);
        finish();
    }

    private void showChangeLangDialog() {
        final String [] listItems = {"English(US)", "French", "Vietnamese"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailUser.this);
        mBuilder.setTitle(R.string.lang_dialog);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Log.e("abc", i+"");
                if(i == 0){
                    setLocale("en");
                    recreate();
                   // Log.e("aef", "onClick:  vao su kien thay doi");
                   // name.setText(R.string.hint_fname);

                }
                else if(i == 1){
                    setLocale("fr");
                    recreate();
                   // name.setText(R.string.hint_fname);
                }
                else if(i == 2 ){
                    setLocale("vi");
                    recreate();
                   /// name.setText(R.string.hint_fname);
                }
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
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
        String language =  prefs.getString("My_Lang", "en");
        setLocale(language);
    }
    private void upGaleryProcess(Uri linkAnh){
        final StorageReference riversRef = mStorageRef.child("User/"+Constants.Uiid+"/"+linkAnh.getLastPathSegment());
        uploadTask = riversRef.putFile(linkAnh);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Xu ly K thanh cong
                Log.e("AAA",exception.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Thanh Cong
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            user.setLinkAvatar(downloadUri.toString());

                        } else {

                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data && data.getData() != null) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Picasso.get().load(selectedImage).into(linkAvatar);
//            upGaleryProcess(selectedImage);
//            if(flat == 1){
//                //ThemContactDB(user);
//            }else{
//                CapNhatContactDB(user);
//            }
            flat++;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(nameContact.equals("")||phoneContact.equals("")){
//            Toast.makeText(getApplicationContext(),"Tên hoặc số điện thoại trống",Toast.LENGTH_LONG);
//            return false;
//        }else{
//            user.setPhone(phoneContact.getText().toString());
//            user.setName(nameContact.getText().toString());
//            user.setLastName(lnameContact.getText().toString());
//            user.setCompany(companyContact.getText().toString());
//            user.setBlock(0);
//            user.setImage("https://drive.google.com/file/d/1mcyhbFB2t4-rRwgmLNV7FSn8y93K6_rH/view?usp=sharing");
//        }
//        if(flat == 1){
//            user.setSttCall(R.drawable.ic_call_white);
//            ThemContactDB(user);
//        }else{
//            //Log.e("def","CAp nhat db");
//            CapNhatContactDB(user);
//        }
        if(flat == 1){
            CapNhatContactDB(user);
        }else{
            upGaleryProcess(selectedImage);
            CapNhatContactDB(user);
        }
        if(item.getItemId() == R.id.nav_edit){
            Toast.makeText(this, R.string.saved_1, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWidget() {
        linkAvatar = findViewById(R.id.linkAvatar);
        change = findViewById(R.id.change);
        name = findViewById(R.id.name);
        lname = findViewById(R.id.lname);
        birthday = findViewById(R.id.birthday);
        work = findViewById(R.id.work);
        relationship = findViewById(R.id.relationship);
        email = findViewById(R.id.email);
        birthday = findViewById(R.id.birthday);
        work = findViewById(R.id.work);
        relationship = findViewById(R.id.relationship);
        language = findViewById(R.id.language);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        darkMode = findViewById(R.id.dark_mode);
        rG_gioiTinh = findViewById(R.id.sex);
        setValue(user);
    }
    public void setValue(User u){
        if(u.getFname()!=null){
            lname.setText(u.getFname());
        }
        if(u.getEmail()!=null){
            email.setText(u.getEmail());
        }
        if(u.getBirthday()!=null){
            birthday.setText(u.getBirthday());
        }
        if(u.getWork()!=null){
            work.setText(u.getWork());
        }
        if(u.getRelationship()!=null){
            relationship.setText(u.getRelationship());
        }
        if(u.getSex()!=null){
            if(u.getSex().equals("male")){

                male.setChecked(true);
            }else{

                female.setChecked(false);
            }
        }
    }
    private void CapNhatContactDB(User u){
        if(user.getLinkAvatar()!=null){
            mDatabaseRef.child("linkAvatar").setValue(user.getLinkAvatar());
        }
        if(!email.getText().toString().equals("")){
            mDatabaseRef.child("email").setValue(email.getText().toString());
        }
        if(!name.getText().toString().equals("")){
            mDatabaseRef.child("name").setValue(name.getText().toString());
        }
        if(!lname.getText().toString().equals("")){
            mDatabaseRef.child("fname").setValue(lname.getText().toString());
        }
        if(!birthday.getText().toString().equals("")){
            mDatabaseRef.child("birthday").setValue(birthday.getText().toString());
        }
        if(!work.getText().toString().equals("")){
            mDatabaseRef.child("work").setValue(work.getText().toString());
        }
        if(!relationship.getText().toString().equals("")){
            mDatabaseRef.child("relationship").setValue(relationship.getText().toString());
        }
        int selectedId = rG_gioiTinh.getCheckedRadioButtonId();
        RadioButton radioSex = findViewById(selectedId);
        if(radioSex.getText().toString().equals(R.string.male)){
            mDatabaseRef.child("sex").setValue("male");
        }else {
            mDatabaseRef.child("sex").setValue("female");
        }


    }
}
