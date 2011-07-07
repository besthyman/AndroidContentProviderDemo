package com.hyman.demo.android.contentprovider.storage.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyman.demo.android.contentprovider.R;

public class InternalStorageActivity extends Activity {
	private static final String TAG = "InternalStorageActivity";
	String FILENAME = "hello_file";
	String string = "abc";

	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storage_internal);
	}
	
	public void onListClick(View src) {
		Log.d(TAG, "onListClick");
		File dir = getFilesDir();
		Log.d(TAG, dir.getAbsolutePath());
		String[] files = fileList();
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
	
	public void onViewClick(View src) {
		Log.d(TAG, "onViewClick");
		FileInputStream fis = null;
		try {
			fis = openFileInput(FILENAME);
			int b;
			while((b=fis.read()) > 0) {
				Log.d(TAG, Integer.toString(b));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void onAddClick(View src) {
		Log.d(TAG, "onAddClick");
		FileOutputStream fos = null;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void onClearClick(View src) {
		Log.d(TAG, "onClearClick");
		deleteFile(FILENAME);
		
	}
	
	public void onListCacheClick(View src) {
		Log.d(TAG, "onListCacheClick");
		File dir = getCacheDir();
		Log.d(TAG, dir.getAbsolutePath());
		String[] files = dir.list();
		for (String file : files) {
			Log.d(TAG, file);
		}
	}
	
	public void onViewRawClick(View src) {
		Log.d(TAG, "onViewRawClick");
		InputStream is = null;
		try {
			is = this.getResources().openRawResource(R.raw.hello);
			int b;
			while((b=is.read()) > 0) {
				Log.d(TAG, Integer.toString(b));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
