package com.jukaela.modesty.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class SocialListViewAdapter extends BaseAdapter implements ListAdapter
{
    private final Activity activity;

    public SocialListViewAdapter(Activity activity) {
        assert activity != null;

        this.activity = activity;
    }


    @Override public int getCount() {

        return 6;
    }

    @Override public String getItem(int position) {

        switch (position) {
            case 0:
                return "Twitter";
            case 1:
                return "Instagram";
            case 2:
                return "Facebook";
            case 3:
                return "Modesty Forums";
            case 4:
                return "PlanetMinecraft";
            case 5:
                return "Minecraftservers.org";
            case 6:
                return "Minecraft Servers List";
            default:
                return "";
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        assert convertView != null;

        TextView titleTextView = (TextView) convertView.findViewById(android.R.id.text1);

        switch (position) {
            case 0:
                titleTextView.setText("Twitter");

                break;
            case 1:
                titleTextView.setText("Instagram");

                break;
            case 2:
                titleTextView.setText("Facebook");

                break;
            case 3:
                titleTextView.setText("Modesty Forums");

                break;
            case 4:
                titleTextView.setText("PlanetMinecraft");

                break;
            case 5:
                titleTextView.setText("Minecraftservers.org");

                break;
            case 6:
                titleTextView.setText("Minecraft Servers List");

                break;
        }

        return convertView;
    }
}