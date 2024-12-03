package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OfficialAdapter extends RecyclerView.Adapter<OfficialAdapter.OfficialViewHolder> {

    private ArrayList<Official> officialsList;
    private OnOfficialClickListener listener;

    public OfficialAdapter(ArrayList<Official> officialsList, OnOfficialClickListener listener) {
        this.officialsList = officialsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OfficialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_official, parent, false);
        return new OfficialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialViewHolder holder, int position) {
        Official official = officialsList.get(position);
        holder.nameTextView.setText(official.getName());
        holder.designationTextView.setText(official.getDesignation());
        holder.deptTextView.setText(official.getDept());

        holder.itemView.setOnClickListener(v -> listener.onOfficialClick(official));
    }

    @Override
    public int getItemCount() {
        return officialsList.size();
    }

    static class OfficialViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, designationTextView, deptTextView;

        public OfficialViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            designationTextView = itemView.findViewById(R.id.textViewDesignation);
            deptTextView = itemView.findViewById(R.id.textViewDept);
        }
    }

    interface OnOfficialClickListener {
        void onOfficialClick(Official official);
    }
}
