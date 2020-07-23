package com.example.restcountries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restcountries.API.APIClient;
import com.example.restcountries.API.APIInterface;
import com.example.restcountries.Models.Region;
import com.example.restcountries.Adapter.RegionAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    String RegionName;
    APIInterface service;
    RecyclerView mRecyclerView;
    RegionAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    private LinearLayout mainLayout;
    View myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        LayoutInflater inflater = getLayoutInflater();
        myLayout = inflater.inflate(R.layout.no_results, mainLayout, false);
        RegionName = intent.getStringExtra("userName");
        mRecyclerView = findViewById(R.id.recycleViewer);
        progressBar = findViewById(R.id.main_progress);
        adapter = new RegionAdapter(this);
        mainLayout = findViewById(R.id.searchLinearLayout);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        service = APIClient.getClient().create(APIInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        loadResults();
    }

    void loadResults() {
        callGithubApi().enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("Response Code", response.code() + "");
                if (response.isSuccessful() && response.code() == 200) {
                    List<Region> results = response.body();
                    if (results.size() == 0) {
                        LinearLayout mainLayout = findViewById(R.id.searchLinearLayout);
                        mainLayout.removeAllViews();
                        // inflate (create) another copy of our custom layout
                        LayoutInflater inflater = getLayoutInflater();
                        View myLayout = inflater.inflate(R.layout.no_results, mainLayout, false);
                        mainLayout.addView(myLayout);
                    }
                    adapter.addAll(results);

                    for (int i = 0; i < results.size(); i++) {

                    }
                } else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "Server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Region>> call, Throwable throwable) {
                mainLayout.removeAllViews();
                // inflate (create) another copy of our custom layout
                LayoutInflater inflater = getLayoutInflater();
                View myLayout = inflater.inflate(R.layout.network_failure, mainLayout, false);
                mainLayout.addView(myLayout);
                Toast.makeText(MainActivity.this, "Following error occurred-: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Call<List<Region>> callGithubApi() {
        return service.getUserDetails();
    }
}