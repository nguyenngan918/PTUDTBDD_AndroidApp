package com.app.ngan.quanlidanhba_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentSms extends Fragment {
    View v;
    Context contextThemeWrapper;
    RecyclerView recyclerView;
    private List<SMS> listSMS;

    public FragmentSms() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //v = inflater.inflate(R.layout.sms_fragment, container, false);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.LightMode);
        }
        else contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DarkTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.sms_fragment, container, false);

        recyclerView = v.findViewById(R.id.sms_recyclerview);
        RecyclerViewAdapterForSMS recyclerViewAdapterForSMS = new RecyclerViewAdapterForSMS(getContext(),listSMS);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapterForSMS);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listSMS = new ArrayList<>();
        listSMS.add(new SMS("Ngân Nguyễn","Tao nè"));
        listSMS.add(new SMS("Diễm Lê","Nhậu đey"));
        listSMS.add(new SMS("Thu Kara","Mở sòng"));
    }
}
