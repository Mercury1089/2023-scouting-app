package com.mercury1089.scoutingapp2023;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.mercury1089.scoutingapp2023.utils.GenUtils;
import java.util.LinkedHashMap;

public class Teleop extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> teleopHashMap;

    //Cone Section
    private TextView conesScoredID;

    private TextView conesPossessedID;
    private ImageButton conePossessedIncrementButton;
    private ImageButton conePossessedDecrementButton;
    private TextView conePossessedCounter;

    private TextView conesScoredTopID;
    private ImageButton coneScoredTopIncrementButton;
    private ImageButton coneScoredTopDecrementButton;
    private TextView coneScoredTopCounter;

    private TextView conesScoredMidID;
    private ImageButton coneScoredMidIncrementButton;
    private ImageButton coneScoredMidDecrementButton;
    private TextView coneScoredMidCounter;

    private TextView coneScoredHybridID;
    private ImageButton coneScoredHybridIncrementButton;
    private ImageButton coneScoredHybridDecrementButton;
    private TextView coneScoredHybridCounter;

    private TextView coneMissedID;
    private ImageButton coneMissedIncrementButton;
    private ImageButton coneMissedDecrementButton;
    private TextView coneMissedCounter;

    //Cube Section
    private TextView cubesScoredID;

    private TextView cubesPossessedID;
    private ImageButton cubePossessedIncrementButton;
    private ImageButton cubePossessedDecrementButton;
    private TextView cubePossessedCounter;

    private TextView cubeScoredTopID;
    private ImageButton cubeScoredTopIncrementButton;
    private ImageButton cubeScoredTopDecrementButton;
    private TextView cubeScoredTopCounter;

    private TextView cubeScoredMidID;
    private ImageButton cubeScoredMidIncrementButton;
    private ImageButton cubeScoredMidDecrementButton;
    private TextView cubeScoredMidCounter;

    private TextView cubeScoredHybridID;
    private ImageButton cubesScoredHybridIncrementButton;
    private ImageButton cubesScoredHybridDecrementButton;
    private TextView cubesScoredHybridCounter;

    private TextView cubesMissedID;
    private ImageButton cubeMissedIncrementButton;
    private ImageButton cubeMissedDecrementButton;
    private TextView cubesMissedCounter;

    //Auton Charge Station
    private TabLayout autonCSTabs;
    private TextView chargeStationID;

    //Switches
    private Switch mobilitySwitch;
    private Switch fellOverSwitch;

    //TextViews
    private TextView timerID;
    private TextView secondsRemaining;
    private TextView teleopWarning;

    private TextView scoringID;
    private TextView scoringDescription;
    private TextView IDCones;
    private TextView IDCubes;

    private TextView miscID;
    private TextView miscDescription;
    private TextView mobilityID;

    private TextView fellOverID;

    //ImageViews
    private ImageView topEdgeBar;
    private ImageView bottomEdgeBar;
    private ImageView leftEdgeBar;
    private ImageView rightEdgeBar;

    private Button nextButton;


    //other variables
    private static CountDownTimer timer;
    private boolean firstTime = true;
    private boolean running = true;
    private int conesPossessed, conesScoredTop, conesScoredMid, conesScoredHybrid, conesMissed;
    private int cubePossessed, cubesScoredTop, cubesScoredMid, cubesScoredHybrid, cubesMissed;
    private ValueAnimator teleopButtonAnimation;
    private AnimatorSet animatorSet;

    public static Auton newInstance() {
        Auton fragment = new Auton();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        View inflated = null;
        try {
            inflated = inflater.inflate(R.layout.fragment_auton, container, false);
        } catch (InflateException e) {
            Log.d("Oncreateview", "ERROR");
            throw e;
        }
        return inflated;
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen
        timerID = getView().findViewById(R.id.IDAutonSeconds1);
        secondsRemaining = getView().findViewById(R.id.AutonSeconds);
        teleopWarning = getView().findViewById(R.id.TeleopWarning);

        scoringID = getView().findViewById(R.id.IDScoring);
        scoringDescription = getView().findViewById(R.id.IDScoringDirections);
        IDCones = getView().findViewById(R.id.IDCones);
        IDCubes = getView().findViewById(R.id.IDCubes);

        conesScoredID = getView().findViewById(R.id.IDConesScored);

        conesPossessedID = getView().findViewById(R.id.IDConesPossessed);
        conePossessedIncrementButton = getView().findViewById(R.id.ConePossessedButton);
        conePossessedDecrementButton = getView().findViewById(R.id.ConeNotPossessedButton);
        conePossessedCounter = getView().findViewById(R.id.ConePossessedCounter);

        conesScoredTopID = getView().findViewById(R.id.IDConesScoredTop);
        coneScoredTopIncrementButton = getView().findViewById(R.id.ConeScoredTopButton);
        coneScoredTopDecrementButton = getView().findViewById(R.id.ConeNotScoredTopButton);
        coneScoredTopCounter = getView().findViewById(R.id.ConeScoredTopCounter);

        conesScoredMidID = getView().findViewById(R.id.IDConesScoredMid);
        coneScoredMidIncrementButton = getView().findViewById(R.id.ConeScoredMidButton);
        coneScoredMidDecrementButton = getView().findViewById(R.id.ConeNotScoredMidButton);
        coneScoredMidCounter = getView().findViewById(R.id.ConeScoredMidCounter);

        coneScoredHybridID = getView().findViewById(R.id.IDConesScoredHybrid);
        coneScoredHybridIncrementButton = getView().findViewById(R.id.ConeScoredHybridButton);
        coneScoredHybridDecrementButton = getView().findViewById(R.id.ConeNotScoredHybridButton);
        coneScoredHybridCounter = getView().findViewById(R.id.ConeScoredHybridCounter);

        coneMissedID = getView().findViewById(R.id.IDConeMissed);
        coneMissedIncrementButton = getView().findViewById(R.id.ConeMissedButton);
        coneMissedDecrementButton = getView().findViewById(R.id.ConeNotMissedButton);
        coneMissedCounter = getView().findViewById(R.id.ConeMissedCounter);

        cubesScoredID = getView().findViewById(R.id.IDCubesScored);

        cubesPossessedID = getView().findViewById(R.id.IDCubesPossessed);
        cubePossessedIncrementButton = getView().findViewById(R.id.CubePossessedButton);
        cubePossessedDecrementButton = getView().findViewById(R.id.CubeNotPossessedButton);
        cubePossessedCounter = getView().findViewById(R.id.CubePossessedCounter);

        cubeScoredTopID = getView().findViewById(R.id.IDCubesScoredTop);
        cubeScoredTopIncrementButton = getView().findViewById(R.id.CubeScoredTopButton);
        cubeScoredTopDecrementButton = getView().findViewById(R.id.CubeNotScoredTopButton);
        cubeScoredTopCounter = getView().findViewById(R.id.CubeScoredTopCounter);

        cubeScoredMidID  = getView().findViewById(R.id.IDCubesScoredMid);
        cubeScoredMidIncrementButton = getView().findViewById(R.id.CubeScoredMidButton);
        cubeScoredMidDecrementButton = getView().findViewById(R.id.CubeNotScoredMidButton);
        cubeScoredMidCounter = getView().findViewById(R.id.cubeScoredMidCounter);

        cubeScoredHybridID = getView().findViewById(R.id.IDCubesScoredHybrid);
        cubesScoredHybridIncrementButton = getView().findViewById(R.id.CubeScoredHybridButton);
        cubesScoredHybridDecrementButton = getView().findViewById(R.id.CubeNotScoredHybridButton);
        cubesScoredHybridCounter = getView().findViewById(R.id.CubesScoredHybridCounter);

        cubesMissedID = getView().findViewById(R.id.IDCubesMissed);
        cubeMissedIncrementButton = getView().findViewById(R.id.CubeMissedButton);
        cubeMissedDecrementButton = getView().findViewById(R.id.CubeNotMissedButton);
        cubesMissedCounter = getView().findViewById(R.id.CubesMissedCounter);

        miscID = getView().findViewById(R.id.IDMisc);
        miscDescription = getView().findViewById(R.id.IDMiscDirections);
        chargeStationID = getView().findViewById(R.id.IDChargeStation);
        autonCSTabs = getView().findViewById(R.id.AutonChargeStationTabs);
        mobilityID = getView().findViewById(R.id.IDMobility);
        mobilitySwitch = getView().findViewById(R.id.MobilitySwitch);
        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);
        fellOverID = getView().findViewById(R.id.IDFellOver);

        nextButton = getView().findViewById(R.id.NextTeleopButton);

        topEdgeBar = getView().findViewById(R.id.topEdgeBar);
        bottomEdgeBar = getView().findViewById(R.id.bottomEdgeBar);
        leftEdgeBar = getView().findViewById(R.id.leftEdgeBar);
        rightEdgeBar = getView().findViewById(R.id.rightEdgeBar);

        //get HashMap data (fill with defaults if empty or null)
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        setupHashMap = HashMapManager.getSetupHashMap();
        teleopHashMap = HashMapManager.getTeleopHashMap();

        //fill in counters with data
        updateXMLObjects();

        //set listeners for buttons and fill the hashmap with data

        autonCSTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        conePossessedIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) conePossessedCounter.getText());
                currentCount++;
                teleopHashMap.put("ConePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        conePossessedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) conePossessedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ConePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredTopIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredTopCounter.getText());
                currentCount++;
                teleopHashMap.put("ConeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredTopDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredTopCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ConeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredMidIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredMidCounter.getText());
                currentCount++;
                teleopHashMap.put("ConeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredMidDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredMidCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ConeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredHybridIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredHybridCounter.getText());
                currentCount++;
                teleopHashMap.put("ConeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredHybridDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredHybridCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ConeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });


        coneMissedIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String) coneMissedCounter.getText());
                currentCount++;
                teleopHashMap.put("ConeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneMissedDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String) coneMissedCounter.getText());
                if(currentCount > 0)
                    coneMissedDecrementButton.setEnabled(false);
                currentCount--;
                teleopHashMap.put("ConeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });




        cubePossessedIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubePossessedCounter.getText());
                currentCount++;
                teleopHashMap.put("CubePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubePossessedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubePossessedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("CubePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubesScoredHybridIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesScoredHybridCounter.getText());
                currentCount++;
                teleopHashMap.put("CubeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubesScoredHybridDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesScoredHybridCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("CubeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredMidIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredMidCounter.getText());
                currentCount++;
                teleopHashMap.put("CubeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredMidDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredMidCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("CubeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredTopIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredTopCounter.getText());
                currentCount++;
                teleopHashMap.put("CubeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredTopDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredTopCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("CubeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeMissedIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesMissedCounter.getText());
                currentCount++;
                teleopHashMap.put("CubeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeMissedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesMissedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("CubeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        mobilitySwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                teleopHashMap.put("Mobility", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        fellOverSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setupHashMap.put("FellOver", isChecked ? "1" : "0");
                updateXMLObjects();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.tabs.getTabAt(1).select();
            }
        });
    }

    private void possessionButtonsEnabledState(boolean enable){
        conesPossessedID.setEnabled(enable);
        conePossessedIncrementButton.setEnabled(enable);
        conePossessedDecrementButton.setEnabled(enable);
        conePossessedCounter.setEnabled(enable);
        cubesPossessedID.setEnabled(enable);
        cubePossessedIncrementButton.setEnabled(enable);
        cubePossessedDecrementButton.setEnabled(enable);
        cubePossessedCounter.setEnabled(enable);
    }

    private void scoringButtonsEnabledState(boolean enable){
        scoringID.setEnabled(enable);
        scoringDescription.setEnabled(enable);
        IDCones.setEnabled(enable);
        IDCubes.setEnabled(enable);

        conesScoredID.setEnabled(enable);

        conesScoredTopID.setEnabled(enable);
        coneScoredTopIncrementButton.setEnabled(enable);
        coneScoredTopDecrementButton.setEnabled(enable);
        coneScoredTopCounter.setEnabled(enable);

        conesScoredMidID.setEnabled(enable);
        coneScoredMidIncrementButton.setEnabled(enable);
        coneScoredMidDecrementButton.setEnabled(enable);
        coneScoredMidCounter.setEnabled(enable);

        coneScoredHybridID.setEnabled(enable);
        coneScoredHybridIncrementButton.setEnabled(enable);
        coneScoredHybridDecrementButton.setEnabled(enable);
        coneScoredHybridCounter.setEnabled(enable);

        coneMissedID.setEnabled(enable);
        coneMissedIncrementButton.setEnabled(enable);
        coneMissedDecrementButton.setEnabled(enable);
        coneMissedCounter.setEnabled(enable);

        cubesScoredID.setEnabled(enable);

        cubeScoredTopID.setEnabled(enable);
        cubeScoredTopIncrementButton.setEnabled(enable);
        cubeScoredTopDecrementButton.setEnabled(enable);
        cubeScoredTopCounter.setEnabled(enable);

        cubeScoredMidID.setEnabled(enable);
        cubeScoredMidIncrementButton.setEnabled(enable);
        cubeScoredMidDecrementButton.setEnabled(enable);
        cubeScoredMidCounter.setEnabled(enable);

        cubeScoredHybridID.setEnabled(enable);
        cubesScoredHybridIncrementButton.setEnabled(enable);
        cubesScoredHybridDecrementButton.setEnabled(enable);
        cubesScoredHybridCounter.setEnabled(enable);

        cubesMissedID.setEnabled(enable);
        cubeMissedIncrementButton.setEnabled(enable);
        cubeMissedDecrementButton.setEnabled(enable);
        cubesMissedCounter.setEnabled(enable);
    }

    private void miscButtonsEnabledState(boolean enable){
        miscID.setEnabled(enable);
        miscDescription.setEnabled(enable);
        chargeStationID.setEnabled(enable);
        autonCSTabs.setEnabled(enable);
        mobilitySwitch.setEnabled(enable);
        mobilityID.setEnabled(enable);
        fellOverSwitch.setEnabled(enable);
        fellOverID.setEnabled(enable);
        nextButton.setEnabled(enable);
    }

    private void allButtonsEnabledState(boolean enable){
        possessionButtonsEnabledState(enable);
        scoringButtonsEnabledState(enable);

        miscID.setEnabled(enable);
        miscDescription.setEnabled(enable);
        chargeStationID.setEnabled(enable);
        autonCSTabs.setEnabled(enable);
        mobilitySwitch.setEnabled(enable);
        mobilityID.setEnabled(enable);
    }

    private void updateXMLObjects(){
        conePossessedCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ConePossessed"), 2));
        coneScoredTopCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ConeScoredHigh"), 2));
        coneScoredMidCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ConeScoredMid"), 2));
        coneScoredHybridCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ConeScoredHybrid"), 2));
        coneMissedCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ConeMissed"), 2));

        cubePossessedCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("CubePossessed"), 2));
        cubeScoredTopCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("CubeScoredHigh"), 2));
        cubeScoredMidCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("CubeScoredMid"), 2));
        cubesScoredHybridCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("CubeScoredHybrid"), 2));
        cubesMissedCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("CubeMissed"), 2));

        mobilitySwitch.setChecked(teleopHashMap.get("Mobility").equals("1"));

        if(setupHashMap.get("FellOver").equals("1")) {
            fellOverSwitch.setChecked(true);
            nextButton.setPadding(150, 0, 150, 0);
            nextButton.setText(R.string.GenerateQRCode);
            allButtonsEnabledState(false);
        } else {
            fellOverSwitch.setChecked(false);
            nextButton.setPadding(150, 0, 185, 0);
            nextButton.setText(R.string.TeleopNext);
            allButtonsEnabledState(true);
            // Disables decrement buttons if counter is at 0
            if(Integer.parseInt((String) conePossessedCounter.getText()) <= 0)
                conePossessedDecrementButton.setClickable(false);
            else
                conePossessedDecrementButton.setClickable(true);
            if (Integer.parseInt((String) coneScoredTopCounter.getText()) <= 0)
                coneScoredTopDecrementButton.setClickable(false);
            else
                coneScoredTopDecrementButton.setClickable(true);
            if (Integer.parseInt((String) coneScoredMidCounter.getText()) <= 0)
                coneScoredMidDecrementButton.setClickable(false);
            else
                coneScoredMidDecrementButton.setClickable(true);
            if (Integer.parseInt((String) coneScoredHybridCounter.getText()) <= 0)
                coneScoredHybridDecrementButton.setClickable(false);
            else
                coneScoredHybridDecrementButton.setClickable(true);
            if (Integer.parseInt((String) coneMissedCounter.getText()) <= 0)
                coneMissedDecrementButton.setClickable(false);
            else
                coneMissedDecrementButton.setClickable(true);
            if (Integer.parseInt((String) cubePossessedCounter.getText()) <= 0)
                cubePossessedDecrementButton.setClickable(false);
            else
                cubePossessedDecrementButton.setClickable(true);
            if (Integer.parseInt((String) cubeScoredTopCounter.getText()) <= 0)
                cubeScoredTopDecrementButton.setClickable(false);
            else
                cubeScoredTopDecrementButton.setClickable(true);
            if (Integer.parseInt((String) cubeScoredMidCounter.getText()) <= 0)
                cubeScoredMidDecrementButton.setClickable(false);
            else
                cubeScoredMidDecrementButton.setClickable(true);
            if (Integer.parseInt((String) cubesScoredHybridCounter.getText()) <= 0)
                cubesScoredHybridDecrementButton.setClickable(false);
            else
                cubesScoredHybridDecrementButton.setClickable(true);
            if (Integer.parseInt((String) cubesMissedCounter.getText()) <= 0)
                cubeMissedDecrementButton.setClickable(false);
            else
                cubeMissedDecrementButton.setClickable(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming visible, then...
            if (isVisibleToUser) {
                setupHashMap = HashMapManager.getSetupHashMap();
                teleopHashMap = HashMapManager.getTeleopHashMap();
                updateXMLObjects();
                // Set all objects in the fragment to their values from the HashMaps
            } else {
                if(teleopButtonAnimation != null) {
                    teleopButtonAnimation.cancel();
                    nextButton.setBackground(getResources().getDrawable(R.drawable.button_next_states));
                    nextButton.setTextColor(new ColorStateList(
                            new int [] [] {
                                    new int [] {android.R.attr.state_enabled},
                                    new int [] {}
                            },
                            new int [] {
                                    GenUtils.getAColor(context, R.color.ice),
                                    GenUtils.getAColor(context, R.color.ocean)
                            }
                    ));
                    nextButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.right_states,0);
                    nextButton.setSelected(true);
                }
                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putTeleopHashMap(teleopHashMap);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        running = false;
        timer.cancel();
    }
}
