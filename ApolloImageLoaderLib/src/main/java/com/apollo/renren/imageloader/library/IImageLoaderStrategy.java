package com.apollo.renren.imageloader.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * loader 策略，定义基本的方法
 */
public interface IImageLoaderStrategy {
    void showImage(@NonNull ImageLoaderOptions options);

    void hideImage(@NonNull View view, int visiable);

    void cleanMemory(Context context);

    void pause(Context context);

    void resume(Context context);

    // 在application的oncreate中初始化
    void init(Context context, ImageLoaderConfig config);
}
