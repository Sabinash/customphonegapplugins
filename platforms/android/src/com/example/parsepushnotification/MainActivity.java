package com.example.parsepushnotification;

import org.json.JSONArray;

import com.parse.ParseAnalytics;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		ParseAnalytics.trackAppOpened(getIntent());
	}

	public static void startNotification(JSONArray args) {
		// TODO Auto-generated method stub
		Log.d("Abinash", "Here I'm At MainActivity");
	}
}
