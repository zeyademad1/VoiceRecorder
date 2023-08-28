package com.zeyad.voicerecorder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyad.voicerecorder.R;
import com.zeyad.voicerecorder.databinding.RecItemBinding;
import com.zeyad.voicerecorder.Interfaces.onNameClicked;

import java.io.File;
import java.util.List;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.RecordViewHolder> {
    List<File> fileList;
    Context context;
    onNameClicked listner;

    public RecordingsAdapter(List<File> fileList, Context context, onNameClicked listner) {
        this.fileList = fileList;
        this.context = context;
        this.listner = listner;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecordViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.binding.recordName.setText(fileList.get(position).getName());
        holder.binding.recordName.setSelected(true);
        holder.binding.container.setOnClickListener(c -> listner.onNameClick(fileList.get(position)));
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }


    static class RecordViewHolder extends RecyclerView.ViewHolder {
        RecItemBinding binding;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RecItemBinding.bind(itemView);
        }
    }

}


