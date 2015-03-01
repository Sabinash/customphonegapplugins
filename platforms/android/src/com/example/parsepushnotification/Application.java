package com.example.parsepushnotification;

import android.util.Log;

import com.parse.Parse;
import com.parse.PushService;

public class Application extends android.app.Application {

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		Parse.initialize(Application.this, "ACEV1ep8gtSbjqCG1gaepC6jJOFpHqWynvD4aAAx",
				"EFO8hMfVx3QYsSwn7qRvCZIExfKEPRBVa5OYfMpR");
Log.d("Abinash", "initialze");
		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);
		
	}
}