package com.example.learnretrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.learnretrofit.adapter.CustomAdapter;
import com.example.learnretrofit.databinding.ActivityMainBinding;
import com.example.learnretrofit.model.RetroPhoto;
import com.example.learnretrofit.network.GetDataService;
import com.example.learnretrofit.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = binding.progressBar;

        /*Create handle for the RetrofitInstance interface*/
        RetrofitClientInstance
                .getRetrofitInstance()
                .create(GetDataService.class)
                .getAllPhotos()
                .enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(@NonNull Call<List<RetroPhoto>> call, @NonNull Response<List<RetroPhoto>> response) {
                progressBar.setVisibility(View.GONE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<RetroPhoto>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<RetroPhoto> photoList) {
        RecyclerView recyclerView = binding.customRecyclerView;
        recyclerView.setAdapter(new CustomAdapter(this, photoList));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

}
