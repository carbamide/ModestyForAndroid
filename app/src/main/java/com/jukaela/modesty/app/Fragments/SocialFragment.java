package com.jukaela.modesty.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.jukaela.modesty.app.adapters.SocialListViewAdapter;
import com.jukaela.modesty.app.R;

public class SocialFragment extends Fragment
{
    private static final String kTwitterURL = "https://twitter.com/modesty_mc";
    private static final String kInstagramURL = "http://instagram.com/degumaster";
    private static final String kPlanetMinecraft = "http://www.planetminecraft.com/server/modesty/";
    private static final String kMinecraftServersOrg = "http://minecraftservers.org/server/6465";
    private static final String kMinecraftServerList = "http://minecraft-server-list.com/server/128633/vote/";
    private static final String kFacebookURL = "https://www.facebook.com/minecraftmodesty";
    private static final String kForumURL = "http://www.minecraftmodesty.enjin.com/forum";

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView listView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    public static SocialFragment newInstance()
    {
        SocialFragment fragment = new SocialFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public SocialFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_social_list, container, false);

        assert view != null;
        listView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(new SocialListViewAdapter(getActivity()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                switch (position) {
                    case 0:
                        openURLInIntent(kTwitterURL);

                        break;
                    case 1:
                        openURLInIntent(kInstagramURL);

                        break;
                    case 2:
                        openURLInIntent(kFacebookURL);

                        break;
                    case 3:
                        openURLInIntent(kForumURL);

                        break;
                    case 4:
                        openURLInIntent(kPlanetMinecraft);

                        break;
                    case 5:
                        openURLInIntent(kMinecraftServersOrg);

                        break;
                    case 6:
                        openURLInIntent(kMinecraftServerList);

                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(String id);
    }

    private void openURLInIntent(String urlString)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

        startActivity(browserIntent);
    }
}
