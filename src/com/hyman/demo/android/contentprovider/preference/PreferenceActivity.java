package com.hyman.demo.android.contentprovider.preference;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyman.demo.android.contentprovider.R;

public class PreferenceActivity extends Activity {
	private static final String TAG = "PreferenceActivity";
	public static final String PREFERENCE_GLOBAL = "preference_global";
	private int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);
	}

	private void logPreferences(SharedPreferences preferences) {
		Map<String, ?> all = preferences.getAll();
		for (String key : all.keySet()) {
			Log.d(TAG, key + ":" + all.get(key));
		}
	}

	public void onListClick(View src) {
		Log.d(TAG, "onListClick");

		SharedPreferences sharedPreferences = getSharedPreferences(
				PREFERENCE_GLOBAL, MODE_PRIVATE);
		Log.d(TAG, "sharedPreferences");
		logPreferences(sharedPreferences);

		Log.d(TAG, "private preferences");
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		logPreferences(preferences);
	}

	public void onSetClick(View src) {
		Log.d(TAG, "onSetClick");
		count++;
		EditText keyEdit = (EditText) findViewById(R.id.keyId);
		String key = keyEdit.getText().toString();
		{
			SharedPreferences sharedPreferences = getSharedPreferences(
					PREFERENCE_GLOBAL, MODE_PRIVATE);
			Editor edit = sharedPreferences.edit();
			edit.putBoolean("active", true);
			edit.putString(key, key + count);
			edit.commit();
		}
		{
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			Editor edit = preferences.edit();
			edit.putBoolean("active", true);
			edit.putString(key, key + count);
			edit.commit();
		}
	}
	
	public void onRemoveClick(View src) {
		Log.d(TAG, "onRemoveClick");
		EditText keyEdit = (EditText) findViewById(R.id.keyId);
		String key = keyEdit.getText().toString();
		{
			SharedPreferences sharedPreferences = getSharedPreferences(
					PREFERENCE_GLOBAL, MODE_PRIVATE);
			Editor edit = sharedPreferences.edit();
			edit.remove(key);
			edit.commit();
		}
		{
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			Editor edit = preferences.edit();
			edit.remove(key);
			edit.commit();
		}
	}
}
