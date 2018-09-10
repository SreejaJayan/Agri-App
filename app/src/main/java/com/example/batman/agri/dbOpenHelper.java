package com.example.batman.agri;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by BATMAN on 23-06-2018.
 */

public class dbOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "agriculture.db";
    private static final int DATABASE_VERSION = 1;

    public dbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<String> getCropName() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> crop_name_list = new ArrayList<String>();

        String query = "SELECT NAME FROM CROPS ORDER BY NAME";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                crop_name_list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return crop_name_list;
    }

    public ArrayList<DetailsSet> getCropDetails(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<DetailsSet> crop_details_list = new ArrayList<DetailsSet>();

        String query = "SELECT * FROM CROPS WHERE NAME = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);

        int i = 1;
        if (cursor.moveToFirst()) {
            while (i < cursor.getColumnCount()) {
                if (cursor.getType(i) == Cursor.FIELD_TYPE_STRING) {
                    if(cursor.getString(i) !="NULL")
                    crop_details_list.add(new DetailsSet(cursor.getColumnName(i), cursor.getString(i)));
                    i++;
                }
            }
        }
        return crop_details_list;
    }
}
