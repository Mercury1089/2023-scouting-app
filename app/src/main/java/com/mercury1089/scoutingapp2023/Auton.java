package com.mercury1089.scoutingapp2023;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Vibrator;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.LinkedHashMap;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.mercury1089.scoutingapp2023.utils.GenUtils;

public class Auton extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> autonHashMap;

    //Cone Section
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
        mobilityID = getView().findViewById(R.id.IDMobility);
        mobilitySwitch = getView().findViewById(R.id.MobilitySwitch);
        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);
        fellOverID = getView().findViewById(R.id.IDFellOver);

        nextButton = getView().findViewById(R.id.NextTeleopButton);
        autonCSTabs = getView().findViewById(R.id.AutonChargeStationTabs);

        topEdgeBar = getView().findViewById(R.id.topEdgeBar);
        bottomEdgeBar = getView().findViewById(R.id.bottomEdgeBar);
        leftEdgeBar = getView().findViewById(R.id.leftEdgeBar);
        rightEdgeBar = getView().findViewById(R.id.rightEdgeBar);

        //get HashMap data (fill with defaults if empty or null)
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        setupHashMap = HashMapManager.getSetupHashMap();
        autonHashMap = HashMapManager.getAutonHashMap();

        //fill in counters with data
        updateXMLObjects();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        timer = new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                secondsRemaining.setText(GenUtils.padLeftZeros("" + millisUntilFinished / 1000, 2));

                if(!running)
                    return;

                if (millisUntilFinished / 1000 <= 3 && millisUntilFinished / 1000 > 0) {  //play the blinking animation
                    teleopWarning.setVisibility(View.VISIBLE);
                    timerID.setTextColor(context.getResources().getColor(R.color.banana));
                    timerID.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.timer_yellow, 0, 0, 0);

                    vibrator.vibrate(500);

                    ObjectAnimator topEdgeLighter = ObjectAnimator.ofFloat(topEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator bottomEdgeLighter = ObjectAnimator.ofFloat(bottomEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator rightEdgeLighter = ObjectAnimator.ofFloat(rightEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator leftEdgeLighter = ObjectAnimator.ofFloat(leftEdgeBar, View.ALPHA, 0.0f, 1.0f);

                    topEdgeLighter.setDuration(500);
                    bottomEdgeLighter.setDuration(500);
                    leftEdgeLighter.setDuration(500);
                    rightEdgeLighter.setDuration(500);

                    topEdgeLighter.setRepeatMode(ObjectAnimator.REVERSE);
                    topEdgeLighter.setRepeatCount(1);
                    bottomEdgeLighter.setRepeatMode(ObjectAnimator.REVERSE);
                    bottomEdgeLighter.setRepeatCount(1);
                    leftEdgeLighter.setRepeatMode(ObjectAnimator.REVERSE);
                    leftEdgeLighter.setRepeatCount(1);
                    rightEdgeLighter.setRepeatMode(ObjectAnimator.REVERSE);
                    rightEdgeLighter.setRepeatCount(1);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(topEdgeLighter, bottomEdgeLighter, leftEdgeLighter, rightEdgeLighter);
                    animatorSet.start();

                }
            }

            public void onFinish() { //sets the label to display a teleop error background and text
                if(running) {
                    secondsRemaining.setText("00");
                    topEdgeBar.setBackground(getResources().getDrawable(R.drawable.teleop_error));
                    bottomEdgeBar.setBackground(getResources().getDrawable(R.drawable.teleop_error));
                    leftEdgeBar.setBackground(getResources().getDrawable(R.drawable.teleop_error));
                    rightEdgeBar.setBackground(getResources().getDrawable(R.drawable.teleop_error));
                    timerID.setTextColor(context.getResources().getColor(R.color.border_warning));
                    timerID.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.timer_red, 0, 0, 0);
                    teleopWarning.setTextColor(getResources().getColor(R.color.white));
                    teleopWarning.setBackground(getResources().getDrawable(R.drawable.teleop_error));
                    teleopWarning.setText(getResources().getString(R.string.TeleopError));

                    ObjectAnimator topEdgeLighter = ObjectAnimator.ofFloat(topEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator bottomEdgeLighter = ObjectAnimator.ofFloat(bottomEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator rightEdgeLighter = ObjectAnimator.ofFloat(rightEdgeBar, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator leftEdgeLighter = ObjectAnimator.ofFloat(leftEdgeBar, View.ALPHA, 0.0f, 1.0f);

                    int currentButtonColor = GenUtils.getAColor(context, R.color.melon);
                    if(!nextButton.isEnabled())
                        currentButtonColor = GenUtils.getAColor(context, R.color.night);

                    ValueAnimator teleopButtonAnim = ValueAnimator.ofArgb(currentButtonColor, GenUtils.getAColor(context, R.color.fire));
                    teleopButtonAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            nextButton.setBackgroundColor((Integer)animation.getAnimatedValue());
                        }
                    });

                    int currentArrowColor = GenUtils.getAColor(context, R.color.ice);
                    if(!nextButton.isEnabled())
                        currentArrowColor = GenUtils.getAColor(context, R.color.ocean);

                    ValueAnimator teleopArrowAnim = ValueAnimator.ofArgb(currentArrowColor, GenUtils.getAColor(context, R.color.ice));
                    teleopArrowAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            nextButton.getCompoundDrawablesRelative()[2].setColorFilter((Integer)animation.getAnimatedValue(), PorterDuff.Mode.SRC_IN);
                        }
                    });

                    teleopArrowAnim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            nextButton.getCompoundDrawablesRelative()[2].clearColorFilter();
                            nextButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.right,0);
                        }
                    });

                    ValueAnimator teleopTextAnim = ValueAnimator.ofArgb(nextButton.getCurrentTextColor(), GenUtils.getAColor(context, R.color.ice));
                    teleopTextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            nextButton.setTextColor((Integer)animation.getAnimatedValue());
                        }
                    });

                    topEdgeLighter.setDuration(500);
                    bottomEdgeLighter.setDuration(500);
                    leftEdgeLighter.setDuration(500);
                    rightEdgeLighter.setDuration(500);
                    teleopButtonAnim.setDuration(500);
                    teleopTextAnim.setDuration(500);
                    teleopArrowAnim.setDuration(500);

                    AnimatorSet animatorSet1 = new AnimatorSet();
                    animatorSet1.playTogether(topEdgeLighter, bottomEdgeLighter, leftEdgeLighter, rightEdgeLighter, teleopButtonAnim, teleopTextAnim, teleopArrowAnim);

                    teleopButtonAnimation = ValueAnimator.ofArgb(GenUtils.getAColor(context, R.color.fire), GenUtils.getAColor(context, R.color.ocean));

                    teleopButtonAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            nextButton.setBackgroundColor((Integer)animation.getAnimatedValue());
                        }
                    });

                    teleopButtonAnimation.setDuration(500);;
                    teleopButtonAnimation.setRepeatMode(ValueAnimator.REVERSE);
                    teleopButtonAnimation.setRepeatCount(ValueAnimator.INFINITE);

                    animatorSet = new AnimatorSet();
                    animatorSet.playSequentially(animatorSet1, teleopButtonAnimation);
                    animatorSet.start();
                }
            }
        };

        if(firstTime) {
            firstTime = false;
            timer.start();
        }
        else {
            topEdgeBar.setAlpha(1);
            bottomEdgeBar.setAlpha(1);
            rightEdgeBar.setAlpha(1);
            leftEdgeBar.setAlpha(1);
        }

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
                autonHashMap.put("ConePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        conePossessedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) conePossessedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("ConePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredTopIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredTopCounter.getText());
                currentCount++;
                autonHashMap.put("ConeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredTopDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredTopCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("ConeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredMidIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredMidCounter.getText());
                currentCount++;
                autonHashMap.put("ConeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredMidDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredMidCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("ConeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredHybridIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredHybridCounter.getText());
                currentCount++;
                autonHashMap.put("ConeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneScoredHybridDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) coneScoredHybridCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("ConeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });


        coneMissedIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String) coneMissedCounter.getText());
                currentCount++;
                autonHashMap.put("ConeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        coneMissedDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String) coneMissedCounter.getText());
                if(currentCount > 0)
                    coneMissedDecrementButton.setEnabled(false);
                currentCount--;
                autonHashMap.put("ConeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });




        cubePossessedIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubePossessedCounter.getText());
                currentCount++;
                autonHashMap.put("CubePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubePossessedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubePossessedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("CubePossessed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubesScoredHybridIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesScoredHybridCounter.getText());
                currentCount++;
                autonHashMap.put("CubeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubesScoredHybridDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesScoredHybridCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("CubeScoredHybrid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredMidIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredMidCounter.getText());
                currentCount++;
                autonHashMap.put("CubeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredMidDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredMidCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("CubeScoredMid", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredTopIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredTopCounter.getText());
                currentCount++;
                autonHashMap.put("CubeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeScoredTopDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubeScoredTopCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("CubeScoredHigh", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeMissedIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesMissedCounter.getText());
                currentCount++;
                autonHashMap.put("CubeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        cubeMissedDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String) cubesMissedCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                autonHashMap.put("CubeMissed", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        mobilitySwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autonHashMap.put("Mobility", isChecked ? "1" : "0");
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
        cubesPossessedID.setEnabled(enable);
        cubesMissedID.setEnabled(enable);

        coneScoredTopIncrementButton.setEnabled(enable);
        coneScoredTopDecrementButton.setEnabled(enable);
        coneScoredTopCounter.setEnabled(enable);

        coneScoredMidIncrementButton.setEnabled(enable);
        coneScoredMidDecrementButton.setEnabled(enable);
        coneScoredMidCounter.setEnabled(enable);

        coneScoredHybridIncrementButton.setEnabled(enable);
        coneScoredHybridDecrementButton.setEnabled(enable);
        coneScoredHybridCounter.setEnabled(enable);

        coneMissedIncrementButton.setEnabled(enable);
        coneMissedDecrementButton.setEnabled(enable);
        coneMissedCounter.setEnabled(enable);

        cubeScoredTopIncrementButton.setEnabled(enable);
        cubeScoredTopDecrementButton.setEnabled(enable);
        cubeScoredTopCounter.setEnabled(enable);

        cubeScoredMidIncrementButton.setEnabled(enable);
        cubeScoredMidDecrementButton.setEnabled(enable);
        cubeScoredMidCounter.setEnabled(enable);

        cubesScoredHybridIncrementButton.setEnabled(enable);
        cubesScoredHybridDecrementButton.setEnabled(enable);
        cubesScoredHybridCounter.setEnabled(enable);

        cubeMissedIncrementButton.setEnabled(enable);
        cubeMissedDecrementButton.setEnabled(enable);
        cubesMissedCounter.setEnabled(enable);
    }

    private void miscButtonsEnabledState(boolean enable){
        miscID.setEnabled(enable);
        miscDescription.setEnabled(enable);
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
        mobilitySwitch.setEnabled(enable);
        mobilityID.setEnabled(enable);
    }

    private void updateXMLObjects(){
        conePossessedCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("ConePossessed"), 2));
        coneScoredTopCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("ConeScoredHigh"), 2));
        coneScoredMidCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("ConeScoredMid"), 2));
        coneScoredHybridCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("ConeScoredHybrid"), 2));
        coneMissedCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("ConeMissed"), 2));

        cubePossessedCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("CubePossessed"), 2));
        cubeScoredTopCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("CubeScoredHigh"), 2));
        cubeScoredMidCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("CubeScoredMid"), 2));
        cubesScoredHybridCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("CubeScoredHybrid"), 2));
        cubesMissedCounter.setText(GenUtils.padLeftZeros(autonHashMap.get("CubeMissed"), 2));

        mobilitySwitch.setChecked(autonHashMap.get("Mobility").equals("1"));

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
                conePossessedDecrementButton.setEnabled(false);
            else
                conePossessedDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) coneScoredTopCounter.getText()) <= 0)
                coneScoredTopDecrementButton.setEnabled(false);
            else
                coneScoredTopDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) coneScoredMidCounter.getText()) <= 0)
                coneScoredMidDecrementButton.setEnabled(false);
            else
                coneScoredMidDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) coneScoredHybridCounter.getText()) <= 0)
                coneScoredHybridDecrementButton.setEnabled(false);
            else
                coneScoredHybridDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) coneMissedCounter.getText()) <= 0)
                coneMissedDecrementButton.setEnabled(false);
            else
                coneMissedDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) cubePossessedCounter.getText()) <= 0)
                cubePossessedDecrementButton.setEnabled(false);
            else
                cubePossessedDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) cubeScoredTopCounter.getText()) <= 0)
                cubeScoredTopDecrementButton.setEnabled(false);
            else
                cubeScoredTopDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) cubeScoredMidCounter.getText()) <= 0)
                cubeScoredMidDecrementButton.setEnabled(false);
            else
                cubeScoredMidDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) cubesScoredHybridCounter.getText()) <= 0)
                cubesScoredHybridDecrementButton.setEnabled(false);
            else
                cubesScoredHybridDecrementButton.setEnabled(true);
            if (Integer.parseInt((String) cubesMissedCounter.getText()) <= 0)
                cubeMissedDecrementButton.setEnabled(false);
            else
                cubeMissedDecrementButton.setEnabled(true);
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
                autonHashMap = HashMapManager.getAutonHashMap();
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
                HashMapManager.putAutonHashMap(autonHashMap);
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
