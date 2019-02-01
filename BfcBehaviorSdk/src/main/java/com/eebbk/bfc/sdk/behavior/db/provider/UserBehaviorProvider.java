package com.eebbk.bfc.sdk.behavior.db.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.db.constant.BCConstants;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.UriUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * contentProvider 对外提供插入记录的接口<br>
 * 每插入一条记录就会通过策略类判断是否满足上传条件
 */
public class UserBehaviorProvider extends ContentProvider {
	private static final String TAG = "UserBehaviorProvider";
	private static final String UNKNOWN_URI = "Unknown URI ";

	private DBOpenHelper dbOpenHelper = null;
	private SQLiteDatabase db;
	private final AtomicInteger mOpenCounter = new AtomicInteger();

	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	@Override
	public boolean onCreate() {
		String AUTHORITY = UriUtils.getAuthority(getContext());
		System.out.println(" create behavior content provider: " + AUTHORITY);

		mUriMatcher.addURI(AUTHORITY, "userbehavior" , BFCColumns.DIR);
		mUriMatcher.addURI(AUTHORITY, "userbehavior/#" , BFCColumns.ITEM);

		synchronized (UserBehaviorProvider.class){
			dbOpenHelper = new DBOpenHelper(this.getContext(),
					BCConstants.DATABASE_NAME, BCConstants.DATABASE_VERSION);
		}
		return true;
	}

	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		getReadableDB();
		long id;
		switch (mUriMatcher.match(uri)) {
			case BFCColumns.DIR:
				id = db.insert(BCConstants.TABLE_NAME, null, values);
				Log.v(TAG, "~~insert record id:" + id);
				return ContentUris.withAppendedId(uri, id);
			case BFCColumns.ITEM:
				id = db.insert(BCConstants.TABLE_NAME, null, values);
				String path = uri.toString();
				Log.v(TAG, "~~~insert record id:" + id);
				return Uri.parse(path.substring(0, path.lastIndexOf("/")) + id); // 替换掉id
			default:
				throw new IllegalArgumentException(UNKNOWN_URI + uri);
		}
	}

	@Override
	public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
		int numValues = 0;
		getReadableDB();
		db.beginTransaction(); //开始事务
		try {
			//数据库操作
			numValues = values.length;
			for (int i = 0; i < numValues; i++) {
				db.insert(BCConstants.TABLE_NAME, null, values[i]);
			}
			db.setTransactionSuccessful(); //别忘了这句 Commit
		} finally {
			db.endTransaction(); //结束事务
		}
		closeDB();
		return numValues;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		getReadableDB();
		int count;
		switch (mUriMatcher.match(uri)) {
			case BFCColumns.DIR:
				count = db.delete(BCConstants.TABLE_NAME, selection, selectionArgs);
				break;
			case BFCColumns.ITEM:
				long id = ContentUris.parseId(uri);
				String where = "_ID=" + id; // 删除指定id的记录
				where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")"
						: ""; // 把其它条件附加上
				count = db.delete(BCConstants.TABLE_NAME, where, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException(UNKNOWN_URI + uri);
		}
		closeDB();
		return count;
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		getReadableDB();
		int count;
		switch (mUriMatcher.match(uri)) {
			case BFCColumns.DIR:
				count = db.update(BCConstants.TABLE_NAME, values, selection,
						selectionArgs);
				break;
			case BFCColumns.ITEM:
				long id = ContentUris.parseId(uri);
				String where = "_ID=" + id;// 获取指定id的记录
				where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")"
						: "";// 把其它条件附加上
				count = db.update(BCConstants.TABLE_NAME, values, where,
						selectionArgs);
				break;
			default:
				throw new IllegalArgumentException(UNKNOWN_URI + uri);
		}
		closeDB();
		return count;
	}

	@Override
	public String getType(@NonNull Uri uri) {
		switch (mUriMatcher.match(uri)) {
			case BFCColumns.DIR:
				return BFCColumns.CONTENT_TYPE;
			case BFCColumns.ITEM:
				return BFCColumns.CONTENT_TYPE_ITME;
			default:
				throw new IllegalArgumentException(UNKNOWN_URI + uri);
		}
	}

	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		getReadableDB();
		switch (mUriMatcher.match(uri)) {
			case BFCColumns.DIR:
				return db.query(BCConstants.TABLE_NAME, projection, selection,
						selectionArgs, null, null, sortOrder);
			case BFCColumns.ITEM:
				// 进行解析，返回值为10
				long id = ContentUris.parseId(uri);
				String where = "_ID=" + id;// 获取指定id的记录
				where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")"
						: "";// 把其它条件附加上
				return db.query(BCConstants.TABLE_NAME, projection, where,
						selectionArgs, null, null, sortOrder);
			default:
				throw new IllegalArgumentException(UNKNOWN_URI + uri);
		}
	}

	private void getReadableDB(){
		synchronized (UserBehaviorProvider.class) {
			if (mOpenCounter.incrementAndGet() == 1) {
				db = dbOpenHelper.getReadableDatabase();
			}
		}
	}

	private void closeDB(){
		synchronized (UserBehaviorProvider.class) {
			if (mOpenCounter.decrementAndGet() == 0 && null != db && db.isOpen()) {
				db.close();
			}
		}
	}

//	private UriMatcher getUriMatcher(){
//		if(mUriMatcher == null){
//			mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//			mUriMatcher.addURI(BFCColumns.getAuthority(), "userbehavior" , BFCColumns.DIR);
//			mUriMatcher.addURI(BFCColumns.getAuthority(), "userbehavior/#" , BFCColumns.ITEM);
//		}
//		return mUriMatcher;
//	}
}


