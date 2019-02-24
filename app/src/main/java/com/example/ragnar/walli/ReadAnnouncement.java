package com.example.ragnar.walli;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ragnar on 30/11/17.
 */

public class ReadAnnouncement extends AppCompatActivity {
    ListView listView;
    DatabaseReference dref, lref,zref;
    Spinner spinner;
   TextView textView,desc;
    TextView orig;

    ArrayAdapter<String> adp;
    ArrayList<String> items = new ArrayList<>();

    String it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readannouncement);
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Check Adverts</font>"));
        actionBar.setBackgroundDrawable(colorDrawable);
        textView=(TextView)findViewById(R.id.Itemname);
        orig=(TextView)findViewById(R.id.orig);
        desc=(TextView)findViewById(R.id.description);
        spinner = (Spinner) findViewById(R.id.spinner);

        lref = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference();
        zref=FirebaseDatabase.getInstance().getReference();
        zref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Announcement"))
                {
                    lref.child("Announcement").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            // for (DataSnapshot student: dataSnapshot.getChildren()) {
                            String value = dataSnapshot.getKey();
                            //if (value.equals("Announcement"))
                            items.add(value);
                            //}

                            adp = new ArrayAdapter<String>(ReadAnnouncement.this, android.R.layout.simple_spinner_item, items);
                            adp.notifyDataSetChanged();
                            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adp);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override

                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    it = parent.getItemAtPosition(position).toString();
                                    Toast.makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();
                                    dref.child("Announcement").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            desc.setText(it);
                                            String value=dataSnapshot.child(it).getValue(String.class);
                                            orig.setText(value);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        /*dref.child("Announcement").addChildEventListener(new ChildEventListener() {
                            @Override

                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                               // Log.d("Value",dataSnapshot.child(it).getValue(String.class));
                                desc.setText(it);
                                String value=dataSnapshot.child(it).getValue(String.class);
                               orig.setText(value);


                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                              //  String value = dataSnapshot.getValue(String.class);


                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

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
                else
                {
                    Toast.makeText(ReadAnnouncement.this, "No announcements", Toast.LENGTH_SHORT).show();
                }
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