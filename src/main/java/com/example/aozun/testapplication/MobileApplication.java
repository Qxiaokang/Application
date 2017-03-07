package com.example.aozun.testapplication;

import android.graphics.Bitmap;

import com.example.aozun.testapplication.utils.CrashHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;

import java.io.File;

/**
 * Created by HHD-H-I-0369 on 2017/1/17.
 *
 */
public class MobileApplication extends LitePalApplication{

    @Override
    public void onCreate(){
        super.onCreate();
        initImageloaders();//初始化imagloader
        CrashHandler.getInstance().init(getApplicationContext());

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);

    }

    //初始化imageloader
    public void initImageloaders(){
        //这里的路径可以自定义
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .build();
               /* // 默认等于你的屏幕尺寸，设备屏幕宽高
                .memoryCacheExtraOptions(480, 800)
                // 在将下载的图片保存到你的sd卡之前会重新计算，压缩。
                // 这个属性不要滥用，只有你在对应的需求时再用，因为他会使你的ImageLoader变的很慢。
                .diskCacheExtraOptions(480, 800, null)
                //用于执行从源获取图片任务的 Executor，为configuration中的 taskExecutor，
                // 如果为null，则会调用DefaultConfigurationFactory.createExecutor(…)根据配置返回一个默认的线程池。
                .taskExecutor(null)
                //用于执行从缓存获取图片任务的 Executor，为configuration中的 taskExecutorForCachedImages，
                // 如果为null，则会调用DefaultConfigurationFactory.createExecutor(…)根据配置返回一个默认的线程池。
                .taskExecutorForCachedImages(null)
                // 表示核心池大小(最大并发数) 默认为3
                .threadPoolSize(3)
                // 线程优先级，默认Thread.NORM_PRIORITY - 2
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // 任务进程的顺序，默认为：FIFO 先进先出
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                //设置内存缓存不允许缓存一张图片的多个尺寸，默认允许。
                .denyCacheImageMultipleSizesInMemory()
                //图片内存缓存
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                //memoryCacheSize 为 0，则设置该内存缓存的最大字节数为 App 最大可用内存的 1/8。
                .memoryCacheSize(2 * 1024 * 1024)
                // 创建最大的内存缓存百分比，默认为 13%
                .memoryCacheSizePercentage(13)
                // 硬盘缓存路径，默认为StorageUtils.getCacheDirectory(context)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                //硬盘缓存大小
                .diskCacheSize(50 * 1024 * 1024)
                //缓存文件数量
                .diskCacheFileCount(100)
                // 硬盘缓存文件名生成器，默认为哈希文件名生成器
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // 创建图片下载器，默认是BaseImageDownloader
                .imageDownloader(new BaseImageDownloader(BaseActivity.this))
                // 图片解码器，负责将图片输入流InputStream转换为Bitmap对象
                .imageDecoder(new BaseImageDecoder(true))
                // 图片显示的配置项。比如加载前、加载中、加载失败应该显示的占位图片，图片是否需要在磁盘缓存，是否需要在内存缓存等。
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                //是否显示调试log信息
                .writeDebugLogs().build();*/
        ImageLoader.getInstance().init(configuration);
    }
    //圆形图
    public static DisplayImageOptions getCircleOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 正在加载时显示的占位图
                .showImageOnLoading(R.drawable.normal)
                // URL为空时显示的占位图
                .showImageForEmptyUri(R.drawable.normal)
                // 加载失败时显示的占位图
                .showImageOnFail(R.drawable.normal).bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();
        return options;

              /*  // 在加载前是否重置 view，默认为false
                .resetViewBeforeLoading(false)
                //设置在开始加载前的延迟时间，单位为毫秒，通过 Builder 构建的对象默认为 0
                .delayBeforeLoading(1000)
                // 是否缓存在内存中，通过 Builder 构建的对象默认为 false
                .cacheInMemory(false)
                // 是否缓存在磁盘中，通过 Builder 构建的对象默认为 false。
                .cacheOnDisk(false)
                //缓存在内存之前的处理程序，默认为 null
                .preProcessor(null)
                //缓存在内存之后的处理程序，默认为 null。
                .postProcessor(null)
                //下载器需要的辅助信息。下载时传入ImageDownloader.getStream(String, Object)的对象，方便用户自己扩展，默认为 null。
                .extraForDownloader(null)
                // 是否考虑图片的 EXIF 信息，通过 Builder 构建的对象默认为 false。
                .considerExifParams(false)
                // 图片的缩放类型，通过 Builder 构建的对象默认为IN_SAMPLE_POWER_OF_2
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                // bitmap的质量，默认为ARGB_8888
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                //为 BitmapFactory.Options，用于BitmapFactory.decodeStream(imageStream, null, decodingOptions)得到图片尺寸等信息。
                .decodingOptions(null)
                // 在ImageAware中显示 bitmap 对象的接口。可在实现中对 bitmap 做一些额外处理，比如加圆角、动画效果。
                .displayer(new SimpleBitmapDisplayer())
                // handler 对象，默认为 null
                .handler(new Handler())*/
    }

    //方形图
    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.normal)
                .showImageOnLoading(R.drawable.normal)
                .showImageOnFail(R.drawable.normal)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        return options;
    }


}
