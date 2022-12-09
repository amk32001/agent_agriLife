package com.example.agent_agrilife;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import authenticate.LoginActivity;


public class Profile extends Fragment {

    Button signout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String currentUser;
    TextView fetchName,fetchEmail,fetchDob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        signout=view.findViewById(R.id.signOut);
        fetchName=view.findViewById(R.id.name);
        fetchEmail=view.findViewById(R.id.email);
        fetchDob=view.findViewById(R.id.dob);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser= firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();

        //firebaseFirestore.collection("AgentDetails").document(firebaseAuth.getCurrentUser().toString())

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        //Toast.makeText(getContext(), currentUser, Toast.LENGTH_SHORT).show();
        DocumentReference document=FirebaseFirestore.getInstance().collection("AgentDetails").document(currentUser);
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                fetchName.setText("Name: " + (value.getString("fullName")));
                fetchEmail.setText(("Email: " + value.getString("email")));
                fetchDob.setText("DOB: " + value.getString("dob"));
            }
        });
        return view;
    }
}