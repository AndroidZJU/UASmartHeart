package com.fengnanyue.uasmartheart;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import atys.AtyInfo;
import atys.AtyInput;
import atys.AtyLink;
import atys.AtyPreGuide;
import atys.AtySetting;

public class MainActivity extends Activity {

    private Button btnUpdateInput,btnStartCPR,btnSetting,btnLink,btnInfo;
    private int mYear,mMonth,mDay;
    private long months,updateMonths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        months=(mYear-2015)*12+mMonth+1+mDay/30;
        updateMonths = Config.getCachedMonths(MainActivity.this);
        if ((months-updateMonths)>=3) {
            Builder builder = new NotificationCompat.Builder(MainActivity.this);
            builder.setSmallIcon(R.drawable.heart);
            builder.setContentTitle("Input out of date!");
            builder.setContentText("Please update the monthly input!");
            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Config.NOTIFICATION_ID, notification);
        }


        btnUpdateInput = (Button) findViewById(R.id.btnUpdateInput);
        btnStartCPR=(Button)findViewById(R.id.btnStartCPR);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        btnLink=(Button)findViewById(R.id.btnLink);
        btnInfo=(Button)findViewById(R.id.btnInfo);
        btnUpdateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this, AtyInput.class);

                startActivity(i);
            }
        });

        btnStartCPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Config.getCachedMethod(MainActivity.this)) {
                    case Config.RESULT_NONE:
                        Toast.makeText(MainActivity.this, R.string.please_update_the_input, Toast.LENGTH_LONG).show();
                        break;


                    default:
                        startActivity(new Intent(MainActivity.this, AtyPreGuide.class));
                        break;

                }

            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AtySetting.class));
            }
        });

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AtyLink.class));

            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AtyInfo.class));

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

