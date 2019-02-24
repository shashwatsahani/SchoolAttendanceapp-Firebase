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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ragnar on 3/12/17.
 */

public class Registrationandhouse extends AppCompatActivity {
    ListView listView;
    DatabaseReference dref,lref;
    Spinner spinner;
    //int z=0;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adp;
    ArrayList<String> items=new ArrayList<>();
    //Button but;
    //Button bt;
    //TextView txt;
    String it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationandhouse);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Check Details</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        //  txt=(TextView)findViewById(R.id.txt);
        // bt=(Button)findViewById(R.id.bt);
        spinner=(Spinner)findViewById(R.id.spinner);
        listView=(ListView)findViewById(R.id.ListView);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter=new ArrayAdapter<String>(Registrationandhouse.this,R.layout.myrow,R.id.Itemname,list);
        listView.setAdapter(adapter);
        lref= FirebaseDatabase.getInstance().getReference();
        dref= FirebaseDatabase.getInstance().getReference();


        lref.child("Registration Number").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // for (DataSnapshot student: dataSnapshot.getChildren()) {
                String value=dataSnapshot.getKey();
                items.add(value);
                //}
                adp = new ArrayAdapter<String>(Registrationandhouse.this, android.R.layout.simple_spinner_item, items);
                adp.notifyDataSetChanged();
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        it=parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();

                        list.clear();


                      /*  for (int i = 0; i < listView.getCount(); i++) {
                            listView.setItemChecked(i,false);
                        }*/
                        //txt.setText("0");


                        dref.child("Registration Number").child(it).addChildEventListener(new ChildEventListener() {
                            @Override

                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                //      z=0;
                                String value=dataSnapshot.getValue(String.class);

                                String key = dataSnapshot.getKey();


                                //list.add(key+": "+value);
                                list.add(key+" \n " +value);
                                adapter.notifyDataSetChanged();
                            /*    bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adapter.notifyDataSetChanged();
                                        z=listView.getCheckedItemCount();
                                        txt.setText(z+"");

                                    }
                                });*/
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                String value=dataSnapshot.getValue(String.class);
                                list.remove(value);
                                adapter.notifyDataSetChanged();
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

    @Override
    protected void onStop() {
        super.onStop();
    }
}
