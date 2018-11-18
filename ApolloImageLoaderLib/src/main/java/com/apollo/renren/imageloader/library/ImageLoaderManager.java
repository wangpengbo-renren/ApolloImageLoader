package com.apollo.renren.imageloader.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ImageLoaderManager {
    private static ImageLoaderManager INSTANCE;
    private IImageLoaderStrategy loaderstrategy;
    private HashMap<LoaderType, IImageLoaderStrategy> imageloaderMap = new HashMap<>();
    private LoaderType loaderType = null;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoaderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoaderManager();
                }
            }
        }
        return INSTANCE;
    }

    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url) {
        return new ImageLoaderOptions.Builder(container, url).isCrossFade(true).build();
    }

    public void showImage(@NonNull ImageLoaderOptions options) {
        if (getLoaderstrategy(loaderType) != null) {
            getLoaderstrategy(loaderType).showImage(options);
        }
    }

    public void showImage(@NonNull ImageLoaderOptions options, LoaderType loaderType) {
        if (getLoaderstrategy(loaderType) != null) {
            getLoaderstrategy(loaderType).showImage(options);
        }
    }

    public void hideImage(@NonNull View view, int visiable) {
        if (getLoaderstrategy(loaderType) != null) {
            getLoaderstrategy(loaderType).hideImage(view, visiable);
        }
    }


    public void cleanMemory(Context context) {
        getLoaderstrategy(loaderType).cleanMemory(context);
    }

    public void pause(Context context) {
        if (getLoaderstrategy(loaderType) != null) {
            getLoaderstrategy(loaderType).pause(context);
        }
    }

    public void resume(Context context) {
        if (getLoaderstrategy(loaderType) != null) {
            getLoaderstrategy(loaderType).resume(context);
        }
    }

    public void setCurImageLoader(LoaderType loader) {
        loaderType = loader;
    }

    // 在application的oncreate中初始化
    public void init(Context context, ImageLoaderConfig config) {
        imageloaderMap = config.getImageloaderMap();
        for (Map.Entry<LoaderType, IImageLoaderStrategy> entry : imageloaderMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().init(context, config);
            }

            if (loaderType == null) {
                loaderType = entry.getKey();
            }
        }


//        loaderstrategy=new GlideImageLocader();
//        loaderstrategy.init(context);
    }

    private IImageLoaderStrategy getLoaderstrategy(LoaderType loaderType) {
        return imageloaderMap.get(loaderType);
    }
}
