package com.example.agent_agrilife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class VerifyReq extends Fragment {

    ArrayList<FarmUser> farmUserArrayList;
    VerifyAdapter verifyAdapter;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_verify_req, container, false);
        //Toast.makeText(getContext(), passStr, Toast.LENGTH_SHORT).show();
        try {
            recyclerView = view.findViewById(R.id.recyclerView1);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            firebaseFirestore = FirebaseFirestore.getInstance();
            farmUserArrayList = new ArrayList<>();
            verifyAdapter = new VerifyAdapter(getContext(), farmUserArrayList);

            recyclerView.setAdapter(verifyAdapter);

            firebaseFirestore.collection("USERS").addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.e("Firebase Error!", error.getMessage());
                    return;
                }

                assert value != null;
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        farmUserArrayList.add(dc.getDocument().toObject(FarmUser.class));
                    }
                    verifyAdapter.notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            Log.d("claimreq" , e.getMessage());
        }
        return view;
    }
}