package com.example.arjun.inclass7;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        TextView appname = (TextView) findViewById(R.id.previewappname);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        ImageView iv = (ImageView) findViewById(R.id.imageView3);
        Drawable drawable = PreviewActivity.this.getResources().getDrawable(R.drawable.goldstar);

        String appn = (String) getIntent().getExtras().get(MainActivity.APPNAME);
        String imgurl = (String) getIntent().getExtras().get(MainActivity.IMG);
        String star = (String) getIntent().getExtras().get(MainActivity.STAR);


        appname.setText(appn);
        Picasso.with(this).load(imgurl).into(image);
        if(star.equals("gold")){
            Log.d("demo","star set as gold");
            iv.setImageDrawable(drawable);
        }

    }
}
