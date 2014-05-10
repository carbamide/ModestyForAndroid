package com.jukaela.modesty.app.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.jukaela.modesty.app.Adapters.StaffListViewAdapter;
import com.jukaela.modesty.app.Models.DataMapper;
import com.jukaela.modesty.app.R;


public class StaffListActivity extends Activity {

    private ListView listView;
    private StaffListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        if (DataMapper.getSharedInstance().getStaff() != null) {
            listViewAdapter = new StaffListViewAdapter(getApplicationContext(), DataMapper.getSharedInstance().getStaff());

            listView = (ListView) findViewById(R.id.listView);

            if ((listView != null) && (listViewAdapter != null)) {
                listView.setAdapter(listViewAdapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
