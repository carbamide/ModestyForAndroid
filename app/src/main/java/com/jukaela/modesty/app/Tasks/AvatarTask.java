package com.jukaela.modesty.app.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jbarrow on 4/25/14.  Yay!
 */

public class AvatarTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public AvatarTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        try {
            String username = params[0];

            return getImage(new URL("https://minotar.net/helm/" + username + "/30.png"), username);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewReference.get();

        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap getImage(URL imageUrl, String username) {
        try {
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            writeBitmapToSDCard(bitmap, username);

            return bitmap;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeBitmapToSDCard(Bitmap bitmap, String username) throws IOException
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + ".modesty");

        folder.mkdir();

        File imageFilename = new File(folder, username + ".png");

        boolean newFile = imageFilename.createNewFile();

        if (newFile) {
            FileOutputStream fo = new FileOutputStream(imageFilename);
            fo.write(bytes.toByteArray());

            fo.close();
        }
        else {
            Log.d("Modesty", "An error has occurred writing the file to disk");
        }
    }
}
