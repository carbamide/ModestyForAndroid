package com.jukaela.modesty.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jukaela.modesty.app.activities.MainActivity;
import com.jukaela.modesty.app.activities.PluginListActivity;
import com.jukaela.modesty.app.activities.StaffListActivity;
import com.jukaela.modesty.app.adapters.InfoListViewAdapter;
import com.jukaela.modesty.app.models.DataMapper;
import com.jukaela.modesty.app.R;

public class InfoListFragment extends Fragment
{
    private AbsListView mListView;
    private ViewGroup containerViewGroup;
    private ProgressDialog progressDialog;

    public static InfoListFragment newInstance()
    {
        InfoListFragment fragment = new InfoListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public InfoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        containerViewGroup = container;

        View view = inflater.inflate(R.layout.fragment_info_list, container, false);

        assert view != null;
        mListView = (AbsListView) view.findViewById(R.id.listView);

        if (DataMapper.getSharedInstance().getModestyInfo() == null) {
            showProgressDialog();
        }

        return view;
    }

    private void showProgressDialog() {
        assert(this.getActivity() != null);
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage(getString(R.string.accessing_modesty_string));
        progressDialog.setCancelable(false);
        progressDialog.show();
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
    }

    public void reloadListViewAdapter() {
        if (DataMapper.getSharedInstance().getModestyInfo() != null) {
            if (progressDialog != null) {
                progressDialog.hide();
            }

            InfoListViewAdapter mAdapter = new InfoListViewAdapter(getActivity(), DataMapper.getSharedInstance().getModestyInfo());

            if (mListView == null) {
                assert(getActivity() != null);
                mListView = (ListView) getActivity().getLayoutInflater().inflate(R.id.listView, containerViewGroup, false);
            }

            assert mListView != null;
            mListView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    switch (position) {
                        case 0:
                        case 1:
                        case 2:
                            ClipboardManager _clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(getString(R.string.modesty_server_information_string), "108.174.48.200:25665");
                            _clipboard.setPrimaryClip(clip);

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                            alertDialog.setTitle(getString(R.string.copied_string));
                            alertDialog.setMessage(getString(R.string.has_been_copied_string));
                            alertDialog.setPositiveButton(android.R.string.ok, null);
                            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                            alertDialog.show();

                            break;
                        case 3:
                            Intent staffListActivityIntent = new Intent(getActivity().getApplicationContext(), StaffListActivity.class);
                            startActivity(staffListActivityIntent);

                            break;
                        case 4:
                            MainActivity activity = (MainActivity) getActivity();

                            activity.getSupportActionBar().setSelectedNavigationItem(1);

                            break;
                        case 5:
                            Intent pluginListActivityIntent = new Intent(getActivity().getApplicationContext(), PluginListActivity.class);
                            startActivity(pluginListActivityIntent);

                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }


}
