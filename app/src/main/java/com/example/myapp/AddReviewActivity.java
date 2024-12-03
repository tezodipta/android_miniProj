package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddReviewActivity extends AppCompatActivity {

    private EditText editTextComment, editTextRating;
    private Button buttonSubmitReview;
    private FirebaseFirestore db;
    private String officialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        editTextComment = findViewById(R.id.editTextComment);
        editTextRating = findViewById(R.id.editTextRating);
        buttonSubmitReview = findViewById(R.id.buttonSubmitReview);

        db = FirebaseFirestore.getInstance();

        // Get the officialId from the intent
        officialId = getIntent().getStringExtra("officialId");

        buttonSubmitReview.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        String comment = editTextComment.getText().toString().trim();
        String ratingStr = editTextRating.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(comment)) {
            editTextComment.setError("Comment is required");
            return;
        }
        if (TextUtils.isEmpty(ratingStr)) {
            editTextRating.setError("Rating is required");
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingStr);
            if (rating < 0 || rating > 10) {
                editTextRating.setError("Rating must be between 0 and 10");
                return;
            }
        } catch (NumberFormatException e) {
            editTextRating.setError("Invalid rating format");
            return;
        }

        // Prepare data to save
        Map<String, Object> review = new HashMap<>();
        review.put("comment", comment);
        review.put("rating", rating);

        if (officialId == null) {
            Toast.makeText(this, "No official selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to Firebase
        db.collection("reviews") // Access the top-level "reviews" collection
                .document(officialId) // Navigate to the specific officialId document
                .collection("reviews") // Access the sub-collection for reviews
                .add(review) // Add the review to the sub-collection
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddReviewActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity and return to the previous one
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddReviewActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", e.getMessage());
                });
    }
}
