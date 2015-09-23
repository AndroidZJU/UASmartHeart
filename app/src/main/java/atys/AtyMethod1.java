package atys;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fernando on 15/9/17.
 */
public class AtyMethod1 extends Activity {
    private TextToSpeech mTextToSpeech;
    private Handler msgHandler;
    private Thread thread,thread_1;
    private TextView tvAge,tvName1,tvTimer;
    public volatile boolean exit;
    private SoundPool sp;
    private int soundId;
    int i,j;
    private Timer timer = null;
    private TimerTask task = null;
    private ScaleAnimation sa,sa1;
    private ImageView hand_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        hand_1=(ImageView)findViewById(R.id.tv_hand1);

//        sa = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        sa1 = new ScaleAnimation(1,0,1,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        sa.setDuration(1000);sa1.setDuration(1000);
//        sa.setRepeatCount(10);sa1.setRepeatCount(10);
//        hand_1.setAnimation(sa);hand_1.setAnimation(sa1);


        i=j=0;
        tvTimer=(TextView)findViewById(R.id.tvTimer);
        tvName1 = (TextView)findViewById(R.id.tvName_1);
        tvAge=(TextView)findViewById(R.id.tvAge);
        tvName1.setText(Config.getCachedName(AtyMethod1.this));
        tvAge.setText(Config.getCachedAge(AtyMethod1.this) + "");
        exit = false;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundId = sp.load(this, R.raw.note1, 1);
        startTime();
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

                    case R.string.scale_animation:
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
                        sp.play(soundId, 1, 1, 0, 0, 1);
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

//        thread_1 = new Thread(){
//            @Override
//            public void run() {
//                while(!exit){
//                    Message msg = msgHandler.obtainMessage();
//                    msg.arg1 = R.string.scale_animation;
//                    msgHandler.sendMessage(msg);
//                }
//            }
//        };
//        thread_1.start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg){
            if(i>=0&&i<10){
                tvTimer.setText(msg.arg2+": 0"+msg.arg1);
            }else {
                tvTimer.setText(msg.arg2 + ": " + msg.arg1);
            }
            startTime();
        }
    };
    public void startTime(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                i++;
                if(i>=60){
                    i=0;j++;
                }
                Message message = mHandler.obtainMessage();
                message.arg1 = i;message.arg2=j;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task,1000);
    }
    public void stopTime(){
        timer.cancel();
    }
    @Override
    protected void onDestroy() {
        exit = true;
        stopTime();
        if(mTextToSpeech!=null)
            mTextToSpeech.shutdown();
        super.onDestroy();
    }

}
