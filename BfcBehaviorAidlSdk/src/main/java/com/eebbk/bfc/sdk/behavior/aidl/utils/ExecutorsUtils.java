package com.eebbk.bfc.sdk.behavior.aidl.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hesn
 * @function 线程池
 * @date 16-8-12
 * @company 步步高教育电子有限公司
 */

public class ExecutorsUtils {

    //公用的线程池
    private final ExecutorService mExecutorService = Executors.newFixedThreadPool(10);

    public static ExecutorsUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 执行公用线程
     * @param command
     */
    public void execute(Runnable command){
        mExecutorService.execute(command);
    }

    private static class Holder {
        public static final ExecutorsUtils INSTANCE = new ExecutorsUtils();
    }

    private ExecutorsUtils() {
        //prevent the instance
    }
}
