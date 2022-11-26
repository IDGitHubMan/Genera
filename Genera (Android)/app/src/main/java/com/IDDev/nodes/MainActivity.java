package com.IDDev.nodes;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import processing.android.PFragment;

public class MainActivity extends AppCompatActivity {
    Context context;
    FrameLayout sketchView;
    final FragmentActivity f = this;
    PFragment sketch;
    TextView settingsToggle;
    TabLayout previews;
    AdView mAdView;
    float adChance = 0.0f;
    final AdRequest adRequest = new AdRequest.Builder().build();
    private InterstitialAd i;
    FlowSketch flow;
    NodeSketch node;
    Button set;
    ViewGroup opts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        adLoad();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.settingsContainer, NodeSettingsFragment.class, null).commit();
        settingsToggle = findViewById(R.id.textView1);
        previews = findViewById(R.id.mode);
        set = findViewById(R.id.wallpaperSet);
        set.setText(getResources().getString(R.string.setNodePaper));
        node = new NodeSketch(context);
        sketch = new PFragment(node);
        sketchView = findViewById(R.id.sketchContainer);
        sketch.setView(sketchView,f);
        opts = findViewById(R.id.opts);
        opts.setVisibility(View.GONE);
        settingsToggle.setOnClickListener(view -> {
            if (opts.getVisibility()==View.GONE){
                opts.setVisibility(View.VISIBLE);
            } else {
                opts.setVisibility(View.GONE);
            }
        });

        set.setOnClickListener(view -> {
            if (previews.getSelectedTabPosition()  == 0){
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(context, NodePaper.class));
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(context, FlowPaper.class));
                startActivity(intent);
            }
        });
        node = new NodeSketch(context);
        sketch = new PFragment(node);
        sketchView = findViewById(R.id.sketchContainer);
        previews.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    node = new NodeSketch(context);
                    sketch = new PFragment(node);
                    sketchView = findViewById(R.id.sketchContainer);
                    sketch.setView(sketchView,f);
                    set.setText(getResources().getString(R.string.setNodePaper));
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.settingsContainer,NodeSettingsFragment.class,null).commit();
                } else if (tab.getPosition()==1){
                    flow = new FlowSketch(context);
                    sketch = new PFragment(flow);
                    sketchView = findViewById(R.id.sketchContainer);
                    sketch.setView(sketchView,f);
                    set.setText(getResources().getString(R.string.setFlowPaper));
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.settingsContainer,FlowSettingsFragment.class,null).commit();
                }
                adCheck();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MobileAds.initialize(this, initializationStatus -> {
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void adLoad(){
        InterstitialAd.load(this, "ca-app-pub-7127109483374745/2062717015", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                i = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                i = interstitialAd;
            }
        });
    }
    public void adCheck(){
        float adNum = flow.random(1);
        if (adNum<adChance){
            if (i != null) {
                i.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        // Called when fullscreen content failed to show.
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        i = null;
                    }
                });
                i.show(MainActivity.this);
            }
            adChance = 0;
            adLoad();
        }
        else{
            adChance += 0.01;
        }
    }
}