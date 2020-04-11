package com.fahru.ticketingbabay.DB;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public abstract class BaseModel extends AppCompatActivity {

    public BaseModel(){};

    protected final String TBL_LOCATION = "location";
    protected final String TBL_DEPARTMENT = "department";
    protected final String TBL_CAT = "category";
    protected final String TBL_SUB = "sub_category";
    protected final String TBL_ORDER_BY = "order_by";
    protected final String TBL_IT_SUPPORT = "it_support";
    protected final String TBL_STATUS= "status";

    protected final String[] PRIORITY = {"Normal", "High", "Urgent"};
    protected final String[] STATUS = {"Open", "In Progress", "Waiting Response", "Closed" };
}
