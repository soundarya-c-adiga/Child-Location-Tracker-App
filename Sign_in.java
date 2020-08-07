package com.example.servce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_in extends AppCompatActivity {
    DbManager db;
    EditText u1,p1;
    Button si;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView signUp_text = findViewById(R.id.signUp_text);
        u1=findViewById(R.id.username);
        p1=findViewById(R.id.password);
        si=findViewById(R.id.signin);


        db=new DbManager(this);

        signUp_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sign_in.this, Sign_up.class));

                finish();
            }
        });
    }
    public void startdbapp(View view)
    {

        startActivity(new Intent(this,Sign_up.class));


    }

    public void signin(View view) {
        boolean a;
       a= db.signin(u1.getText().toString(),p1.getText().toString());
       if(a==false)
       {
           Toast.makeText(this,"INVALID CREDENTIALS",Toast.LENGTH_LONG).show();
       }
       else
       {
           startActivity(new Intent(Sign_in.this,SearchLoc.class));
           finish();
       }

    }
}
