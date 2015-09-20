package atys;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.R;

import java.util.Locale;

/**
 * Created by Fernando on 15/9/17.
 */
public class AtyMethod1 extends Activity {
    private TextToSpeech mTextToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int supported = mTextToSpeech.setLanguage(Locale.CANADA);
                    if((supported!=TextToSpeech.LANG_AVAILABLE)&&(supported!=TextToSpeech.LANG_COUNTRY_AVAILABLE)){
                        Toast.makeText(AtyMethod1.this,"Unsupported language!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTextToSpeech!=null)
            mTextToSpeech.shutdown();
    }
}
