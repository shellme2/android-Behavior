package com.eebbk.behavior.demo.test.performance.usetime;

/**
 * @author hesn
 * @function
 * @date 16-9-29
 * @company 步步高教育电子有限公司
 */

public class UseTimeTestInfo {

    private Runnable runnable;
    private String methodName;

    public UseTimeTestInfo setMethod(Runnable runnable){
        this.runnable = runnable;
        return this;
    }

    public UseTimeTestInfo setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Runnable getMethod() {
        return runnable;
    }

    public String getMethodName() {
        return methodName;
    }
}
