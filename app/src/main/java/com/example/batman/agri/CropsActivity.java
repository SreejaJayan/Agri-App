package com.example.batman.agri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CropsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        TextView textView = (TextView) findViewById(R.id.title_text_view);
        textView.setText("Major Crops In India");

        dbOpenHelper dbHelper = new dbOpenHelper(this);

        final ArrayList<String> crop_names = dbHelper.getCropName();

        ListView listView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> crops_name_list = new ArrayAdapter<String>(this,R.layout.list_text,R.id.list_text_view
                ,crop_names);

        listView.setAdapter(crops_name_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_crop_name = crop_names.get(i);

                Intent intent = new Intent(CropsActivity.this,CropDetailsActivity.class);

                intent.putExtra("selected_crop_name", selected_crop_name);
                startActivity(intent);


            }
        });
    }

}
