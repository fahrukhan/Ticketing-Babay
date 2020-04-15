package com.fahru.ticketingbabay.menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fahru.ticketingbabay.model.BaseModel;
import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.R;
import com.fahru.ticketingbabay.adapter.LocationAdapter;
import com.fahru.ticketingbabay.object.ItemModel1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Map;

public class SettingDataModel1 extends BaseModel {
    String key, currentTable;
    TextView label;
    DBHandler db;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ItemModel1> model;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_data_1);

        label = findViewById(R.id.settingNameLabel);
        db = new DBHandler(getApplicationContext());
        recyclerView = findViewById(R.id.settingDataRV);
        model = new ArrayList<>();
        fab = findViewById(R.id.settingDataFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder inputDialog = new AlertDialog.Builder(view.getContext());
                View inputView = LayoutInflater.from(view.getContext()).inflate(R.layout.input_data_model, null);
                final TextInputEditText inputName = inputView.findViewById(R.id.inputMode1Name);
                inputDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.insertRecordModel1(currentTable, inputName.getText().toString());
                        Toast.makeText(SettingDataModel1.this, "DATA ADDED", Toast.LENGTH_SHORT).show();
                        setData();
                    }
                });
                inputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                inputDialog.setView(inputView);
                inputDialog.show();
            }
        });
        key = getIntent().getStringExtra("key");
        if (key != null && !key.isEmpty()){
            loadData(key);
        }

    }

    private void loadData(String key) {
        switch (key){
            case "loc":
                label.setText("Location:");
                currentTable = TBL_LOCATION;
                break;
            case "dep":
                label.setText("Department:");
                currentTable = TBL_DEPARTMENT;
                break;
            case "cat":
                label.setText("Category:");
                currentTable = TBL_CAT;
                break;
            case "sub":
                break;
            case "ord":
                label.setText("Order By:");
                currentTable = TBL_ORDER_BY;
                break;
            case "its":
                label.setText("IT Support Team:");
                currentTable = TBL_IT_SUPPORT;
                break;
            default:
                Toast.makeText(this, "Setting key not found", Toast.LENGTH_SHORT).show();
        }

        setData();
    }

    private void setData() {
        model.clear();
        Map<Integer, String[]> data = db.getAllRecordModel1(currentTable);

        for (int i = 0; i < data.size(); i++) {
            String[] value = data.get(i);
            String data_id = value[0];
            String name = value[1];
            model.add(new ItemModel1(data_id, name));
            adapter = new LocationAdapter(model, currentTable);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(SettingDataModel1.this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        db.close();
    }

}
