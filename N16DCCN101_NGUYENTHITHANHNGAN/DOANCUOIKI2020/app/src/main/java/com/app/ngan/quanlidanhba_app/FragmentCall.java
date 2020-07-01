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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;


public class FragmentCall extends Fragment {
    View v;
    Context contextThemeWrapper;
    RecyclerView recyclerView;
    private List<Contact> listContact;
    private DatabaseReference mDatabaseRef;

    public FragmentCall() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.LightMode);
        }
        else contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DarkTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.call_fragment, container, false);

        recyclerView = v.findViewById(R.id.call_recyclerview);
        String Uiid = getArguments().getString("Uiid");
       // Log.e("abcd",Uiid);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),listContact,Constants.Uiid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
        getDataFireBase();

       }

    public void getDataFireBase(){
        DatabaseReference allContact = mDatabaseRef.child("contacts_history");
        listContact = new ArrayList<>();

        allContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listContact.clear();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {

                    Contact c = item.getValue(Contact.class);
                    c.setKey(item.getKey());
                    listContact.add(c);
                }



                recyclerView = v.findViewById(R.id.call_recyclerview);
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),listContact,Constants.Uiid);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
