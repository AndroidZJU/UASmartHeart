package atys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.CircularProgressButton;
import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import at.markushi.ui.CircleButton;
import info.hoang8f.widget.FButton;
import tools.DialogActivityFront;
import tools.DialogActivitySide;
import tools.ShowFront;
import tools.ShowSide;

/**
 * Created by Fernando on 15/9/18.
 */
public class AtyMonthInput extends Activity implements View.OnClickListener {

    private MaterialEditText etHeight,etWeight,etChestCircumference,etSternum;
    private FButton btnMonthtoMain,btnEdit;
    private CircleButton btnFrontUpload,btnSideUpload,btnFrontView,btnSideView;
    private CircularProgressButton btnMonthSave;
    private TextView tvLastUpdate;
    private int mYear,mMonth,mDay,age;
    private long currentmonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_monthly_input);
        btnFrontUpload= (CircleButton) findViewById(R.id.btnFrontUpload);
        btnFrontView= (CircleButton) findViewById(R.id.btnFrontView);
        btnSideUpload= (CircleButton) findViewById(R.id.btnSideUpload);
        btnSideView= (CircleButton) findViewById(R.id.btnSideView);
        btnMonthSave = (CircularProgressButton) findViewById(R.id.btnMonthSave);
        btnMonthtoMain= (FButton) findViewById(R.id.btnMonthtoMain);
        btnEdit= (FButton) findViewById(R.id.btnEdit);


        etHeight= (MaterialEditText) findViewById(R.id.et_height);
        etWeight= (MaterialEditText) findViewById(R.id.et_weight);
        etChestCircumference= (MaterialEditText) findViewById(R.id.et_chestcircumference);
        etSternum= (MaterialEditText) findViewById(R.id.et_sternum);
        tvLastUpdate= (TextView) findViewById(R.id.tvLastUpdate);

        btnFrontUpload.setOnClickListener(this);
        btnFrontView.setOnClickListener(this);
        btnSideUpload.setOnClickListener(this);
        btnSideView.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnMonthSave.setOnClickListener(this);
        btnMonthtoMain.setOnClickListener(this);
        tvLastUpdate.setText(Config.getCachedUpdateDate(AtyMonthInput.this));
        etHeight.setText(Config.getCachedHeight(AtyMonthInput.this));
        etWeight.setText(Config.getCachedWeight(AtyMonthInput.this));
        etSternum.setText(Config.getCachedSternum(AtyMonthInput.this));
        etChestCircumference.setText(Config.getCachedChestCircumference(AtyMonthInput.this));
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
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
        intent.putExtra("return-data",true);
        startActivityForResult(intent,Config.CROP_REQUEST_COED);


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
            Config.cacheUpdateFrontImage(AtyMonthInput.this,img);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMonthtoMain:
                startActivity(new Intent(AtyMonthInput.this, MainActivity.class));
                finish();
                break;
            case R.id.btnEdit:
                etHeight.setEnabled(true);
                etWeight.setEnabled(true);
                etChestCircumference.setEnabled(true);
                etSternum.setEnabled(true);
                break;
            case R.id.btnFrontUpload:
                Intent intent = new Intent();
                intent.setClass(AtyMonthInput.this, DialogActivityFront.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dialog_enter, 0);
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, Config.CAMERA_REQUEST_COED);
                break;

            case R.id.btnFrontView:
                startActivity(new Intent(AtyMonthInput.this, ShowFront.class));
                break;

            case R.id.btnSideUpload:
                Intent i = new Intent();
                i.setClass(AtyMonthInput.this, DialogActivitySide.class);
                startActivity(i);
                overridePendingTransition(R.anim.dialog_enter, 0);
                break;

            case R.id.btnSideView:
                startActivity(new Intent(AtyMonthInput.this, ShowSide.class));

                break;

            case R.id.btnMonthSave:
                btnMonthSave.setIndeterminateProgressMode(true);

                if((Integer.parseInt(etHeight.getText().toString())<=0)||(Integer.parseInt(etHeight.getText().toString())>200)){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.layoutHeight));
                    Toast.makeText(AtyMonthInput.this, "Please set the correct height!", Toast.LENGTH_LONG).show();
                    btnMonthSave.setProgress(-1);
                    break;
                }
                if((Integer.parseInt(etWeight.getText().toString())<=0)||(Integer.parseInt(etWeight.getText().toString())>150)){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.laioutWeight));
                    Toast.makeText(AtyMonthInput.this, "Please set the correct weight!", Toast.LENGTH_LONG).show();
                    btnMonthSave.setProgress(-1);
                    break;
                }
                if((Integer.parseInt(etChestCircumference.getText().toString())<=0)||(Integer.parseInt(etChestCircumference.getText().toString())>200)){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.layoutChest));
                    Toast.makeText(AtyMonthInput.this, "Please set the correct chest circumference!", Toast.LENGTH_LONG).show();
                    btnMonthSave.setProgress(-1);
                    break;
                }
                if((Integer.parseInt(etSternum.getText().toString())<=0)||(Integer.parseInt(etSternum.getText().toString())>100)){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.layoutSternum));
                    Toast.makeText(AtyMonthInput.this, "Please set the correct length!", Toast.LENGTH_LONG).show();
                    btnMonthSave.setProgress(-1);
                    break;
                }

                btnMonthSave.setProgress(10);
                Config.cacheHeight(AtyMonthInput.this, etHeight.getText().toString());
                btnMonthSave.setProgress(20);
                Config.cacheWeight(AtyMonthInput.this, etWeight.getText().toString());
                btnMonthSave.setProgress(30);
                Config.cacheSternum(AtyMonthInput.this, etSternum.getText().toString());
                btnMonthSave.setProgress(40);
                Config.cacheChestCircumference(AtyMonthInput.this, etChestCircumference.getText().toString());
                btnMonthSave.setProgress(50);
                Config.cacheUpdateDate(AtyMonthInput.this, mYear + " - " + (mMonth + 1) + " - " + mDay);
                btnMonthSave.setProgress(60);
                currentmonths=(mYear-2015)*12+mMonth+1+mDay/30;
                Config.cacheMonths(AtyMonthInput.this,currentmonths);
                btnMonthSave.setProgress(70);
                etHeight.setEnabled(false);
                btnMonthSave.setProgress(80);
                etWeight.setEnabled(false);
                btnMonthSave.setProgress(90);
                etChestCircumference.setEnabled(false);
                btnMonthSave.setProgress(100);

                etSternum.setEnabled(false);
                Toast.makeText(AtyMonthInput.this, "Saved successful", Toast.LENGTH_SHORT).show();
                btnMonthSave.setProgress(0);
                break;

        }
    }
}
