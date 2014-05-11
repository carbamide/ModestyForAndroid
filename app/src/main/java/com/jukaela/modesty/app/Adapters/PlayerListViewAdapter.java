package com.jukaela.modesty.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jukaela.modesty.app.models.DataMapper;
import com.jukaela.modesty.app.models.Player;
import com.jukaela.modesty.app.models.Staff;
import com.jukaela.modesty.app.R;
import com.jukaela.modesty.app.tasks.AvatarTask;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by jbarrow on 5/5/14.  Yay!
 */

public class PlayerListViewAdapter extends ArrayAdapter<Player>
{
    private final Context context;
    private ArrayList<Player> data = null;

    public PlayerListViewAdapter(Context _context, ArrayList<Player> _data)
    {
        super(_context, android.R.layout.simple_list_item_1, _data);

        this.context = _context;
        this.data = _data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_item, null);
        }

        Player playerObject = data.get(position);

        TextView usernameTextView = (TextView) convertView.findViewById(R.id.username);
        TextView rankTextView = (TextView) convertView.findViewById(R.id.rank);

        usernameTextView.setText(playerObject.getUsername());

        try {
            for (Staff staffMember : DataMapper.getSharedInstance().getStaff()) {
                if (staffMember.getUsername().equals(playerObject.getUsername())) {
                    rankTextView.setText(staffMember.getRank());
                }
            }
        }
        catch (NullPointerException e) {
            try {
                DataMapper.getSharedInstance().staffListing();
            }
            catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }


        File imageFilename = new File(Environment.getExternalStorageDirectory() + File.separator + playerObject.getUsername() + ".png");

        Bitmap bitmap = BitmapFactory.decodeFile(imageFilename.getAbsolutePath());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            new AvatarTask(imageView).execute(playerObject.getUsername());
        }

        return convertView;
    }
}
