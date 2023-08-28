package com.zeyad.voicerecorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.zeyad.voicerecorder.Adapters.ViewPagerAdapter;
import com.zeyad.voicerecorder.Fragments.Recorder;
import com.zeyad.voicerecorder.Fragments.Recordings;
import com.zeyad.voicerecorder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        requestFullScreen();
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        setUpWithViewPager();

        binding.tabLayout.setupWithViewPager(binding.viewPager, true);

    }

    private void requestFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setUpWithViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new Recorder(), "Recorder");
        adapter.addFragments(new Recordings(), "Recordings");

        binding.viewPager.setAdapter(adapter);
    }
}