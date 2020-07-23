package com.example.restcountries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class RegionDetailsActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView capitalTextView;
    TextView regionTextView;
    TextView subRegionTextView;
    TextView populationTextView;
    TextView bordersTextView;
    TextView languagesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_details);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<String> borders = new ArrayList<>();
        String region;
        String subRegion;
        String name;
        String lang;
        String bds;
        String capital;
        int population;
        languages = (ArrayList<String>) args.getSerializable("LANGUAGES");
        borders = (ArrayList<String>) args.getSerializable("BORDERS");
        region = (String) args.getSerializable("REGION");
        subRegion = (String) args.getSerializable("SUBREGION");
        population = (int) args.getSerializable("POPULATION");
        name = (String) args.getSerializable("NAME");
        capital = (String) args.getSerializable("CAPITAL");

        lang = languages.toString().replaceAll("\\[", "").replaceAll("]","");

        bds = borders.toString().replaceAll("\\[", "").replaceAll("]","");

        Log.e("DISPLAY", "" + name);
        Log.e("DISPLAY", "" + capital);
        Log.e("DISPLAY", "" + borders.toString());
        Log.e("DISPLAY", "" + population);
        Log.e("DISPLAY", "" + languages.toString());
        Log.e("DISPLAY", "" + region);
        Log.e("DISPLAY", "" + subRegion);

        nameTextView = findViewById(R.id.name);
        capitalTextView = findViewById(R.id.capital);
        regionTextView = findViewById(R.id.region);
        subRegionTextView = findViewById(R.id.subRegion);
        populationTextView = findViewById(R.id.population);
        bordersTextView = findViewById(R.id.borders);
        languagesTextView = findViewById(R.id.languages);

        if (borders.size() == 0) {
            bordersTextView.setText("No borders defined in API");
        } else {
            bordersTextView.setText("Borders-: " + bds);
        }

        if (languages.size() == 0)
            languagesTextView.setText("No languages defined in API");
        else
            languagesTextView.setText("Languages-: " + lang);


        assert region != null;
        if (!region.isEmpty())
            regionTextView.setText("Region-: " + region);
        else
            regionTextView.setText("No region defined in API");

        if (!subRegion.isEmpty())
            subRegionTextView.setText("Subregion-: " + subRegion);
        else
            subRegionTextView.setText("No subregions defined in API");

        populationTextView.setText("Population-: " + population);

        if (!name.isEmpty())
            nameTextView.setText("Name-: " + name);
        else
            nameTextView.setText("No name defined in API");
        if (!capital.isEmpty())
            capitalTextView.setText("Capital -: " + capital);
        else
            capitalTextView.setText("No capital defined in API");
    }
}