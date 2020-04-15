package com.fahru.ticketingbabay.object;

public class ItemModel1 {

    private String data_id;
    private String name;

    public ItemModel1(){}

    public ItemModel1(String data_id, String name) {
        this.data_id = data_id;
        this.name = name;
    }

    public String getDataId() {
        return data_id;
    }

    public String getName() {
        return name;
    }
}
