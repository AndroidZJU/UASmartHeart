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
public class ShowFront extends Activity {
    private ImageView showFront;
    private String strFront;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfront);
        showFront = (ImageView)findViewById(R.id.showFront);
        strFront = Config.getCachedFrontImage(ShowFront.this);
        Bitmap bmp = Config.convertStringToIcon(strFront);

    }
}

