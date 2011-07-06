package com.hyman.demo.android.contentprovider.system.contactscontract;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyman.demo.android.contentprovider.ContentProviderUtil;
import com.hyman.demo.android.contentprovider.R;

public class ContactsContractActivity extends Activity {
	private static final String TAG = "ContactsContractActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_contactscontract);
	}

	private Cursor getContacts() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.PHOTO_ID,
				ContactsContract.Contacts.IN_VISIBLE_GROUP,
				ContactsContract.Contacts.HAS_PHONE_NUMBER,
				ContactsContract.Contacts.LOOKUP_KEY,
//				ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
//				ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
				ContactsContract.Contacts.PHOTO_ID,
//				ContactsContract.Contacts.PHOTO_URI,
//				ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
				ContactsContract.Contacts.IN_VISIBLE_GROUP,
				ContactsContract.Contacts.HAS_PHONE_NUMBER,
				ContactsContract.Contacts.TIMES_CONTACTED,
				ContactsContract.Contacts.LAST_TIME_CONTACTED,
				ContactsContract.Contacts.STARRED,
				ContactsContract.Contacts.CUSTOM_RINGTONE,
				ContactsContract.Contacts.SEND_TO_VOICEMAIL,
				ContactsContract.Contacts.CONTACT_PRESENCE,
				ContactsContract.Contacts.CONTACT_STATUS,
				ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP,
				ContactsContract.Contacts.CONTACT_STATUS_RES_PACKAGE 	,
				ContactsContract.Contacts.CONTACT_STATUS_LABEL,
				ContactsContract.Contacts.	CONTACT_STATUS_ICON};
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		return managedQuery(uri, projection, null, selectionArgs,
				sortOrder);
	}

	private Cursor getRawContacts() {
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.RawContacts._ID,
				ContactsContract.RawContacts.CONTACT_ID ,
				ContactsContract.RawContacts.AGGREGATION_MODE ,
				ContactsContract.RawContacts.DELETED ,
				ContactsContract.RawContacts.TIMES_CONTACTED ,
				ContactsContract.RawContacts.LAST_TIME_CONTACTED,
				ContactsContract.RawContacts.STARRED,
				ContactsContract.RawContacts.CUSTOM_RINGTONE,
				ContactsContract.RawContacts.SEND_TO_VOICEMAIL,
				ContactsContract.RawContacts.ACCOUNT_NAME,
				ContactsContract.RawContacts.ACCOUNT_TYPE,
				ContactsContract.RawContacts.SOURCE_ID,
				ContactsContract.RawContacts.VERSION,
				ContactsContract.RawContacts.DIRTY,
				ContactsContract.RawContacts.SYNC1,
				ContactsContract.RawContacts.SYNC2,
				ContactsContract.RawContacts.SYNC3,
				ContactsContract.RawContacts.SYNC4};
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";
		sortOrder = null;

		return managedQuery(uri, projection, null, selectionArgs,
				sortOrder);
	}
	private Cursor getDataContacts() {
		Uri uri = ContactsContract.Data.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Data._ID,
				ContactsContract.Data.MIMETYPE,
				ContactsContract.Data.RAW_CONTACT_ID,
				ContactsContract.Data.IS_PRIMARY,
				ContactsContract.Data.IS_SUPER_PRIMARY,
				ContactsContract.Data.DATA_VERSION,
				ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER};
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";
		sortOrder = null;

		return managedQuery(uri, projection, null, selectionArgs,
				sortOrder);
	}

	public void onListClick(View src) {
		Log.d(TAG, "onListClick");
		Log.d(TAG, "getContacts");
		Cursor contacts = getContacts();
		ContentProviderUtil.getInstance().logCursor(TAG, contacts);
		contacts.close();
		Log.d(TAG, "getRawContacts");
		Cursor rawcontacts = getRawContacts();
		ContentProviderUtil.getInstance().logCursor(TAG, rawcontacts);
		rawcontacts.close();
		Log.d(TAG, "getDataContacts");
		Cursor datacontacts = getDataContacts();
		ContentProviderUtil.getInstance().logCursor(TAG, datacontacts);
		datacontacts.close();
	}

	private ArrayList<ContentProviderOperation> getAddContactOperations(
			String name, String phone) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						"accountname@gmail.com")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"com.google").build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
				.build());
		return ops;
	}

	public void onAddClick(View src) {
		Log.d(TAG, "onAddClick");
		ArrayList<ContentProviderOperation> addContactOperations = getAddContactOperations(
				"Hyman", "8601087654567");
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				ContactsContract.AUTHORITY, addContactOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}

	private ArrayList<ContentProviderOperation> getUpdateRawContactOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(ContactsContract.RawContacts._ID + "=?",
						new String[] { dataId })
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google.2").build());
		return ops;
	}

	public void onRawUpdateClick(View src) {
		Log.d(TAG, "onRawUpdateClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.rawupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> updateContactOperations = getUpdateRawContactOperations(
				updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				ContactsContract.AUTHORITY, updateContactOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}

	private ArrayList<ContentProviderOperation> getDeleteRawContactOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(ContactsContract.RawContacts._ID + "=?",
						new String[] { dataId }).build());
		return ops;
	}

	public void onRawDeleteClick(View src) {
		Log.d(TAG, "onRawDeleteClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.rawupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> deleteContactOperations = getDeleteRawContactOperations(
				updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				ContactsContract.AUTHORITY, deleteContactOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}


	private ArrayList<ContentProviderOperation> getUpdateDataContactOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(ContactsContract.Data._ID + "=?",
						new String[] { dataId })
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Smith").build());
		return ops;
	}

	public void onDataUpdateClick(View src) {
		Log.d(TAG, "onDataUpdateClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.dataupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> updateContactOperations = getUpdateDataContactOperations(
				updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				ContactsContract.AUTHORITY, updateContactOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}

	private ArrayList<ContentProviderOperation> getDeleteDataContactOperations(
			String dataId) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.Data.CONTENT_URI)
				.withSelection(ContactsContract.Data._ID + "=?",
						new String[] { dataId }).build());
		return ops;
	}

	public void onDataDeleteClick(View src) {
		Log.d(TAG, "onDataDeleteClick");
		EditText updateIdEditText = (EditText) findViewById(R.id.dataupdateId);
		String updateId = updateIdEditText.getText().toString();
		ArrayList<ContentProviderOperation> deleteContactOperations = getDeleteDataContactOperations(
				updateId);
		ContentProviderResult[] results = ContentProviderUtil.getInstance().applyBatch(this,
				ContactsContract.AUTHORITY, deleteContactOperations);
		ContentProviderUtil.getInstance().logContentProviderResult(TAG, results);
	}
}

/*
 * Caused by: java.lang.SecurityException: Permission Denial: reading
 * com.android.providers.contacts.ContactsProvider2 uri
 * content://com.android.contacts/contacts from pid=406, uid=10043 requires
 * android.permission.READ_CONTACTS
 * 
 * java.lang.SecurityException: Permission Denial: writing
 * com.android.providers.contacts.ContactsProvider2 uri
 * content://com.android.contacts/raw_contacts from pid=484, uid=10043 requires
 * android.permission.WRITE_CONTACTS
 */
