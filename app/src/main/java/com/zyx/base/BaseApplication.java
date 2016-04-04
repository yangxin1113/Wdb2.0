package com.zyx.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.zyx.utils.GetApiData;
import com.zyx.R;
import com.zyx.utils.HttpClientUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zyx on 2016/2/11.
 */
public abstract class BaseApplication extends Application{

    private final int THREAD_SIZE = 5;

    public static DisplayImageOptions pagerOptions;
    public static DisplayImageOptions options;

    public static ExecutorService executorService;//线程池

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newCachedThreadPool();
        String ip = initDomain();
        GetApiData.URLS = ip;
        HttpClientUtil.URLS = ip;


        initImageLoader(getApplicationContext());

        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     *
     * 初始化域名
     *
     * @return 域名
     */
    protected abstract String initDomain();

    /**
     * imageLoader相关
     *
     * @param context
     *            上下文
     */
    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSize(20 * 1024 * 1024)
                        // 加载到内存大小，超过此大小会释放
                .diskCacheSize(500 * 1024 * 1024).threadPoolSize(THREAD_SIZE)
                        // 保存至sd卡大小
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 设置options
     */
    private void setOptions() {
        pagerOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.sorry)
                .showImageOnFail(R.mipmap.errorimg)
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.waitnet)
                .showImageForEmptyUri(R.mipmap.sorry)
                .showImageOnFail(R.mipmap.errorimg).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                        // RoundedBitmapDisplayer(0)参数是圆角半径
                .build();
    }

    /**
     * 获取用户头像Img属性
     *
     * @return
     */
    public DisplayImageOptions getUserHeadOptions() {
        DisplayImageOptions userHeadOption = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.user_head_loading_img)
                .showImageForEmptyUri(R.mipmap.user_head_loading_img)
                .showImageOnFail(R.mipmap.user_head_loading_img)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                        // RoundedBitmapDisplayer(0)参数是圆角半径
                .build();
        return userHeadOption;
    }

}
