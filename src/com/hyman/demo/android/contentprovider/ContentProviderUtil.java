package com.hyman.demo.android.contentprovider;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

public class ContentProviderUtil {
	private static ContentProviderUtil singleton;
	private ContentProviderUtil() {
		
	}
	
	public static ContentProviderUtil getInstance() {
		if (singleton == null) {
			singleton = new ContentProviderUtil();
		}
		return singleton;
	}

	public void logCursor(String TAG, Cursor cursor) {
		Log.d(TAG, "count:" + cursor.getCount());
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d(TAG, "row:" + cursor.getPosition());
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				Log.d(TAG,
						"column " + cursor.getColumnName(i) + ":"
								+ cursor.getString(i));
			}
			cursor.moveToNext();
		}
	}

	public ContentProviderResult[] applyBatch(Context context, String authority,
			ArrayList<ContentProviderOperation> operations) {
		ContentResolver cr = context.getContentResolver();
		try {
			ContentProviderResult[] results = cr.applyBatch(authority,
					operations);
			return results;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void logContentProviderResult(String TAG, ContentProviderResult[] results) {
		for (ContentProviderResult result : results) {
			Log.d(TAG, result.toString());
		}
	}
}
