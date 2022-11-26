package com.IDDev.nodes;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class NodeSettingsFragment extends Fragment {
    ViewGroup nodeColGroup, actRange, randRange, sparkle, sparkleCol;
    Spinner sparkMode, edgeMode, touchMode;
    SwitchCompat coreShow, connectShow, rangeShow, nodeColorUniform, rangeToggle, sparkleShow;
    SeekBar nodeCount, drift, nodeR, nodeG, nodeB, rangeMin, rangeMax, rangeAct, sparkleWeight, sparkleDisplacement, sparkleR, sparkleG, sparkleB, backR, backG, backB, backA;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = requireContext().getSharedPreferences(requireContext().getString(R.string.prefsName),0);
        SharedPreferences.Editor e = prefs.edit();
        View view = inflater.inflate(R.layout.node_settings, container, false);

        nodeColGroup = view.findViewById(R.id.nodeColorGroup);
        actRange = view.findViewById(R.id.actualRangeGroup);
        randRange = view.findViewById(R.id.randomRangeGroup);
        sparkle = view.findViewById(R.id.sparkleGroup);
        sparkleCol = view.findViewById(R.id.sparkleUniformColorGroup);

        sparkMode = view.findViewById(R.id.sparkleType);
        sparkMode.setAdapter(new ArrayAdapter<>(getContext(),R.layout.bspinner_item,getResources().getStringArray(R.array.sparkleTypes)));
        edgeMode = view.findViewById(R.id.edgeBehavior);
        edgeMode.setAdapter(new ArrayAdapter<>(getContext(),R.layout.bspinner_item,getResources().getStringArray(R.array.edgeBehaviors)));
        touchMode = view.findViewById(R.id.touchBehaviors);
        touchMode.setAdapter(new ArrayAdapter<>(getContext(),R.layout.bspinner_item,getResources().getStringArray(R.array.touchActions)));

        coreShow = view.findViewById(R.id.coreToggle);
        connectShow = view.findViewById(R.id.connectionToggle);
        rangeShow = view.findViewById(R.id.rangeToggle);
        nodeColorUniform = view.findViewById(R.id.uniformColorSwitch);
        rangeToggle = view.findViewById(R.id.uniformRange);
        sparkleShow = view.findViewById(R.id.sparkleToggle);

        nodeCount = view.findViewById(R.id.nodeCount);
        drift = view.findViewById(R.id.maximumDrift);
        nodeR = view.findViewById(R.id.nodeRed);
        nodeG = view.findViewById(R.id.nodeGreen);
        nodeB = view.findViewById(R.id.nodeBlue);
        rangeMin = view.findViewById(R.id.min);
        rangeMax = view.findViewById(R.id.max);
        rangeAct = view.findViewById(R.id.actRange);
        sparkleWeight = view.findViewById(R.id.sparkleWeight);
        sparkleDisplacement = view.findViewById(R.id.sparkleDisplacement);
        sparkleR = view.findViewById(R.id.sparkRed);
        sparkleG = view.findViewById(R.id.sparkGreen);
        sparkleB = view.findViewById(R.id.sparkBlue);
        backR = view.findViewById(R.id.backRed);
        backG = view.findViewById(R.id.backGreen);
        backB = view.findViewById(R.id.backBlue);
        backA = view.findViewById(R.id.backAlpha);

        nodeCount.setProgress(prefs.getInt("Nodes Amount",30) - 1);
        nodeCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Amount",i+1);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        drift.setProgress(prefs.getInt("Nodes Velocity Limit",5));
        drift.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Velocity Limit",i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        coreShow.setChecked(prefs.getBoolean("Nodes Core Display",true));
        coreShow.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Core Display", b);
            e.apply();
        });

        connectShow.setChecked(prefs.getBoolean("Nodes Connection Visibility",true));
        connectShow.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Connection Visibility", b);
            e.apply();
        });

        rangeShow.setChecked(prefs.getBoolean("Nodes Range Display",true));
        rangeShow.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Range Display", b);
            e.apply();
        });

        nodeColorUniform.setChecked(prefs.getBoolean("Nodes Color Uniformity",false));
        if (nodeColorUniform.isChecked()) {
            nodeColGroup.setVisibility(View.VISIBLE);
        } else {
            nodeColGroup.setVisibility(View.GONE);
        }
        nodeColorUniform.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Color Uniformity",b);
            e.apply();
            if (b) {
                nodeColGroup.setVisibility(View.VISIBLE);
            } else {
                nodeColGroup.setVisibility(View.GONE);
            }
        });

        nodeR.setProgress(prefs.getInt("Nodes Uniform Red",66));
        nodeR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Uniform Red", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nodeG.setProgress(prefs.getInt("Nodes Uniform Green",66));
        nodeG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Uniform Green", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nodeB.setProgress(prefs.getInt("Nodes Uniform Blue",207));
        nodeB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Uniform Blue",i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rangeToggle.setChecked(prefs.getBoolean("Nodes Range Uniformity",false));
        if (rangeToggle.isChecked()) {
            randRange.setVisibility(View.GONE);
            actRange.setVisibility(View.VISIBLE);
        } else {
            randRange.setVisibility(View.VISIBLE);
            actRange.setVisibility(View.GONE);
        }
        rangeToggle.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Range Uniformity",b);
            e.apply();
            if (b) {
                randRange.setVisibility(View.GONE);
                actRange.setVisibility(View.VISIBLE);
            } else {
                randRange.setVisibility(View.VISIBLE);
                actRange.setVisibility(View.GONE);
            }
        });

        rangeAct.setMax(400);
        rangeAct.setProgress(prefs.getInt("Nodes Uniform Range",100)-10);
        rangeAct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Uniform Range",i+10);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rangeMax.setMax(400);
        rangeMax.setProgress(prefs.getInt("Nodes Maximum Range",300)-100);
        rangeMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Maximum Range",i+100);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rangeMin.setMax(100);
        rangeMin.setProgress(prefs.getInt("Nodes Minimum Range",25)-10);
        rangeMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Minimum Range",i+10);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sparkleShow.setChecked(prefs.getBoolean("Nodes Sparkle Visibility",false));
        if (sparkleShow.isChecked()) {
            sparkle.setVisibility(View.VISIBLE);
        } else {
            sparkle.setVisibility(View.GONE);
        }
        sparkleShow.setOnCheckedChangeListener((compoundButton, b) -> {
            e.putBoolean("Nodes Sparkle Visibility",b);
            e.apply();
            if (b) {
                sparkle.setVisibility(View.VISIBLE);
            } else {
                sparkle.setVisibility(View.GONE);
            }
        });

        sparkleWeight.setProgress(prefs.getInt("Nodes Sparkle Size",4));
        sparkleWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Sparkle Size",i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sparkleDisplacement.setProgress(prefs.getInt("Nodes Sparkle Displacement",4));
        sparkleDisplacement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Sparkle Displacement",i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sparkMode.setSelection(prefs.getInt("Nodes Sparkle Coloration",0));
        if (sparkMode.getSelectedItemPosition() != 0) {
            sparkleCol.setVisibility(View.GONE);
        } else {
            sparkleCol.setVisibility(View.VISIBLE);
        }
        sparkMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                e.putInt("Nodes Sparkle Coloration",i);
                e.apply();
                if (i != 0) {
                    sparkleCol.setVisibility(View.GONE);
                } else {
                    sparkleCol.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sparkleR.setProgress(prefs.getInt("Nodes Sparkle Red",255));
        sparkleR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Sparkle Red", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sparkleG.setProgress(prefs.getInt("Nodes Sparkle Green",255));
        sparkleG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Sparkle Green", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sparkleB.setProgress(prefs.getInt("Nodes Sparkle Blue",255));
        sparkleB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Sparkle Blue", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        edgeMode.setSelection(prefs.getInt("Nodes Edge Behavior",0));
        edgeMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                e.putInt("Nodes Edge Behavior",i);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        touchMode.setSelection(prefs.getInt("Nodes Touch Action",0));
        touchMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                e.putInt("Nodes Touch Action",i);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        backR.setProgress(prefs.getInt(requireContext().getString(R.string.prefsNodesBGRed),255));
        backR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt(requireContext().getString(R.string.prefsNodesBGRed), i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        backG.setProgress(prefs.getInt("Nodes Background Green",255));
        backG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Background Green", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        backB.setProgress(prefs.getInt("Nodes Background Blue",255));
        backB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Background Blue", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        backA.setProgress(prefs.getInt("Nodes Background Alpha",255));
        backA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Nodes Background Alpha", i);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}
