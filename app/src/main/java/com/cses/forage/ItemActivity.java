package com.cses.forage;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;
import com.usebutton.sdk.ButtonContext;
import com.usebutton.sdk.ButtonDropin;
import com.usebutton.sdk.context.Location;

public class ItemActivity extends AppCompatActivity {

    private ButtonDropin mDropin;

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
                MaterialDialog dialog = new MaterialDialog.Builder(ItemActivity.this)
                        .title("Success!")
                        .customView(R.layout.dialog, true)
                        .positiveText("OK")
                        .build();

                mDropin = (ButtonDropin) dialog.getCustomView().findViewById(R.id.deliveryButton);
                setupButton();
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent acceptOrder = new Intent(ItemActivity.this, MainActivity.class);
                        startActivity(acceptOrder);
                    }
                });
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

    private void setupButton() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // Setup your context
        final Location location = new Location("Here", 37.563724, -122.325342);
        final ButtonContext context = ButtonContext.withSubjectLocation(location);

        // Prepare the Button for display with our context
        mDropin.prepareForDisplay(context);
    }
}
