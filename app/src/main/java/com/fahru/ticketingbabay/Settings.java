package com.fahru.ticketingbabay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fahru.ticketingbabay.menu.SettingDataModel1;
import com.fahru.ticketingbabay.menu.SettingDataModel2;
import com.google.android.material.button.MaterialButton;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    MaterialButton locBtn, depBtn, catBtn, subCatBtn, ordBtn, itSupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        locBtn = findViewById(R.id.settingLocation);
        depBtn = findViewById(R.id.settingDepartment);
        catBtn = findViewById(R.id.settingCategory);
        subCatBtn = findViewById(R.id.settingSubCategory);
        ordBtn = findViewById(R.id.settingOrder);
        itSupBtn = findViewById(R.id.settingItSupport);

        locBtn.setOnClickListener(this);
        depBtn.setOnClickListener(this);
        catBtn.setOnClickListener(this);
        subCatBtn.setOnClickListener(this);
        ordBtn.setOnClickListener(this);
        itSupBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(new Intent(this, SettingDataModel1.class));
        switch (view.getId()){
            case R.id.settingLocation:
                intent.putExtra("key", "loc");
                startActivity(intent);
                break;
            case R.id.settingDepartment:
                intent.putExtra("key", "dep");
                startActivity(intent);
                break;
            case R.id.settingCategory:
                intent.putExtra("key", "cat");
                startActivity(intent);
                break;
            case R.id.settingSubCategory:
                startActivity(new Intent(this, SettingDataModel2.class));
                break;
            case R.id.settingOrder:
                intent.putExtra("key", "ord");
                startActivity(intent);
                break;
            case R.id.settingItSupport:
                intent.putExtra("key", "its");
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Button not found", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
