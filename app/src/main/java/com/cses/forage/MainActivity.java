package com.cses.forage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.usebutton.sdk.ButtonContext;
import com.usebutton.sdk.ButtonDropin;
import com.usebutton.sdk.context.Location;
import com.usebutton.sdk.util.LocationProvider;

public class MainActivity extends AppCompatActivity {

    private ButtonDropin mDropin;

    private MaterialListView mListView;

    private int[] imageRes = {R.drawable.food, R.drawable.food2, R.drawable.food3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.usebutton.sdk.Button.getButton(this).start();

        mDropin = (ButtonDropin) findViewById(R.id.deliveryButton);

//        showButton();

        mListView = (MaterialListView) findViewById(R.id.material_listview);

        for (int i = 0; i < imageRes.length; i++) {
            createCard("Joel's Pub", "Delivery Time: 45 minutes", imageRes[i]);
        }

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Intent transitionToOrder = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(transitionToOrder);
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {

            }
        });
    }

    private void showButton() {

        final ButtonContext context = new ButtonContext();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final android.location.Location loc = new LocationProvider(this).getBestLocation();

//        if (loc != null) {
//            final Location location = new Location(loc);
//            context.setUserLocation(location);
//        } else {

            final Location location = new Location("Here", 37.563724, -122.325342);
            context.setUserLocation(location);
//        }

        mDropin.prepareForDisplay(context);
    }

    private void createCard(String title, String description, int drawable) {
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
