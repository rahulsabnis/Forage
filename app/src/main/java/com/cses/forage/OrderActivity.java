package com.cses.forage;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.view.MaterialListView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("storeName"));
        setSupportActionBar(toolbar);

        MaterialListView listView = (MaterialListView) findViewById(R.id.listView);

        Card card = new Card.Builder(this)
                .setTag("Item")
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_list_custom_item)
                .setTitle("Chocolate Cake")
                .setDescription("Made fresh daily with real chocolate")
                .setDrawable(getDrawable(R.drawable.cake))
                .endConfig()
                .build();

        listView.getAdapter().add(card);

        AppBarLayout layout = (AppBarLayout) findViewById(R.id.app_bar);
        layout.setBackgroundResource(extras.getInt("image"));
    }
}
