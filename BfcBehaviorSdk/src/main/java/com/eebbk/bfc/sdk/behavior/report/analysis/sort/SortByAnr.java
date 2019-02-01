package com.eebbk.bfc.sdk.behavior.report.analysis.sort;

//import android.text.TextUtils;
//
//import com.eebbk.bfc.sdk.behavior.Constant;
//import com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType;
//import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
//import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
//import com.eebbk.bfc.sdk.behavior.db.entity.Record;
//import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
//import com.eebbk.bfc.sdk.behavior.report.upload.tasks.ReportAnrLog;
//import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
//import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
//import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * anr文件过滤
// * 由于服务器对上报文件做过滤，大小超过300K会直接抛弃
// * 所以大文件需要分开上报
// * anr里有日志信息的，需要把日志信息上传到七牛，埋点中只上报下载日志的url
// * @author hesn
// * 2018/8/31
// */
//public class SortByAnr implements ISort<List<Record>> {
//
//    private static final String TAG = "SortByAnr";
//
//    @Override
//    public List<Record> doSort(List<Record> list) {
//        return ListUtils.isEmpty(list) ? list : filterAnr(list);
//    }
//
//    private List<Record> filterAnr(List<Record> dataList) {
//        List<Record> filterList = new ArrayList<>();
//        List<Record> anrList = new ArrayList<>();
//        for (Record record : dataList) {
//            if(record == null){
//                continue;
//            }
//            if(needCloud(record)){
//                anrList.add(record);
//            }else {
//                filterList.add(record);
//            }
//        }
//        LogUtils.i(TAG, "可直接上报的数据 size=" + filterList.size());
//        LogUtils.i(TAG, "需要先上报anr日志的数据 size=" + anrList.size());
//        new ReportAnrLog().start(anrList);
//        return filterList;
//    }
//
//    private boolean needCloud(Record record){
//        // 只处理anr异常记录
//        if (record.getEventType() != EType.TYPE_EXCEPTION) {
//            return false;
//        }
//        Map trigValue = getTrigValueMap(record);
//        if(trigValue == null || !trigValue.containsKey(ExceptionEvent.TrigValueKey.TYPE)
//                || !TextUtils.equals(String.valueOf(trigValue.get(ExceptionEvent.TrigValueKey.TYPE)), CrashType.TYPE_ANR)){
//            return false;
//        }
//        String stack = String.valueOf(trigValue.get(ExceptionEvent.TrigValueKey.STACK));
//        if(TextUtils.isEmpty(stack) || !stack.contains(Constant.Exception.FLAG_ANR_LOG)){
//            return false;
//        }
//        return !trigValue.containsKey(ExceptionEvent.TrigValueKey.URL)
//                || TextUtils.isEmpty(String.valueOf(trigValue.get(ExceptionEvent.TrigValueKey.URL)));
//    }
//
//    private Map getTrigValueMap(Record record){
//        try {
//            return JsonUtils.fromJson(record.getTrigValue(), Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
