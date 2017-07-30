package com.cses.forage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usebutton.sdk.ButtonDropin;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Vendor> vendorArrayList = new ArrayList<Vendor>();

    private MaterialListView mListView;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private int[] imageRes = {R.drawable.food, R.drawable.food2, R.drawable.food3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.usebutton.sdk.Button.getButton(this).start();

        mListView = (MaterialListView) findViewById(R.id.material_listview);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DataSnapshot vendors = dataSnapshot.child("Vendors");
                int i = 0;
                for( DataSnapshot places : vendors.getChildren() ) {
                    String name = places.child("name").getValue(String.class);
                    String location = places.child("location").getValue(String.class);
                    DataSnapshot items = places.child("items");
                    ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
                    for (DataSnapshot menuItems : items.getChildren()) {
                        Number test = (Number) menuItems.child("price").getValue();
                        double price = test.doubleValue();
                        MenuItem toAdd = new MenuItem(menuItems.child("name").getValue(String.class),
                                price);
                        menu.add(toAdd);
                    }
                    createCard(name, location, menu, imageRes[i]);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //for (int i = 0; i < imageRes.length; i++) {
          //  createCard("Joel's Pub", "Delivery Time: 45 minutes", imageRes[i]);
        //}

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Intent transitionToOrder = new Intent(MainActivity.this, OrderActivity.class);
                CardProvider provider = card.getProvider();

                Vendor vendor = vendorArrayList.get(position);
                ArrayList<MenuItem> items = vendor.menuItems;

                Bundle extras = new Bundle();
                extras.putInt("image", imageRes[position]);
                extras.putString("storeName", provider.getTitle());
                extras.putInt("lengthOfItems", items.size());
                for (int i = 0; i < items.size(); i++) {
                    MenuItem item = items.get(i);
                    extras.putString("name"+i, item.name);
                    extras.putDouble("price"+i, item.price);
                }
                transitionToOrder.putExtras(extras);

                startActivity(transitionToOrder);
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {

            }
        });
    }

    private void createCard(String title, String description, ArrayList<MenuItem> menu,
                            int drawable) {
        Vendor newPlace = new Vendor(title, description, menu);
        vendorArrayList.add(newPlace);
        Card card = new Card.Builder(this)
                .setTag("Restaurant")
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_list_custom_restaurant)
                .setTitle(title)
                .setTitleColor(getResources().getColor(R.color.btn_white))
                .setDescription(description)
                .setDescriptionColor(getResources().getColor(R.color.black_button))
                .setDrawable(drawable)
                .endConfig()
                .build();
        mListView.getAdapter().add(card);
    }
}
