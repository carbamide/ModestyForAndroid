package com.jukaela.modesty.app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jbarrow on 5/7/14.  Yay!
 */

public class PluginsListViewAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private ArrayList<String> data = null;

    public PluginsListViewAdapter(Context _context, ArrayList<String> _data)
    {
        super(_context, android.R.layout.simple_list_item_1, _data);

        this.context = _context;
        this.data = _data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
        }

        String plugin = data.get(position);

        TextView mainTextView = (TextView)convertView.findViewById(android.R.id.text1);

        mainTextView.setText(plugin);

        return convertView;
    }
}