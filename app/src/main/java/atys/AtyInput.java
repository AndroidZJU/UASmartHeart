package atys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;

import java.util.Calendar;

/**
 * Created by Fernando on 15/9/16.
 */
public class AtyInput extends Activity implements View.OnClickListener {
    private Button btnBacktoMain;
    private Button btnSave;
    private TextView daTextView;
    private TextView tvName;
    private String savedName;
    private String selectedDate;
    private int mYear,mMonth,mDay,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_input_data);

        btnBacktoMain = (Button)findViewById(R.id.btnBacktoMain);
        btnBacktoMain.setOnClickListener(this);
        tvName = (EditText)findViewById(R.id.tv_name);
        savedName = Config.getCachedName(AtyInput.this);
        System.out.println(savedName);
        tvName.setText(savedName);
        daTextView = (TextView) findViewById(R.id.tv_date);
        daTextView.setOnClickListener(this);
        daTextView.setText(Config.getCachedDate(AtyInput.this));
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBacktoMain:

                startActivity(new Intent(AtyInput.this, MainActivity.class));
                finish();
                break;
            case R.id.tv_date:
                new DatePickerDialog(AtyInput.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String theDate =String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth);
                        selectedDate=theDate;
                        age = (int)((mYear+mMonth/12)-(year+monthOfYear/12));
                        daTextView.setText(theDate);
                    }
                },mYear,mMonth,mDay).show();
                break;

            case R.id.btnSave:
                Toast.makeText(AtyInput.this,"Saved successful",Toast.LENGTH_SHORT).show();
                Config.cacheAge(AtyInput.this,age);
                Config.cacheName(AtyInput.this, tvName.getText().toString());
                Config.cacheDate(AtyInput.this,daTextView.getText().toString());
                break;


        }

    }


}
