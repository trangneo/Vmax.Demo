package com.fplay.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

import com.vmax.android.ads.api.VmaxAdSettings;
import com.vmax.android.ads.api.VmaxAdView;
import com.vmax.android.ads.api.VmaxSdk;
import com.vmax.android.ads.common.VmaxAdListener;
import com.vmax.android.ads.nativeads.NativeAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    TabLayout tabLayout;
    PagerAdapter pagerAdapter;
    ActionBar actionBar;
    VmaxAdView vmaxAdView;
    ViewGroup view;
    Map tempAdSettings;
    ViewPager viewPager;
    ListFragment listFragment;
    List<NativeAd> list = new ArrayList<>();
    NativeAd nativeAd;
    List<ItemModel> listItem = new ArrayList<ItemModel>();
    int[] imageResId = {R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a,
            R.drawable.a, R.drawable.a, R.drawable.a};
    String[] textContent = {"Anh Phap 2/1", "My - Bo 0/0", "Bi - Ha Lan 0/0", "Trung - Phap 2/0",
            "Anh-My 2/2", "Viet Nam- Han 1/0", "My - Phap 1/0", "Trung - Viet 0/2"};
    String[] textT1 = {"La Liga 2016/2017", "Croatia 2016/2017", "Chiago 2016/2017", "Duban 2016/2017",
            "Mongo 2016/2017", "La Liga 2016/2017", "Mehico 2016/2017", "Olala 2016/2017"};
    String[] textT2 = {"1/1", "1/2", "1/1", "2/2", "1/1", "1/2", "2/1", "1/2",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VmaxSdk.init(this);
        VmaxSdk.getSDKVersion();
        MultiDex.install(this);
        Log.d(TAG + "Trang", VmaxSdk.getSDKVersion());
        initComponent();
        initToolbar();
        initVmax();
    }


    private void initVmax() {
        vmaxAdView = new VmaxAdView(this, "7b33c0dd", VmaxAdView.UX_NATIVE);
        tempAdSettings = new HashMap<>();
        tempAdSettings.put(VmaxAdSettings.AdSettings_admob_nativeAdSize, "180x160");
        vmaxAdView.setAdSettings(tempAdSettings);
        setupViewPager(viewPager);
        vmaxAdView.setAdListener(new VmaxAdListener() {
            @Override
            public void adViewDidCacheAd(VmaxAdView adView) {
                nativeAd = adView.getNativeAd();
                if (nativeAd != null) {
//                    view=listFragment.getAdsView();
//                    nativeAd.registerViewForInteraction(adView, view, view,null);
                    list.add(nativeAd);
                    pagerAdapter.update();
                }
                Log.d(TAG + "Trang", list.size() + "vv");
            }

            @Override
            public void adViewDidLoadAd(VmaxAdView adView) {

            }

            @Override
            public VmaxAdView didFailedToLoadAd(String s) {
                return null;
            }

            @Override
            public VmaxAdView didFailedToCacheAd(String s) {
                return null;
            }

            @Override
            public void didInteractWithAd(VmaxAdView adView) {

            }

            @Override
            public void willPresentAd(VmaxAdView adView) {

            }

            @Override
            public void willDismissAd(VmaxAdView adView) {

            }

            @Override
            public void onVideoView(boolean b, int i, int i1) {

            }

            @Override
            public void onAdExpand() {

            }

            @Override
            public void onAdCollapsed() {

            }
        });

        vmaxAdView.cacheAd();


    }


    private void initComponent() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        addData();

    }

    void addData() {

        for (int i = 0; i < 8; i++) {
            ItemModel itemModel = new ItemModel();
            itemModel.setAvatar(imageResId[i]);
            itemModel.setTextContent(textContent[i]);
            itemModel.setText1(textT1[i]);
            itemModel.setText2(textT2[i]);
            listItem.add(i, itemModel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.humburger);
        actionBar.setTitle("THỂ THAO");
    }

    void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        listFragment= new ListFragment(getApplicationContext(), listItem, list);
        pagerAdapter.addFragment(listFragment, "Bóng Đá Quốc Tế");
        pagerAdapter.addFragment(new ListFragment(getApplicationContext(), listItem, list), "World Cup 2018");
        pagerAdapter.addFragment(new ListFragment(getApplicationContext(), listItem, list), "VPF");
        viewPager.setAdapter(pagerAdapter);
    }


    static class PagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void update() {
            for (int i = 0; i < getCount(); i++) {
                ((ListFragment) getItem(i)).update();
            }
        }

    }
}
