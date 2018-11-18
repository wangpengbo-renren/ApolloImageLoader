package com.apollo.renren.apolloimageloader;

import android.app.Application;

import com.apollo.renren.imageloader.glide.GlideImageLoader;
import com.apollo.renren.imageloader.library.ImageLoaderConfig;
import com.apollo.renren.imageloader.library.ImageLoaderManager;
import com.apollo.renren.imageloader.library.LoaderType;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig config = new ImageLoaderConfig
                .Builder(LoaderType.GLIDE, new GlideImageLoader())
//                        .addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())
                .maxMemory(40 * 1024 * 1024L)  // 单位为Byte
                .build();
        ImageLoaderManager.getInstance().init(this, config);

    }
}
