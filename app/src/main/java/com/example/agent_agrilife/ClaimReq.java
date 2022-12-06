package com.example.agent_agrilife;

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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ClaimReq extends Fragment {


    ArrayList<FarmUser> farmUserArrayList= new ArrayList<>();
    ClaimAdapter claimAdapter;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
         // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_claim_req, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore=FirebaseFirestore.getInstance();
        farmUserArrayList=new ArrayList<>();
        claimAdapter=new ClaimAdapter(getContext(),farmUserArrayList);

        recyclerView.setAdapter(claimAdapter);

        firebaseFirestore.collection("USERS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Log.e("Firebase Errror!",error.getMessage());
                    return;
                }

                assert value != null;
                for(DocumentChange dc: value.getDocumentChanges())
                {
                    if(dc.getType()== DocumentChange.Type.ADDED)
                    {
                        farmUserArrayList.add(dc.getDocument().toObject(FarmUser.class));
                    }
                    claimAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//        firebaseFirestore.collection("USERS").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.e("Firebase Errror!", error.getMessage());
//                    return;
//                }
//
//                assert value != null;
//                for (DocumentChange dc : value.getDocumentChanges()) {
//                    if (dc.getType() == DocumentChange.Type.ADDED) {
//                        farmUserArrayList.add(dc.getDocument().toObject(FarmUser.class));
//                    }
//                    claimAdapter.notifyDataSetChanged();
//                }
//            }
//        });
}