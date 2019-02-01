package com.eebbk.bfc.sdk.behavior.db.provider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.eebbk.bfc.sdk.behavior.db.constant.BCConstants;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 这个类继承SQLiteOpenHelper抽象类，用于创建数据库和表。创建数据库是调用它的父类构造方法创建。
 *
 * @author HB
 */
class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBOpenHelper";

    private DBOpenHelper(Context context, String name, CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    /**
     * 只有当数据库执行创建 的时候，才会执行这个方法。如果更改表名，也不会创建，只有当创建数据库的时候，才会创建改表名之后 的数据表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, BCConstants.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //有数据库升级时实现
        LogUtils.i(TAG, "oldVersion=" + oldVersion + ",newVersion" + newVersion);
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            upgradeTo(db, version);
        }
    }

    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                LogUtils.v(TAG, "DBVersion=" + 1);
                db.execSQL(BCConstants.CREATE_TABLE);
                break;
            case 2:
                LogUtils.v(TAG, "DBVersion=" + 2);
                //	插入字段	 	ALTER TABLE  表名   ADD  字段名  类型   default '默认值'
                String SQL_addSessionId = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_EA_SESSIONID + " TEXT default '0'";
                db.execSQL(SQL_addSessionId);
                break;
            case 3:
                LogUtils.v(TAG, "DBVersion=" + 3);
                //	插入字段	 	ALTER TABLE  表名   ADD  字段名  类型   default '默认值'
                String sqlJudgmentExtend = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_OA_EXTENDJUDGMENT + " TEXT ";
                db.execSQL(sqlJudgmentExtend);

                break;
            case 4:
            case 5:
            case 6:
                //历史遗留问题,版本号不统一导致,不想解释
                break;
            case 7:
                //施伟彬发了一个inner.ver.20161123的版本
                //里面在db ver 3 加了一个"imei"字段,但是没有加"judgmentExtend"字段
                LogUtils.v(TAG, "DBVersion=" + 7);
                //	插入字段	 	ALTER TABLE  表名   ADD  字段名  类型   default '默认值'
                try {
                    String sqlJudgmentExtend1 = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_OA_EXTENDJUDGMENT + " TEXT ";
                    db.execSQL(sqlJudgmentExtend1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    String sqlImei = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_OA_IMEI + " TEXT ";
                    db.execSQL(sqlImei);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                String[] columns = new String[]{
                        BFCColumns.COLUMN_UA_AGE, BFCColumns.COLUMN_UA_SCHOOL, BFCColumns.COLUMN_UA_GRADETYPE, BFCColumns.COLUMN_UA_SUBJECTS
                };
                for (String column : columns){
                    String sql = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + column + " TEXT ";
                    db.execSQL(sql);
                }
                break;
            case 9:
                String stateSql = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_INNER_STATE + " INTEGER default " + BCConstants.RecordState.IDLE;
                db.execSQL(stateSql);
                break;
            case 10:
                String innerModelSql = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_OA_INNER_MODEL + " TEXT ";
                db.execSQL(innerModelSql);
                break;
            case 11:
                String romverSql = "ALTER TABLE " + BCConstants.TABLE_NAME + " ADD " + BFCColumns.COLUMN_OA_ROMVER + " TEXT ";
                db.execSQL(romverSql);
                break;
            default:
                break;
        }
    }
}