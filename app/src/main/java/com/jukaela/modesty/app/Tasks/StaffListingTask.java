package com.jukaela.modesty.app.Tasks;

import android.os.AsyncTask;

import com.jukaela.modesty.app.Models.DataMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by jbarrow on 5/4/14.  Yay!
 */

public class StaffListingTask extends AsyncTask<URL, Integer, JSONArray>
{

    @Override
    protected JSONArray doInBackground(URL... params)
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

        String inputStreamAsString;

        try {
            inputStreamAsString = DataMapper.getSharedInstance().convertStreamToString(inputStream);

            try {
                return new JSONArray(inputStreamAsString);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    protected void onPostExecute(JSONArray result) {
        try {
            DataMapper.getSharedInstance().staffListingCallback(result);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
