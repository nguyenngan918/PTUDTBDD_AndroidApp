package com.app.ngan.quanlidanhba_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngan.quanlidanhba_app.models.Blacklist;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity
{
    private static final int PICK_IMAGE_REQUEST =1;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageReference storageRef;
    public Uri selectedImage;
    public String Uiid;
    public int flat;
    public Contact user;

    private CircleImageView imgContact;
    private TextView changeImg;
    private EditText lnameContact;
    private EditText nameContact;
    private EditText companyContact;
    private TextView clearPhone;
    private TextView removeHomeAddress;
    private TextView removeGmail;
    private TextView removeFB;
    private TextView removeURL;
    private EditText phoneContact;
    private TextView addPhone;
    private TextView addAddress;
    private TextView addGmail;
    private TextView addFB;
    private TextView addURL;
    private TextView blockCall;
    private TextView blockSMS;
    private TextView deleteContact;
    private LinearLayout containerPhone;
    private LinearLayout containerHomeAddress;
    private LinearLayout containerGmail;
    private LinearLayout containerFB;
    private LinearLayout containerURL;
    private EditText homeAddress, gmail, fbName, url;
    private TextView activeCall;
    private TextView activeSMS;
    private UploadTask uploadTask;
    private static int RESULT_LOAD_IMAGE = 1;

    //list nay la danh sach chan cuoc goi
    public static List<Blacklist> blockCallList;
    // Object of BlacklistDAO to query to database
    //private BlacklistDAO blackListDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.LightMode);
        }
        else setTheme(R.style.DarkTheme);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       /// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setWidget();
        blockOrActive();
//        mStorageRef = FirebaseStorage.getInstance();
//        storageRef = mStorageRef.getReference();


        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("avarter");
        //storageRef = mStorageRef.getReference();
        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);



            }
        });


        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });

        addGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGmail();
            }
        });

        addFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFB();
            }
        });

        addURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addURL();
            }
        });

        clearPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneContact.setText("");
            }
        });

        removeHomeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHomeAddress();
            }
        });

        removeGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeGmail();
            }
        });

        removeFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFB();
            }
        });

        removeURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeURL();
            }
        });

        blockCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoneToBlockList();
            }
        });

        activeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePhoneFromBlockList();
            }
        });

        blockSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSMSToBlockList();
            }
        });

        activeSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSMSFromBlockList();
            }
        });


        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaContract(user.getKey());
                Toast.makeText(getApplicationContext(),R.string.delete_succ, Toast.LENGTH_LONG).show();
                Intent home =  new Intent(getApplicationContext(),MainActivity.class);
                home.putExtra("Uiid",Uiid);
                startActivity(home);
                finish();

            }
        });
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
                            user.setImage(downloadUri.toString());

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

            Picasso.get().load(selectedImage).into(imgContact);
            if(flat == 1){
                flat = 2;

            }


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
        if(item.getItemId() == R.id.nav_edit){
            if(nameContact.getText().length() == 0 || phoneContact.getText().length() == 0){
                Toast.makeText(this ,R.string.err_null,Toast.LENGTH_LONG).show();
                return false;
            }else{
                user.setPhone(phoneContact.getText().toString());
                user.setName(nameContact.getText().toString());
                user.setLastName(lnameContact.getText().toString());
                //user.setSttCall(R.id.stt_call);
//            user.setCompany(companyContact.getText().toString());
                user.setBlock(0);
                //user.setImage("https://drive.google.com/file/d/1mcyhbFB2t4-rRwgmLNV7FSn8y93K6_rH/view?usp=sharing");
            }
            if(flat == 1){
                user.setImage("https://firebasestorage.googleapis.com/v0/b/quanlidanhba.appspot.com/o/meoavar.jpg?alt=media&token=d38a729f-ae50-4819-966c-1e28ac8e30b5");
                user.setSttCall(R.drawable.ic_call_white);
                ThemContactDB(user);
            }else{
                if(flat==2){
                    user.setSttCall(R.drawable.ic_call_white);
                    upGaleryProcess(selectedImage);
                    ThemContactDB(user);

                }
                //Log.e("def","CAp nhat db");
                user.setSttCall(R.drawable.ic_call_white);
                CapNhatContactDB(user);
            }

            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, nameContact.getText(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setWidget(){
        imgContact = findViewById(R.id.img_contact);
        changeImg = findViewById(R.id.change_img);
        phoneContact = findViewById(R.id.phone_contact);
        containerHomeAddress = findViewById(R.id.container_home_address);
        containerGmail = findViewById(R.id.container_gmail);
        containerFB = findViewById(R.id.container_fb);
        containerURL = findViewById(R.id.container_url);
        homeAddress = findViewById(R.id.home_address);
        gmail = findViewById(R.id.gmail);
        fbName = findViewById(R.id.fb_name);
        url = findViewById(R.id.url);
        addAddress = findViewById(R.id.add_address);
        addGmail = findViewById(R.id.add_gmail);
        addFB = findViewById(R.id.add_fb);
        addURL = findViewById(R.id.add_url);
        clearPhone = findViewById(R.id.clear_phone);
        removeHomeAddress = findViewById(R.id.remove_home_address);
        removeGmail = findViewById(R.id.remove_gmail);
        removeFB = findViewById(R.id.remove_fb);
        removeURL = findViewById(R.id.remove_url);
        blockCall = findViewById(R.id.block_call);
        activeCall = findViewById(R.id.active_call);
        blockSMS = findViewById(R.id.block_sms);
        activeSMS = findViewById(R.id.active_sms);
        //companyContact = findViewById(R.id.company_contact);


        lnameContact = findViewById(R.id.lname_contact);
        nameContact = findViewById(R.id.name_contact);
        //companyContact = findViewById(R.id.company_contact);
        deleteContact = findViewById(R.id.delete_contact);


        Intent intent = getIntent();
         flat = intent.getIntExtra("flat",0);
        Uiid = intent.getStringExtra("Uiid");

        if(flat == 1){
            //String Phone = intent.getStringExtra("SDT");
            user = new Contact();
            user.setPhone(intent.getStringExtra("SDT"));
            phoneContact.setText(user.getPhone());
//            user.setPhone(phoneContact.getText().toString());
        }else{

            Bundle bundle = intent.getBundleExtra("UserBundle");
            user =  (Contact) bundle.getSerializable("User");

            if (user.getImage()!=null) {
                Picasso.get().load(user.getImage()).into(imgContact);

            }
            phoneContact.setText(user.getPhone());

            if(user.getLastName()!= null){
                lnameContact.setText(user.getLastName());
                //containerHomeAddress.setText(user.getAccess());
            }

            if(user.getName()!=null){
                nameContact.setText(user.getName());
            }

            if(user.getCompany()!=null){
                // có company
                companyContact.setText(user.getCompany());
            }

            if(user.getAccess()!= null){
                // có địa chỉ
                addAddress();
                homeAddress.setText(user.getAccess());
            }

            if(user.getGmail()!= null){
               // có mail
                addGmail();
                gmail.setText(user.getGmail());
            }

            if(user.getFacebook()!= null){
              // có facebook
                addFB();
                fbName.setText(user.getFacebook());
            }

            if(user.getUrl()!= null){
               //có url
                addURL();
                url.setText(user.getUrl());
            }
        }
    }

    protected void addAddress(){
        containerHomeAddress.setVisibility(View.VISIBLE);
        addAddress.setVisibility(View.GONE);
    }

    protected void addGmail(){
        containerGmail.setVisibility(View.VISIBLE);
        addGmail.setVisibility(View.GONE);
    }

    protected void addFB(){
        containerFB.setVisibility(View.VISIBLE);
        addFB.setVisibility(View.GONE);
    }

    protected void addURL(){
        containerURL.setVisibility(View.VISIBLE);
        addURL.setVisibility(View.GONE);
    }

    protected void removeHomeAddress(){
        containerHomeAddress.setVisibility(View.GONE);
        addAddress.setVisibility(View.VISIBLE);
        homeAddress.setText(null);
    }

    protected void removeGmail(){
        containerGmail.setVisibility(View.GONE);
        addGmail.setVisibility(View.VISIBLE);
        gmail.setText(null);
    }

    protected void removeFB(){
        containerFB.setVisibility(View.GONE);
        addFB.setVisibility(View.VISIBLE);
        fbName.setText(null);
    }

    protected void removeURL(){
        containerURL.setVisibility(View.GONE);
        addURL.setVisibility(View.VISIBLE);
        url.setText(null);
    }

    protected void blockOrActive(){
        //String phone = phoneContact.getText().toString();
        //neu sdt khong ton tai trong bang call block thi hien nut chan cuoc goi
        //nguoc lai thi hien nut bo chan cuoc goi
        //tuong tu doi voi sms block
    }

    protected void addPhoneToBlockList(){
        blockCall.setVisibility(View.GONE);
        activeCall.setVisibility(View.VISIBLE);
        // Once click, it's first creates the Blacklist object
        //final Blacklist phone = new Blacklist();

        // Then, set all the values from user input
        //phone.phoneNumber = phoneContact.getText().toString();

        // Insert the object to the database
        //blackListDao.create(phone);

        // Show the success message to user
        user.setBlock(1);
        Toast.makeText(this, R.string.blocked, Toast.LENGTH_SHORT).show();
    }

    protected void removePhoneFromBlockList(){
        blockCall.setVisibility(View.VISIBLE);
        activeCall.setVisibility(View.GONE);
        user.setBlock(0);
        //blackListDao.delete(phoneContact.getText().toString());
        Toast.makeText(this, R.string.cancel_block, Toast.LENGTH_SHORT).show();
    }

    protected void addSMSToBlockList(){
        blockSMS.setVisibility(View.GONE);
        activeSMS.setVisibility(View.VISIBLE);
        //tuong tu chan cuoc goi
        Toast.makeText(this, R.string.blocked, Toast.LENGTH_SHORT).show();
    }

    protected void removeSMSFromBlockList(){
        blockSMS.setVisibility(View.VISIBLE);
        activeSMS.setVisibility(View.GONE);
        //tuong tu huy chan cuoc goi
        Toast.makeText(this, R.string.cancel_block, Toast.LENGTH_SHORT).show();
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void ThemContactDB(Contact m){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        mDatabaseRef.child("contacts").push().setValue(user);
    }
    private void CapNhatContactDB(Contact user){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        mDatabaseRef.child("contacts").child(user.getKey()).setValue(user);
    }
    protected void XoaContract(String id){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        mDatabaseRef.child("contacts").child(user.getKey()).removeValue();
    }
}
