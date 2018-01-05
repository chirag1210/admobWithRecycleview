package com.demo.admob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.List;

/**
 * Created by vbusani on 3/1/16.
 */
public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MyListModel> mList;

    public RecyclerViewAdaptor(Context mContext, List<MyListModel> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.listView_name);
        }
    }

    public static class ViewHolderAdMob extends RecyclerView.ViewHolder {

        public AdView mAdView;
//    private PublisherAdView mPublisherAdView;

        public ViewHolderAdMob(View view) {
            super(view);
            mAdView = (AdView) view.findViewById(R.id.adView);
          /*  MobileAds.initialize(getApplicationContext(),
                    getString(R.string.adunitid));*/
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // Check the LogCat to get your test device ID
                    .addTestDevice("C2E16D457061D4332D1BF438131DF931")
                    .build();
            mAdView.loadAd(adRequest);



        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType){
            case 1:{
                View v = inflater.inflate(R.layout.list_item_1, parent, false);
                viewHolder = new MyViewHolder(v);
                break;
            }
            case 2:{
                View v = inflater.inflate(R.layout.list_item_admob, parent, false);
                viewHolder = new ViewHolderAdMob(v);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        MyListModel model = mList.get(holder.getAdapterPosition());

        switch(holder.getItemViewType()){
            case 1:{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.name.setText(model.getName());
                break;
            }
            case 2:{
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
