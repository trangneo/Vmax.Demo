package com.fplay.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vmax.android.ads.nativeads.NativeAd;

import java.util.List;

/**
 * Created by poiuyt on 9/26/16.
 */

public class ListFragment  extends Fragment {
    Context context;
    List<ItemModel> listItem;
    List<NativeAd> list;
    private ListDataAdapter adapter;

    public ListFragment(Context context, List<ItemModel> listItem, List<NativeAd> list) {
        this.context = context;
        this.listItem= listItem;
        this.list= list;
        adapter = new ListDataAdapter(context, listItem, list);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_item, container, false);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        rv.addItemDecoration(new SpacesItemDecoration(8));
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);

        return rv;
    }
    public void update() {
        adapter.notifyDataSetChanged();
    }
    public ViewGroup getAdsView(){
        return (ViewGroup) adapter.normalView;
    }
}
