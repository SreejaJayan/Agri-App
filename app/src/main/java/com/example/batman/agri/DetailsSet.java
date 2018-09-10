package com.example.batman.agri;

/**
 * Created by BATMAN on 23-06-2018.
 */

public class DetailsSet {
    private String column_name ;
    private String column_details;

    DetailsSet(String name, String details){

        this.column_name = name;
        this.column_details = details;
    }

    public String getColumn_details() {
        return column_details;
    }

    public String getColumn_name() {
        return column_name;
    }
}
