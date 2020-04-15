package com.fahru.ticketingbabay.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.R;
import com.fahru.ticketingbabay.model.BaseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Map;

public class AddSubCategory extends BaseModel implements OnSpinnerItemSelectedListener {
    NiceSpinner catSpin;
    ArrayList<String> catArr, subCatArr, catIdArr;
    DBHandler db;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);

        db = new DBHandler(getApplicationContext());
        catSpin = findViewById(R.id.inputModel2Cat);

        catArr = new ArrayList<>();
        subCatArr = new ArrayList<>();
        catIdArr = new ArrayList<>();

        populateDataForSpinner();

    }

    private void populateDataForSpinner() {
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
        //Sub Category spinner
    }

    @Override
    public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
        Toast.makeText(this, catIdArr.get(position), Toast.LENGTH_SHORT).show();
    }
}
