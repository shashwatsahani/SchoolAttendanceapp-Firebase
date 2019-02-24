package com.example.ragnar.walli;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class DeleteAnnouncement extends AppCompatActivity {

    DatabaseReference dref, lref;
    Spinner spinner;

    Button button;
    ArrayAdapter<String> adp;
    ArrayList<String> items = new ArrayList<>();

    String it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteannouncement);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Delete Announcement</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        spinner = (Spinner) findViewById(R.id.spinner);
        button=(Button)findViewById(R.id.button6);
        lref = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                if (!dataSnapshot.exists()) {
                                                                    Toast.makeText(getBaseContext(), "There are no announcements available RN", Toast.LENGTH_SHORT).show();
                                                                    button.setClickable(false);
                                                                } else
                                                                    items.add(value);

                                                                //}
                                                                adp = new ArrayAdapter<String>(DeleteAnnouncement.this, android.R.layout.simple_spinner_item, items);
                                                                adp.notifyDataSetChanged();
                                                                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                spinner.setAdapter(adp);
                                                                if (items.isEmpty()) {
                                                                    Toast.makeText(getBaseContext(), "There are no announcements available RN", Toast.LENGTH_SHORT).show();
                                                                    button.setClickable(false);
                                                                } else {
                                                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                                        @Override

                                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                            it = parent.getItemAtPosition(position).toString();
                                                                            if (it.equals("")) {
                                                                                Toast.makeText(parent.getContext(), "There are no announcements available RN", Toast.LENGTH_SHORT).show();
                                                                                button.setClickable(false);
                                                                            } else {
                                                                                Toast.makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();
                                                                                button.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        if (it.equals(""))
                                                                                            Toast.makeText(DeleteAnnouncement.this, "Deleted", Toast.LENGTH_LONG).show();
                                                                                        else {
                                                                                            dref.child("Announcement").child(it).removeValue();
                                                                                            Toast.makeText(DeleteAnnouncement.this, "Deleted", Toast.LENGTH_LONG).show();
                                                                                            startActivity(new Intent(getBaseContext(), Front.class));
                                                                                            finish();
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void onNothingSelected(AdapterView<?> parent) {

                                                                        }
                                                                    });
                                                                }
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
                                                        Toast.makeText(DeleteAnnouncement.this, "There are no announcements", Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

