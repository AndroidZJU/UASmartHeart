package tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Fernando on 15/9/22.
 */
public class DialogActivitySide extends Activity implements View.OnClickListener {

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
        DialogActivitySide.this.overridePendingTransition(0, R.anim.dialog_exit);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            //取消按钮的点击事件，关闭对话框
            case R.id.button_cancle:
                finish();
                break;
            case R.id.btnCamera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Config.CAMERA_REQUEST_COED);

                break;
            case R.id.btnPhoto:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,Config.PHOTO_REQUEST_COED);

                break;
            default:

                break;
        }
    }
    private Uri saveBitmap(Bitmap bm){
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.fengnanyue.camera");
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath()+ "front.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    private Uri convertUri(Uri uri){
        InputStream is =null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
    private void startImageZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY",1);
        DisplayMetrics dm = new DisplayMetrics();getWindowManager().getDefaultDisplay().getMetrics(dm);
        intent.putExtra("outputX",dm.widthPixels);
        intent.putExtra("outputY",dm.heightPixels);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Config.CROP_REQUEST_COED);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Config.CAMERA_REQUEST_COED)
        {
            if(data==null){
                return;
            }
            else{
                Bundle extras = data.getExtras();
                if(extras!=null){
                    Bitmap bm =extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);

                    startImageZoom(uri);
//                    ImageView imageView = (ImageView)findViewById(R.id.imageView);
//                    imageView.setImageBitmap(bm);
                }
            }
        }
        else if(requestCode==Config.PHOTO_REQUEST_COED){
            if(data==null){
                return;
            }
            else{
                Uri uri;
                uri = data.getData();
                Uri fileUri = convertUri(uri);
                startImageZoom(fileUri);
            }
        }
        else if(requestCode==Config.CROP_REQUEST_COED){
            if(data==null){
                return;
            }
            Bundle extras =data.getExtras();
            Bitmap bm = extras.getParcelable("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,60,stream);

            byte[] bytes = stream.toByteArray();
            String img  = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
            Config.cacheUpdateSideImage(DialogActivitySide.this,img);
            Toast.makeText(DialogActivitySide.this, "Upload successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("<<<<<<<<<<<<<<end");
    }
}

