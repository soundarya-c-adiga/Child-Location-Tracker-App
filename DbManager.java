package com.example.servce;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DbManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="userinfo.db";
    public static final String TABLE_NAME="user_table";
    public static final String col_1="ID";
    public static final String col_2="USERNAME";
    public static final String col_3="EMAIL";
    public static final String col_4="MOBILE";
    public static final String col_5="PASSWORD";
    public static final String TABLE_LOCATION="Tracked_Location";

    public static final String clm_1="ID";

    public static final String clm_2="DATE";

    public static final String clm_3="TIME";

    public static final String clm_4="COORDINATES";

    public static final String clm_5="ADDRESSS";
    public Context Mcontext;







    public DbManager(Context context) {
        super(context, DATABASE_NAME, null,  1);
        Mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT,MOBILE INTEGER)");
       db.execSQL("create table "+TABLE_LOCATION+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE REAL,TIME REAL,COORDINATES REAL,ADDRESS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOCATION);
        onCreate(db);

    }
    public String addRecord(String p1,String p2,String p3,String p4)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("USERNAME",p1);
        cv.put("MOBILE",p2);
        cv.put("EMAIL",p3);
        cv.put("PASSWORD",p4);



        long res=db.insert("USER_TABLE",null,cv);
       if (res==-1)
           return "FAILED";
           else
               return "SUCCESSFULLY INSERTED";
    }

    public String update_location(String latitude,String longitude)
    {
        Geocoder geocoder;
        String  cityName = null,stateName = null,countryName;

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);


        cv.put("DATE",formattedDate);
        //time variable



 Calendar calendar=Calendar.getInstance();
 SimpleDateFormat  format= new SimpleDateFormat("hh:mm:ss");
 String time=format.format(calendar.getTime());

        cv.put("TIME",time );


        cv.put("COORDINATES",latitude+","+longitude);


        geocoder = new Geocoder(Mcontext, Locale.getDefault());

        List<Address> addresses = null;
        Double laat,lnng;
        laat= Double.valueOf(latitude);
        lnng= Double.valueOf(longitude);

        try {
            addresses = geocoder.getFromLocation(laat, lnng, 1);
            cityName = addresses.get(0).getAddressLine(0);
            stateName = addresses.get(0).getAddressLine(1);
            countryName = addresses.get(0).getAddressLine(2);





        } catch (IOException e1) {
            e1.printStackTrace();
        }
        cv.put("ADDRESS",cityName+"\n"+stateName);



        long res1=db.insert("Tracked_Location",null,cv);
        if (res1==-1)
            return "FAILED";
        else
            return "SUCCESSFULLY INSERTED";
    }
    public boolean signin(String USERNAME, String PASSWORD)
    {
        Cursor cursor=null;
        SQLiteDatabase db=this.getWritableDatabase();
        cursor=db.rawQuery("Select * from USER_TABLE WHERE USERNAME =? AND PASSWORD=?",new String[]{USERNAME,PASSWORD});


        int sizeofcr=cursor.getCount();
        if (sizeofcr<1)
        {
            return false;

        }
        else
        {
            return true;
        }

    }
    public Cursor search_loc(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=null;
        cursor=db.rawQuery("select * from Tracked_Location where date =date(?) ",new String[]{date});
        return cursor;

    }

}
