package com.apollo.renren.imageloader.glide;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.apollo.renren.imageloader.library.IImageLoaderStrategy;
import com.apollo.renren.imageloader.library.ImageLoaderConfig;
import com.apollo.renren.imageloader.library.ImageLoaderOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class GlideImageLoader implements IImageLoaderStrategy {
    @Override
    public void showImage(@NonNull final ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();

        if (options.getHolderDrawable() != -1) {
            requestOptions.placeholder(options.getHolderDrawable());
        }
        if (options.getErrorDrawable() != -1) {
            requestOptions.fallback(options.getErrorDrawable());
        }

        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            if (ImageLoaderOptions.DiskCacheStrategy.NONE == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            } else if (ImageLoaderOptions.DiskCacheStrategy.All == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            } else if (ImageLoaderOptions.DiskCacheStrategy.SOURCE == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            } else if (ImageLoaderOptions.DiskCacheStrategy.RESULT == options.getDiskCacheStrategy()) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
            }

        }
        if (options.isSkipMemoryCache()) {
            requestOptions.skipMemoryCache(true);
        }
        if (options.getImageSize() != null) {
            requestOptions.override(options.getImageSize().getWidth(), options.getImageSize().getHeight());
        }

        List<Transformation> list = new ArrayList<Transformation>();
        if (options.isBlurImage()) {
            list.add(new BlurTransformation(options.getBlurValue()));
//            requestOptions.transforms(new BlurTransformation(options.getBlurValue()));
        }
        if (options.needImageRadius()) {
            list.add(new RoundedCorners(options.getImageRadius()));
//            requestOptions.transforms(new RoundedCorners(options.getImageRadius()));
        }
        if (options.isCircle()) {
            list.add(new CircleTransformation());

//            requestOptions.transforms(new CircleTransformation());
        }
        if (list.size() > 0) {
            Transformation[] transformations = list.toArray(new Transformation[list.size()]);
            requestOptions.transforms(transformations);

        }


        RequestBuilder builder = getRequestBuilder(options);
        builder.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {

                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onFail();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (options.getLoaderResultCallBack() != null) {
                    options.getLoaderResultCallBack().onSuccess();
                }
                return false;
            }
        });

        builder.apply(requestOptions).into((ImageView) options.getViewContainer());
    }

    private RequestBuilder getRequestBuilder(ImageLoaderOptions options) {
        RequestBuilder builder = null;
        if (options.isAsGif()) {
            builder = getRequestManager(options.getViewContainer()).asGif();
        } else {
            builder = getRequestManager(options.getViewContainer()).asBitmap();
        }

        if (!TextUtils.isEmpty(options.getUrl())) {
            builder.load(options.getUrl());
        } else {
            builder.load(options.getResource());
        }
        return builder;

    }

    private RequestManager getRequestManager(View view) {
        return Glide.with(view);

    }

    @Override
    public void hideImage(@NonNull View view, int visiable) {
        if (view != null) {
            view.setVisibility(visiable);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory();
        }
    }

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void init(Context context, ImageLoaderConfig config) {

    }

}
