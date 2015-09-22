package atys;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

import java.util.Locale;

/**
 * Created by Fernando on 15/9/17.
 */
public class AtyMethod1 extends Activity {
    private TextToSpeech mTextToSpeech;
    private Handler msgHandler;
    private Thread thread;
    private TextView tvAge,tvName1;
    public volatile boolean exit;
    private SoundPool sp;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        tvName1 = (TextView)findViewById(R.id.tvName_1);
        tvAge=(TextView)findViewById(R.id.tvAge);
        tvName1.setText(Config.getCachedName(AtyMethod1.this));
        tvAge.setText(Config.getCachedAge(AtyMethod1.this)+"");
        exit = false;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundId = sp.load(this, R.raw.note1, 1);

        msgHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case R.string.push_harder:
                        Toast.makeText(AtyMethod1.this,"Push Harder!",Toast.LENGTH_SHORT).show();
                        break;

                    case R.string.push_faster:
                        Toast.makeText(AtyMethod1.this,"Push Faster!",Toast.LENGTH_SHORT).show();
                        break;

                    case R.string.release_fully:
                        Toast.makeText(AtyMethod1.this,"Release Fully!",Toast.LENGTH_SHORT).show();
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
        thread = new Thread(){

            public void run() {
                try {
                    while(!exit) {
                        sleep(5000);
//                        sp.play(soundId, 1, 1, 0, 0, 1);
                        sleep(1000);
                        Message msg = msgHandler.obtainMessage();
                        msg.arg1 = R.string.push_harder;
                        msgHandler.sendMessage(msg);
                        mTextToSpeech.speak("Push harder", TextToSpeech.QUEUE_FLUSH, null);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();



    }

    @Override
    protected void onDestroy() {
        exit = true;
        if(mTextToSpeech!=null)
            mTextToSpeech.shutdown();
        super.onDestroy();
    }

}
