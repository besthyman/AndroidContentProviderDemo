package com.hyman.demo.android.contentprovider.custom.notes.client;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyman.demo.android.contentprovider.ContentProviderUtil;
import com.hyman.demo.android.contentprovider.R;
import com.hyman.demo.android.contentprovider.custom.notes.Note;
import com.hyman.demo.android.contentprovider.custom.notes.NotesContentProvider;

public class NotesClientActivity extends Activity {
	private static final String TAG = "NotesClient";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_notesclient);
	}

	private Cursor getNotes() {
		Uri uri = Note.Notes.CONTENT_URI;
		String[] projection = new String[] { Note.Notes._ID, Note.Notes.TITLE,
				Note.Notes.TEXT };
		String[] selectionArgs = null;
		String sortOrder = null;

		return getContentResolver().query(uri, projection, null, selectionArgs,
				sortOrder);
	}

	public void onListClick(View src) {
		Log.d(TAG, "onListClick");
		Cursor notes = getNotes();
		ContentProviderUtil.getInstance().logCursor(TAG, notes);
		notes.close();
	}

	private ArrayList<ContentProviderOperation> getAddOperations(String title,
			String text) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation.newInsert(Note.Notes.CONTENT_URI)
				.withValue(Note.Notes.TITLE, title)
				.withValue(Note.Notes.TEXT, text).build());
		return ops;
	}

	public void onAddClick(View src) {
		Log.d(TAG, "onAddClick");
		ArrayList<ContentProviderOperation> addOperations = getAddOperations("title", "text");
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				NotesContentProvider.AUTHORITY, addOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}

	private ArrayList<ContentProviderOperation> getUpdateOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newUpdate(Note.Notes.CONTENT_URI)
				.withSelection(Note.Notes._ID + "=?",
						new String[] { dataId })
				.withValue(Note.Notes.TITLE, "title2").build());
		return ops;
	}
	
	public void onUpdateClick(View src) {
		Log.d(TAG, "onUpdateClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.rawupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> updateOperations = getUpdateOperations(updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				NotesContentProvider.AUTHORITY, updateOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);

	}

	private ArrayList<ContentProviderOperation> getDeleteOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(Note.Notes.CONTENT_URI)
				.withSelection(Note.Notes._ID + "=?",
						new String[] { dataId }).build());
		return ops;
	}

	public void onDeleteClick(View src) {
		Log.d(TAG, "onDeleteClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.rawupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> deleteOperations = getDeleteOperations(updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				NotesContentProvider.AUTHORITY, deleteOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}
}
