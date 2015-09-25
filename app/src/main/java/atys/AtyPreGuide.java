package atys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/19.
 */
public class AtyPreGuide extends Activity implements View.OnClickListener {
    private Button btnCPR,btnCall,btnEmergencyCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_preguide);
        btnEmergencyCall=(Button)findViewById(R.id.btnEmergencyCall);
        btnCPR = (Button)findViewById(R.id.btnCPR);
        btnCall =(Button)findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        btnCPR.setOnClickListener(this);
        btnEmergencyCall.setOnClickListener(this);
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
            case R.id.btnCall:
                Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + "911"));
                startActivity(i);
                break;

            case R.id.btnEmergencyCall:
                Intent i1 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" +Config.getCachedPhone(AtyPreGuide.this)));
                startActivity(i1);
                break;
            default:

                break;
        }
    }
}
