package com.example.agent_agrilife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    long pressedTime;
    ClaimReq claimReq =new ClaimReq();
    Profile profile=new Profile();
    VerifyReq verifyReq=new VerifyReq();
   // String passStr= getIntent().getStringExtra("password");




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        //Toast.makeText(this, passStr, Toast.LENGTH_SHORT).show();


       try{ getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, claimReq).commit();}
       catch(Exception e)
       {
           Log.e("error",e.getMessage());
           Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
       }
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.claimReq:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, claimReq).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profile).commit();
                        return true;
                    case R.id.verifiReq:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,verifyReq).commit();
                        return true;

                }
                return false;
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}