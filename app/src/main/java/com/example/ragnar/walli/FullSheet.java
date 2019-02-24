package com.example.ragnar.walli;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ragnar on 16/12/17.
 */

public class FullSheet extends AppCompatActivity {
    EditText search;
    ListView listView;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference dref;
    String section,name;
    String[] myStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullsheet);
        search=(EditText)findViewById(R.id.editText5);
        listView=(ListView)findViewById(R.id.list);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Check Full list</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        adapter=new ArrayAdapter<String>(FullSheet.this,R.layout.myrow,R.id.Itemname,list);
        listView.setAdapter(adapter);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            myStrings = iin.getStringArrayExtra("key");


        }
        name=myStrings[0];
        section=myStrings[1];

        dref= FirebaseDatabase.getInstance().getReference();
        dref.child(section).child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Calendar cal = Calendar.getInstance();
                final DateFormat day=new SimpleDateFormat("EEE");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {    if(dataSnapshot1.getValue().toString().equals("P"))
                {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = format.parse(dataSnapshot1.getKey());
                        list.add("Present on "+dataSnapshot1.getKey()+" "+day.format(date.getTime()));
                     //   System.out.println(date);
                    } catch (ParseException e) {
                       // e.printStackTrace();
                        Toast.makeText(FullSheet.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }

                }
                    else if(dataSnapshot1.getValue().toString().equals("A"))
                {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = format.parse(dataSnapshot1.getKey());
                        list.add("Absent on  "+dataSnapshot1.getKey()+" "+day.format(date.getTime()));
                        //   System.out.println(date);
                    } catch (ParseException e) {
                        // e.printStackTrace();
                        Toast.makeText(FullSheet.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                   // list.add("Absent on  "+dataSnapshot1.getKey()+" "+day.format(cal.getTime()));
                }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 FullSheet.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
