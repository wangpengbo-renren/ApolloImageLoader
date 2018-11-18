package com.apollo.renren.apolloimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.apollo.renren.imageloader.library.ImageLoaderManager;
import com.apollo.renren.imageloader.library.ImageLoaderOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivTest = findViewById(R.id.iv_test);

        String url = "http://img1.imgtn.bdimg.com/it/u=679805784,3150507797&fm=214&gp=0.jpg";
        String urlGif = "http://img2.imgtn.bdimg.com/it/u=2938769139,1872984641&fm=214&gp=0.jpg";

        ImageLoaderOptions options = new ImageLoaderOptions.Builder(ivTest, urlGif)
//                .blurImage(true)
//                .blurValue(35)
//                .needImageRadius(true)
//                .imageRadiusDp(5)
//                .isCircle()
                .asGif(true)
                .placeholder(R.mipmap.ic_launcher).build();

        ImageLoaderManager.getInstance().showImage(options);
    }
}
