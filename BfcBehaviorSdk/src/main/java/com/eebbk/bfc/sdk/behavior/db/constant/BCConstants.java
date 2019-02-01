package com.eebbk.bfc.sdk.behavior.db.constant;

public interface BCConstants {
	String TAG = "BehaivorCollectorSystem";
	String DATABASE_NAME = "UserBehavior.db";
	int DATABASE_VERSION = 11;
	String TEXT = " TEXT,";
	String TABLE_NAME = "UserBehavior";
	String KEY_MODULE = "moduleName";
	String KEY_FUNCTION = "functionName";
	String KEY_TASK_FLAG = "taskFlag";
	String KEY_IDS = "ids";
	String KEY_RECORD = "record";
	String KEY_FILE_NAME = "fileName";
	String KEY_FILE_PATH = "filePath";

	String CREATE_TABLE = "CREATE TABLE " + BCConstants.TABLE_NAME + "("
			+ BFCColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ BFCColumns.COLUMN_MA_MID + TEXT
			+ BFCColumns.COLUMN_MA_DEVICE + TEXT
			+ BFCColumns.COLUMN_MA_OSVERSION + TEXT
			+ BFCColumns.COLUMN_MA_BRAND + TEXT

			+ BFCColumns.COLUMN_UA_USERID + TEXT
			+ BFCColumns.COLUMN_UA_USERNAME + TEXT
			+ BFCColumns.COLUMN_UA_SEX + TEXT
			+ BFCColumns.COLUMN_UA_BIRTHDAY + TEXT
			+ BFCColumns.COLUMN_UA_GRADE + TEXT
			+ BFCColumns.COLUMN_UA_PHONENUM + TEXT
			+ BFCColumns.COLUMN_UA_USEREXTEND + TEXT

			+ BFCColumns.COLUMN_AA_APPID + TEXT
			+ BFCColumns.COLUMN_AA_APPVER + TEXT
			+ BFCColumns.COLUMN_AA_MODULENAME + TEXT
			+ BFCColumns.COLUMN_AA_PACKAGENAME + TEXT

			+ BFCColumns.COLUMN_EA_EVENTNAME + TEXT
			+ BFCColumns.COLUMN_EA_FUNCTIONNAME + TEXT
			+ BFCColumns.COLUMN_EA_EVENTTYPE + " INTEGER,"
			+ BFCColumns.COLUMN_EA_TTIME + TEXT
			+ BFCColumns.COLUMN_EA_TVALUE + TEXT
			+ BFCColumns.COLUMN_EA_PAGE + TEXT
			+ BFCColumns.COLUMN_EA_MODULEDETAIL + TEXT

			+ BFCColumns.COLUMN_DA_DATAID + TEXT
			+ BFCColumns.COLUMN_DA_DATATYPE + TEXT
			+ BFCColumns.COLUMN_DA_DATATITLE + TEXT
			+ BFCColumns.COLUMN_DA_DATAEDITION + TEXT
			+ BFCColumns.COLUMN_DA_DATAPUBLISHER + TEXT
			+ BFCColumns.COLUMN_DA_DATASUBJECT + TEXT
			+ BFCColumns.COLUMN_DA_DATAGRADE + TEXT
			+ BFCColumns.COLUMN_DA_DATAEXTEND + TEXT

			+ BFCColumns.COLUMN_OA_DAVER + TEXT
			+ BFCColumns.COLUMN_OA_ROUTERMAC + TEXT
			+ BFCColumns.COLUMN_OA_CHANNELID + TEXT
			+ BFCColumns.COLUMN_OA_EXTEND + " TEXT);";

	interface RecordState{
		int IDLE = 0;
		int UPLOADING = 1;
		int UPLOADED = 2;
	}
}
