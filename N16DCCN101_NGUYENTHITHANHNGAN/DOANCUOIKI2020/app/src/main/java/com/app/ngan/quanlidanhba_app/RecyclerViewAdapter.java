package com.app.ngan.quanlidanhba_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;

import android.widget.Button;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Contact> dataFiltered;
    List<Contact> data;
    Dialog dialog;
   Contact x = new Contact();
   String Uiid;
    private DatabaseReference mDatabaseRef;

    public RecyclerViewAdapter(Context context, List<Contact> data,String Uiid) {
        this.context = context;
        this.data = data;
        //this.dataFiltered = data;
        this.dataFiltered = new ArrayList<>(data);
        this.Uiid = Uiid;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);



        viewHolder.contactItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialog.show();
                showContactDialog(viewHolder, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(dataFiltered.get(position).getName());
        viewHolder.tvPhone.setText(dataFiltered.get(position).getPhone());
        //viewHolder.imgvP.setBackgroundResource(R.drawable.hoa);
        Picasso.get().load(dataFiltered.get(position).getImage()).into(viewHolder.imgvP);
        viewHolder.sttCall.setImageResource(dataFiltered.get(position).getSttCall());
    }

    @Override
    public int getItemCount() {
        return dataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                //Toast.makeText(context, "list full", Toast.LENGTH_SHORT).show();
                filteredList.addAll(data);
                Log.e("as", "performFiltering: " + data.size()+"   " + dataFiltered.size());
            }

            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact contact : data){
                    if(contact.getName().toLowerCase().trim().contains(filterPattern)){
                        filteredList.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                dataFiltered.clear();
                dataFiltered.addAll((List)results.values);
                notifyDataSetChanged();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout contactItem;
        private TextView tvName;
        private TextView tvPhone;
        private ImageView imgvP;
        private ImageView sttCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactItem = itemView.findViewById(R.id.contact_item);
            tvName = itemView.findViewById(R.id.name_contact);
            tvPhone = itemView.findViewById(R.id.phone_contact);
            imgvP = itemView.findViewById(R.id.img_contact);
            sttCall = itemView.findViewById(R.id.stt_call);
        }
    }

    public void showContactDialog(ViewHolder viewHolder, final int position){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.contact_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        x = dataFiltered.get(viewHolder.getAdapterPosition());

        ImageView btnSetting = dialog.findViewById(R.id.btn_setting);
        TextView dialogName = dialog.findViewById(R.id.name);
        TextView dialogPhone = dialog.findViewById(R.id.phone);
        ImageView dialogPhoto = dialog.findViewById(R.id.photo);

        dialogName.setText(dataFiltered.get(viewHolder.getAdapterPosition()).getName());
        dialogPhone.setText(dataFiltered.get(viewHolder.getAdapterPosition()).getPhone());
        Picasso.get().load(dataFiltered.get(viewHolder.getAdapterPosition()).getImage()).into(dialogPhoto);

        //Toast.makeText(context, "Chọn: " + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
        Button btnCall = dialog.findViewById(R.id.btn_call);
        Button btnSMS = dialog.findViewById(R.id.btn_sms);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.Uiid);
                DatabaseReference addContact = mDatabaseRef.child("contacts_history");
                x.setSttCall(R.drawable.ic_call_made);
                addContact.push().setValue(x);
                intentCall(position);
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSMS(position);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home =  new Intent(context,SettingActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("User", x);
                home.putExtra("UserBundle", bundle);
                home.putExtra("flat",0);
                home.putExtra("Uiid",Uiid);
                //startActivity(context,home);
                context.startActivity(home);
            }
        });
        dialog.show();
    }

    private void intentSMS(int position) {
        Intent intent = new Intent();//keu goi lam mot hanh dong nao do, trao doi du lieu trong cung 1 ung dung hoac giua cac ung dung vs nhau
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+dataFiltered.get(position).getPhone()));
        //startActivity(intent);
        startActivity(context, intent, Bundle.EMPTY);

    }

    private void intentCall(int position) {
        Intent intent = new Intent();//keu goi lam mot hanh dong nao do, trao doi du lieu trong cung 1 ung dung hoac giua cac ung dung vs nhau
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+dataFiltered.get(position).getPhone()));
        //startActivity(intent);
        startActivity(context, intent, Bundle.EMPTY);


    }

}
