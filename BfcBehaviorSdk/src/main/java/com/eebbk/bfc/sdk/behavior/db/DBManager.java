package com.eebbk.bfc.sdk.behavior.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.cache.CachePool;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.db.constant.BCConstants;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBManager {

    private static final String TAG = "DBManager";
    private static class InstanceHolder {
        private static final DBManager mInstance = new DBManager();
    }

    public static DBManager getInstance() {
        return InstanceHolder.mInstance;
    }

    /**
     * 插入记录
     *
     * @param events
     * @return
     */
    public void insert(IEvent events) {
        synchronized(TAG){
            if (events == null || ContextUtils.isEmpty()) {
                return;
            }
            try {
                getResolver().insert(BehaviorCollector.getInstance().getContentUri(), events.getContentValues());
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_PROVIDER);
                CachePool.getInstance().cache(events);
            }
        }
    }

    /**
     * 批量插入记录
     *
     * @param events
     * @return
     */
    public void insertEvents(List<IEvent> events) {
        synchronized(TAG) {
            if (ListUtils.isEmpty(events) || ContextUtils.isEmpty()) {
                return;
            }
            int size = events.size();
            ContentValues[] arrayValues = new ContentValues[size];
            for (int i = 0; i < size; i++) {
                IEvent event = events.get(i);
                if (event == null) {
                    continue;
                }
                arrayValues[i] = event.getContentValues();
            }
            try {
                getResolver().bulkInsert(BehaviorCollector.getInstance().getContentUri(), arrayValues);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_PROVIDER);
                CachePool.getInstance().cache(events);
            }
        }
    }

    /**
     * 批量插入记录
     *
     * @param records
     * @return
     */
    public void insertRecords(List<Record> records) {
        synchronized(TAG) {
            if (ListUtils.isEmpty(records) || ContextUtils.isEmpty()) {
                return;
            }
            int size = records.size();
            ContentValues[] arrayValues = new ContentValues[size];
            for (int i = 0; i < size; i++) {
                arrayValues[i] = records.get(i).getContentValues();
            }
            try {
                getResolver().bulkInsert(BehaviorCollector.getInstance().getContentUri(), arrayValues);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_PROVIDER);
            }
        }
    }

    /**
     * 记录条数
     *
     * @return
     */
    public int getRecordcount() {
        synchronized(TAG) {
            int counter = 0;
            Cursor cursor = null;
            try {
                cursor = getResolver().query(BehaviorCollector.getInstance().getContentUri(), null, null, null, null);
                if (cursor != null) {
                    counter = cursor.getCount();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, e.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return counter;
        }
    }

    /**
     * 获取记录(只用作查询，不用作上报)
     *
     * @return
     */
    public List<Record> queryRecordList() {
        synchronized(TAG) {
            Cursor cursor = null;
            List<Record> lists = new ArrayList<Record>();
            try {
                cursor = getResolver().query(BehaviorCollector.getInstance().getContentUri(), null, null, null, BFCColumns._ID);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        lists.add(new Record(cursor));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                LogUtils.e(TAG, e.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return lists;
        }
    }

    /**
     * 获取记录（上报）
     *
     * @return
     */
    public List<Record> getRecordList(int size) {
        synchronized(TAG) {
            Cursor cursor = null;
            List<Record> lists = new ArrayList<Record>();
            try {
                cursor = getResolver().query(BehaviorCollector.getInstance().getContentUri(), null, BFCColumns.COLUMN_INNER_STATE + "='"+ BCConstants.RecordState.IDLE +"'", null, BFCColumns._ID + " limit " + size + " offset 0");
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        lists.add(new Record(cursor));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                LogUtils.e(TAG, e.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            if (lists.size() > 0) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(BFCColumns.COLUMN_INNER_STATE, BCConstants.RecordState.UPLOADING);
                    String[] whereArgs = getWhereArgsForIds(lists);
                    LogUtils.i(TAG, "update record state to " + BCConstants.RecordState.UPLOADING
                            + " ids:" + Arrays.toString(whereArgs));
                    getResolver().update(BehaviorCollector.getInstance().getContentUri(), values,
                            getWhereClauseForIds(lists), whereArgs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return lists;
        }
    }

    /**
     * 删除重复数据和已经上报的数据
     */
    public void deleteRepeatRecord() {
        synchronized (TAG) {
            try {
                getResolver().delete(BehaviorCollector.getInstance().getContentUri(), BFCColumns._ID
                        + " not in ( select min(" + BFCColumns._ID + ") from " + BCConstants.TABLE_NAME
                        + " where " + BFCColumns.COLUMN_INNER_STATE + " <> '" + BCConstants.RecordState.UPLOADED + "'"
                        + " group by "
                        + BFCColumns.COLUMN_AA_MODULENAME + ","
                        + BFCColumns.COLUMN_EA_EVENTNAME + ","
                        + BFCColumns.COLUMN_EA_EVENTTYPE + ","
                        + BFCColumns.COLUMN_EA_TTIME + ","
                        + BFCColumns.COLUMN_EA_PAGE + ","
                        + BFCColumns.COLUMN_EA_MODULEDETAIL + ","
                        + BFCColumns.COLUMN_OA_EXTEND + ","
                        + BFCColumns.COLUMN_EA_FUNCTIONNAME + ")", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除数据
     */
    public void deleteRepeatRecord(int id) {
        synchronized (TAG) {
            try {
                getResolver().delete(BehaviorCollector.getInstance().getContentUri(), BFCColumns._ID + " = '" + id + "'", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新记录状态
     *
     * @return
     */
    public void updateRecordState(List<Integer> lists, int state) {
        synchronized(TAG) {
            if (!ListUtils.isEmpty(lists)) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(BFCColumns.COLUMN_INNER_STATE, state);
                    LogUtils.i(TAG, "update record state to " + state + " ids:" + Arrays.toString(lists.toArray()));
                    getResolver().update(BehaviorCollector.getInstance().getContentUri(), values,
                            getWhereClauseForIds(lists), getWhereArgsForIds(lists));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取记录（上报）
     *
     * @return
     */
    public void reloadAbnormalRecord() {
        synchronized(TAG) {
            try {
                ContentValues values = new ContentValues();
                values.put(BFCColumns.COLUMN_INNER_STATE, BCConstants.RecordState.IDLE);
                int number = getResolver().update(BehaviorCollector.getInstance().getContentUri(), values,
                        BFCColumns.COLUMN_INNER_STATE + " = ? ", new String[]{String.valueOf(BCConstants.RecordState.UPLOADING)});
                LogUtils.i(TAG, "reloadAbnormalRecord " + number);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getWhereClauseForIds(List lists){
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("(");
        int len = lists.size();
        for(int i = 0; i < len; i++){
            if(i > 0){
                whereClause.append("OR ");
            }
            whereClause.append(BFCColumns._ID);
            whereClause.append(" = ? ");
        }
        whereClause.append(")");

        return whereClause.toString();
    }

    private String[] getWhereArgsForIds(List lists){
        int len = lists.size();
        String[] whereArgs = new String[len];
        for(int i = 0; i < len; i++){
            Object item = lists.get(i);
            if(item instanceof Record){
                whereArgs[i] = Integer.toString(((Record)item).getId());
            } else if(item instanceof Integer){
                whereArgs[i] = String.valueOf(item);
            }
        }
        return whereArgs;
    }

    private ContentResolver getResolver() {
        return ContextUtils.getContext().getContentResolver();
    }

    private DBManager() {
        //prevent the instance
    }
}
