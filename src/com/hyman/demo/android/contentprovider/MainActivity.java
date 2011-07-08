package com.hyman.demo.android.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.hyman.demo.android.contentprovider.custom.notes.client.NotesClientActivity;
import com.hyman.demo.android.contentprovider.database.dictionary.DatabaseActivity;
import com.hyman.demo.android.contentprovider.preference.PreferenceActivity;
import com.hyman.demo.android.contentprovider.storage.external.ExternalStorageActivity;
import com.hyman.demo.android.contentprovider.storage.internal.InternalStorageActivity;
import com.hyman.demo.android.contentprovider.system.contactscontract.ContactsContractActivity;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onLoadClick(View src) {
        Spinner spinner = (Spinner) this.findViewById(R.id.broadcastsspinner);
		String value = spinner.getSelectedItem().toString();
		if ("ContactsContract".equals(value)) {
			Intent intent = new Intent(this, ContactsContractActivity.class);
			MainActivity.this.startActivity(intent);
		} else if ("Custom Notes Client".equals(value)) {
			Intent intent = new Intent(this, NotesClientActivity.class);
			MainActivity.this.startActivity(intent);
		} else if ("Preference".equals(value)) {
			Intent intent = new Intent(this, PreferenceActivity.class);
			MainActivity.this.startActivity(intent);
		} else if ("Storage Internal".equals(value)) {
			Intent intent = new Intent(this, InternalStorageActivity.class);
			MainActivity.this.startActivity(intent);
		} else if ("Storage External".equals(value)) {
			Intent intent = new Intent(this, ExternalStorageActivity.class);
			MainActivity.this.startActivity(intent);
		} else if ("Database".equals(value)) {
			Intent intent = new Intent(this, DatabaseActivity.class);
			MainActivity.this.startActivity(intent);
		}
    }
}