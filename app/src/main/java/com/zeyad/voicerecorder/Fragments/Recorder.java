package com.zeyad.voicerecorder.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zeyad.voicerecorder.Interfaces.onFragmentFinishRecord;
import com.zeyad.voicerecorder.R;
import com.zeyad.voicerecorder.databinding.FragmentRecorderBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Recorder extends Fragment {
    FragmentRecorderBinding binding;
    onFragmentFinishRecord listner;
    MediaRecorder recorder;
    boolean isRecording = false;
    String fileName;
    File path;

    public Recorder() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentFinishRecord) {
            listner = (onFragmentFinishRecord) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null)
            binding = FragmentRecorderBinding.inflate(LayoutInflater.from(container.getContext()));
        askRunTimePermission();
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // To handle the storage file
        handleFile();
        //LongClick for recording

        binding.btnRecord.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
                binding.gifRecording.setVisibility(View.VISIBLE);
                binding.timeRecording.setBase(SystemClock.elapsedRealtime());
                binding.timeRecording.start();
                binding.recStatus.setText("Recording");
                binding.btnRecord.setImageResource(R.drawable.ic_stop);
                isRecording = true;
            } else {
                stopRecording();
                binding.gifRecording.setVisibility(View.GONE);
                binding.recStatus.setText("");
                binding.timeRecording.setBase(SystemClock.elapsedRealtime());
                binding.timeRecording.stop();
                isRecording = false;
                binding.btnRecord.setImageResource(R.drawable.ic_record);
                listner.sendRecord(path);
            }

        });

    }

    private void handleFile() {
        path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zeyad Emad is Here");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());
        fileName = path + "/recording_" + date + ".amr";
        if (!path.exists())
            path.mkdirs();


    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void askRunTimePermission() {

        Dexter.withContext(getContext()).withPermissions
                (Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }


}
