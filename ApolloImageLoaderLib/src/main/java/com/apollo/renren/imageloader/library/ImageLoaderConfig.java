package com.apollo.renren.imageloader.library;

import java.util.HashMap;

public class ImageLoaderConfig {
    private HashMap<LoaderType, IImageLoaderStrategy> imageloaderMap;
    private long maxMemory = 0;

    private ImageLoaderConfig(Builder builder) {
        imageloaderMap = builder.imageloaderMap;
        maxMemory = builder.maxMemory;
    }

    public long getMaxMemory() {
        return maxMemory <= 0 ? 40 * 1024 * 1024 : maxMemory;
    }

    public HashMap<LoaderType, IImageLoaderStrategy> getImageloaderMap() {
        return imageloaderMap;
    }

    public static class Builder {
        private HashMap<LoaderType, IImageLoaderStrategy> imageloaderMap = new HashMap<>();
        private long maxMemory = 40 * 1024 * 1024;

        public Builder(LoaderType type, IImageLoaderStrategy loaderstrategy) {
            imageloaderMap.put(type, loaderstrategy);
        }

        public Builder addImageLodaer(LoaderType type, IImageLoaderStrategy loaderstrategy) {
            imageloaderMap.put(type, loaderstrategy);
            return this;
        }

        /**
         * @param maxMemory 单位为 Byte
         * @return
         */
        public Builder maxMemory(Long maxMemory) {
            this.maxMemory = maxMemory;
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }
    }
}
