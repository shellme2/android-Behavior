package com.eebbk.behavior.demo.common.mode;

/**
 * @author hesn
 * @function 埋点模式,正常和aidl
 * @date 17-4-13
 * @company 步步高教育电子有限公司
 */

public interface ITestBehaviorMode {

    void event(int type, String curActivityName, String functionName, String moduleDetail, String trigValue, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend);

    void realTime2Upload();
}
