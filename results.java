package com.example.servce;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class results extends AppCompatActivity {
    String getting_date;
    Cursor cursor;
    DbManager dbManager;
    TextView date;
    ListView listView;
    ArrayList<String> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        dbManager=new DbManager(this);
        date=findViewById(R.id.date);
        listView=findViewById(R.id.listview);
        results=new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, int i, long l) {
                String text=results.get(i);
                String[] seperated=text.split("latlng:");
                String latlmg=seperated[1];
                String urlAddress = "http://maps.google.com/maps?q="+ latlmg +"("+ "Location" + ")&iwloc=A&hl=es";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                startActivity(intent);
            }
        });


date.setText(getting_date);
        getting_date=getIntent().getStringExtra("date");
        cursor=dbManager.search_loc(getting_date);
        Log.d("size_of_cursor",String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()){
            Log.d("location_from_db",cursor.getString(cursor.getColumnIndex("ADDRESS")));
            String time,address,latlng;
            time=cursor.getString(cursor.getColumnIndex("TIME"));
            address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            latlng=cursor.getString(cursor.getColumnIndex("COORDINATES"));
            results.add(time+"\n"+address+"\n"+"latlng:"+latlng);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.result_view,results);
        listView.setAdapter(adapter);


    }
}
