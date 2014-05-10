package com.jukaela.modesty.app.Tasks;

import android.os.AsyncTask;

import com.jukaela.modesty.app.Models.DataMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class PingTask extends AsyncTask<URL, Integer, JSONObject>
{

    @Override
    protected JSONObject doInBackground(URL... params)
    {
        HttpResponse response = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(params[0].toString()));
            response = client.execute(request);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        if (response != null) {
            try {
                inputStream = response.getEntity().getContent();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        String inputStreamAsString = null;
        try {
            inputStreamAsString = DataMapper.getSharedInstance().convertStreamToString(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new JSONObject(inputStreamAsString);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject result) {
        DataMapper.getSharedInstance().pingModestyCallback(result);
    }
}
