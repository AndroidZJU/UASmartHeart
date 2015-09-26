package atys;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fernando on 15/9/17.
 */
public class AtyMethod1 extends Activity implements SensorEventListener{
    private TextToSpeech mTextToSpeech;
    private Handler msgHandler;
    private Thread thread,thread_1;
    private TextView tvAge,tvName1,tvTimer,tvRate,tvTest;
    public volatile boolean exit;
    private SoundPool sp;
    private int soundId;
    int i,j;
    private Timer timer = null;
    private TimerTask task = null;
    private ScaleAnimation sa,sa1;
    private ImageView hand_1;
    private float[] gravity = new float[3];

    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        hand_1=(ImageView)findViewById(R.id.tv_hand1);
        YoYo.with(Techniques.Pulse).duration(1000).playOn(hand_1);
        tvTest = (TextView)findViewById(R.id.tvTest);
        tvRate=(TextView)findViewById(R.id.tvRate);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);



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
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        exit = true;
        stopTime();
        if(mTextToSpeech!=null)
            mTextToSpeech.shutdown();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                final float alpha =(float)0.8;
                gravity[0]=alpha * gravity[0]+(1-alpha)*event.values[0];
                gravity[1]=alpha * gravity[1]+(1-alpha)*event.values[1];
                gravity[2]=alpha * gravity[2]+(1-alpha)*event.values[2];

                String accelerometer = "Acceleration\n" + "X:" + (event.values[0]-gravity[0]) + "\n" +"Y:" +(event.values[1]-gravity[1])
                        + "\n" +"Z:" +(event.values[2]-gravity[2]);
                Log.d("z", String.valueOf(event.values[2] - gravity[2]));
//                tvTest.setText(accelerometer);
                break;
            case Sensor.TYPE_GRAVITY:
                gravity[0] = event.values[0];
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];

                break;

            case Sensor.TYPE_PROXIMITY:
                tvTest.setText(String.valueOf(event.values[0]));
                break;
            

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
