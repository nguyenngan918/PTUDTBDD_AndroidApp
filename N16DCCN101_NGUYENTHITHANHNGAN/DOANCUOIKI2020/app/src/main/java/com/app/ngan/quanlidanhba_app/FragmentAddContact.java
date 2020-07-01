package com.app.ngan.quanlidanhba_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ContextThemeWrapper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.provider.MediaStore.Images.ImageColumns.DESCRIPTION;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static android.support.v4.content.ContextCompat.startActivity;

public class FragmentAddContact extends Fragment {
    View v;
    Context contextThemeWrapper;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnStar, btn0, btnSharp;
    private ImageView addNewPhone, backspace, call;
    private EditText phoneContact;
    private String Uiid;
    private DatabaseReference mDatabaseRef;

    public FragmentAddContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.LightMode);
        }
        else contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DarkTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        //v = inflater.inflate(R.layout.add_contact_fragment, container, false);
        v = localInflater.inflate(R.layout.add_contact_fragment, container, false);
        Uiid = getArguments().getString("Uiid");
//        Log.e("def",Uiid);
        setWidget();
        setEventClickView();
        return v;
        //return  v;
    }

    public void setWidget(){
        btn0 = v.findViewById(R.id.btn_0);
        btn1 = v.findViewById(R.id.btn_1);
        btn2 = v.findViewById(R.id.btn_2);
        btn3 = v.findViewById(R.id.btn_3);
        btn4 = v.findViewById(R.id.btn_4);
        btn5 = v.findViewById(R.id.btn_5);
        btn6 = v.findViewById(R.id.btn_6);
        btn7 = v.findViewById(R.id.btn_7);
        btn8 = v.findViewById(R.id.btn_8);
        btn9 = v.findViewById(R.id.btn_9);
        btnStar = v.findViewById(R.id.btn_star);
        btnSharp = v.findViewById(R.id.btn_sharp);
        call = v.findViewById(R.id.call);
        addNewPhone = v.findViewById(R.id.add_new_phone);
        backspace = v.findViewById(R.id.backspace);
        phoneContact = v.findViewById(R.id.phone_contact);
    }

    public void setEventClickView(){
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phoneContact.setText("");
                return false;
            }
        });
        addNewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });
        btnSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(v);
            }
        });

    }

    public void pressButton(View v){
        switch (v.getId()){
            case R.id.btn_0:{
                phoneContact.append("0");
                break;
            }
            case R.id.btn_1:{
                phoneContact.append("1");
                break;
            }
            case R.id.btn_2:{
                phoneContact.append("2");
                break;
            }
            case R.id.btn_3:{
                phoneContact.append("3");
                break;
            }
            case R.id.btn_4:{
                phoneContact.append("4");
                break;
            }
            case R.id.btn_5:{
                phoneContact.append("5");
                break;
            }
            case R.id.btn_6:{
                phoneContact.append("6");
                break;
            }
            case R.id.btn_7:{
                phoneContact.append("7");
                break;
            }
            case R.id.btn_8:{
                phoneContact.append("8");
                break;
            }
            case R.id.btn_9:{
                phoneContact.append("9");
                break;
            }
            case R.id.btn_star:{
                phoneContact.append("*");
                break;
            }
            case R.id.btn_sharp:{
                phoneContact.append("#");
                break;
            }

            case R.id.add_new_phone:{
                if(phoneContact.getText().length() == 0){
                    return;
                }
                Intent home =  new Intent(getContext(),SettingActivity.class);
                home.putExtra("flat",1);
                home.putExtra("Uiid",Constants.Uiid);
                home.putExtra("SDT",phoneContact.getText().toString().trim());
                startActivity(home);
                break;
            }
            case R.id.backspace:{
                BaseInputConnection textFieldInputConnection = new BaseInputConnection(phoneContact, true);
                textFieldInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;
            }
            case R.id.call:{
                if(!phoneContact.getText().toString().equals("")){
                    // add PhoneStateListener
                    PhoneCallListener phoneListener = new PhoneCallListener();
                    TelephonyManager telephonyManager = (TelephonyManager) getActivity()
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

                    mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
                    DatabaseReference addContact = mDatabaseRef.child("contacts_history");
                    Contact x = new Contact();
                    x.setSttCall(R.drawable.ic_call_made);
                    x.setPhone(phoneContact.getText().toString().trim());
                    addContact.push().setValue(x);

                    Intent intent = new Intent();//keu goi lam mot hanh dong nao do, trao doi du lieu trong cung 1 ung dung hoac giua cac ung dung vs nhau
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneContact.getText()));
                    //startActivity(intent);
                    startActivity(intent, Bundle.EMPTY);
                }
                else{
                    Toast.makeText(getContext(), R.string.enter_num, Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended, 
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    FragmentManager fm = getFragmentManager();
//                    fm.popBackStack ();
                    isPhoneCalling = false;
                }

            }
        }
    }
    
}
