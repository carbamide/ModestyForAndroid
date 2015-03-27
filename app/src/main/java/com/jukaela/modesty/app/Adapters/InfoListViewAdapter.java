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
import com.jukaela.modesty.app.models.ModestyInfo;
import com.jukaela.modesty.app.models.Server;

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
                return activity.getString(R.string.host_string);
            case 1:
                return activity.getString(R.string.port_string);
            case 2:
                return activity.getString(R.string.version_string);
            case 3:
                return activity.getString(R.string.staff_string);
            case 4:
                return activity.getString(R.string.players_string);
            case 5:
                return activity.getString(R.string.plugins_string);
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
                titleTextView.setText(activity.getString(R.string.host_string));
                detailTextView.setText(tempServer.getHostIp());

                break;
            case 1:
                titleTextView.setText(activity.getString(R.string.port_string));
                detailTextView.setText(Integer.toString(tempServer.getHostPort()));

                break;
            case 2:
                titleTextView.setText(activity.getString(R.string.version_string));
                detailTextView.setText(tempServer.getVersion());

                break;
            case 3:
                titleTextView.setText(activity.getString(R.string.staff_string));
                detailTextView.setText(activity.getString(R.string.staff_description_string));

                break;
            case 4:
                titleTextView.setText(activity.getString(R.string.players_string));
                detailTextView.setText(String.format("%d %s %d %s", tempServer.getPlayers(), activity.getString(R.string.of_string), tempServer.getMaxPlayers(), activity.getString(R.string.max_players_string)));

                break;
            case 5:
                titleTextView.setText(activity.getString(R.string.plugins_string));
                detailTextView.setText(activity.getString(R.string.plugin_description_string));

                break;
        }

        return convertView;
    }
}