package com.hyman.demo.android.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

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
		}
    }
}