package com.eebbk.behavior.demo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hesn
 * @function 线程池
 * @date 16-8-12
 * @company 步步高教育电子有限公司
 */

public class ExecutorsUtils {

    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(10);

    /**
     * 执行线程
     * @param command
     */
    public static void execute(Runnable command){
        mExecutorService.execute(command);
    }

    private ExecutorsUtils(){

    }
}
