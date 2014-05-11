package com.jukaela.modesty.app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jukaela.modesty.app.adapters.PluginsListViewAdapter;
import com.jukaela.modesty.app.models.DataMapper;
import com.jukaela.modesty.app.R;

import java.util.ArrayList;


public class PluginListActivity extends ModestyActivity {

    private static final String kXpBanker = "http://dev.bukkit.org/bukkit-plugins/xpbanker/";
    private static final String kCaptureCraft = "http://dev.bukkit.org/bukkit-plugins/capture-craft/";
    private static final String kDisguiseCraft = "http://dev.bukkit.org/bukkit-plugins/disguisecraft/";
    private static final String kMcmmo = "http://dev.bukkit.org/bukkit-plugins/mcmmo/";
    private static final String kModestyHomepage = "http://www.minecraftmodesty.enjin.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        if (DataMapper.getSharedInstance().getStaff() != null) {
            ArrayList<String> pluginArray = new ArrayList<String>();

            for (String plugin : DataMapper.getSharedInstance().getModestyInfo().getServerInformation().getPlugins()) {
                if (plugin.toLowerCase().contains("capturecraft") ||
                        plugin.toLowerCase().contains("disguisecraft") ||
                        plugin.toLowerCase().contains("mcmmo") ||
                        plugin.toLowerCase().contains("xpbanker")) {
                    pluginArray.add(plugin);
                }
            }

            pluginArray.add("And Many More!");

            PluginsListViewAdapter listViewAdapter = new PluginsListViewAdapter(getApplicationContext(), pluginArray);

            ListView listView = (ListView) findViewById(R.id.listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
                {
                    String value = (String) adapter.getItemAtPosition(position);

                    if (value.toLowerCase().contains("capturecraft")) {
                        openURLInIntent(kCaptureCraft);
                    }
                    else if (value.toLowerCase().contains("disguisecraft")) {
                        openURLInIntent(kDisguiseCraft);
                    }
                    else if (value.toLowerCase().contains("mcmmo")) {
                        openURLInIntent(kMcmmo);
                    }
                    else if (value.toLowerCase().contains("xpbanker")) {
                        openURLInIntent(kXpBanker);
                    }
                    else {
                        showMarketingFluff();
                    }
                }
            });

            if ((listView != null) && (listViewAdapter != null)) {
                listView.setAdapter(listViewAdapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plugin_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void openURLInIntent(String urlString)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

        startActivity(browserIntent);
    }

    private void showMarketingFluff() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Secret Sauce!");
        alertDialog.setMessage("Part of what makes Modesty so great is the secret sauce of plugins that have created such a great environment for us to enjoy!  Come check it out!");
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                openURLInIntent(kModestyHomepage);
            }
        });

        alertDialog.setNegativeButton(android.R.string.cancel, null);
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.show();
    }
}
