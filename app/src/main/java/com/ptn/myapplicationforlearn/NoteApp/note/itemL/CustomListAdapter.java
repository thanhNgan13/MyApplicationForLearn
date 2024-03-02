package com.ptn.myapplicationforlearn.NoteApp.note.itemL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptn.myapplicationforlearn.R;

import java.util.List;

public class CustomListAdapter  extends BaseAdapter {

    private List<ItemNote> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    private boolean isShowCheckBox = false;

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
            holder.checkBoxDeleteNote = (CheckBox) convertView.findViewById(R.id.checkBoxDeleteNote);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemNote itemdraw = this.listData.get(position);

        holder.itemNameTitle.setText(itemdraw.getItemNameTitle());
        holder.itemNameContent.setText(itemdraw.getItemNameContent());
        holder.itemNameDateCreate.setText(itemdraw.getItemNameDateCreate());
        holder.checkBoxDeleteNote.setVisibility(isShowCheckBox ? View.VISIBLE : View.GONE);
        return convertView;
    }


    public void setShowCheckbox(boolean show) {
        this.isShowCheckBox = show;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView itemNameTitle, itemNameContent, itemNameDateCreate;
        CheckBox checkBoxDeleteNote;
        LinearLayout linearLayout;
    }

}
