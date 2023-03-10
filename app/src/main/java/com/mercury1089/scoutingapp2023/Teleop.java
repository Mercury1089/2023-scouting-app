package com.mercury1089.scoutingapp2023;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mercury1089.scoutingapp2023.utils.GenUtils;
import java.util.LinkedHashMap;

public class Teleop extends Fragment {
    //HashMaps for sending QR data between screens
    private LinkedHashMap<String, String> setupHashMap;
    private LinkedHashMap<String, String> teleopHashMap;

    //RadioButtons
    private ImageButton pickedUpIncrementButton;
    private ImageButton pickedUpDecrementButton;
    private ImageButton scoredUpperButton;
    private ImageButton notScoredUpperButton;
    private ImageButton scoredLowerButton;
    private ImageButton notScoredLowerButton;
    private ImageButton missedUpperButton;
    private ImageButton notMissedUpperButton;
    private ImageButton missedLowerButton;
    private ImageButton notMissedLowerButton;
    private Button nextButton;

    //Switches
    private Switch fellOverSwitch;

    //TextViews
    private TextView possessionID;
    private TextView possessionDescription;
    private TextView pickedUpID;
    private TextView pickedUpCounter;
    private TextView IDUpperHub;
    private TextView IDLowerHub;
    private TextView IDScoredUpper;
    private TextView IDScoredLower;
    private TextView IDMissedUpper;
    private TextView IDMissedLower;

    private TextView scoringID;
    private TextView scoringDescription;
    private TextView scoredUpperCounter;
    private TextView scoredLowerCounter;;
    private TextView missedUpperCounter;
    private TextView missedLowerCounter;

    private TextView miscID;
    private TextView miscDescription;

    private TextView fellOverID;

    private Boolean isQRButton;

    public static Teleop newInstance() {
        Teleop fragment = new Teleop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MatchActivity context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (MatchActivity) getActivity();
        return inflater.inflate(R.layout.fragment_teleop, container, false);
    }

    public void onStart(){
        super.onStart();

        //linking variables to XML elements on the screen
        possessionID = getView().findViewById(R.id.IDPossession);
        possessionDescription = getView().findViewById(R.id.IDPossessionDirections);
        pickedUpID = getView().findViewById(R.id.IDConesPossessed);
        pickedUpIncrementButton = getView().findViewById(R.id.ConePossessedButton);
        pickedUpDecrementButton = getView().findViewById(R.id.ConeNotPossessedButton);
        pickedUpCounter = getView().findViewById(R.id.ConeMissedCounter);

        scoringID = getView().findViewById(R.id.IDScoring);
        scoringDescription = getView().findViewById(R.id.IDScoringDirections);
        scoredUpperButton = getView().findViewById(R.id.ConeScoredTopButton);
        notScoredUpperButton = getView().findViewById(R.id.ConeNotScoredTopButton);
        scoredLowerButton = getView().findViewById(R.id.cubePossessedButton);
        notScoredLowerButton = getView().findViewById(R.id.cubeNotPossessedButton);
        missedUpperButton = getView().findViewById(R.id.ConeScoredHybridButton);
        notMissedUpperButton = getView().findViewById(R.id.ConeNotScoredHybridButton);
        missedLowerButton = getView().findViewById(R.id.cubeMissedButton);
        notMissedLowerButton = getView().findViewById(R.id.cubeNotMissedButton);
        scoredUpperCounter = getView().findViewById(R.id.ConeScoredTopCounter);
        scoredLowerCounter = getView().findViewById(R.id.cubesPossessedCounter);
        missedUpperCounter = getView().findViewById(R.id.ConeScoredMidCounter);
        missedLowerCounter = getView().findViewById(R.id.cubesMissedCounter);
        IDUpperHub = getView().findViewById(R.id.IDCones);
        IDLowerHub = getView().findViewById(R.id.IDCubes);
        IDScoredUpper = getView().findViewById(R.id.IDConesScoredTop);
        IDScoredLower = getView().findViewById(R.id.IDCubesPossessed);
        IDMissedUpper = getView().findViewById(R.id.IDConesScoredMid);
        IDMissedLower = getView().findViewById(R.id.IDCubesMissed);

        miscID = getView().findViewById(R.id.IDMisc);
        miscDescription = getView().findViewById(R.id.IDMiscDirections);
        fellOverSwitch = getView().findViewById(R.id.FellOverSwitch);
        fellOverID = getView().findViewById(R.id.IDFellOver);

        nextButton = getView().findViewById(R.id.NextClimbButton);

        //get HashMap data (fill with defaults if empty or null)
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.SETUP);
        HashMapManager.checkNullOrEmpty(HashMapManager.HASH.AUTON);
        setupHashMap = HashMapManager.getSetupHashMap();
        teleopHashMap = HashMapManager.getTeleopHashMap();

        //set listeners for buttons
        pickedUpIncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                currentCount++;
                teleopHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        pickedUpDecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                int currentCount = Integer.parseInt((String)pickedUpCounter.getText());
                if(currentCount > 0)
                    currentCount--;
                teleopHashMap.put("NumberPickedUp", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        scoredUpperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)scoredUpperCounter.getText());
                currentCount++;
                teleopHashMap.put("ScoredUpper", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        notScoredUpperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)scoredUpperCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ScoredUpper", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        missedUpperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)missedUpperCounter.getText());
                currentCount++;
                teleopHashMap.put("MissedUpper", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        notMissedUpperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)missedUpperCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("MissedUpper", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        scoredLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)scoredLowerCounter.getText());
                currentCount++;
                teleopHashMap.put("ScoredLower", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        notScoredLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)scoredLowerCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("ScoredLower", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        missedLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)missedLowerCounter.getText());
                currentCount++;
                teleopHashMap.put("MissedLower", String.valueOf(currentCount));
                updateXMLObjects();
            }
        });

        notMissedLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentCount = Integer.parseInt((String)missedLowerCounter.getText());
                if (currentCount > 0)
                    currentCount--;
                teleopHashMap.put("MissedLower", String.valueOf(currentCount));
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
                context.tabs.getTabAt(2).select();
            }
        });
    }

    private void possessionButtonsEnabledState(boolean enable){
        possessionID.setEnabled(enable);
        possessionDescription.setEnabled(enable);

        pickedUpID.setEnabled(enable);
        pickedUpIncrementButton.setEnabled(enable);
        pickedUpDecrementButton.setEnabled(enable);
        pickedUpCounter.setEnabled(enable);
    }

    private void scoringButtonsEnabledState(boolean enable){
        scoringID.setEnabled(enable);
        scoringDescription.setEnabled(enable);
        IDUpperHub.setEnabled(enable);
        IDLowerHub.setEnabled(enable);
        IDScoredUpper.setEnabled(enable);
        IDScoredLower.setEnabled(enable);
        IDMissedUpper.setEnabled(enable);
        IDMissedLower.setEnabled(enable);

        scoredUpperButton.setEnabled(enable);
        notScoredUpperButton.setEnabled(enable);
        scoredLowerButton.setEnabled(enable);
        notScoredLowerButton.setEnabled(enable);
        scoredUpperCounter.setEnabled(enable);
        scoredLowerCounter.setEnabled(enable);
        missedUpperCounter.setEnabled(enable);
        missedLowerCounter.setEnabled(enable);

        missedUpperButton.setEnabled(enable);
        notMissedUpperButton.setEnabled(enable);
        missedLowerButton.setEnabled(enable);
        notMissedLowerButton.setEnabled(enable);
    }


    private void allButtonsEnabledState(boolean enable){
        possessionButtonsEnabledState(enable);
        scoringButtonsEnabledState(enable);

        miscID.setEnabled(enable);
        miscDescription.setEnabled(enable);


    }

    private void updateXMLObjects(){
        scoredUpperCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ScoredUpper"), 3));
        missedUpperCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("MissedUpper"), 3));
        scoredLowerCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("ScoredLower"), 3));
        missedLowerCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("MissedLower"), 3));
        pickedUpCounter.setText(GenUtils.padLeftZeros(teleopHashMap.get("NumberPickedUp"), 3));

        if(setupHashMap.get("FellOver").equals("1")) {
            fellOverSwitch.setChecked(true);
            nextButton.setPadding(150, 0, 185, 0);
            nextButton.setText(R.string.GenerateQRCode);
            isQRButton = true;
            allButtonsEnabledState(false);
        } else {
            fellOverSwitch.setChecked(false);
            nextButton.setPadding(150, 0, 150, 0);
            nextButton.setText(R.string.ClimbNext);
            isQRButton = false;
            allButtonsEnabledState(true);

            if(Integer.parseInt((String)pickedUpCounter.getText()) == 0)
                pickedUpDecrementButton.setEnabled(false);
            else
                pickedUpDecrementButton.setEnabled(true);
            if (Integer.parseInt((String)scoredUpperCounter.getText()) <= 0)
                notScoredUpperButton.setEnabled(false);
            else
                notScoredUpperButton.setEnabled(true);
            if (Integer.parseInt((String)scoredLowerCounter.getText()) <= 0)
                notScoredLowerButton.setEnabled(false);
            else
                notScoredLowerButton.setEnabled(true);
            if (Integer.parseInt((String)missedUpperCounter.getText()) <= 0)
                notMissedUpperButton.setEnabled(false);
            else
                notMissedUpperButton.setEnabled(true);
            if (Integer.parseInt((String)missedLowerCounter.getText()) <= 0)
                notMissedLowerButton.setEnabled(false);
            else
                notMissedLowerButton.setEnabled(true);
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
                //set all objects in the fragment to their values from the HashMaps
            } else {
                HashMapManager.putSetupHashMap(setupHashMap);
                HashMapManager.putTeleopHashMap(teleopHashMap);
            }
        }
    }
}
