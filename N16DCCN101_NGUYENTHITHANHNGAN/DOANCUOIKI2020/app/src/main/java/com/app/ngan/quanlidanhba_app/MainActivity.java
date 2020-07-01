package com.app.ngan.quanlidanhba_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngan.quanlidanhba_app.Dao.BlacklistDAO;
import com.app.ngan.quanlidanhba_app.adapters.MenuAdapter;
import com.app.ngan.quanlidanhba_app.models.Blacklist;
import com.app.ngan.quanlidanhba_app.models.ItemMenu;
import com.app.ngan.quanlidanhba_app.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{
    ImageView changeBackHeader;
    LinearLayout backHeader;
    private LinearLayout itemEdit, itemLogout, itemHelp;
    private NavigationView nav;
    private CircleImageView activity_main_imv_avatar;

    public String Uiid;
    private DatabaseReference mData;
    private FirebaseStorage storage;

    private BlacklistDAO blackListDao;

    public static List<Blacklist> blockList;


    private List<ItemMenu> arrayMenu;
    private ListView menu;
    private ImageView itemIcon;
    private TextView itemTitle,activity_main_tv_user_name,activity_main_tv_email;
    private MenuAdapter menuAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    public Uri selectedImage;
    private static int RESULT_LOAD_IMAGE = 1;
    CustomLayout myBackHeader;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.LightMode);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.LightMode);
        }
        else setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db list
        blackListDao = new BlacklistDAO(this);

        // Fetch the list of Black listed numbers from Database using DAO object
       // blackListDao.DropDb();
        //blockList = blackListDao.getAllBlacklist();

        // permissions
        Permissions.checkAndRequest(this);

        // init settings defaults
        //Settings.initDefaults(this);
        // Initialize the DAO object
       // blackListDao = new BlacklistDAO(this);

        // Fetch the list of Black listed numbers from Database using DAO object
       // blockList = blackListDao.getAllBlacklist();

        Intent intent = getIntent();
        Uiid = intent.getStringExtra("Uiid");
        mData = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);
                user.setId(dataSnapshot.getKey());

                if(user.getName()!=null){
                    activity_main_tv_user_name.setText(user.getName());
                }
                if(user.getLinkAvatar()!=null){
                    Picasso.get().load(user.getLinkAvatar()).into(activity_main_imv_avatar);
                }
                if(user.getLinkBackground()!=null){
                   // Picasso.get().load(user.getLinkBackground()).into(backHeader);
                }
                activity_main_tv_email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setWidget();

        arrayMenu = new ArrayList<>();
        arrayMenu.add(new ItemMenu(R.drawable.ic_edit_black, R.string.fix_info));
        arrayMenu.add(new ItemMenu(R.drawable.ic_exit_to_app_black, R.string.logout));
        arrayMenu.add(new ItemMenu(R.drawable.ic_help_outline_black, R.string.help));

        menuAdapter = new MenuAdapter(this, R.layout.item_menu, arrayMenu);
        menu.setAdapter(menuAdapter);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        //nav = findViewById(R.id.nav);

        //them fragment



        Bundle bundle = new Bundle();
        bundle.putString("Uiid", Uiid);
        FragmentCall fragmentCall = new FragmentCall();
        fragmentCall.setArguments(bundle);

        FragmentContact fragmentContact = new FragmentContact();
        fragmentContact.setArguments(bundle);

        FragmentAddContact fragmentAddContact = new FragmentAddContact();
        fragmentAddContact.setArguments(bundle);


        viewPageAdapter.AddFragment(fragmentCall, "");
        viewPageAdapter.AddFragment(fragmentContact, "");
        viewPageAdapter.AddFragment(new FragmentSms(), "");
        viewPageAdapter.AddFragment(fragmentAddContact, "");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_call);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_people);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_sms);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_dialpad);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setupTabIcons(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        changeBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "back", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        //Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(getApplicationContext(),DetailUser.class);
                        Bundle bundle = new Bundle();

                        bundle.putSerializable("User", user);
                        intent.putExtra("UserBundle", bundle);
                        //home.putExtra("flat",0);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        //Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent =  new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        Toast.makeText(MainActivity.this, R.string.help, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

//    public void getifoUserUiid(){
//
//        mData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot dataSnapshot) {
//               // User user = new User();
//
//                User item = dataSnapshot.getValue(User.class);
//                item.setId(dataSnapshot.getKey());
//                Log.e("data",item.getId());
//
//                //User x = user;
//                //return x;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//        //return user;
//
//    }

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

            Picasso.get().load(selectedImage).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    backHeader.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
//            upGaleryProcess(selectedImage);
//            if(flat == 1){
//                //ThemContactDB(user);
//            }else{
//                CapNhatContactDB(user);
//            }


        }
    }

    private void setupTabIcons(int pos) {
        switch (pos){
            case 0:
//                tabLayout.getTabAt(0).setIcon(R.drawable.ic_people);
//                tabLayout.getTabAt(1).setIcon(tabIconsNoFocus[1]);
//                tabLayout.getTabAt(2).setIcon(tabIconsNoFocus[2]);

                break;
            case 1:
//                tabLayout.getTabAt(0).setIcon(tabIconsNoFocus[0]);
//                tabLayout.getTabAt(1).setIcon(tabIconsFocus[1]);
//                tabLayout.getTabAt(2).setIcon(tabIconsNoFocus[2]);

                break;

            case 2:
//                tabLayout.getTabAt(0).setIcon(tabIconsNoFocus[0]);
//                tabLayout.getTabAt(1).setIcon(tabIconsNoFocus[1]);
//                tabLayout.getTabAt(2).setIcon(tabIconsFocus[2]);

                break;
        }
    }

    private void setWidget(){
        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewpaper_id);
        activity_main_imv_avatar = findViewById(R.id.activity_main_imv_avatar);
        activity_main_tv_user_name = findViewById(R.id.activity_main_tv_user_name);
        activity_main_tv_email = findViewById(R.id.activity_main_tv_email);

        changeBackHeader = findViewById(R.id.change_back_header);
        backHeader = findViewById(R.id.back_header);
        menu = findViewById(R.id.menu);
        itemIcon = findViewById(R.id.item_icon);
        itemTitle = findViewById(R.id.item_title);




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAndRequestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissions.add(permission);
            }
        }
        if (!listPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissions.toArray(new String[listPermissions.size()]), 1);
        }
    }


   // @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Initialize the DAO object
//        blackListDao = new BlacklistDAO(this);
//
//        // Fetch the list of Black listed numbers from Database using DAO object
//       // blackListDao.DropDb();
//        blockList = blackListDao.getAllBlacklist();
//
//        //context.deleteDatabase(DATABASE_NAME);
//
//
//    }

}
