package tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.fengnanyue.uasmartheart.Config;
import com.fengnanyue.uasmartheart.R;

/**
 * Created by Fernando on 15/9/22.
 */
public class ShowSide extends Activity {
    private ImageView showSide;
    private String strSide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showside);
        showSide = (ImageView)findViewById(R.id.showSide);
        strSide = Config.getCachedSideImage(ShowSide.this);
        Bitmap bmp = Config.convertStringToIcon(strSide);
        showSide.setImageBitmap(bmp);
    }
}
