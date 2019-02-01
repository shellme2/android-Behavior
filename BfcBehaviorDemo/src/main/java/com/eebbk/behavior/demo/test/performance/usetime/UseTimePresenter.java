package com.eebbk.behavior.demo.test.performance.usetime;

import android.os.SystemClock;
import android.text.TextUtils;

import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.behavior.demo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-9-29
 * @company 步步高教育电子有限公司
 */

class UseTimePresenter {

    private boolean mRunning = false;
    private StringBuilder mResultHistory = new StringBuilder(100);
    private OnUseTimeListener mOnUseTimeListener;
    private List<UseTimeTestInfo> mRunningList;
    private int mSelectedPosition;
    private static final String SEPARATOR = "\n";
    private static final int MAX_TIMES = 100;
    private static final int TIME_SPAN = 2000;
    private long mlastTime = -1;

    public UseTimePresenter(OnUseTimeListener l){
        mOnUseTimeListener = l;
        mRunningList = new RunningList().getList();
    }

    public List<String> getSpinnerList(){
        List<String> list = new ArrayList<String>();
        for (UseTimeTestInfo info : mRunningList){
            list.add(info.getMethodName());
        }
        return list;
    }

    public void selected(int position){
        mSelectedPosition = position;
    }

    /**
     * 开始测试
     * @param timesStr 测试次数
     */
    public void start(String timesStr) {
        start(timesStr, false);
    }

    /**
     * 一键测试
     * @param timesStr
     */
    public void startOnekeyTest(String timesStr){
        start(timesStr, true);
    }

    public void stop(){
        mRunning = false;
    }

    /**
     * 开始测试
     * @param timesStr 测试次数
     */
    private void start(String timesStr, final boolean isOneKey) {
        if(!checkInput(timesStr)){
            return;
        }

        final long times = StringUtils.str2Long(timesStr);
        if(times <= 0){
            mOnUseTimeListener.onUseTimeTestResult(showWithHistory("请输入正确的次数"));
            return;
        }

        if(times > MAX_TIMES){
            mOnUseTimeListener.onUseTimeTestResult(showWithHistory(TextUtils.concat(
                    "输入次数不宜超过", String.valueOf(MAX_TIMES), "次, 否则会影响耗时测试准确性!"
            ).toString()));
            return;
        }

        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                doTest(times, isOneKey);
            }
        });
    }

    private void doTest(long times, boolean isOneKey){
        mRunning = true;
        mOnUseTimeListener.onUseTimeTestResult(saveHistory("==== 开始测试 ====" + SEPARATOR));
        if(isOneKey){
            int size = mRunningList.size();
            for (int i = 0; i < size; i++) {
                if(!mRunning){
                    return;
                }
                doTest(times, mRunningList.get(i).getMethodName(),
                        mRunningList.get(i).getMethod(), i == size -1);
            }
        }else{
            doTest(times, mRunningList.get(mSelectedPosition).getMethodName(),
                    mRunningList.get(mSelectedPosition).getMethod(), true);
        }
    }

    private void doTest(final long times, final String methodName, final Runnable runnable, boolean isEnd){
        if(runnable == null){
            mRunning = !isEnd;
            return;
        }
        mOnUseTimeListener.onUseTimeTestResult(showWithHistory("正在运行" + methodName + "......"));
        long start = SystemClock.elapsedRealtime();
        for (int i = 0; i < times; i++) {
            if(!mRunning){
                return;
            }
            runnable.run();
        }
        long end = SystemClock.elapsedRealtime();
        mOnUseTimeListener.onUseTimeTestResult(saveHistory(resultFormat(methodName, times, start, end)));
        if(isEnd){
            mOnUseTimeListener.onUseTimeTestResult(saveHistory(SEPARATOR + "==== 测试结束 ===="));
            mRunning = false;
        }
    }

    private boolean checkInput(String timesStr){
        if(mOnUseTimeListener == null){
            return false;
        }

        if(mRunning){
            mOnUseTimeListener.onUseTimeTestResult(
                    showWithHistory("正在运行......请耐心等待"));
            return false;
        }

        if (TextUtils.isEmpty(timesStr)) {
            mOnUseTimeListener.onUseTimeTestResult(showWithHistory("请输入正确的次数"));
            return false;
        }

        long time = SystemClock.elapsedRealtime();
        if(time - mlastTime <= TIME_SPAN){
            mOnUseTimeListener.onUseTimeTestResult(showWithHistory("请不要操作太频繁"));
            return false;
        }
        mlastTime = time;

        return true;
    }

    private String resultFormat(String methodName, long times, long start, long end){
        return methodName + SEPARATOR + " 运行 " + times + " 次，用时： " + (end - start) + " 毫秒";
    }

    /**
     * 保存历史记录
     * @param result 返回保存新数据后的历史记录
     * @return
     */
    private String saveHistory(String result){
        mResultHistory.insert(0, result + SEPARATOR + SEPARATOR);
        return mResultHistory.toString();
    }

    /**
     * 显示结果
     * <p> 带历史记录，但是不保存到历史记录中 </p>
     * @param result
     * @return
     */
    private String showWithHistory(String result){
        return result + "\n" + mResultHistory.toString();
    }

    public interface OnUseTimeListener{
        /**
         * 反馈结果
         * @param result
         */
        void onUseTimeTestResult(String result);
    }
}
