package com.example.ragnar.walli;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Check extends AppCompatActivity {
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
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);
      //  txt=(TextView)findViewById(R.id.txt);
       // bt=(Button)findViewById(R.id.bt);
        spinner=(Spinner)findViewById(R.id.spinner);

        listView=(ListView)findViewById(R.id.ListView);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // String a = (String)parent.getItemAtPosition(position);
                Intent intent=new Intent(Check.this,FullSheet.class);
                a=new String[]{list2.get(position),it};
                intent.putExtra("key",a);
                startActivity(intent);
                Toast.makeText(Check.this,list2.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        adapter=new ArrayAdapter<String>(Check.this,R.layout.myrow,R.id.Itemname,list);
        listView.setAdapter(adapter);
        lref=FirebaseDatabase.getInstance().getReference();
        dref= FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Check Attendance</font>"));
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
                adp = new ArrayAdapter<String>(Check.this, android.R.layout.simple_spinner_item, items);
                adp.notifyDataSetChanged();
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        it=parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Selected: " + it, Toast.LENGTH_SHORT).show();

                        list.clear();
                        list2.clear();
                        list3.clear();

                         lref.child(it).addChildEventListener(new ChildEventListener() {
                             @Override
                             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                final String key=dataSnapshot.getKey().toString();
                                 list2.add(key);
                                 lref.child(it).child(key).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                       {
                                      //  hashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                                        list3.add(dataSnapshot1.getValue().toString());

                                           adapter.notifyDataSetChanged();
                                       }
                                       String a=count(list3);
                                       list.add(key+" \n  " +"Attendance-"+
                                               ""+a+"%");
                                      list3.clear();
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

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
    public String count(ArrayList<String> a)
    {
        float m=a.size();float rt;
        int jj=0;
        String d="P";
        for (int i=0;i<a.size();i++)
        {
            if(d.equalsIgnoreCase(a.get(i)))
                jj++;
        }
        if(jj==0&&m==0)
            return "0";
        else
        {
            rt=(float)jj/a.size();
            //rt=Math.round(rt);
        }
        rt=rt*100;
        rt=Math.round(rt);
        String ll=Float.toString(rt);
        return ll;
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
