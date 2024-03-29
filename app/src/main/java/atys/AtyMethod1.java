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

import java.text.DecimalFormat;
import java.util.Calendar;
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
    private TextView tvAge,tvName1,tvTimer,tvRate,tvOri,tvTest,tvAcc,tvProx,tvMag,tvMoving;
    public volatile boolean exit;
    private SoundPool sp;
    private int soundId,mX,mY,mZ;
    private long lasttimestamp = 0;
    Calendar mCalendar;
    private YoYo.AnimationComposer pulse;
    int i,j;
    private Timer timer,soundTimer = null;
    private TimerTask task,soundTask = null;
    private ScaleAnimation sa,sa1;
    private ImageView hand_1;
    private float[] gravity = new float[3];
    private float v,vx,vy,vz;
    DecimalFormat df;
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_method_1);
        hand_1=(ImageView)findViewById(R.id.tv_hand1);
        pulse= YoYo.with(Techniques.Pulse).duration(1000);
        pulse.playOn(hand_1);

        tvMoving = (TextView)findViewById(R.id.tvMoving);
        tvTest = (TextView)findViewById(R.id.tvTest);
        tvOri = (TextView)findViewById(R.id.tvOri);
        tvMag = (TextView)findViewById(R.id.tvMag);
        tvProx=(TextView)findViewById(R.id.tvProx);
        tvAcc = (TextView)findViewById(R.id.tvAcc);
        tvRate=(TextView)findViewById(R.id.tvRate);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        df = new DecimalFormat("0.000");
        v=vx=vy=vz=0;

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
                        Toast.makeText(AtyMethod1.this,R.string.push_harder,Toast.LENGTH_SHORT).show();
                        break;

                    case R.string.push_faster:
                        Toast.makeText(AtyMethod1.this,R.string.push_faster,Toast.LENGTH_SHORT).show();
                        break;

                    case R.string.release_fully:
                        Toast.makeText(AtyMethod1.this,R.string.release_fully,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AtyMethod1.this, R.string.unsupported_language, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        thread = new Thread(){

            public void run() {
                try {
                    while(!exit) {
                        sleep(5000);
                        sleep(1000);
                        Message msg = msgHandler.obtainMessage();
                        msg.arg1 = R.string.push_harder;
                        msgHandler.sendMessage(msg);
                        mTextToSpeech.speak(getString(R.string.speak_push_harder), TextToSpeech.QUEUE_FLUSH, null);

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

        soundTimer=new Timer();
        soundTask = new TimerTask() {
            @Override
            public void run() {
                sp.play(soundId, 1, 1, 0, 0, 1);
            }
        };
        soundTimer.schedule(soundTask,1000);

    }
    public void stopTime(){
        timer.cancel();
        soundTimer.cancel();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor
                (Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor
                (Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor
                (Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor
                (Sensor.TYPE_LINEAR_ACCELERATION),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor
                (Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);

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
                mCalendar = Calendar.getInstance();
                long stamp = mCalendar.getTimeInMillis() / 1000l;// 1393844912
                int x = (int) event.values[0];
                int y = (int) event.values[1];
                int z = (int) event.values[2];
                int second = mCalendar.get(Calendar.SECOND);// 53
                final float alpha =(float)0.8;
                gravity[0]=alpha * gravity[0]+(1-alpha)*event.values[0];
                gravity[1]=alpha * gravity[1]+(1-alpha)*event.values[1];
                gravity[2]=alpha * gravity[2]+(1-alpha)*event.values[2];
                int px = Math.abs(mX - x);
                int py = Math.abs(mY - y);
                int pz = Math.abs(mZ - z);
                int maxvalue = Math.max(Math.max(px,py),pz);
                if (maxvalue > 1) {
                    lasttimestamp = stamp;
                    tvMoving.setText(R.string.device_is_moving);
                }else{
                    tvMoving.setText(R.string.not_moving);
                }

                mX = x;
                mY = y;
                mZ = z;

                String accelerometer = getString(R.string.acceleration_m2_s2) + "X:" + (df.format(event.values[0]-gravity[0])) + "\n" +"Y:" +(df.format(event.values[1]-gravity[1]))
                        + "\n" +"Z:" +(df.format(event.values[2]-gravity[2]));
                Log.d("z", String.valueOf(event.values[2] - gravity[2]));
                tvAcc.setText(accelerometer);
                vx=  vx+ (event.values[0]-gravity[0])*0.05f;
                vy=  vy+ (event.values[1]-gravity[1])*0.05f;
                vz=  vz+ (event.values[2]-gravity[2])*0.05f;
                v = (float) Math.sqrt((vx*vx+vy*vy+vz*vz));
                tvTest.setText(getString(R.string.velocity_maohao) +String.valueOf(df.format(v)) +"m/s");
//                tvTest.setText(accelerometer);
                break;
            case Sensor.TYPE_GRAVITY:
                gravity[0] = event.values[0];
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];

                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
//                String linear = "Linear\n" + "X: " +df.format(event.values[0]) +"\n" +"Y:"+df.format(event.values[1])+"\n"+"Z:"+df.format(event.values[2]);
//                v=  (v+event.values[2]*0.2f);
                break;
            case Sensor.TYPE_PROXIMITY:
//                tvTest.setText(String.valueOf(even    t.values[0]));
                tvProx.setText(getString(R.string.proximity_maohao) +String.valueOf(df.format(event.values[0]))+"cm");

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                String magnetic = getString(R.string.magnetic_gangn) + "X: " +df.format(event.values[0])+"uT" +"\n" +"Y:"+df.format(event.values[1])+"uT" +"\n"+"Z:"+df.format(event.values[2])+"uT";
                tvMag.setText(magnetic);

                break;
            case Sensor.TYPE_ORIENTATION:
                String orientation = getString(R.string.orientation_degree_gangn) + "Azimuth: " +df.format(event.values[0]) +"\n" +"Pitch:"+df.format(event.values[1]) +"\n"+"Roll:"+df.format(event.values[2]);
                tvOri.setText(orientation);
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
