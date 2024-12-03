package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OfficialActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOfficials;
    private OfficialAdapter officialAdapter;
    private FirebaseFirestore db;
    private ArrayList<Official> officialsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        recyclerViewOfficials = findViewById(R.id.recyclerViewOfficials);
        recyclerViewOfficials.setLayoutManager(new LinearLayoutManager(this));
        officialsList = new ArrayList<>();
        officialAdapter = new OfficialAdapter(officialsList, official -> {
            // Handle click event - Go to Review Activity
            Intent intent = new Intent(OfficialActivity.this, ReviewActivity.class);
            intent.putExtra("officialId", official.getId());
            startActivity(intent);
        });
        recyclerViewOfficials.setAdapter(officialAdapter);

        db = FirebaseFirestore.getInstance();
        fetchOfficialsFromFirebase();
    }

    private void fetchOfficialsFromFirebase() {
        db.collection("officials")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        officialsList.clear();
                        QuerySnapshot documents = task.getResult();
                        for (DocumentSnapshot document : documents) {
                            String id = document.getId();
                            String name = document.getString("name");
                            String designation = document.getString("designation");
                            String dept = document.getString("dept");

                            officialsList.add(new Official(id, name, designation, dept));
                        }
                        officialAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", task.getException().getMessage());
                    }
                });
    }
}
