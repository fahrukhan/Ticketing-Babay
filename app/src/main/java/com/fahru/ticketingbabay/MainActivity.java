package com.fahru.ticketingbabay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fahru.ticketingbabay.DB.BaseModel;
import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.menu.CreateNewTicket;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends BaseModel implements View.OnClickListener {
    ImageView settings;
    MaterialButton createTicket;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
        settings = findViewById(R.id.mainSettingBtn);
        createTicket = findViewById(R.id.mainCreateTicketBtn);

        settings.setOnClickListener(this);
        createTicket.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainSettingBtn:
                startActivity(new Intent(MainActivity.this, Settings.class));
                break;
            case R.id.mainCreateTicketBtn:
                startActivity(new Intent(MainActivity.this, CreateNewTicket.class));
                break;
        }
    }
}
