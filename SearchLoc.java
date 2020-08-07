package com.example.servce;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class SearchLoc extends AppCompatActivity {
    SQLiteDatabase db;
    private int mYear, mMonth, mDay;
    Button date_picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_loc);
        date_picker = findViewById(R.id.t_2);

    }

//    public void msg(Context context, String str) {
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//    }


    public void start_track(View view) {
        Intent intent = new Intent(SearchLoc.this, MainActivity.class);
        startActivity(intent);
    }

    public void select_date(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String str_month, str_date;
                        str_month = String.valueOf(monthOfYear + 1);
                        if (str_month.length() == 1) {
                            str_month = "0" + str_month;
                        }

                        str_date = String.valueOf(dayOfMonth);
                        if (str_date.length() == 1) {
                            str_date = "0" + str_date;
                        }


                        date_picker.setText(year + "-" + str_month + "-" + str_date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onClick(View view) {
        Log.d("fafaf",date_picker.getText().toString());
        if (date_picker.getText().toString().equals("select date")) {
            Toast.makeText(this, "select date", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SearchLoc.this, results.class);
            intent.putExtra("date", date_picker.getText().toString());
            startActivity(intent);
        }
    }
}`