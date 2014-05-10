package com.jukaela.modesty.app.Models;

import android.util.Log;

import com.jukaela.modesty.app.Activities.MainActivity;
import com.jukaela.modesty.app.Tasks.PingTask;
import com.jukaela.modesty.app.Tasks.ServerInformationTask;
import com.jukaela.modesty.app.Tasks.StaffListingTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class DataMapper
{
    private static DataMapper sharedInstance;

    public ModestyInfo getModestyInfo()
    {
        return modestyInfo;
    }

    void setModestyInfo(ModestyInfo modestyInfo)
    {
        this.modestyInfo = modestyInfo;
    }

    private ModestyInfo modestyInfo;

    public ArrayList<Staff> getStaff()
    {
        return staff;
    }

    void setStaff(ArrayList<Staff> staff)
    {
        this.staff = staff;
    }

    private ArrayList<Staff> staff;

    public void setActivity(MainActivity activity)
    {
        this.activity = activity;
    }

    private MainActivity activity;

    private DataMapper() {}

    public static DataMapper getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new DataMapper();
        }

        return sharedInstance;
    }

    public void refreshInformation() throws MalformedURLException
    {
        new ServerInformationTask().execute(new URL("http://aqueous-lowlands-3303.herokuapp.com"));
    }

    public void refreshInformationCallback(JSONObject jsonObject) throws JSONException
    {
        if (jsonObject != null) {
            mapToModestyInfo(jsonObject);

            this.activity.reloadListViewAdapter();

            Log.i("DataMapper", jsonObject.toString());
        }
    }

    public void pingModesty () throws IOException
    {
        new PingTask().execute(new URL("http://aqueous-lowlands-3303.herokuapp.com/ping.php"));
    }

    public void pingModestyCallback(JSONObject jsonObject)
    {
        if (jsonObject != null) {
            try {
                if (jsonObject.getString("status").equals("up")) {
                    activity.checkConnectionCallback(true);
                }
                else {
                    activity.checkConnectionCallback(false);
                }
            }
            catch (JSONException e) {
                activity.checkConnectionCallback(false);
                e.printStackTrace();
            }
            Log.i("DataMapper", jsonObject.toString());
        }
    }

    public void staffListing () throws MalformedURLException
    {
        new StaffListingTask().execute(new URL("http://safe-retreat-6833.herokuapp.com/users.json"));
    }

    public void staffListingCallback(JSONArray jsonObject) throws JSONException
    {
        if (jsonObject != null) {
            Log.i("DataMapper", jsonObject.toString());

            mapToStaff(jsonObject);
        }
    }

    public String convertStreamToString(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),1024);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    private void mapToModestyInfo(JSONObject object) throws JSONException
    {
        if (object != null) {
            ModestyInfo _modestyInfo = new ModestyInfo();

            ArrayList<Player> playersArray = new ArrayList<Player>();

            JSONArray tempPlayersArray = object.getJSONArray("players");

            for (int i = 0; i < tempPlayersArray.length(); i++) {
                String username = tempPlayersArray.getString(i);

                Player tempPlayer = new Player();
                tempPlayer.setUsername(username);

                playersArray.add(tempPlayer);
            }

            _modestyInfo.setPlayers(playersArray);

            JSONObject tempDict = object.getJSONObject("info");

            Server tempServer = new Server();

            ArrayList<String> pluginArray = new ArrayList<String>();

            JSONArray tempPluginArray = tempDict.getJSONArray("Plugins");

            for (int i = 0; i < tempPluginArray.length(); i++) {
                String plugin = tempPluginArray.getString(i);

                pluginArray.add(plugin);
            }

            tempServer.setPlugins(pluginArray);
            tempServer.setHostIp(tempDict.getString("HostIp"));
            tempServer.setVersion(tempDict.getString("Version"));
            tempServer.setSoftware(tempDict.getString("Software"));
            tempServer.setHostName(tempDict.getString("HostName"));
            tempServer.setMaxPlayers(tempDict.getInt("MaxPlayers"));
            tempServer.setMap(tempDict.getString("Map"));
            tempServer.setHostPort(tempDict.getInt("HostPort"));
            tempServer.setPlayers(tempDict.getInt("Players"));

            this.setModestyInfo(_modestyInfo);

            modestyInfo.setServerInformation(tempServer);

        }
    }

    void mapToStaff(JSONArray staffArray) throws JSONException
    {
        ArrayList<Staff> tempStaffArray = new ArrayList<Staff>();

        for (int i = 0; i < staffArray.length(); i++) {
            JSONArray tempJsonArray = staffArray.getJSONArray(i);

            for (int j = 0; j < tempJsonArray.length(); j++) {
                JSONObject tempJsonObject = tempJsonArray.getJSONObject(j);

                Staff tempStaff = new Staff();

                tempStaff.setStaffId(tempJsonObject.getInt("id"));
                tempStaff.setUsername(tempJsonObject.getString("username"));
                tempStaff.setRank(tempJsonObject.getString("rank"));

                try {
                    tempStaff.setUrl(new URL(tempJsonObject.getString("url")));
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                tempStaffArray.add(tempStaff);
            }
        }

       setStaff(tempStaffArray);
    }
}
