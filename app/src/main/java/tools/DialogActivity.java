package tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/22.
 */
public class DialogActivity extends Activity implements View.OnClickListener {

    private Button button_cancle,btnphoto,btncamera;//取消按钮

    private static int screenHeight;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏显示
        setContentView(R.layout.activity_dialog);
        init();
    }

    private void init(){
        screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();//获取屏幕高度

        WindowManager.LayoutParams lp = getWindow().getAttributes();////lp包含了布局的很多信息，通过lp来设置对话框的布局
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.height=screenHeight/4;//lp高度设置为屏幕的一半
        getWindow().setAttributes(lp);//将设置好属性的lp应用到对话框
        btncamera=(Button)findViewById(R.id.btnCamera);
        btnphoto=(Button)findViewById(R.id.btnPhoto);
        button_cancle=(Button) findViewById(R.id.button_cancle);
        btnphoto.setOnClickListener(this);
        btncamera.setOnClickListener(this);
        btncamera.setHeight(lp.height/3);
        btnphoto.setHeight(lp.height/3);
        button_cancle.setOnClickListener(this);//取消按钮的点击事件监听
        button_cancle.setHeight(lp.height/3);//将button的高度设置为对话框的1/6

    }

    //重写finish（）方法，加入关闭时的动画
    public void finish() {
        super.finish();
        DialogActivity.this.overridePendingTransition(0, R.anim.dialog_exit);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            //取消按钮的点击事件，关闭对话框
            case R.id.button_cancle:
                finish();
                break;
            case R.id.btnCamera:

                finish();
                break;
            case R.id.btnPhoto:
                finish();
                break;
        }
    }

}

