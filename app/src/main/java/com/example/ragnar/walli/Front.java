package com.example.ragnar.walli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ragnar on 26/11/17.
 */

public class Front extends AppCompatActivity {
   Button button1,button2,button3,button4,button5,button6;
    boolean doubleBackToExitPressedOnce = false;
    ImageView imageAnim;
    private DrawerLayout drawerLayout;
    NavigationView navig;
    private ActionBarDrawerToggle toggle;
    boolean a;
   static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);


        //a=haveNetworkConnection();
  /*      if(!a)
        {
            View CustomToast=Front.this.getLayoutInflater().inflate(R.layout.toast,null);
            Toast toast=new Toast(getApplicationContext());
            toast.setView(CustomToast);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }*/
/*        if (!isTaskRoot()) {
            finish();
            return;
        }*/
      /*  if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }
*/
        /*if(i==0)
        { FirebaseDatabase.getInstance().setPersistenceEnabled(true);i=1;}
        FirebaseDatabase.getInstance().getReference().keepSynced(true);*/
        //Intent inten =new Intent(getBaseContext(),Front.class);
        //inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,inten,0);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Dashboard</font>"));
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        navig=(NavigationView)findViewById(R.id.navig);
        navig.setItemIconTintList(null);
       ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF9100"));
        actionBar.setBackgroundDrawable(colorDrawable);

        button5=(Button)findViewById(R.id.button5);
        button6=(Button)findViewById(R.id.button6);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.school), 1000);
        animation.addFrame(getResources().getDrawable(R.drawable.school3), 2000);
        animation.addFrame(getResources().getDrawable(R.drawable.ik2), 3000);
        animation.addFrame(getResources().getDrawable(R.drawable.ik), 4000);
        animation.addFrame(getResources().getDrawable(R.drawable.school2), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.ik), 7000);
        animation.addFrame(getResources().getDrawable(R.drawable.ik2), 9000);
        animation.setOneShot(false);

        imageAnim =  (ImageView) findViewById(R.id.img);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //imageView=(ImageView)findViewById(R.id.imageView);
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,Announcement.class));
            }
        });*/
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,MainActivity.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,Check.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,Announcement.class));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,ReadAnnouncement.class));
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,DeleteAnnouncement.class));
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Front.this,Registrationandhouse.class));
            }
        });
        navig.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.db1:startActivity(new Intent(Front.this,Front.class));finishAffinity();return true;
                    case R.id.db2:startActivity(new Intent(Front.this,Aboutschool.class));return true;
                    case R.id.db3:startActivity(new Intent(Front.this,achieve.class));return true;
                    case R.id.db4:startActivity(new Intent(Front.this,principal.class));return true;
                    case R.id.db5:startActivity(new Intent(Front.this,Creator.class));return true;
                    case R.id.db6:startActivity(new Intent(Front.this,Rectify.class));return true;
                    case R.id.db7:startActivity(new Intent(Front.this,Md.class));return true;
                    default:
                        return false;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(toggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_red:/*if (item.isChecked())item.setChecked(false);else item.setChecked(true);
                getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF4081")));
                return true;*/ startActivity(new Intent(Front.this,Registrationandhouse.class));return true;
            case R.id.menu_green:/*if (item.isChecked())item.setChecked(false);else item.setChecked(true);
                getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));*/
              //  startActivity(new Intent(Front.this,Registrationandhouse.class))
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Front.this,"Good bye",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Front.this,Login.class));
                finish();


                 return true;
            case R.id.menu_blue:/*if (item.isChecked())item.setChecked(false);else item.setChecked(true);
                getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4CAF50")));*/
                startActivity(new Intent(Front.this,ReadAnnouncement.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    protected void onResume() {
        super.onResume();
        a=haveNetworkConnection();
        if(!a)
        {
           /* View CustomToast=Front.this.getLayoutInflater().inflate(R.layout.toast,null);
            Toast toast=new Toast(getApplicationContext());
            toast.setView(CustomToast);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();*/
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ").append(" ");
            builder.setSpan(new ImageSpan(Front.this, R.drawable.error), builder.length() - 1, builder.length(), 0);
            builder.append(" \t   No active internet or Slow speed");
            Snackbar.make(findViewById(android.R.id.content), builder, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();


            return;

        }
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            this.drawerLayout.closeDrawer(GravityCompat.START);

        }


        this.doubleBackToExitPressedOnce = true;
      //  Toast.makeText(this, "To Exit ,Click twice simultaneously", Toast.LENGTH_SHORT).show();
Snackbar.make(findViewById(android.R.id.content),"Press back again to exit",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
