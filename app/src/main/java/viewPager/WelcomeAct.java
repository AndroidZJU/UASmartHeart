package viewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/16.
 */
public class WelcomeAct extends Activity{
    private boolean isFirstIn = false;
    private static final int TIME = 1500;
    private static final int GO_HOME = 2000;
    private static final int GO_GUIDE = 2001;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
        Config.cacheMethod(WelcomeAct.this,Config.RESULT_METHOD1);
    }

    private void goHome(){
        Intent i = new Intent(WelcomeAct.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goGuide(){
        Intent i = new Intent(WelcomeAct.this,Guide.class);
        startActivity(i);
        finish();
    }

    private void init(){
        SharedPreferences perPreferences = getSharedPreferences("Fengnan",MODE_PRIVATE);
        isFirstIn = perPreferences.getBoolean("isFirstIn",true);
        if(!isFirstIn){
            mHandler.sendEmptyMessageDelayed(GO_HOME,TIME);
        }else{
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }
}
