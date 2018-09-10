package com.example.batman.agri;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BATMAN on 23-06-2018.
 */

public class DetailsAdapter extends ArrayAdapter<DetailsSet> {


    public DetailsAdapter(@NonNull Context context, @NonNull ArrayList<DetailsSet> objects) {
        super(context, 0 , objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DetailsSet currentSet = getItem(position);

        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_text, parent, false);
        }

        TextView name_view = (TextView) listView.findViewById(R.id.column_name_view);
        name_view.setText(currentSet.getColumn_name());

        TextView content_view = (TextView) listView.findViewById(R.id.column_content_view);
        content_view.setText(currentSet.getColumn_details());

        return listView;

    }
}
