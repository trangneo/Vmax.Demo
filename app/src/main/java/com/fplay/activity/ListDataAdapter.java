package com.fplay.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vmax.android.ads.nativeads.NativeAd;
import com.vmax.android.ads.util.Constants;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by poiuyt on 8/4/16.
 */
/* show data in MainActivity */
public class ListDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static String TAG = ListDataAdapter.class.getSimpleName();
    static final int ITEM_TYPE_NORMAL = 0;
    static final int ITEM_TYPE_ADS = 1;
    List<ItemModel> listItem;
    List<NativeAd> list;
    Context context;

    View normalView, adsView;

    public ListDataAdapter(Context context, List<ItemModel> listItem, List<NativeAd> list) {
        this.context = context;
        this.listItem = listItem;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            normalView = LayoutInflater.from(context).inflate(R.layout.item, null);
            return new ViewHolderNormal(normalView);
        } else {
            adsView = LayoutInflater.from(context).inflate(R.layout.item_as, null);
            return new ViewHolderAds(adsView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % 3 == 0) {
            return ITEM_TYPE_ADS;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_NORMAL) {
            ItemModel itemModel = listItem.get(position);
            Picasso.with(context).load(itemModel.getAvatar()).into(((ViewHolderNormal) holder).avatar);
            ((ViewHolderNormal) holder).textContent.setText(itemModel.getTextContent());
            ((ViewHolderNormal) holder).text1.setText(itemModel.getText1());
            ((ViewHolderNormal) holder).text2.setText(itemModel.getText2());
        } else {
            if (list.size() != 0) {
                final NativeAd nativeAd = list.get(0);
                Log.d("vmax AdchoiceUrl ", nativeAd.getAdChoiceUrl()+"");
                Log.d("vmax Address ", nativeAd.getAddress()+"");
                Log.d("vmax Cta ", nativeAd.getCtaText()+"");
                Log.d("vmax Desc ", nativeAd.getDesc()+"");
                Log.d("vmax Desc2 ", nativeAd.getDesc2()+"");
                Log.d("vmax Displayurl ", nativeAd.getDisplayurl()+"");
                Log.d("vmax Downloads ", nativeAd.getDownloads()+"");
                Log.d("vmax Like ", nativeAd.getLikes()+"");
                Log.d("vmax Link ", nativeAd.getLink()  +"");
                Log.d("vmax Title  ", nativeAd.getTitle()  +"");
                Log.d("vmax partner  ", nativeAd.getNativeAdPartner()+"");
                Log.d("vmax ad type  ", nativeAd.getNativeAdType()+"");
                Log.d("vmax Objective ", nativeAd.getObjective()+"");
                Log.d("vmax Phone ", nativeAd.getPhone()+"");
                Log.d("vmax Price ", nativeAd.getPrice()+"");
                Log.d("vmax Rating ", nativeAd.getRating()+"");
                Log.d("vmax SalePrice ", nativeAd.getSalePrice()+"");
                Log.d("vmax Sponsored ", nativeAd.getSponsored()+"");
                Log.d("vmax VastVideoTag ", nativeAd.getVastVideoTag()+"");

                ((ViewHolderAds) holder).tvDes.setText(nativeAd.getDesc());
                ((ViewHolderAds) holder).tvTitle.setText(nativeAd.getTitle());
                ((ViewHolderAds) holder).btn.setText(nativeAd.getCtaText());
                //main icon la dung
                ((ViewHolderAds) holder).iconMain.setImageDrawable(nativeAd.getImageMedium().getImageView().getDrawable());
                ((ViewHolderAds) holder).icon.setImageDrawable(nativeAd.getIcon().getImageView().getDrawable());
                if (nativeAd.getNativeAdPartner() != null &&
                        nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_FACEBOOK)) {
                    if (nativeAd.getImageAdChoice() != null && nativeAd.getImageAdChoice().getImageView() != null) {
                        ImageView adChoiceView = new ImageView(context);

                        adChoiceView.setImageDrawable(nativeAd.getImageAdChoice().getImageView().getDrawable());
                    }
                }
                ((ViewHolderAds) holder).btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String adChoiceActionUrl = nativeAd.getAdChoiceUrl();
                        Uri uri = Uri.parse(adChoiceActionUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public static class ViewHolderNormal extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView textContent, text1, text2;

        public ViewHolderNormal(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            textContent = (TextView) view.findViewById(R.id.tvTextContent);
            text1 = (TextView) view.findViewById(R.id.tv1);
            text2 = (TextView) view.findViewById(R.id.tv2);
        }
    }

    public static class ViewHolderAds extends RecyclerView.ViewHolder {
        ImageView iconMain, icon;
        TextView tvDes, tvTitle;
        Button btn;

        public ViewHolderAds(View itemView) {
            super(itemView);
            iconMain = (ImageView) itemView.findViewById(R.id.avatar);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconMain = (ImageView) itemView.findViewById(R.id.avatar);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDes = (TextView) itemView.findViewById(R.id.tvDes);
            btn = (Button) itemView.findViewById(R.id.btn);
        }
    }

}


