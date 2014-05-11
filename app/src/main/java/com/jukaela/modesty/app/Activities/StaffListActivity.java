package com.jukaela.modesty.app.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.jukaela.modesty.app.adapters.StaffListViewAdapter;
import com.jukaela.modesty.app.models.DataMapper;
import com.jukaela.modesty.app.R;


public class StaffListActivity extends ModestyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        if (DataMapper.getSharedInstance().getStaff() != null) {
            StaffListViewAdapter listViewAdapter = new StaffListViewAdapter(getApplicationContext(), DataMapper.getSharedInstance().getStaff());

            ListView listView = (ListView) findViewById(R.id.listView);

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
