package com.eebbk.behavior.demo;

import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.bfc.common.app.AppUtils;
import com.github.moduth.blockcanary.BlockCanaryContext;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 17-4-18
 * @company 步步高教育电子有限公司
 */

public class AppContext extends BlockCanaryContext {

    @Override
    public String provideQualifier() {
        return AppUtils.getVersionCode(MyApplication.getContext()) + "_" + AppUtils.getVersionName(MyApplication.getContext()) + "_YYB";
    }

    @Override
    public boolean monkeyTest() {
        return ConfigUtils.isMonkeyTest;
    }

    @Override
    public String provideUid() {
        // 用户ID，对于我们来讲，可以随便写
        return "C2";
    }

    @Override
    public String provideNetworkType() {
        // 这个不知道是干什么的，先不管它
        return "4G";
    }

    @Override
    public int provideMonitorDuration() {
        // 单位是毫秒
        return 10*1000;
    }

    @Override
    public int provideBlockThreshold() {
        // 主要配置这里，多长时间的阻塞算是卡顿，单位是毫秒
        return 500;
    }

    @Override
    public boolean displayNotification() {
        return true;
    }

    @Override
    public List<String> concernPackages() {
        List<String> list = super.provideWhiteList();

        return list;
    }

    @Override
    public List<String> provideWhiteList() {
        // 白名单，哪些包名的卡顿不算在内
        List<String> list = super.provideWhiteList();
        return list;
    }

    @Override
    public boolean stopWhenDebugging() {
        return true;
    }

}
