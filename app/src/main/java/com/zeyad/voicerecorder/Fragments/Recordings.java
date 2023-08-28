package com.zeyad.voicerecorder.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;

import com.zeyad.voicerecorder.Adapters.RecordingsAdapter;
import com.zeyad.voicerecorder.Interfaces.onFragmentFinishRecord;
import com.zeyad.voicerecorder.Interfaces.onNameClicked;
import com.zeyad.voicerecorder.databinding.FragmentRecordingsBinding;

import java.io.File;
import java.util.ArrayList;

public class Recordings extends Fragment implements onNameClicked, onFragmentFinishRecord {
    FragmentRecordingsBinding binding;
    File path;
    ArrayList<File> recordingsList;
    RecordingsAdapter adapter;

    public Recordings() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null)
            binding = FragmentRecordingsBinding.inflate(LayoutInflater.from(container.getContext()));
        path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Zeyad Emad is Here");

        displayRecords();

        return binding.getRoot();
    }

    private void displayRecords() {
        binding.recList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.recList.setItemAnimator(new DefaultItemAnimator());
        binding.recList.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        recordingsList = new ArrayList<>();
        recordingsList.addAll(getRecordings(path));
        adapter = new RecordingsAdapter(recordingsList, requireContext(), this);
        binding.recList.setAdapter(adapter);

    }

    private ArrayList<File> getRecordings(File file) {
        ArrayList<File> fileList = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1 != null && file1.getName().toLowerCase().endsWith(".amr")) {
                    fileList.add(file1);
                }
            }
        }
        return fileList;
    }

    @Override
    public void onNameClick(File file) {
        Uri uri = FileProvider.getUriForFile(requireContext(), requireContext()
                .getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "audio/x-wav");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        requireActivity().startActivity(intent);
    }


    @Override
    public void sendRecord(File file) {


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            displayRecords();
    }
}