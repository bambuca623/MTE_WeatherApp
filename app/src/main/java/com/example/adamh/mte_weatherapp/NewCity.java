package com.example.adamh.mte_weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCity extends AppCompatActivity {
    private static final String TAG = "NewCity";

    DatabaseHelper mDatabaseHelper;
    private EditText etCity;
    private Button btnSave;

    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_city);

        etCity = (EditText) findViewById(R.id.etCity);
        btnSave = (Button) findViewById(R.id.btnSave);
        mDatabaseHelper = new DatabaseHelper(this);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();

                if (etCity.length() != 0) {
                    AddData(city);
                    etCity.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }
                Intent intent = new Intent(NewCity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void AddData(String newCity) {
        boolean insertCity = mDatabaseHelper.addData(newCity);
        if (insertCity == true) {
            Toast.makeText(NewCity.this, "ACCESS", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(NewCity.this, "WRONG", Toast.LENGTH_LONG).show();
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
