package com.lany.box.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lany.box.R;

import java.util.List;

public class SimpleAdapter extends BasicAdapter<String> {

    public SimpleAdapter(List<String> items) {
        super(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.simple_item, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(getItem(position));
        return convertView;
    }

    class ViewHolder extends BaseViewHolder {
        TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_text);
        }
    }
}
