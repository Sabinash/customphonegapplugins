package nl.xservices.plugins;

import nl.xservices.plugins.accessor.AbstractCalendarAccessor;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.parsepushnotification.MainActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("InlinedApi") public class Calendar extends CordovaPlugin {
	public static final String ACTION_OPEN_CALENDAR = "openCalendar";
	public static final String ACTION_CREATE_CALENDAR_EVENT = "createCalendarEvent";
	
	
	public static final String ACTION_CREATE_TEST_BUTTON = "testButton";
	

	public static final Integer RESULT_CODE_CREATE = 0;
	public static final Integer RESULT_CODE_OPENCAL = 1;

	private CallbackContext callback;

	private static final String LOG_TAG = AbstractCalendarAccessor.LOG_TAG;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		callback = callbackContext;
		if (ACTION_OPEN_CALENDAR.equals(action)) {
			return openCalendar(args);
		}else if (ACTION_CREATE_CALENDAR_EVENT.equals(action)) {
			return createCalendarEvent(args);
		}else if (ACTION_CREATE_TEST_BUTTON.equals(action)) {
			return testButton(args);
		}
		return false;
	}

	
	private boolean testButton(JSONArray args) {
		// TODO Auto-generated method stub
		
		Log.d("Abinash", "Here I'm");
		MainActivity.startNotification(args);
		return true;
	}


	@TargetApi(14)
	private boolean openCalendar(JSONArray args) throws JSONException {
		final Long millis = args.getJSONObject(0).optLong("date");
		final Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon()
				.appendPath("time");
		ContentUris.appendId(builder, millis);

		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder
				.build());
		this.cordova.startActivityForResult(this, intent, RESULT_CODE_OPENCAL);

		return true;
	}

	private boolean createCalendarEvent(JSONArray args) throws JSONException {
		// TODO Auto-generated method stub
		for (int i = 0; i < args.length(); i++) {
			JSONObject jsonObject = args.getJSONObject(i);
			String title = jsonObject.getString("title");
			String location = jsonObject.getString("location");
			String notes =jsonObject.getString("notes");
			String  startTime = jsonObject.getString(String.valueOf("startTime"));
			String endTime = jsonObject.getString(String.valueOf("endTime"));
			
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra(Events.TITLE, title);
			intent.putExtra(Events.EVENT_LOCATION, location);
			intent.putExtra(Events.DESCRIPTION, notes);
			intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
			this.cordova.startActivityForResult(this, intent, RESULT_CODE_OPENCAL);
	
		}
		
		
		return true;
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_CODE_CREATE) {
			if (resultCode == Activity.RESULT_OK
					|| resultCode == Activity.RESULT_CANCELED) {
				// resultCode may be 0 (RESULT_CANCELED) even when it was
				// created, so passing nothing is the clearest option here
				callback.success();
			}
		} else if (requestCode == RESULT_CODE_OPENCAL) {
			callback.success();
		} else {
			callback.error("Unable to add event (" + resultCode + ").");
		}
	}
}
