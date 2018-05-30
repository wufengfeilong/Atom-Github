package woxingwoxiu.com;

import android.app.Application;

import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * “我行我秀”APP的Application
 */

public class ShowApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // KSYUN Demo建议在app加载时对DeviceInfoTools进行初始化，以便最快拿到设备信息
        // 初始化本地存储，若本地无信息或者信息已经过期，会向服务器发起请求
        DeviceInfoTools.getInstance().init(this);

        // ImageLoader初始化
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        // 第三方登录分享SDK初期化
        MobSDK.init(this);
    }
}
