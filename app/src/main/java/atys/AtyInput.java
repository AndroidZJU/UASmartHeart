package atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/16.
 */
public class AtyInput extends Activity implements View.OnClickListener {
    private Button btnBacktoMain;
    private TextView daTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_input_data);
        btnBacktoMain = (Button)findViewById(R.id.btnBacktoMain);
        btnBacktoMain.setOnClickListener(this);
        daTextView = (TextView) findViewById(R.id.tv_date);
        daTextView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBacktoMain:

                startActivity(new Intent(AtyInput.this, MainActivity.class));
                finish();
                break;
        }

    }



}
