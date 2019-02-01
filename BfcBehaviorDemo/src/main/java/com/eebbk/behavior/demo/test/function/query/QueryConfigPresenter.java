package com.eebbk.behavior.demo.test.function.query;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.eebbk.behavior.demo.test.function.query.aidl.QueryConfigRemoteManager;
import com.eebbk.behavior.demo.test.function.query.report.AnrReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.CrashReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.DebugReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.Error;
import com.eebbk.behavior.demo.test.function.query.report.IReport;
import com.eebbk.behavior.demo.test.function.query.report.InitFailReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.MasterSwitchReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.BindFailReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.TotalReportImpl;
import com.eebbk.behavior.demo.test.function.query.report.VersionLowReportImpl;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.devices.MediaLibraryUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.Constant;
import com.eebbk.bfc.sdk.behavior.aidl.utils.StorageUtils;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

class QueryConfigPresenter {

    private QueryConfigRemoteManager mQueryRemoteMgr;
    private IConfigRefreshUI mRefreshUI;
    private boolean isQuery = false;
    private List<QueryConfigInfo> mLastResult = new ArrayList<>();
    private static final String TAG = "QueryConfigPresenter";
    private List<IReport> mReport;
    private AtomicBoolean mBind = new AtomicBoolean();

    QueryConfigPresenter() {
        mQueryRemoteMgr = new QueryConfigRemoteManager();
        mQueryRemoteMgr.setOnRemoteConnectListener(mRemoteConnectListener);
        mReport = new ArrayList<>();
        mReport.add(new TotalReportImpl());
        mReport.add(new BindFailReportImpl());
        mReport.add(new VersionLowReportImpl());
        mReport.add(new InitFailReportImpl());
        mReport.add(new MasterSwitchReportImpl());
        mReport.add(new CrashReportImpl());
        mReport.add(new AnrReportImpl());
        mReport.add(new DebugReportImpl());
    }

    public void setOnRefreshUIListener(IConfigRefreshUI refreshUI) {
        synchronized (QueryConfigPresenter.class){
            mRefreshUI = refreshUI;
        }
    }

    public boolean isQuery(){
        return isQuery;
    }

    public void query(final Context context, final List<String> packageNameList) {
        synchronized (QueryConfigPresenter.class) {
            if (mRefreshUI == null) {
                return;
            }
            if (isQuery) {
                mRefreshUI.onError("正在查询,请稍后...");
                return;
            }
            isQuery = true;
            ExecutorsUtils.execute(new Runnable() {
                @Override
                public void run() {
                    List<QueryConfigInfo> list = new ArrayList<>();
                    int totle = packageNameList.size();
                    String configStr;
                    int versionCode;
                    boolean init;
                    int errorCode;
                    for (int i = 0; i < totle; i++) {
                        String appPackageName = packageNameList.get(i);
                        if (context == null || TextUtils.isEmpty(appPackageName)) {
                            if(mRefreshUI != null){
                                mRefreshUI.onError("找不到该包名应用，请确认包名是否正确");
                            }
                            isQuery = false;
                            return;
                        }
                        try {
                            if(mRefreshUI == null) {
                                isQuery = false;
                                return;
                            }
                            mRefreshUI.onProgress(totle, i, "read " + appPackageName + " config...", false);
                            //因为aidl的action是可以动态设置，所以每次都绑定获取后就解绑
                            mQueryRemoteMgr.bindService(context, appPackageName);
                            try {
                                // 1.aidl绑定耗时
                                // 2.需要等待app application初始化完后获取的配置信息才准确
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            configStr = mQueryRemoteMgr.queryConfig();
                            versionCode = -1;
                            init = false;
                            errorCode = Error.Code.SUCCESS;
                            BehaviorConfig behaviorConfig = null;
                            if(!TextUtils.isEmpty(configStr)){
                                JSONObject jsonObject = new JSONObject(configStr);
                                versionCode = jsonObject.getInt(Constant.Remote.Key.VERSION_CODE);
                                init = jsonObject.getBoolean(Constant.Remote.Key.INIT);
                                behaviorConfig = JsonUtils.fromJson(configStr, BehaviorConfig.class);
                                if(!init) {
                                    errorCode = Error.Code.ERROR_INIT;
                                }
                                mRefreshUI.onProgress(-1, -1, " " + Error.getErrorMsg(errorCode), true);
                            }else {
                                errorCode = mBind.get() ? Error.Code.ERROR_VERSION_LOW : Error.Code.ERROR_BIND_FAIL;
                                mRefreshUI.onProgress(-1, -1, " " + Error.getErrorMsg(errorCode), true);
                            }
                            list.add(new QueryConfigInfo()
                                    .setInit(init)
                                    .setJson(configStr)
                                    .setVersionCode(versionCode)
                                    .setPackageName(appPackageName)
                                    .setAppName(AppUtils.getAppName(context, appPackageName))
                                    .setBehaviorConfig(behaviorConfig)
                                    .setErrorCode(errorCode));
                            mQueryRemoteMgr.unbindService(context);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            if(mRefreshUI != null) {
                                mRefreshUI.onError("找不到该包名应用，请确认包名是否正确");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(mRefreshUI != null) {
                                mRefreshUI.onError("error");
                            }
                        }
                    }
                    mLastResult.clear();
                    mLastResult.addAll(list);
                    if(mRefreshUI != null) {
                        mRefreshUI.onRefreshUI(list);
                    }
                    isQuery = false;
                }
            });
        }
    }

    List<String> getInstalledPackageList(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        LogUtils.e(TAG,"packages.size():" + packages.size());
        List<String> list = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            LogUtils.e(TAG,"packageInfo=" + packageInfo.packageName);
            if(TextUtils.isEmpty(packageInfo.packageName)){
                continue;
            }
            if(packageInfo.packageName.startsWith("com.eebbk.")
                    || packageInfo.packageName.startsWith("com.bbk.")
                    || packageInfo.packageName.startsWith("com.xtc.")){
                list.add(packageInfo.packageName);
            }
        }
        return list;
    }

    String getReport(Context context, List<QueryConfigInfo> configList, boolean isDetail){
        StringBuilder brief = new StringBuilder();
        StringBuilder detail = new StringBuilder("\n");
        for (IReport report : mReport) {
            report.clear();
            report.check(context, configList);
            brief.append(report.getBriefReport());
            detail.append(report.getDetailReport());
        }
        return isDetail ? brief.toString() + detail.toString() : brief.toString();
    }

    /**
     * 导出报告
     *
     * @param context
     */
    String exportFile(Context context) {
        List<QueryConfigInfo> configList = new ArrayList<>(mLastResult);
        if(configList.size() == 0){
            return "没有数据";
        }
        String filePath = StorageUtils.getExternalStorageFile().getAbsolutePath() + File.separator
                + "bfc_behavior_config_report_" + System.currentTimeMillis() + ".txt";
        FileUtils.createFileOrExists(filePath);
        MediaLibraryUtils.scanFile(context, filePath);
        try {
            FileUtils.writeFile(new File(filePath), getReport(context, configList, true), false);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    void destroy() {
        mRefreshUI = null;
        mQueryRemoteMgr.setOnRemoteConnectListener(null);
    }

    private QueryConfigRemoteManager.OnRemoteConnectListener mRemoteConnectListener = new QueryConfigRemoteManager.OnRemoteConnectListener() {
        @Override
        public void onBindSuccess(String packageName) {
            mBind.set(true);
            progress(" bind success");
        }

        @Override
        public void onBindFail(String packageName) {
            mBind.set(false);
            progress(" bind fail");
        }

        @Override
        public void onConnectedSuccess(String packageName) {
            mBind.set(true);
            progress(" connected");
        }

        private void progress(String text){
            if(mRefreshUI == null){
                return;
            }
            mRefreshUI.onProgress(-1, -1, text, false);
        }
    };
}
