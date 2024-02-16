package com.ptn.myapplicationforlearn.itemL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptn.myapplicationforlearn.R;

import java.util.List;

public class CustomListAdapter  extends BaseAdapter {

    private List<ItemNote> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<ItemNote> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();

            holder.itemNameTitle = (TextView) convertView.findViewById(R.id.txtTitleNote);
            holder.itemNameContent = (TextView) convertView.findViewById(R.id.txtContentNote);
            holder.itemNameDateCreate = (TextView) convertView.findViewById(R.id.txtDateCreateNote);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemNote itemdraw = this.listData.get(position);

        holder.itemNameTitle.setText(itemdraw.getItemNameTitle());
        holder.itemNameContent.setText(itemdraw.getItemNameContent());
        holder.itemNameDateCreate.setText(itemdraw.getItemNameDateCreate());

        return convertView;
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }

    public static String getSubstringBeforeUnderscore(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] parts = input.split("_");
        return parts[0];
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        TextView itemNameTitle, itemNameContent, itemNameDateCreate;
        LinearLayout linearLayout;
    }

}
