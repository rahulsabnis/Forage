package com.cses.forage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private int[] itemRes = {R.drawable.clam_chowder, R.drawable.apple_cider, R.drawable.cake, R.drawable.mocha};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("storeName"));
        setSupportActionBar(toolbar);

        MaterialListView listView = (MaterialListView) findViewById(R.id.listView);

        int length = extras.getInt("lengthOfItems");

        for (int i = 0; i < length; i++) {
            String itemName = extras.getString("name"+i);
            Double price = extras.getDouble("price"+i);

            Card card = new Card.Builder(this)
                    .setTag("Item")
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_list_custom_item)
                    .setTitle(itemName)
                    .setDescription(String.format("$%.2f", price))
                    .setDrawable(getDrawable(itemRes[i]))
                    .endConfig()
                    .build();

            listView.getAdapter().add(card);
        }

        listView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {

                Intent acceptOrder = new Intent(OrderActivity.this, ItemActivity.class);
                CardProvider provider = card.getProvider();

                Bundle extras = new Bundle();
                String description = provider.getDescription();
                extras.putInt("image", itemRes[position]);
                extras.putString("itemName", provider.getTitle());
                extras.putDouble("cost", Double.parseDouble(description.substring(1, description.length())));
                acceptOrder.putExtras(extras);

                startActivity(acceptOrder);

            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
            }
        });

        AppBarLayout layout = (AppBarLayout) findViewById(R.id.app_bar);
        layout.setBackgroundResource(extras.getInt("image"));
    }
}
