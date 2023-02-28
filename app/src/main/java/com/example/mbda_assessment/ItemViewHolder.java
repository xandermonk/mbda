package com.example.mbda_assessment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    // TODO: add image
    TextView nameTextView;
    TextView descriptionTextView;
    public ItemViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.itemName);
        descriptionTextView = itemView.findViewById(R.id.itemDescription);

        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
    }
}
