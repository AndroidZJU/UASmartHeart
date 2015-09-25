package atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;
import com.phillipcalvin.iconbutton.IconButton;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by Fernando on 15/9/18.
 */
public class AtySetting extends Activity {
    private IconButton btnSetMain;
    private ShimmerTextView st;
    private Shimmer shimmer;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_setting);
        st= (ShimmerTextView) findViewById(R.id.shimmer_setting);
        shimmer = new Shimmer();
        shimmer.setRepeatCount(2);
        shimmer.start(st);
        btnSetMain=(IconButton)findViewById(R.id.btnSetMain);
        btnSetMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtySetting.this, MainActivity.class));
                finish();
            }
        });
        findViewById(R.id.switch_main_2).setEnabled(false);
        findViewById(R.id.switch_main_3).setEnabled(false);
        findViewById(R.id.switch_main_4).setEnabled(false);
        findViewById(R.id.switch_main_5).setEnabled(false);
        findViewById(R.id.switch_main_6).setEnabled(false);
        findViewById(R.id.switch_main_7).setEnabled(false);
        findViewById(R.id.switch_main_8).setEnabled(false);
        findViewById(R.id.switch_main_9).setEnabled(false);

        ((CompoundButton) findViewById(R.id.switch_main_1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.switch_main_2).setEnabled(!isChecked);
                findViewById(R.id.switch_main_3).setEnabled(!isChecked);
                findViewById(R.id.switch_main_4).setEnabled(!isChecked);
                findViewById(R.id.switch_main_5).setEnabled(!isChecked);
                findViewById(R.id.switch_main_6).setEnabled(!isChecked);
                findViewById(R.id.switch_main_7).setEnabled(!isChecked);
                findViewById(R.id.switch_main_8).setEnabled(!isChecked);
                findViewById(R.id.switch_main_9).setEnabled(!isChecked);

            }
        });
    }
}
