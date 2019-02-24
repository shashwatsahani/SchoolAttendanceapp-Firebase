package com.example.ragnar.walli;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    ListView listView;
   ActionBar actionBar;

    DatabaseReference dref, lref, writeref, zref;
    Spinner spinner;
    int z = 0;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adp;
    ArrayList<String> items = new ArrayList<>();
    Button but;
    Button bt;
    Button punch;
    TextView txt;
    String it;
    ArrayList<String> state;
    private HashMap<String, String> hashMap;
    TextView date;
    String dub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // adapter.notifyDataSetChanged();
        txt = (TextView) findViewById(R.id.txt);
        bt = (Button) findViewById(R.id.bt);
        date=(TextView)findViewById(R.id.date);
        final DateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-yyyy");
        final Calendar cali = Calendar.getInstance();
        date.setText(dateFormat.format(cali.getTime())+"");

        getSupportActionBar().setTitle(Html.fromHtml("<font color='ffffff'>Mark Attendance</font>"));
        ActionBar   actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);



        state = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.ListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.rowlayout, R.id.txt_lan, list);
        listView.setAdapter(adapter);
        lref = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference();
        punch = (Button) findViewById(R.id.Punch);
        hashMap = new HashMap<>();
        if(list.isEmpty())
        {bt.setEnabled(false);punch.setEnabled(false);}
        else
        {  bt.setEnabled(true);punch.setEnabled(true);}



        lref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // for (DataSnapshot student: dataSnapshot.getChildren()) {
                String value = dataSnapshot.getKey();
                if(!value.equals("Announcement")&&!value.equals("Registration Number"))
                items.add(value);
                //}
                adp = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, items);
                adp.notifyDataSetChanged();
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        it = parent.getItemAtPosition(position).toString();





                        makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();

                        list.clear();


                        for (int i = 0; i < listView.getCount(); i++) {
                            listView.setItemChecked(i, false);
                        }
                        txt.setText("0");
                        final int[] ar=new int[]{1};

                        dref.child(it).addChildEventListener(new ChildEventListener() {
                            @Override

                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar cal = Calendar.getInstance();
                                //date.setText(dateFormat.format(cali.getTime())+"");
                                z = 0;
                               // String value = dataSnapshot.getValue(String.class);

                                String key = dataSnapshot.getKey();


                                if(dataSnapshot.hasChild(dateFormat.format(cal.getTime())+"")&&ar[0]==1)
                                {Toast.makeText(MainActivity.this,"Already Marked Attendance for today",Toast.LENGTH_SHORT).show();ar[0]=-1;
                                    Snackbar.make(findViewById(android.R.id.content), "Already Marked", Snackbar.LENGTH_SHORT).show();}
                                list.add(key);
                                adapter.notifyDataSetChanged();
                                bt.setEnabled(true);punch.setEnabled(true);
                                bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        adapter.notifyDataSetChanged();
                                        z = listView.getCheckedItemCount();
                                        txt.setText(z + "");

                                    }
                                });

                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
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

        writeref = FirebaseDatabase.getInstance().getReference();
        zref = FirebaseDatabase.getInstance().getReference();

String k;

        punch.setOnClickListener(new View.OnClickListener() {
            String value, m;int jj=1;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Want to mark Attendance").
                        setMessage("Mark or Not").
                        setCancelable(true).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //reset database
                                for (int i = 0; i < listView.getCount(); i++) {
                                    if (listView.isItemChecked(i)) {
                                        m = listView.getItemAtPosition(i).toString();

                                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        Calendar cal = Calendar.getInstance();
                                        DateFormat day=new SimpleDateFormat("EEE");
                                   zref.child(it).child(m).child(dateFormat.format(cal.getTime())).setValue("P");



                                    } else {
                                        String m = listView.getItemAtPosition(i).toString();
                                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        Calendar cal = Calendar.getInstance();
                                        DateFormat day=new SimpleDateFormat("EEE");
                                        zref.child(it).child(m).child(dateFormat.format(cal.getTime())).setValue("A");

                                   //     writeref.child(it).child(m).setValue(hashMap.get(m) + "N");
                                    }

                                }

                                Toast.makeText(MainActivity.this, "Marked", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getBaseContext(),Front.class));
                                finish();
                                finishAffinity();
                                // startActivity(new Intent(MainActivity.this,Front.class));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dlg, int which)
                    {
                        dlg.cancel();

                        makeText(MainActivity.this, "Not Marked", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog al = dialog.create();
                al.show();

                //Toast.makeText(MainActivity.this, " Marked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this,Front.class));

            }
        });


    }



}
