package com.eebbk.behavior.demo.test.function.mswitch;

import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.utils.NetUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.uploadsdk.upload.net.NetworkType;

/**
 * @author hesn
 * @function
 * @date 16-10-11
 * @company 步步高教育电子有限公司
 */

public class ModuleSwitchPresenter {

    private BehaviorConfig mConfig;

    public ModuleSwitchPresenter(){
        mConfig = BehaviorCollector.getInstance().getConfig();
    }

    /**
     * 行为采集开关
     * @return
     */
    public boolean enable(){
        return mConfig.usable;
    }

    /**
     * 自动采集activity使用时长
     * @return
     */
    public boolean openActivityDurationTrack(){
        return mConfig.openActivityDurationTrack;
    }

    /**
     * 缓存开关
     * @return
     */
    public boolean enableCache(){
        return mConfig.cacheConfig.usable;
    }

    /**
     * 上报开关
     * @return
     */
    public boolean enableReport(){
        return mConfig.reportConfig.usable;
    }

    /**
     * Debug模式开关
     * @return
     */
    public boolean enableDebugMode(){
        return mConfig.isDebugMode;
    }

    /**
     * 缓存log日志
     * @return
     */
    public boolean enableCacheLog(){
        return mConfig.isCacheLog;
    }

    /**
     * 应用异常捕获
     * @return
     */
    public boolean enableCrash(){
        return mConfig.crashConfig.usable;
    }

    /**
     * 应用异常捕获 toast
     * @return
     */
    public boolean enableCrashToast(){
        return mConfig.crashConfig.isToast;
    }

    /**
     * 应用异常捕获 ui
     * @return
     */
    public boolean enableCrashUI(){
        return mConfig.crashConfig.isUIReport;
    }

    /**
     * 允许wifi上报
     * @return
     */
    public boolean enableNetworkWifi(){
        return NetUtils.containsNetworkType(mConfig.reportConfig.mNetworkTypes, NetworkType.NETWORK_WIFI);
    }

    /**
     * 允许移动网络上报
     * @return
     */
    public boolean enableNetworkMobile(){
        return NetUtils.containsNetworkType(mConfig.reportConfig.mNetworkTypes, NetworkType.NETWORK_MOBILE);
    }

//    /**
//     * 允许2G上报
//     * @return
//     */
//    public boolean enableNetwork2G(){
//        return NetUtils.containsNetworkType(mConfig.reportConfig.mNetworkTypes, NetworkType.NETWORK_MOBILE_2G);
//    }
//
//    /**
//     * 允许3G上报
//     * @return
//     */
//    public boolean enableNetwork3G(){
//        return NetUtils.containsNetworkType(mConfig.reportConfig.mNetworkTypes, NetworkType.NETWORK_MOBILE_3G);
//    }
//
//    /**
//     * 允许4G上报
//     * @return
//     */
//    public boolean enableNetwork4G(){
//        return NetUtils.containsNetworkType(mConfig.reportConfig.mNetworkTypes, NetworkType.NETWORK_MOBILE_4G);
//    }

    public void save(boolean...switchs){
        mConfig.usable = switchs[0];
        mConfig.cacheConfig.usable = switchs[1];
        mConfig.reportConfig.usable = switchs[2];
        mConfig.isDebugMode = switchs[3];
        mConfig.openActivityDurationTrack = switchs[4];
        mConfig.isCacheLog = switchs[5];
        mConfig.crashConfig.usable = switchs[6];
        mConfig.crashConfig.isToast = switchs[7];
        mConfig.crashConfig.isUIReport = switchs[8];

        int network = 0;
        if(switchs[9]){
            network = addNetworkType(network, NetworkType.NETWORK_WIFI);
        }
        if(switchs[10]){
            network = addNetworkType(network, NetworkType.NETWORK_MOBILE);
        }
//        if(switchs[11]){
//            network = addNetworkType(network, NetworkType.NETWORK_MOBILE_2G);
//        }
//        if(switchs[12]){
//            network = addNetworkType(network, NetworkType.NETWORK_MOBILE_3G);
//        }
//        if(switchs[13]){
//            network = addNetworkType(network, NetworkType.NETWORK_MOBILE_4G);
//        }

        mConfig.reportConfig.mNetworkTypes = network;
        LogUtils.i("ModuleSwitchPresenter","network:" + Integer.toBinaryString(network));

        BehaviorCollector.getInstance().setConfig(mConfig);
        ConfigUtils.saveConfig();
    }

    public int addNetworkType(int curNetworkTypes, int newNetworkType){
        return curNetworkTypes | newNetworkType;
    }
}
