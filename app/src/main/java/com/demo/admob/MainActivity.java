package com.demo.admob;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Recycle View";
    private Context mContext;
    private List<MyListModel> mList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getResources().getString(R.string.banner_ad_unit_id));
        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mList = new ArrayList<MyListModel>();

        for(int i=0;i<10;i++){
            MyListModel myString = new MyListModel();
            myString.setName(i+"- Blank Item");
            myString.setViewType(1);
            mList.add(myString);
        }

        //Place two Admob Ads at position index 1 and 5 in recyclerview
        MyListModel myString1 = new MyListModel();
        myString1.setViewType(2);
        mList.add(1,myString1);

        MyListModel myString2 = new MyListModel();
        myString2.setViewType(2);
        mList.add(5,myString2);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdaptor(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
