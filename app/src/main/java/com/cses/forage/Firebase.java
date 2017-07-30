package com.cses.forage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import static android.content.ContentValues.TAG;

/**
 * Created by Megan on 7/29/17.
 */

public class Firebase {
    // Write a message to the database
    public DatabaseReference mDatabase;

    public Firebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void writeString() {
        // Write a message to the database
        mDatabase.child("Test").child("Test").setValue("Test");
    }

    void readString() {
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
