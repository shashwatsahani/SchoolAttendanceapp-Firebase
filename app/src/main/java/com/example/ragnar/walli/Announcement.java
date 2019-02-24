package com.example.ragnar.walli;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.makeText;

/**
 * Created by ragnar on 29/11/17.
 */

public class Announcement extends AppCompatActivity {
    DatabaseReference zref;
    Button button;
    String Key="",Value="",val="";
    EditText editText2,editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements);
        button=(Button)findViewById(R.id.submitit);
        editText2=(EditText)findViewById(R.id.editText2);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Post Announcement</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF216D8F"));
        actionBar.setBackgroundDrawable(colorDrawable);

        editText3=(EditText)findViewById(R.id.editText3);
        zref= FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                         Key=editText2.getText().toString().trim();

                         Value=editText3.getText().toString().trim();
                val=Value.replaceAll("\\n","");
                        if(Key.equals("")||Value.equals(""))
                            makeText(Announcement.this, "Title or Description can't be Empty", Toast.LENGTH_SHORT).show();
                        else
                        {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Announcement.this);
                            dialog.setTitle("Want to Post the announcement").
                                    setMessage(" ").
                                    setCancelable(true).
                                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //reset database
                                            zref.child("Announcement").child(Key).setValue(val);
                                            makeText(Announcement.this, "Announcement Posted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Announcement.this,Front.class));
                                            finish();
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dlg, int which)
                                {
                                    dlg.cancel();

                                    makeText(Announcement.this, "Cancelled", Toast.LENGTH_SHORT).show();


                                }
                            });
                            AlertDialog al = dialog.create();
                            al.show();



                        }
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

