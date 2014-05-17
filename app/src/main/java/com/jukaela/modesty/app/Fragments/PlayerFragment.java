package com.jukaela.modesty.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.jukaela.modesty.app.R;
import com.jukaela.modesty.app.adapters.PlayerListViewAdapter;
import com.jukaela.modesty.app.models.DataMapper;

public class PlayerFragment extends Fragment
{
    private AbsListView listView;

    public static PlayerFragment newInstance()
    {
        PlayerFragment fragment = new PlayerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public PlayerFragment() {

    }

    public void reloadListViewAdapter()
    {
        if (DataMapper.getSharedInstance().getModestyInfo().getPlayers() != null) {
            listView.setAdapter(new PlayerListViewAdapter(getActivity().getApplicationContext(), DataMapper.getSharedInstance().getModestyInfo().getPlayers()));
        }
    }

    public void setAdapter(ListAdapter adapter)
    {
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);

        assert view != null;
        listView = (AbsListView) view.findViewById(android.R.id.list);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            Log.d("Modesty", "Activity implements OnFragmentInteractionListener");
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(String id);
    }

}
