package com.app.ngan.quanlidanhba_app;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapterForSMS extends RecyclerView.Adapter<RecyclerViewAdapterForSMS.ViewHolder> {
    Context context;
    List<SMS> data;
    Dialog dialog;

    public RecyclerViewAdapterForSMS(Context context, List<SMS> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.sms_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);



        viewHolder.smsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, R.string.detail_sms, Toast.LENGTH_SHORT).show();
                //dialog.show();
                //showContactDialog(viewHolder, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(data.get(position).getNameSMS());
        viewHolder.cpnName.setText(data.get(position).getCpnSMS());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout smsItem;
        private TextView tvName;
        private TextView cpnName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            smsItem = itemView.findViewById(R.id.sms_item);
            tvName = itemView.findViewById(R.id.name_sms);
            cpnName = itemView.findViewById(R.id.cpn_sms);
        }
    }
}
