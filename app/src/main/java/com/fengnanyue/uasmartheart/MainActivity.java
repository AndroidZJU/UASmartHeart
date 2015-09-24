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
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.victor.ringbutton.RingButton;

import java.util.Calendar;

import atys.AtyInfo;
import atys.AtyInput;
import atys.AtyLink;
import atys.AtyPreGuide;
import atys.AtySetting;

public class MainActivity extends Activity {

    private int mYear,mMonth,mDay;
    private long months,updateMonths;
    private RingButton ringButton;
    private Shimmer shimmer;
    private ShimmerTextView st;
    private FloatingActionButton btnSetting,btnLink,btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        st= (ShimmerTextView) findViewById(R.id.shimmer_tv);
        shimmer = new Shimmer();
        shimmer.setRepeatCount(10);
        shimmer.start(st);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        months=(mYear-2015)*12+mMonth+1+mDay/30;
        updateMonths = Config.getCachedMonths(MainActivity.this);
        if ((months-updateMonths)>=1) {
            Builder builder = new NotificationCompat.Builder(MainActivity.this);
            builder.setSmallIcon(R.drawable.heart);
            builder.setContentTitle("Input out of date!");
            builder.setContentText("Please update the monthly input!");
            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Config.NOTIFICATION_ID, notification);
        }
        btnSetting = (FloatingActionButton)findViewById(R.id.btnSettings);
        btnInfo=(FloatingActionButton)findViewById(R.id.btnInfo);
        btnLink=(FloatingActionButton)findViewById(R.id.btnLinks);

        ringButton = (RingButton)findViewById(R.id.ringButton);
        ringButton.setOnClickListener(new RingButton.OnClickListener() {
            @Override
            public void clickUp() {
                switch (Config.getCachedMethod(MainActivity.this)) {
                    case Config.RESULT_NONE:
                        Toast.makeText(MainActivity.this, R.string.please_update_the_input, Toast.LENGTH_LONG).show();
                        break;


                    default:
                        startActivity(new Intent(MainActivity.this, AtyPreGuide.class));
                        break;

                }
            }

            @Override
            public void clickDown() {
                Intent i =new Intent(MainActivity.this, AtyInput.class);

                startActivity(i);
                finish();
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

