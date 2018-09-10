package com.example.batman.agri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CropDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        Intent intent = getIntent();
        String selected_crop_name = intent.getStringExtra("selected_crop_name");

        TextView textView = (TextView) findViewById(R.id.title_text_view);
        textView.setText(selected_crop_name);

        dbOpenHelper dbHelper = new dbOpenHelper(this);

        ArrayList<DetailsSet> detail_set = dbHelper.getCropDetails( selected_crop_name);

        ListView listView = (ListView) findViewById(R.id.list);

        DetailsAdapter crops_details = new DetailsAdapter(this,detail_set);

        listView.setAdapter(crops_details);
    }
}
