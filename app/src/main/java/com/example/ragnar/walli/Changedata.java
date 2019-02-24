package com.example.ragnar.walli;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ragnar on 23/12/17.
 */

public class Changedata extends AppCompatActivity {
    String[] myStrings;
    String section,name;
    TextView editText,textView;
    Button button;
    Calendar calendar;
    int day,month,year;
    String df="";
    DatabaseReference dref,zref;
    CheckBox present,absent;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changedata);
        textView=(TextView)findViewById(R.id.textView);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='ffffff'>Rectify</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        button=(Button)findViewById(R.id.but);
        calendar=Calendar.getInstance();
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        dref= FirebaseDatabase.getInstance().getReference();
        zref=FirebaseDatabase.getInstance().getReference();
        present=(CheckBox)findViewById(R.id.present);
        absent=(CheckBox)findViewById(R.id.absent);
        done=(Button)findViewById(R.id.done);

        if(b!=null)
        {
            myStrings = iin.getStringArrayExtra("key");
        }
        name=myStrings[0];
        editText=(TextView)findViewById(R.id.editText);
        section=myStrings[1];
        final int[] i = {0};
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                absent.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                present.setVisibility(View.INVISIBLE);
                //Log.d("All",name+section);
                DatePickerDialog datePickerDialog=new DatePickerDialog(Changedata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    month+=1;
                        String dm;

                        final DateFormat dayy=new SimpleDateFormat("EEE");
                        if(dayOfMonth<10)
                             dm="0"+dayOfMonth+"-"+month+"-"+year;
                        else
                         dm=dayOfMonth+"-"+month+"-"+year;
                        df=dm;
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date date = format.parse(dm);
                            editText.setText("Date selected "+dayOfMonth+"-"+month+"-"+year+" "+dayy.format(date.getTime()));
                            //   System.out.println(date);
                        } catch (ParseException e) {
                            // e.printStackTrace();
                            Toast.makeText(Changedata.this,"Error Occured",Toast.LENGTH_SHORT).show();
                        }
                        dref.child(section).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChild(df))
                                {
                                    i[0] =1;   Toast.makeText(Changedata.this, "No record:Select correct date", Toast.LENGTH_SHORT).show();
                                    textView.setText("Status:No record found");}
                                else {
                                    String a = dataSnapshot.child(df).getValue().toString();

                                    if (a.equalsIgnoreCase("P"))
                                    {   textView.setText("Attendance Status :Present"); absent.setVisibility(View.VISIBLE);
                                        done.setVisibility(View.VISIBLE);
                                        done.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(absent.isChecked())
                                                {
                                                    Toast.makeText(Changedata.this,"Changed to Absent",Toast.LENGTH_SHORT).show();
                                                    zref.child(section).child(name).child(df).setValue("A");
                                                    absent.setVisibility(View.INVISIBLE);
                                                    done.setVisibility(View.INVISIBLE);
                                                    present.setVisibility(View.VISIBLE);
                                                    startActivity(new Intent(Changedata.this,Front.class));
                                                    finishAffinity();
                                                }
                                                else
                                                {
                                                    Toast.makeText(Changedata.this,"Please check the box",Toast.LENGTH_SHORT).show();
                                                    }
                                            }
                                        });

                                    }
                                    else if (a.equalsIgnoreCase("A"))
                                    {textView.setText("Attendance Status :Absent");
                                        present.setVisibility(View.VISIBLE);
                                        done.setVisibility(View.VISIBLE);
                                        done.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(present.isChecked())
                                                {
                                                    Toast.makeText(Changedata.this,"Changed to Present",Toast.LENGTH_SHORT).show();
                                                    zref.child(section).child(name).child(df).setValue("P");
                                                    present.setVisibility(View.INVISIBLE);
                                                    done.setVisibility(View.INVISIBLE);
                                                    absent.setVisibility(View.INVISIBLE);
                                                    startActivity(new Intent(Changedata.this,Front.class));
                                                    finishAffinity();}
                                                else
                                                {
                                                    Toast.makeText(Changedata.this,"Please check the box",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }


                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                    //    textView.setText("Status:No record found");


                    }
                },year,month,day);
                datePickerDialog.show();
                absent.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                present.setVisibility(View.INVISIBLE);
            }
        });

       /* if(i[0]==2)
        {
            present.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Changedata.this,"Changed to Present",Toast.LENGTH_SHORT).show();
                }
            });
            present.setVisibility(View.INVISIBLE);
            done.setVisibility(View.INVISIBLE);
        }
        else if(i[0]==3)
        {
            absent.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Changedata.this,"Changed to Absent",Toast.LENGTH_SHORT).show();
                }
            });
            absent.setVisibility(View.INVISIBLE);
            done.setVisibility(View.INVISIBLE);
        }*/
    }
}
