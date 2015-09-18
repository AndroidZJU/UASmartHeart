package atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/18.
 */
public class AtyMonthInput extends Activity implements View.OnClickListener {

    private EditText etHeight,etWeight,etChestCircumference,etSternum;
    private Button btnMonthSave,btnMonthtoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_monthly_input);
        etHeight= (EditText) findViewById(R.id.et_height);
        etWeight= (EditText) findViewById(R.id.et_weight);
        etChestCircumference= (EditText) findViewById(R.id.et_chestcircumference);
        etSternum= (EditText) findViewById(R.id.et_sternum);
        btnMonthSave = (Button)findViewById(R.id.btnMonthSave);
        btnMonthtoMain=(Button)findViewById(R.id.btnMonthtoMain);
        btnMonthSave.setOnClickListener(this);
        btnMonthtoMain.setOnClickListener(this);
        etHeight.setText(Config.getCachedHeight(AtyMonthInput.this));
        etWeight.setText(Config.getCachedWeight(AtyMonthInput.this));
        etSternum.setText(Config.getCachedSternum(AtyMonthInput.this));
        etChestCircumference.setText(Config.getCachedChestCircumference(AtyMonthInput.this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMonthtoMain:
                startActivity(new Intent(AtyMonthInput.this, MainActivity.class));
                finish();
                break;
            case R.id.btnMonthSave:
                Toast.makeText(AtyMonthInput.this, "Saved successful", Toast.LENGTH_SHORT).show();
                Config.cacheHeight(AtyMonthInput.this, etHeight.getText().toString());
                Config.cacheWeight(AtyMonthInput.this, etWeight.getText().toString());
                Config.cacheSternum(AtyMonthInput.this, etSternum.getText().toString());
                Config.cacheChestCircumference(AtyMonthInput.this, etChestCircumference.getText().toString());
                break;
        }
    }
}
