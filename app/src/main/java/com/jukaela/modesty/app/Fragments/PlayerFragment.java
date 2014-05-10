package com.jukaela.modesty.app.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.jukaela.modesty.app.R;

public class PlayerFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    public ListAdapter mAdapter;

    public static PlayerFragment newInstance()
    {
        PlayerFragment fragment = new PlayerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public PlayerFragment() {

    }

    public void setAdapter(ListAdapter adapter)
    {
        mListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);

        assert view != null;
        mListView = (AbsListView) view.findViewById(android.R.id.list);

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

}
