package com.jukaela.modesty.app.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jukaela.modesty.app.adapters.PlayerListViewAdapter;
import com.jukaela.modesty.app.fragments.InfoListFragment;
import com.jukaela.modesty.app.fragments.PlayerFragment;
import com.jukaela.modesty.app.fragments.SocialFragment;
import com.jukaela.modesty.app.models.DataMapper;
import com.jukaela.modesty.app.R;

public class MainActivity extends ModestyActivity implements android.support.v7.app.ActionBar.TabListener, PlayerFragment.OnFragmentInteractionListener, SocialFragment.OnFragmentInteractionListener, InfoListFragment.OnFragmentInteractionListener
{

    private ViewPager mViewPager;
    private PlayerFragment playerFragment;
    private SocialFragment socialFragment;
    private InfoListFragment infoListFragment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }

        try {
            DataMapper.getSharedInstance().setActivity(this);
            DataMapper.getSharedInstance().refreshInformation();
            DataMapper.getSharedInstance().staffListing();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setupActionBarTabFont();
    }

    private void setupActionBarTabFont()
    {
        android.support.v7.app.ActionBar bar = this.getSupportActionBar();
        assert bar != null;
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        String[] tabNames = {getString(R.string.info_string), getString(R.string.players_string), getString(R.string.social_string)};

        for(int i = 0; i < bar.getTabCount(); i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView = inflater.inflate(R.layout.tab_title, null);

            Typeface minecraftFont = Typeface.createFromAsset(getAssets(), "fonts/minecraft.ttf");

            assert customView != null;
            TextView titleTextView = (TextView) customView.findViewById(R.id.action_custom_title);
            titleTextView.setText(tabNames[i]);
            titleTextView.setTypeface(minecraftFont);

            bar.getTabAt(i).setCustomView(customView);
        }
    }
    public void reloadListViewAdapter() {
        if (infoListFragment == null) {
            infoListFragment = InfoListFragment.newInstance();
        }

        if (playerFragment == null) {
            playerFragment = PlayerFragment.newInstance();
        }

        if (progressDialog != null) {
            progressDialog.hide();
        }

        playerFragment.reloadListViewAdapter();
        infoListFragment.reloadListViewAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.updating_information_string));
            progressDialog.setCancelable(false);
            progressDialog.show();

            try {
                DataMapper.getSharedInstance().staffListing();
                DataMapper.getSharedInstance().refreshInformation();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if (id == R.id.action_check_connection) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.checking_modesty_information_string));
            progressDialog.setCancelable(false);
            progressDialog.show();

            try {
                DataMapper.getSharedInstance().pingModesty();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void onFragmentInteraction(String id)
    {

    }

    public void checkConnectionCallback (Boolean isUp) {
        progressDialog.hide();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        if (isUp) {
            alertDialog.setTitle(getString(R.string.modesty_is_up_title_string));
            alertDialog.setMessage(getString(R.string.modesty_is_up_string));
            alertDialog.setPositiveButton(android.R.string.ok, null);

            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        }
        else {
            alertDialog.setTitle(getString(R.string.modesty_is_down_title_string));
            alertDialog.setMessage(getString(R.string.modesty_is_down_string));
            alertDialog.setPositiveButton(android.R.string.ok, null);

            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }

        alertDialog.show();
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction)
    {
        mViewPager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()) {
            case 0:
                if (DataMapper.getSharedInstance().getModestyInfo() != null) {
                    if (infoListFragment != null) {
                        infoListFragment.reloadListViewAdapter();
                    }
                }
                break;
            case 1:
                if (DataMapper.getSharedInstance().getModestyInfo().getPlayers() != null) {
                    playerFragment.setAdapter(new PlayerListViewAdapter(getApplicationContext(), DataMapper.getSharedInstance().getModestyInfo().getPlayers()));
                }

                break;
        }
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction)
    {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction)
    {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position) {
                case 0:
                    if (infoListFragment == null) {
                        infoListFragment = InfoListFragment.newInstance();
                    }

                    return infoListFragment;
                case 1:
                    if (playerFragment == null) {
                        playerFragment = PlayerFragment.newInstance();
                    }

                    return playerFragment;
                case 2:
                    if (socialFragment == null) {
                        socialFragment = SocialFragment.newInstance();
                    }

                    return socialFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale.getDefault();

            switch (position) {
                case 0:
                    return getString(R.string.modesty_string);
                case 1:
                    return getString(R.string.players_string);
                case 2:
                    return getString(R.string.social_string);
            }
            return null;
        }
    }
}
