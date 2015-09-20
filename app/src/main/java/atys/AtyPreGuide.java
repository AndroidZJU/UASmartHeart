package atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/19.
 */
public class AtyPreGuide extends Activity implements View.OnClickListener {
    private Button btnCPR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_preguide);
        btnCPR = (Button)findViewById(R.id.btnCPR);
        btnCPR.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCPR:
                switch (Config.getCachedMethod(AtyPreGuide.this)) {
                    case Config.RESULT_METHOD1:
                        startActivity(new Intent(AtyPreGuide.this, AtyMethod1.class));
                        finish();
                        break;
                    case Config.RESULT_METHOD2:

                        startActivity(new Intent(AtyPreGuide.this, AtyMethod2.class));
                        finish();
                        break;
                    case Config.RESULT_METHOD3:

                        startActivity(new Intent(AtyPreGuide.this, AtyMethod3.class));
                        finish();
                        break;
                    default:

                        break;

                }
                break;
            default:

                break;
        }
    }
}
