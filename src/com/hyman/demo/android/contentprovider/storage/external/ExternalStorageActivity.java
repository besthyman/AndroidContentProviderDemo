package com.hyman.demo.android.contentprovider.storage.external;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.hyman.demo.android.contentprovider.R;

public class ExternalStorageActivity extends Activity {
	private static final String TAG = "ExternalStorageActivity";
	private static final String TYPE = "type1";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storage_external);
	}
	
	public void onCheckClick(View src) {
		Log.d(TAG, "onCheckClick");
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			Log.d(TAG, "We can read and write the media");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
			Log.d(TAG, "We can only read the media");
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
			Log.d(TAG, "Something else is wrong");
		}
	}
	
	public void onListClick(View src) {
		Log.d(TAG, "onListClick getExternalFilesDir");
		File externalFilesDir = getExternalFilesDir(TYPE);
		Log.d(TAG, externalFilesDir.getAbsolutePath());
		String[] files = externalFilesDir.list();
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
	
	public void onList2Click(View src) {
		Log.d(TAG, "onListClick getExternalStorageDirectory");
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		Log.d(TAG, externalStorageDirectory.getAbsolutePath());
		String[] files = externalStorageDirectory.list();
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
	
	public void onList3Click(View src) {
		Log.d(TAG, "onListClick getExternalStoragePublicDirectory");
		File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		Log.d(TAG, externalStorageDirectory.getAbsolutePath());
		String[] files = externalStorageDirectory.list();
		if (files == null) {
			Log.d(TAG, "exists:" + externalStorageDirectory.exists());
			return;
		}
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
	
	public void onList4Click(View src) {
		Log.d(TAG, "onListClick getExternalCacheDir");
		File externalStorageDirectory = getExternalCacheDir();
		Log.d(TAG, externalStorageDirectory.getAbsolutePath());
		String[] files = externalStorageDirectory.list();
		if (files == null) {
			Log.d(TAG, "exists:" + externalStorageDirectory.exists());
			return;
		}
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
}
