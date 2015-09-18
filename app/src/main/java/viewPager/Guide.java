package viewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fengnanyue.uasmartheart.MainActivity;
import com.fengnanyue.uasmartheart.R;

import java.util.ArrayList;
import java.util.List;

import atys.AtyInput;

/**
 * Created by Fernando on 15/9/16.
 */
public class Guide extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3};
    private Button btnStart,btnInputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initViews();
        initDots();
    }

    private void initViews(){
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one,null));
        views.add(inflater.inflate(R.layout.two,null));
        views.add(inflater.inflate(R.layout.three,null));

        vpAdapter = new ViewPagerAdapter(views,this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        btnStart = (Button) views.get(2).findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Guide.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnInputData = (Button) views.get(2).findViewById(R.id.btnInputData);
        btnInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Guide.this, AtyInput.class);
                startActivity(i);
                finish();
            }
        });
        vp.setOnPageChangeListener(this);

    }
    private void initDots(){
        dots = new ImageView[views.size()];
        for(int i=0;i<views.size();i++){
            dots[i]= (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        for(int j =0;j<ids.length;j++){
            if(i==j){
                dots[j].setImageResource(R.drawable.login_point_selected);
            }else{
                dots[j].setImageResource(R.drawable.login_point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
