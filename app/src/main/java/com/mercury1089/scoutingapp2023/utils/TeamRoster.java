package com.mercury1089.scoutingapp2023.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamRoster {
    private File file;
    private ArrayList<String> roster;

    public TeamRoster(Context context) throws IOException, FileNotFoundException {
        Log.d("TEST", "HI");
        FileInputStream fis = context.openFileInput("");
        roster = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
    }

    public String getRandomName() {
        final String rahil = "Shiraz Rahil";
        int rand = (int)(Math.random() * (roster.size()+1));

        String last_first = rand > roster.size()-1 ? rahil : roster.get(rand); //Pulls random name and sets it to "first last" order
        String name = parseName(last_first);
        return name;
    }

    private String parseName(String name) {
        String[] parts = name.split(",");
        String newName = "";
        if (parts.length > 2) {
            newName = parts[parts.length - 1] + " ";
            for (int i = 0; i < parts.length - 1; i++)
                newName += parts[i] + " ";
        } else
            newName = parts[1] + " " + parts[0];
        return newName;
    }
}
