package com.jukaela.modesty.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jukaela.modesty.app.R;

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
                return activity.getString(R.string.twitter_string);
            case 1:
                return activity.getString(R.string.instagram_string);
            case 2:
                return activity.getString(R.string.facebook_string);
            case 3:
                return activity.getString(R.string.modesty_forums_string);
            case 4:
                return activity.getString(R.string.planetminecraft_string);
            case 5:
                return activity.getString(R.string.minecraftservers_org_string);
            case 6:
                return activity.getString(R.string.minecraft_servers_list_string);
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
                titleTextView.setText(activity.getString(R.string.twitter_string));

                break;
            case 1:
                titleTextView.setText(activity.getString(R.string.instagram_string));

                break;
            case 2:
                titleTextView.setText(activity.getString(R.string.facebook_string));

                break;
            case 3:
                titleTextView.setText(activity.getString(R.string.modesty_forums_string));

                break;
            case 4:
                titleTextView.setText(activity.getString(R.string.planetminecraft_string));

                break;
            case 5:
                titleTextView.setText(activity.getString(R.string.minecraftservers_org_string));

                break;
            case 6:
                titleTextView.setText(activity.getString(R.string.minecraft_servers_list_string));

                break;
        }

        return convertView;
    }
}