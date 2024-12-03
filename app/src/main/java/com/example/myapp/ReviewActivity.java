package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReviews;
    private ReviewAdapter reviewAdapter;
    private FirebaseFirestore db;
    private ArrayList<Review> reviewList;
    private Button buttonAddReview;
    private String officialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        buttonAddReview = findViewById(R.id.buttonAddReview);

        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList);
        recyclerViewReviews.setAdapter(reviewAdapter);

        db = FirebaseFirestore.getInstance();

        // Get the officialId from the intent
        officialId = getIntent().getStringExtra("officialId");

        fetchReviewsFromFirebase();

        buttonAddReview.setOnClickListener(v -> {
            // Navigate to AddReviewActivity
            Intent intent = new Intent(ReviewActivity.this, AddReviewActivity.class);
            intent.putExtra("officialId", officialId);
            startActivity(intent);
        });
    }

    private void fetchReviewsFromFirebase() {
        if (officialId == null) {
            Toast.makeText(this, "No official selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Access the "reviews" collection using the correct path
        db.collection("reviews")
                .document(officialId)  // Using officialId to access the document
                .collection("reviews") // Accessing the sub-collection inside the document
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reviewList.clear(); // Clear the list to avoid duplication
                        for (DocumentSnapshot document : task.getResult()) {
                            // Extract data from each document
                            String comment = document.getString("comment");
                            Long ratingLong = document.getLong("rating"); // Get rating as Long
                            int rating = (ratingLong != null) ? ratingLong.intValue() : 0;

                            reviewList.add(new Review(comment, rating));
                        }
                        reviewAdapter.notifyDataSetChanged(); // Notify the adapter about the data change
                    } else {
                        Toast.makeText(this, "Failed to load reviews", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", task.getException().getMessage());
                    }
                });
    }
}
