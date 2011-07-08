package com.hyman.demo.android.contentprovider.database.dictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyman.demo.android.contentprovider.ContentProviderUtil;
import com.hyman.demo.android.contentprovider.R;

public class DatabaseActivity extends Activity {
	private static final String TAG = "DatabaseActivity";
	private DictionaryOpenHelper dbHelper;
	
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_dictionary);
        dbHelper = new DictionaryOpenHelper(this);
	}
	
	public void onListClick(View src) {
		Log.d(TAG, "onListClick");
		SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DictionaryOpenHelper.DICTIONARY_TABLE_NAME);
		Cursor c = qb.query(readableDatabase, null, null, null, null, null, null);
		ContentProviderUtil.getInstance().logCursor(TAG, c);
		c.close();
	}
	
	public void onAddClick(View src) {
		Log.d(TAG, "onAddClick");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DictionaryOpenHelper.KEY_WORD, "Love");
		values.put(DictionaryOpenHelper.KEY_DEFINITION, "Where is Love?");
        long newId = db.insert(DictionaryOpenHelper.DICTIONARY_TABLE_NAME, null, values);
        Log.d(TAG, "new id:" + newId);
	}
	
	public void onUpdateClick(View src) {
		Log.d(TAG, "onUpdateClick");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		EditText editText = (EditText) findViewById(R.id.keyId);
		String id = editText.getText().toString();
		ContentValues values = new ContentValues();
		values.put(DictionaryOpenHelper.KEY_WORD, "Love2");
		values.put(DictionaryOpenHelper.KEY_DEFINITION, "Love is lost.");
		db.update(DictionaryOpenHelper.DICTIONARY_TABLE_NAME, values, DictionaryOpenHelper.KEY_ID + " = ?", new String[]{id});
	}
	
	public void onDeleteClick(View src) {
		Log.d(TAG, "onDeleteClick");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		EditText editText = (EditText) findViewById(R.id.keyId);
		String id = editText.getText().toString();
		db.delete(DictionaryOpenHelper.DICTIONARY_TABLE_NAME, DictionaryOpenHelper.KEY_ID + " = ?", new String[]{id});
	}
}
