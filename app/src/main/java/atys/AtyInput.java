package atys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.CircularProgressButton;
import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;
import com.phillipcalvin.iconbutton.IconButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Calendar;

/**
 * Created by Fernando on 15/9/16.
 */
public class AtyInput extends Activity implements View.OnClickListener {
//    private FButton btnMonthInput;
    private IconButton btnBacktoMain,btnMonthInput;
    private CircularProgressButton btnSave;
    private RadioButton btnMale,btnFemale;
    private TextView daTextView;
    private com.rengwuxian.materialedittext.MaterialEditText tvName,etEmergency;
    private String savedName;
    private String selectedDate;
    private int mYear,mMonth,mDay,age;
    private ShimmerTextView st;
    private Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_input_data);
        etEmergency=(MaterialEditText)findViewById(R.id.et_emergency);
        etEmergency.setText(Config.getCachedPhone(AtyInput.this));
        st= (ShimmerTextView) findViewById(R.id.shimmer_input);
        shimmer = new Shimmer();
        shimmer.setRepeatCount(2);
        shimmer.start(st);
        findViewById(R.id.rootviewBasic).setOnClickListener(this);
        btnMonthInput= (IconButton) findViewById(R.id.btnMonthInput);
        btnMonthInput.setOnClickListener(this);
        btnMale= (RadioButton) findViewById(R.id.btnMale);
        age=Config.getCachedAge(AtyInput.this);
        btnFemale = (RadioButton) findViewById(R.id.btnFemale);
        btnFemale.setOnClickListener(this);
        btnMale.setOnClickListener(this);
        btnBacktoMain = (IconButton) findViewById(R.id.btnBacktoMain);
        btnBacktoMain.setOnClickListener(this);
        tvName = (MaterialEditText) findViewById(R.id.tv_name);
        savedName = Config.getCachedName(AtyInput.this);
        tvName.setText(savedName);
        daTextView = (TextView) findViewById(R.id.tv_date);
        daTextView.setOnClickListener(this);
        daTextView.setText(Config.getCachedDate(AtyInput.this));
        btnSave= (CircularProgressButton) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        switch (Config.getGender(AtyInput.this)){
            case Config.MALE:
                btnMale.setChecked(true);
                break;
            case Config.FEMALE:
                btnFemale.setChecked(true);
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rootviewBasic:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
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
                btnSave.setProgress(0);

                if(tvName.getText().toString().length()==0){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.layoutName));
                    Toast.makeText(AtyInput.this, "Please enter name!", Toast.LENGTH_LONG).show();
                    btnSave.setProgress(-1);

                    break;
                }
                if((!btnMale.isChecked())&&(!btnMale.isChecked())){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.layoutGender));
                    Toast.makeText(AtyInput.this, "Please choose gender!", Toast.LENGTH_LONG).show();
                    btnSave.setProgress(-1);

                    break;
                }
                if(age<0||age>100){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.laioutBirthday));
                    Toast.makeText(AtyInput.this, "Please set birthday!", Toast.LENGTH_LONG).show();
                    btnSave.setProgress(-1);

                    break;
                }

                btnSave.setIndeterminateProgressMode(true);

                    btnSave.setProgress(0);
                    btnSave.setProgress(50);
                    YoYo.with(Techniques.Wobble)
                        .duration(700)
                        .playOn(findViewById(R.id.btnMonthInput));
                    btnSave.setProgress(75);
                    btnSave.setProgress(100);
                    Toast.makeText(AtyInput.this, "Saved successful", Toast.LENGTH_SHORT).show();
                    btnSave.setProgress(0);
                    if (btnFemale.isChecked()) {
                        Config.cacheGender(AtyInput.this, Config.FEMALE);
                    }
                    if (btnMale.isChecked()) {
                        Config.cacheGender(AtyInput.this, Config.MALE);
                    }
                    Config.cachePhone(AtyInput.this,etEmergency.getText().toString());
                    Config.cacheAge(AtyInput.this, age);
                    Config.cacheName(AtyInput.this, tvName.getText().toString());
                    Config.cacheDate(AtyInput.this, daTextView.getText().toString());

                break;
            case R.id.btnMonthInput:
                startActivity(new Intent(AtyInput.this,AtyMonthInput.class));
                break;

        }

    }


}
