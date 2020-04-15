package com.fahru.ticketingbabay.menu;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.fahru.ticketingbabay.model.BaseModel;
import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.R;
import com.google.android.material.textfield.TextInputEditText;
import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import java.util.ArrayList;

public class CreateNewTicket extends BaseModel implements OnSpinnerItemSelectedListener {
    TextInputEditText ipAdd;
    NiceSpinner locSpin, deptSpin, catSpin, subCatSpin, orderBySpin, priorSpin, assignSpin, statSpin;
    ArrayList<String> locArr, deptArr, catArr, subCatArr, ordArr, prioArr, assignArr, statArr,
    catIdArr;
    DBHandler db;
    String CAT_SELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ticket);
        db = new DBHandler(this);
        initialize(this);
        populateDataForSpinner();
        addFilterToIPAddressInput();
    }

    private void addFilterToIPAddressInput() {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
        ipAdd.setFilters(filters);
    }

    private void initialize(Context context) {
        ipAdd = findViewById(R.id.createNewTicketIPAddress);
        //spinner
        locSpin = findViewById(R.id.createNewTicketLocation);
        deptSpin = findViewById(R.id.createNewTicketDepartment);
        catSpin = findViewById(R.id.createNewTicketCategory);
        subCatSpin = findViewById(R.id.createNewTicketSubCategory);
        orderBySpin = findViewById(R.id.createNewTicketOrderBy);
        priorSpin = findViewById(R.id.createNewTicketPriority);
        assignSpin = findViewById(R.id.createNewTicketAssignTo);
        statSpin = findViewById(R.id.createNewTicketStatus);
        locArr = new ArrayList<>();
        deptArr = new ArrayList<>();
        catArr = new ArrayList<>();
        catIdArr = new ArrayList<>();
        subCatArr = new ArrayList<>();
        ordArr = new ArrayList<>();
        prioArr = new ArrayList<>();
        assignArr = new ArrayList<>();
        statArr = new ArrayList<>();
    }

    private void populateDataForSpinner() {
        //location spinner
        locArr.add("Pilih");
        locArr.addAll(db.getRecordInOneColumnByField("name", TBL_LOCATION, "name"));
        ArrayAdapter<String> locAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, locArr);
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locSpin.setAdapter(locAdapter);
        locSpin.setOnSpinnerItemSelectedListener(this);
        //Department spinner
        deptArr.add("Pilih");
        deptArr.addAll(db.getRecordInOneColumnByField("name", TBL_DEPARTMENT, "name"));
        ArrayAdapter<String> depAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, deptArr);
        depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptSpin.setAdapter(depAdapter);
        deptSpin.setOnSpinnerItemSelectedListener(this);
        //Category spinner
        catIdArr.addAll(db.getRecordInOneColumnByField("id", TBL_CAT, "name"));
        catArr.addAll(db.getRecordInOneColumnByField("name", TBL_CAT, "name"));
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, catArr);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpin.setAdapter(catAdapter);
        catSpin.setOnSpinnerItemSelectedListener(this);

        //Order By
        ordArr.addAll(db.getAllRecordInOneColumn("SELECT name FROM " + TBL_ORDER_BY + " ORDER BY name"));
        ArrayAdapter<String> ordAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, ordArr);
        ordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderBySpin.setAdapter(ordAdapter);
        orderBySpin.setOnSpinnerItemSelectedListener(this);
        //Priority By
//      prioArr.addAll(PRIORITY);
        ArrayAdapter<String> priAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, PRIORITY);
        priAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorSpin.setAdapter(priAdapter);
        priorSpin.setOnSpinnerItemSelectedListener(this);
        //Assign By
        assignArr.addAll(db.getAllRecordInOneColumn("SELECT name FROM " + TBL_IT_SUPPORT + " ORDER BY name"));
        ArrayAdapter<String> assignAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, assignArr);
        assignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignSpin.setAdapter(assignAdapter);
        assignSpin.setOnSpinnerItemSelectedListener(this);
        //Status
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, STATUS);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statSpin.setAdapter(statusAdapter);
        statSpin.setOnSpinnerItemSelectedListener(this);
    }

    private void setSubCategory(String fk){
        if (subCatArr.size() > 0){
            subCatArr.clear();
        }
        //Sub Category spinner
        subCatArr.addAll(db.getAllRecordInOneColumn("SELECT name FROM "+TBL_SUB+" WHERE fk = "+fk+" ORDER BY name"));
        ArrayAdapter<String> subCatAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, subCatArr);
        subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCatSpin.setAdapter(subCatAdapter);
        subCatSpin.setOnSpinnerItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.createNewTicketLocation:
                Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                break;
            case R.id.createNewTicketDepartment:
                Toast.makeText(this, "depxart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.createNewTicketCategory:
                CAT_SELECTED =catIdArr.get(position);
                setSubCategory(CAT_SELECTED);
                break;
        }
    }
}
