package com.cses.forage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("itemName"));
        setSupportActionBar(toolbar);

        AppCompatButton submit = (AppCompatButton) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ItemActivity.this)
                        .setTitle("Success!")
                        .setMessage("Your order was successfully placed!")
                        .setPositiveButton("OK", null)
                        .create();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent acceptOrder = new Intent(ItemActivity.this, MainActivity.class);
                        startActivity(acceptOrder);
                    }
                });
                dialog.show();
            }
        });

        final Double initCost = extras.getDouble("cost");
        final TextView view = (TextView) findViewById(R.id.total);
        view.setText(String.format("Total Cost: $%.2f", initCost));

        NumberPicker picker = (NumberPicker) findViewById(R.id.picker);
        picker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                Double updatedCost = initCost * value;
                view.setText(String.format("Total Cost: $%.2f", updatedCost));
            }
        });
        AppBarLayout layout = (AppBarLayout) findViewById(R.id.app_bar);
        layout.setBackgroundResource(extras.getInt("image"));
    }
}
