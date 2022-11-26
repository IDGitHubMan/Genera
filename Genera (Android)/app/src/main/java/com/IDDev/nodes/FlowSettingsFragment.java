package com.IDDev.nodes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Arrays;

public class FlowSettingsFragment extends Fragment {
    SeekBar flowAmount, flowSize, velocity, uniformR, uniformG, uniformB, horStartR, horStartG, horStartB, horEndR, horEndG, horEndB, vertStartR, vertStartG, vertStartB, vertEndR, vertEndG, vertEndB, flowRes, noiseInterval, bgr, bgg, bgb, bga;
    Spinner flowColorSystem, flowSystem, noiseXIncAmount, noiseXIncOrder, noiseYIncAmount, noiseYIncOrder, noiseDynamic;
    ViewGroup uniformColor, horizontalGradient, verticalGradient, noiseGroup, roughNoise;
    ConstraintLayout pointGroup;
    SwitchCompat fullCircle;
    MainActivity main;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = requireContext().getSharedPreferences(requireContext().getString(R.string.prefsName),0);
        SharedPreferences.Editor e = prefs.edit();
        ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pointTypes)));
        ArrayAdapter<String> spin = new ArrayAdapter<>(getActivity(), R.layout.bspinner_item, spinnerList);
        View view = inflater.inflate(R.layout.flow_settings, container, false);
        main = (MainActivity) getActivity();

        Integer[] numOpts = new Integer[99];
        for (int i = 0; i < numOpts.length; i++) {
            numOpts[i] = i + 1;
        }

        Integer[] ordOpts = new Integer[11];
        for (int i = 0; i < ordOpts.length; i++) {
            ordOpts[i] = i - 5;
        }
        flowAmount = view.findViewById(R.id.flowCount);
        flowSize = view.findViewById(R.id.flowSize);
        velocity = view.findViewById(R.id.driftSpeed);
        uniformR = view.findViewById(R.id.uniformRed);
        uniformG = view.findViewById(R.id.uniformGreen);
        uniformB = view.findViewById(R.id.uniformBlue);
        horStartR = view.findViewById(R.id.startXRed);
        horStartG = view.findViewById(R.id.startXGreen);
        horStartB = view.findViewById(R.id.startXBlue);
        horEndR = view.findViewById(R.id.endXRed);
        horEndG = view.findViewById(R.id.endXGreen);
        horEndB = view.findViewById(R.id.endXBlue);
        vertStartR = view.findViewById(R.id.yStartR);
        vertStartG = view.findViewById(R.id.yStartG);
        vertStartB = view.findViewById(R.id.yStartB);
        vertEndR = view.findViewById(R.id.yEndR);
        vertEndG = view.findViewById(R.id.yEndG);
        vertEndB = view.findViewById(R.id.yEndB);
        flowRes = view.findViewById(R.id.resolution);
        noiseInterval = view.findViewById(R.id.roughTime);
        bgr = view.findViewById(R.id.flowsBGR);
        bgg = view.findViewById(R.id.flowsBGG);
        bgb = view.findViewById(R.id.flowsBGB);
        bga = view.findViewById(R.id.flowsBGA);



        flowColorSystem = view.findViewById(R.id.flowColorMode);
        ArrayAdapter<String> flowCols = new ArrayAdapter<>(getActivity(),R.layout.bspinner_item,getResources().getStringArray(R.array.flowColorModes));
        flowColorSystem.setAdapter(flowCols);

        flowSystem = view.findViewById(R.id.fieldMode);
        ArrayAdapter<String> flowModes = new ArrayAdapter<>(getActivity(),R.layout.bspinner_item,getResources().getStringArray(R.array.flowFieldModes));
        flowSystem.setAdapter(flowModes);

        noiseYIncAmount = view.findViewById(R.id.yIncNum);
        noiseXIncAmount = view.findViewById(R.id.xIncNum);
        ArrayAdapter<Integer> incNumbers = new ArrayAdapter<>(getActivity(), R.layout.bspinner_item, numOpts);
        noiseXIncAmount.setAdapter(incNumbers);
        noiseYIncAmount.setAdapter(incNumbers);

        noiseYIncOrder = view.findViewById(R.id.yIncOrder);
        noiseXIncOrder = view.findViewById(R.id.xIncOrder);
        ArrayAdapter<Integer> incOrders = new ArrayAdapter<>(getActivity(), R.layout.bspinner_item, ordOpts);
        noiseXIncOrder.removeAllViewsInLayout();
        noiseXIncOrder.setAdapter(incOrders);
        noiseYIncOrder.removeAllViewsInLayout();
        noiseYIncOrder.setAdapter(incOrders);
        noiseDynamic = view.findViewById(R.id.noiseDynamicOptions);
        ArrayAdapter<String> dynamics = new ArrayAdapter<>(getActivity(),R.layout.bspinner_item,getResources().getStringArray(R.array.noiseDynamicChoices));
        noiseDynamic.setAdapter(dynamics);

        uniformColor = view.findViewById(R.id.uniformFlowColorGroup);
        horizontalGradient = view.findViewById(R.id.horizontalGradient);
        verticalGradient = view.findViewById(R.id.verticalGradient);
        pointGroup = view.findViewById(R.id.pointGroup);
        noiseGroup = view.findViewById(R.id.noiseGroup);
        roughNoise = view.findViewById(R.id.roughGroup);


        fullCircle = view.findViewById(R.id.fullRotation);

        flowAmount.setProgress(prefs.getInt("Flows Flow Count",1000) / 100);
        flowAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Flow Count",progress*100);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        flowSize.setProgress(prefs.getInt("Flows Flow Size",5)-1);
        flowSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Flow Size",progress+1);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        velocity.setProgress(prefs.getInt("Flows Velocity Limit",5));
        velocity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Velocity Limit",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        uniformR.setProgress(prefs.getInt("Flows Uniform Red",255));
        uniformR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Uniform Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        uniformG.setProgress(prefs.getInt("Flows Uniform Green",255));
        uniformG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Uniform Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        uniformB.setProgress(prefs.getInt("Flows Uniform Blue",255));
        uniformB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Uniform Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horStartR.setProgress(prefs.getInt("Flows Horizontal 1 Red",255));
        horStartR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 1 Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horStartG.setProgress(prefs.getInt("Flows Horizontal 1 Green",255));
        horStartG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 1 Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horStartB.setProgress(prefs.getInt("Flows Horizontal 1 Blue",255));
        horStartB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 1 Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horEndR.setProgress(prefs.getInt("Flows Horizontal 2 Red",0));
        horEndR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 2 Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horEndG.setProgress(prefs.getInt("Flows Horizontal 2 Green",0));
        horEndG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 2 Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        horEndB.setProgress(prefs.getInt("Flows Horizontal 2 Blue",255));
        horEndB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Horizontal 2 Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertStartR.setProgress(prefs.getInt("Flows Vertical 1 Red",255));
        vertStartR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 1 Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertStartG.setProgress(prefs.getInt("Flows Vertical 1 Green",255));
        vertStartG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 1 Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertStartB.setProgress(prefs.getInt("Flows Vertical 1 Blue",255));
        vertStartB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 1 Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertEndR.setProgress(prefs.getInt("Flows Vertical 2 Red",255));
        vertEndR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 2 Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertEndG.setProgress(prefs.getInt("Flows Vertical 2 Green",255));
        vertEndG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 2 Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vertEndB.setProgress(prefs.getInt("Flows Vertical 2 Blue",255));
        vertEndB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Vertical 2 Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        flowColorSystem.setSelection(prefs.getInt("Flows Coloration",4));
        if (flowColorSystem.getSelectedItemPosition() == 0) {
            uniformColor.setVisibility(View.VISIBLE);
            horizontalGradient.setVisibility(View.GONE);
            verticalGradient.setVisibility(View.GONE);
        } else if (flowColorSystem.getSelectedItemPosition() == 1) {
            uniformColor.setVisibility(View.GONE);
            horizontalGradient.setVisibility(View.VISIBLE);
            verticalGradient.setVisibility(View.GONE);
        } else if (flowColorSystem.getSelectedItemPosition() == 2) {
            uniformColor.setVisibility(View.GONE);
            horizontalGradient.setVisibility(View.GONE);
            verticalGradient.setVisibility(View.VISIBLE);
        } else if (flowColorSystem.getSelectedItemPosition() == 3) {
            uniformColor.setVisibility(View.GONE);
            horizontalGradient.setVisibility(View.VISIBLE);
            verticalGradient.setVisibility(View.VISIBLE);
        } else {
            uniformColor.setVisibility(View.GONE);
            horizontalGradient.setVisibility(View.GONE);
            verticalGradient.setVisibility(View.GONE);
        }

        flowColorSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows Coloration",position);
                e.apply();
                if (position == 0) {
                    uniformColor.setVisibility(View.VISIBLE);
                    horizontalGradient.setVisibility(View.GONE);
                    verticalGradient.setVisibility(View.GONE);
                } else if (position == 1) {
                    uniformColor.setVisibility(View.GONE);
                    horizontalGradient.setVisibility(View.VISIBLE);
                    verticalGradient.setVisibility(View.GONE);
                } else if (position == 2) {
                    uniformColor.setVisibility(View.GONE);
                    horizontalGradient.setVisibility(View.GONE);
                    verticalGradient.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    uniformColor.setVisibility(View.GONE);
                    horizontalGradient.setVisibility(View.VISIBLE);
                    verticalGradient.setVisibility(View.VISIBLE);
                } else {
                    uniformColor.setVisibility(View.GONE);
                    horizontalGradient.setVisibility(View.GONE);
                    verticalGradient.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        flowSystem.setSelection(prefs.getInt("Flows Field System",1));
        if (prefs.getInt("Flows Field System",1) == 0) {
            pointGroup.setVisibility(View.GONE);
            noiseGroup.setVisibility(View.VISIBLE);
        } else {
            pointGroup.setVisibility(View.VISIBLE);
            noiseGroup.setVisibility(View.GONE);
        }
        if (flowSystem.getSelectedItemPosition() == 1) {
            pointGroup.setVisibility(View.VISIBLE);
            noiseGroup.setVisibility(View.GONE);
            ConstraintSet set = new ConstraintSet();
            ArrayList<TextView> t = new ArrayList<>();
            ArrayList<TextView> ty = new ArrayList<>();
            ArrayList<Spinner> s = new ArrayList<>();
            ArrayList<SeekBar> y = new ArrayList<>();
            for (int i = 0; i < main.flow.pointsList.size(); i++) {
                if (i == 0) {
                    TextView n = new TextView(getContext());
                    t.add(n);
                    n.setId(View.generateViewId());
                    n.setText(String.valueOf(i + 1));
                    n.setHeight(96 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    n.setGravity(Gravity.CENTER);
                    pointGroup.removeAllViewsInLayout();
                    pointGroup.addView(n);
                    set.clone(pointGroup);
                    set.connect(n.getId(), ConstraintSet.TOP, pointGroup.getId(), ConstraintSet.TOP);
                    set.connect(n.getId(), ConstraintSet.START, pointGroup.getId(), ConstraintSet.START);
                    set.applyTo(pointGroup);
                    Spinner type = new Spinner(getContext());
                    s.add(type);
                    type.setId(View.generateViewId());
                    type.setAdapter(spin);
                    type.setMinimumHeight(96 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    type.setSelection(main.flow.pointsList.get(i).type);
                    int finalI = i;
                    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            main.flow.pointsList.get(finalI).type = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    pointGroup.addView(type);
                    set.clone(pointGroup);
                    set.connect(type.getId(), ConstraintSet.START, t.get(i).getId(), ConstraintSet.END);
                    set.connect(type.getId(), ConstraintSet.TOP, pointGroup.getId(), ConstraintSet.TOP);
                    set.applyTo(pointGroup);
                    TextView xHint = new TextView(getContext());
                    xHint.setText(R.string.x);
                    xHint.setId(View.generateViewId());
                    xHint.setHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    xHint.setGravity(Gravity.CENTER);
                    pointGroup.addView(xHint);
                    set.clone(pointGroup);
                    set.connect(xHint.getId(), ConstraintSet.TOP, pointGroup.getId(), ConstraintSet.TOP);
                    set.connect(xHint.getId(), ConstraintSet.START, type.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    TextView yHint = new TextView(getContext());
                    ty.add(yHint);
                    yHint.setText(R.string.y);
                    yHint.setId(View.generateViewId());
                    yHint.setHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    yHint.setGravity(Gravity.CENTER);
                    pointGroup.addView(yHint);
                    set.clone(pointGroup);
                    set.connect(yHint.getId(), ConstraintSet.TOP, xHint.getId(), ConstraintSet.BOTTOM);
                    set.connect(yHint.getId(), ConstraintSet.START, type.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    SeekBar xPos = new SeekBar(getContext());
                    xPos.setMinimumHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    xPos.setMax(main.flow.width);
                    xPos.setId(View.generateViewId());
                    int finalI1 = i;
                    xPos.setProgress((int) main.flow.pointsList.get(i).pos.x);
                    xPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            main.flow.pointsList.get(finalI1).pos.x = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    pointGroup.addView(xPos);
                    set.clone(pointGroup);
                    set.constrainWidth(xPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.constrainHeight(xPos.getId(), 48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    set.connect(xPos.getId(), ConstraintSet.TOP, pointGroup.getId(), ConstraintSet.TOP);
                    set.connect(xPos.getId(), ConstraintSet.START, xHint.getId(), ConstraintSet.END);
                    set.connect(xPos.getId(), ConstraintSet.END, pointGroup.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    SeekBar yPos = new SeekBar(getContext());
                    y.add(yPos);
                    yPos.setMinimumHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    yPos.setMax(main.flow.height);
                    yPos.setId(View.generateViewId());
                    int finalI2 = i;
                    yPos.setProgress((int) main.flow.pointsList.get(i).pos.y);
                    yPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            main.flow.pointsList.get(finalI2).pos.y = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    pointGroup.addView(yPos);
                    set.clone(pointGroup);
                    set.constrainWidth(yPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.constrainHeight(yPos.getId(), 48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    set.constrainDefaultWidth(yPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.connect(yPos.getId(), ConstraintSet.TOP, xPos.getId(), ConstraintSet.BOTTOM);
                    set.connect(yPos.getId(), ConstraintSet.START, yHint.getId(), ConstraintSet.END);
                    set.connect(yPos.getId(), ConstraintSet.END, pointGroup.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                } else {
                    TextView n = new TextView(getContext());
                    t.add(n);
                    n.setId(View.generateViewId());
                    n.setText(String.valueOf(i + 1));
                    n.setHeight(96 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    n.setGravity(Gravity.CENTER);
                    pointGroup.addView(n);
                    set.clone(pointGroup);
                    set.connect(n.getId(), ConstraintSet.TOP, t.get(i - 1).getId(), ConstraintSet.BOTTOM);
                    set.connect(n.getId(), ConstraintSet.START, pointGroup.getId(), ConstraintSet.START);
                    set.applyTo(pointGroup);
                    Spinner type = new Spinner(getContext());
                    s.add(type);
                    type.setId(View.generateViewId());
                    type.setAdapter(spin);
                    type.setSelection(main.flow.pointsList.get(i).type);
                    int finalI = i;
                    type.setMinimumHeight(96 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            main.flow.pointsList.get(finalI).type = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    pointGroup.addView(type);
                    set.clone(pointGroup);
                    set.connect(type.getId(), ConstraintSet.START, n.getId(), ConstraintSet.END);
                    set.connect(type.getId(), ConstraintSet.TOP, s.get(i - 1).getId(), ConstraintSet.BOTTOM);
                    set.applyTo(pointGroup);
                    TextView xHint = new TextView(getContext());
                    xHint.setText(R.string.x);
                    xHint.setId(View.generateViewId());
                    xHint.setHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    xHint.setGravity(Gravity.CENTER);
                    pointGroup.addView(xHint);
                    set.clone(pointGroup);
                    set.connect(xHint.getId(), ConstraintSet.TOP, ty.get(i - 1).getId(), ConstraintSet.BOTTOM);
                    set.connect(xHint.getId(), ConstraintSet.START, type.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    TextView yHint = new TextView(getContext());
                    yHint.setText(R.string.y);
                    yHint.setId(View.generateViewId());
                    yHint.setHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    yHint.setGravity(Gravity.CENTER);
                    pointGroup.addView(yHint);
                    set.clone(pointGroup);
                    set.connect(yHint.getId(), ConstraintSet.TOP, xHint.getId(), ConstraintSet.BOTTOM);
                    set.connect(yHint.getId(), ConstraintSet.START, type.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    SeekBar xPos = new SeekBar(getContext());
                    xPos.setMinimumHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    xPos.setMax(main.flow.width);
                    xPos.setId(View.generateViewId());
                    int finalI1 = i;
                    xPos.setProgress((int) main.flow.pointsList.get(i).pos.x);
                    xPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            main.flow.pointsList.get(finalI1).pos.x = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    pointGroup.addView(xPos);
                    set.clone(pointGroup);
                    set.constrainWidth(xPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.constrainHeight(xPos.getId(), 48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    set.connect(xPos.getId(), ConstraintSet.TOP, y.get(i - 1).getId(), ConstraintSet.BOTTOM);
                    set.connect(xPos.getId(), ConstraintSet.START, xHint.getId(), ConstraintSet.END);
                    set.connect(xPos.getId(), ConstraintSet.END, pointGroup.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                    SeekBar yPos = new SeekBar(getContext());
                    y.add(yPos);
                    yPos.setMinimumHeight(48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    yPos.setMax(main.flow.height);
                    yPos.setId(View.generateViewId());
                    int finalI2 = i;
                    yPos.setProgress((int) main.flow.pointsList.get(i).pos.y);
                    yPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            main.flow.pointsList.get(finalI2).pos.y = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    pointGroup.addView(yPos);
                    set.clone(pointGroup);
                    set.constrainWidth(yPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.constrainHeight(yPos.getId(), 48 * (requireActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
                    set.constrainDefaultWidth(yPos.getId(), ConstraintSet.MATCH_CONSTRAINT_SPREAD);
                    set.connect(yPos.getId(), ConstraintSet.TOP, xPos.getId(), ConstraintSet.BOTTOM);
                    set.connect(yPos.getId(), ConstraintSet.START, yHint.getId(), ConstraintSet.END);
                    set.connect(yPos.getId(), ConstraintSet.END, pointGroup.getId(), ConstraintSet.END);
                    set.applyTo(pointGroup);
                }
            }
        }
        flowSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows Field System",position);
                e.apply();
                if (position == 0) {
                    pointGroup.setVisibility(View.GONE);
                    noiseGroup.setVisibility(View.VISIBLE);
                } else {
                    pointGroup.setVisibility(View.VISIBLE);
                    noiseGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        flowRes.setProgress(prefs.getInt("Flows Field Resolution",1) - 1);
        flowRes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Field Resolution",progress+1);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        fullCircle.setChecked(prefs.getBoolean("Flows Noise Rotation",true));
        fullCircle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            e.putBoolean("Flows Noise Rotation", isChecked);
            e.apply();
        });

        noiseXIncAmount.setSelection(prefs.getInt("Flows X Val",1) - 1);
        noiseXIncAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows X Val",position+1);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noiseXIncOrder.setSelection(prefs.getInt("Flows X Order",1) + 5);
        noiseXIncOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows X Order",position-5);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noiseYIncAmount.setSelection(prefs.getInt("Flows Y Val",1) - 1);
        noiseYIncAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows Y Val",position+1);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noiseYIncOrder.setSelection(prefs.getInt("Flows Y Order",1) + 5);
        noiseYIncOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e.putInt("Flows Y Order",position-5);
                e.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noiseDynamic.setSelection(prefs.getInt("Flows Dynamism",0));
        if (noiseDynamic.getSelectedItemPosition()!=1){
            roughNoise.setVisibility(View.GONE);
        }
        else{
            roughNoise.setVisibility(View.VISIBLE);
        }
        noiseDynamic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                e.putInt("Flow Dynamism",i);
                e.apply();
                if (noiseDynamic.getSelectedItemPosition()!=1){
                    roughNoise.setVisibility(View.GONE);
                }
                else{
                    roughNoise.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        noiseInterval.setProgress(prefs.getInt("Flows Time Interval",5000)-100);
        noiseInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                e.putInt("Flows Time Interval",i+100);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bgr.setProgress(prefs.getInt("Flows Background Red",0));
        bgr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Background Red",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bgg.setProgress(prefs.getInt("Flows Background Green",0));
        bgg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Background Green",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bgb.setProgress(prefs.getInt("Flows Background Blue",0));
        bgb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Background Blue",progress);
                e.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bga.setProgress(prefs.getInt("Flow Background Alpha",20));
        bga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                e.putInt("Flows Background Alpha",progress);
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
