package com.jukaela.modesty.app.Activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jukaela.modesty.app.Adapters.PlayerListViewAdapter;
import com.jukaela.modesty.app.Fragments.InfoListFragment;
import com.jukaela.modesty.app.Fragments.PlayerFragment;
import com.jukaela.modesty.app.Fragments.SocialFragment;
import com.jukaela.modesty.app.Models.DataMapper;
import com.jukaela.modesty.app.R;

import org.json.JSONException;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, PlayerFragment.OnFragmentInteractionListener, SocialFragment.OnFragmentInteractionListener, InfoListFragment.OnFragmentInteractionListener
{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private PlayerFragment playerFragment;
    private SocialFragment socialFragment;
    private InfoListFragment infoListFragment;
    private ProgressDialog checkModestyConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

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
    }

    public void reloadListViewAdapter() {
        if (infoListFragment == null) {
            infoListFragment = InfoListFragment.newInstance();
        }

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

        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Not Implemented");
            alertDialog.setMessage("This function is not yet implemented");
            alertDialog.setPositiveButton(android.R.string.ok, null);

            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
            alertDialog.show();
        }
        else if (id == R.id.action_check_connection) {
            checkModestyConnectionDialog = new ProgressDialog(this);
            checkModestyConnectionDialog.setMessage("Checking Modesty Connection...");
            checkModestyConnectionDialog.setCancelable(false);
            checkModestyConnectionDialog.show();

            try {
                DataMapper.getSharedInstance().pingModesty();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
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
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
    }

    @Override
    public void onFragmentInteraction(String id)
    {

    }

    public void checkConnectionCallback (Boolean isUp) {
        checkModestyConnectionDialog.hide();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        if (isUp) {
            alertDialog.setTitle("Modesty is Up!");
            alertDialog.setMessage("Modesty appears to be up! Yay!");
            alertDialog.setPositiveButton(android.R.string.ok, null);

            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        }
        else {
            alertDialog.setTitle("Modesty is Down!");
            alertDialog.setMessage("Modsety appears to be down! :-(\nThe information display may not be current or correct.");
            alertDialog.setPositiveButton(android.R.string.ok, null);

            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }

        alertDialog.show();
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
                    return "Modesty";
                case 1:
                    return "Players";
                case 2:
                    return "Social";
            }
            return null;
        }
    }
}
