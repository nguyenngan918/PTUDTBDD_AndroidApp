package com.app.ngan.quanlidanhba_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.app.ngan.quanlidanhba_app.Dao.BlacklistDAO;
import com.app.ngan.quanlidanhba_app.models.Blacklist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentContact extends Fragment {
    View v;
    Context contextThemeWrapper;
    RecyclerView recyclerView;
    private List<Contact> listContact;
    RecyclerViewAdapter recyclerViewAdapter;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private BlacklistDAO blackListDao;
    String Uiid;

    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //v = inflater.inflate(R.layout.contact_fragment, container, false);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.LightMode);
        }
        else contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DarkTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.contact_fragment, container, false);

        recyclerView = v.findViewById(R.id.contact_recyclerview);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),listContact,Uiid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        blackListDao = new BlacklistDAO(getContext());
        Uiid  = getArguments().getString("Uiid");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        getDataFireBase();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    public void getDataFireBase(){
        DatabaseReference allContact = mDatabaseRef.child("contacts");
        listContact = new ArrayList<>();

        allContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listContact.clear();
                blackListDao.DropDb();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {

                    Contact c = item.getValue(Contact.class);
                    c.setKey(item.getKey());
                    if(c.getBlock()==1 ){
                       // Log.e("SDTB",c.getPhone()+"  da duoc dua vao");
                       // MainActivity.blockList.add(new Blacklist(c.getPhone()));
                        blackListDao.create(new Blacklist(c.getPhone()));

                    }
                    listContact.add(c);
                }
                MainActivity.blockList = blackListDao.getAllBlacklist();



                recyclerView = v.findViewById(R.id.contact_recyclerview);
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(),listContact,Uiid);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
