package com.fengnanyue.uasmartheart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import atys.AtyInput;
import atys.AtyMethod1;
import atys.AtyMethod2;
import atys.AtyMethod3;

public class MainActivity extends Activity {

    private Button btnUpdateInput,btnStartCPR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpdateInput = (Button) findViewById(R.id.btnUpdateInput);
        btnStartCPR=(Button)findViewById(R.id.btnStartCPR);
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
                    case Config.RESULT_METHOD1:
                        startActivity(new Intent(MainActivity.this, AtyMethod1.class));
                        finish();
                        break;
                    case Config.RESULT_METHOD2:

                        startActivity(new Intent(MainActivity.this, AtyMethod2.class));
                        finish();
                        break;
                    case Config.RESULT_METHOD3:

                        startActivity(new Intent(MainActivity.this, AtyMethod3.class));
                        finish();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, R.string.please_update_the_input, Toast.LENGTH_LONG).show();

                        break;

                }

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

