package atys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.R;

import java.util.Locale;

/**
 * Created by Fernando on 15/9/17.
 */
public class AtyMethod1 extends Activity {
    private TextToSpeech mTextToSpeech;
    private Handler msgHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        msgHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case R.string.push_harder:
                        Toast.makeText(AtyMethod1.this,"Push Harder!",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
            }
        };
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int supported = mTextToSpeech.setLanguage(Locale.ENGLISH);
                    if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
                        Toast.makeText(AtyMethod1.this, "Unsupported language!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        new Thread(){

            public void run() {
                try {
                    while(true) {
                        sleep(4000);
                        Message msg = msgHandler.obtainMessage();
                        msg.arg1 = R.string.push_harder;
                        msgHandler.sendMessage(msg);
                        mTextToSpeech.speak("Push harder", TextToSpeech.QUEUE_FLUSH, null);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTextToSpeech!=null)
            mTextToSpeech.shutdown();
    }
}
