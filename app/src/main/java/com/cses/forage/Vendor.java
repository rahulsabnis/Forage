package com.cses.forage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

/**
 * Created by Megan on 7/29/17.
 */

public class Vendor {
    public String name;
    public String deliveryTime;
    public ArrayList<MenuItem> menuItems;

    public Vendor(String name, String deliveryTime, ArrayList<MenuItem> menuItems) {
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.menuItems = new ArrayList<MenuItem>(menuItems);
    }

}
