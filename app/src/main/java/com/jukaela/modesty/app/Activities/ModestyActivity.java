package com.jukaela.modesty.app.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jukaela.modesty.app.R;

public class ModestyActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBarFont();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modesty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBarFont()
    {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View view = inflator.inflate(R.layout.titleview, null);

        assert view != null;
        TextView titleTextView = (TextView)view.findViewById(R.id.textView);
        titleTextView.setText(this.getTitle());

        Typeface minecraftFont = Typeface.createFromAsset(getAssets(), "fonts/minecraft.ttf");

        titleTextView.setTypeface(minecraftFont);
        this.getSupportActionBar().setCustomView(view);
    }
}
