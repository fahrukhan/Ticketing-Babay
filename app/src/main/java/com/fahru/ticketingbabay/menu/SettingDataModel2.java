package com.fahru.ticketingbabay.menu;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.R;
import com.fahru.ticketingbabay.adapter.LocationAdapter;
import com.fahru.ticketingbabay.model.BaseModel;
import com.fahru.ticketingbabay.object.ItemModel1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Map;

public class SettingDataModel2 extends BaseModel implements OnSpinnerItemSelectedListener {
    NiceSpinner catSpin;
    ArrayList<String> catArr, subCatArr, catIdArr;
    DBHandler db;
    FloatingActionButton fab;
    String catId;
    RecyclerView recyclerView, subRecyclerView;
    RecyclerView.Adapter adapter, subAdapter;
    RecyclerView.LayoutManager layoutManager, sublayoutManager;
    ArrayList<ItemModel1> model, subModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_data_2);

        db = new DBHandler(getApplicationContext());
        catSpin = findViewById(R.id.settingData2Cat);
        fab = findViewById(R.id.settingData2Fab);
        recyclerView = findViewById(R.id.settingData2RV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catSpin.getSelectedItem().toString().equals("Pilih")){
                    Toast.makeText(SettingDataModel2.this, "Please choose the Category", Toast.LENGTH_SHORT).show();
                }else {
                    addDialog(catSpin.getSelectedItem().toString(), view.getContext());
                }
            }
        });
        model = new ArrayList<>();
        catArr = new ArrayList<>();
        subCatArr = new ArrayList<>();
        catIdArr = new ArrayList<>();

        populateDataForSpinner();

    }

    private void addDialog(String cat, Context context){
        final AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
        View inputView = LayoutInflater.from(context).inflate(R.layout.input_data_model, null);
        final TextInputEditText inputName = inputView.findViewById(R.id.inputMode1Name);
        TextView title = inputView.findViewById(R.id.inputModel1label);
        title.setText(cat);
        inputDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.insertRecordModel2(TBL_SUB, inputName.getText().toString(), catId);
                Toast.makeText(SettingDataModel2.this, "DATA ADDED", Toast.LENGTH_SHORT).show();
                populateSubData();
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

    private void populateDataForSpinner() {
        catArr.add("Pilih");
        catIdArr.add("0");
        //Category spinner
        Map<Integer, String[]> data = db.getAllRecordModel1(TBL_CAT);

        for (int i = 0; i < data.size(); i++) {
            String[] value = data.get(i);
            String data_id = value[0];
            String name = value[1];
            catIdArr.add(data_id);
            catArr.add(name);
        }

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpin.setAdapter(catAdapter);
        catSpin.setOnSpinnerItemSelectedListener(this);
    }

    private void populateSubData() {
        Map<Integer, String[]> data = db.getAllRecordModel2WhereId(TBL_SUB, catId);
        if (data.size()>0){
            setData(data);
        }else {
            clearModel();
            Toast.makeText(this,"No Sub in this Category", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearModel(){
        if (model.size() > 0 ){
            model.clear();
        }
        if (layoutManager != null){
            layoutManager.scrollToPosition(model.size() - 1);
        }
    }

    private void setData(Map<Integer, String[]> data) {
        clearModel();
        for (int i = 0; i < data.size(); i++) {
            String[] value = data.get(i);
            String data_id = value[0];
            String name = value[1];
            model.add(new ItemModel1(data_id, name));
            adapter = new LocationAdapter(model, TBL_SUB);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(SettingDataModel2.this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        db.close();
    }

    @Override
    public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
        catId = catIdArr.get(position);
        populateSubData();
    }
}
