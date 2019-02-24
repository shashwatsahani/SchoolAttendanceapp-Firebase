package com.example.ragnar.walli;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ragnar on 22/12/17.
 */

public class Rectify extends AppCompatActivity {
    ListView listView;
    DatabaseReference dref,lref;
    Spinner spinner;
    //int z=0;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    ArrayList<String> list3=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adp;
    ArrayList<String> items=new ArrayList<>();
    String[] a;
    //Button but;
    //Button bt;
    //TextView txt;
    String it;
    static int mmm=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rectify);

        spinner=(Spinner)findViewById(R.id.spinner);
        listView=(ListView)findViewById(R.id.ListView);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String a = (String)parent.getItemAtPosition(position);
                Intent intent=new Intent(Rectify.this,Changedata.class);
                a=new String[]{list.get(position),it};
                intent.putExtra("key",a);
                startActivity(intent);

                Toast.makeText(Rectify.this,list.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        adapter=new ArrayAdapter<String>(Rectify.this,R.layout.myrow,R.id.Itemname,list);
        listView.setAdapter(adapter);
        lref= FirebaseDatabase.getInstance().getReference();
        dref= FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Rectify Attendance</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);

        lref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // for (DataSnapshot student: dataSnapshot.getChildren()) {
                String value=dataSnapshot.getKey();
                if(!value.equals("Announcement")&&!value.equals("Registration Number"))
                    items.add(value);
                //}
                adp = new ArrayAdapter<String>(Rectify.this, android.R.layout.simple_spinner_item, items);
                adp.notifyDataSetChanged();
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        it=parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(android.R.id.content),"Select Student to rectify attendance",Snackbar.LENGTH_SHORT).show();
                        list.clear();


                        lref.child(it).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                final String key=dataSnapshot.getKey().toString();
                               list.add(key);
                                adapter.notifyDataSetChanged();


                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
