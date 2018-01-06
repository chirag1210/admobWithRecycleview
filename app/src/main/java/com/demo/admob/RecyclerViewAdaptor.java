package com.demo.admob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.List;


/**
 * Created by vbusani on 3/1/16.
 */
public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

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

    public class ViewHolderAdMob extends RecyclerView.ViewHolder {

        NativeContentAdView adView;

        public ViewHolderAdMob(View view) {
            super(view);
              adView = view.findViewById(R.id.native_ad_id);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1: {
                View v = inflater.inflate(R.layout.list_item_1, parent, false);
                viewHolder = new MyViewHolder(v);
                break;
            }
            case 2: {
                View v = inflater.inflate(R.layout.list_item_admob, parent, false);


                viewHolder = new ViewHolderAdMob(v);
                break;
            }
        }
        return viewHolder;
    }

    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {
        // mVideoStatus.setText("Video status: Ad does not contain a video asset.");

         adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
          adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
         ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
         ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
         ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        MyListModel model = mList.get(holder.getAdapterPosition());

        switch (holder.getItemViewType()) {
            case 1:
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.name.setText(model.getName());
                break;

            case 2:
               final ViewHolderAdMob viewHolderAdMob = (ViewHolderAdMob) holder;

                AdLoader.Builder builder = new AdLoader.Builder(mContext, ADMOB_AD_UNIT_ID);
                builder.forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                    @Override
                    public void onContentAdLoaded(NativeContentAd ad) {
                        populateContentAdView(ad, viewHolderAdMob.adView);
                    }
                });

                AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        System.out.println("### Failed to load ad ###");
                    }
                }).build();

                adLoader.loadAd(new AdRequest.Builder().build());

                break;

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
