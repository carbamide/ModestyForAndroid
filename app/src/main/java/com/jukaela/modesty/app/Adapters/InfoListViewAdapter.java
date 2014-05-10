package com.jukaela.modesty.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jukaela.modesty.app.Models.ModestyInfo;
import com.jukaela.modesty.app.Models.Server;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class InfoListViewAdapter extends BaseAdapter implements ListAdapter
{
    private final Activity activity;
    private final ModestyInfo modestyInfo;

    public InfoListViewAdapter(Activity activity, ModestyInfo _modestyInfo) {
        assert activity != null;
        assert _modestyInfo != null;

        this.modestyInfo = _modestyInfo;
        this.activity = activity;
    }


    @Override public int getCount() {

        return 6;
    }

    @Override public String getItem(int position) {

        switch (position) {
            case 0:
                return "Host";
            case 1:
                return "Port";
            case 2:
                return "Version";
            case 3:
                return "Staff";
            case 4:
                return "Players";
            case 5:
                return "Plugins";
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
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, null);
        }

        Server tempServer = this.modestyInfo.getServerInformation();

        assert convertView != null;

        TextView titleTextView = (TextView) convertView.findViewById(android.R.id.text1);
        TextView detailTextView = (TextView) convertView.findViewById(android.R.id.text2);

        switch (position) {
            case 0:
                titleTextView.setText("Host");
                detailTextView.setText(tempServer.getHostIp());

                break;
            case 1:
                titleTextView.setText("Port");
                detailTextView.setText(Integer.toString(tempServer.getHostPort()));

                break;
            case 2:
                titleTextView.setText("Version");
                detailTextView.setText(tempServer.getVersion());

                break;
            case 3:
                titleTextView.setText("Staff");
                detailTextView.setText("Staff Listing, along with Ranks");

                break;
            case 4:
                titleTextView.setText("Players");
                detailTextView.setText(tempServer.getPlayers() + " of " + tempServer.getMaxPlayers() + " max players");

                break;
            case 5:
                titleTextView.setText("Plugins");
                detailTextView.setText("Plugin Information and More!");

                break;
        }

        return convertView;
    }
}