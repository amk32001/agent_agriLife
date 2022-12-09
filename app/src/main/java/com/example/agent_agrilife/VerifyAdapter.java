package com.example.agent_agrilife;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerifyAdapter extends RecyclerView.Adapter<ClaimAdapter.MyViewHolder>
{
    Context context;
    ArrayList<FarmUser> userList1;
    @NonNull
    @Override
    public ClaimAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userList1.size();
    }
}
