package com.example.agent_agrilife;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.MyViewHolder>
{

    Context context;
    ArrayList<FarmUser> userList=new ArrayList<>();

    public ClaimAdapter(Context context, ArrayList<FarmUser> userList)
    {
        this.context=context;
        this.userList=userList;
    }

    @NonNull
    @Override
    public ClaimAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimAdapter.MyViewHolder holder, int position)
    {
        FarmUser user=userList.get(position);

        holder.farmerName.setText(user.name);
        holder.farmEmail.setText(user.email);
        holder.policyOpt.setText(user.uid);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openApproveRejectWindow();
            }
        });
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView farmerName,farmEmail,policyOpt;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            farmerName=(TextView) itemView.findViewById(R.id.farmerName);
            farmEmail=(TextView) itemView.findViewById(R.id.farmEmail);
            policyOpt=(TextView) itemView.findViewById(R.id.optPolicy);
        }
    }
    private void openApproveRejectWindow() {
        Intent ApproveRejectWindow = new Intent(context , ApproveRejectClaim.class);
        context.startActivity(ApproveRejectWindow);
    }

}
