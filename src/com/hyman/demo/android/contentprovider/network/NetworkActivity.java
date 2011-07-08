package com.hyman.demo.android.contentprovider.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyman.demo.android.contentprovider.R;

public class NetworkActivity extends Activity {
	private static final String TAG = "NetworkActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network);
	}
	
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	     protected Long doInBackground(URL... urls) {
	         Log.d(TAG, "doInBackground");
	         int count = urls.length;
	         long totalSize = 0;
	         for (int i = 0; i < count; i++) {
		         Log.d(TAG, urls[i].toString());
	             download(urls[i].toString());
	         }
	         return totalSize;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         Log.d(TAG, "onProgressUpdate");
	     }

	     protected void onPostExecute(Long result) {
	         Log.d(TAG, "onPostExecute");
	     }
	 }
	
	private void download(String url) {
		String userAgent = "Android";
		AndroidHttpClient client = AndroidHttpClient.newInstance(userAgent);
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            Log.d(TAG, str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onLoadClick(View src) {
		Log.d(TAG, "onLoadClick");
		EditText editText = (EditText) findViewById(R.id.url);
		String url = editText.getText().toString();
		Log.d(TAG, url);
		try {
			new DownloadFilesTask().execute(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
