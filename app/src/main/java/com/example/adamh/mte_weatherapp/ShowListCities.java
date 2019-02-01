package com.example.adamh.mte_weatherapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

    public class ShowListCities extends AppCompatActivity {

        DatabaseHelper db;
        private static final String TAG = "ShowListCities";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_city);

            ListView listView = (ListView) findViewById(R.id.listCity);
            db = new DatabaseHelper(this);

            ArrayList<String> theList = new ArrayList<>();
            Cursor data = db.getData();
            if (data.getCount() == 0) {
                Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
            } else {
                while (data.moveToNext()) {
                    theList.add(data.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                    listView.setAdapter(listAdapter);
                }
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString();
                    Log.d(TAG, "onItemClick: You Clicked on " + name);

                    Cursor data = db.getItemID(name); //get the id associated with that name
                    int itemID = -1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                    if (itemID > -1) {
                        Log.d(TAG, "onItemClick: The ID is: " + itemID);
                        Intent editScreenIntent = new Intent(ShowListCities.this, MainActivity.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("name", name);
                        startActivity(editScreenIntent);
                    } else {
                        toastMessage("No ID associated with that name");
                    }
                }
            });
        }

        private void toastMessage(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

