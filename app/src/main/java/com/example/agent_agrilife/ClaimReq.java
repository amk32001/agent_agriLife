package com.example.agent_agrilife;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ClaimReq extends Fragment {
    ArrayList<FarmUser> farmUserArrayList;
    ClaimAdapter claimAdapter;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
   // String passStr= getActivity().getIntent().getStringExtra("password");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_claim_req, container, false);
        //Toast.makeText(getContext(), passStr, Toast.LENGTH_SHORT).show();
        try {
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            firebaseFirestore = FirebaseFirestore.getInstance();
            farmUserArrayList = new ArrayList<>();
            claimAdapter = new ClaimAdapter(getContext(), farmUserArrayList);

            recyclerView.setAdapter(claimAdapter);

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
                    claimAdapter.notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            Log.d("claimreq" , e.getMessage());
        }
        return view;
    }

}