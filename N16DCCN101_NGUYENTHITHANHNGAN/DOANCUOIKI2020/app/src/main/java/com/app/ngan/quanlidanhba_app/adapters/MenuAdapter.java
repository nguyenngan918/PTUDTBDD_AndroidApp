package com.app.ngan.quanlidanhba_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ngan.quanlidanhba_app.R;
import com.app.ngan.quanlidanhba_app.models.ItemMenu;

import java.util.List;
public class MenuAdapter extends ArrayAdapter<ItemMenu> {
    private Context context;
    private int resource;
    private List<ItemMenu> arrayMenu;
    public MenuAdapter(Context context, int resource, List<ItemMenu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayMenu = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
            viewHolder.itemIcon = convertView.findViewById(R.id.item_icon);
            viewHolder.itemTitle = convertView.findViewById(R.id.item_title);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemMenu itemMenu = arrayMenu.get(position);
        viewHolder.itemIcon.setBackgroundResource(itemMenu.getIcon());
        viewHolder.itemTitle.setText(itemMenu.getTitle());

        return convertView;
    }

    public class ViewHolder{
        ImageView itemIcon;
        TextView itemTitle;
    }
}
