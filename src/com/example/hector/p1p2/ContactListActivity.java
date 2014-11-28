package com.example.hector.p1p2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactListActivity extends ActionBarActivity{
	protected static final int REQUEST_OK = 1;
	private static final int REQUEST_CODE = 1234;
	private static final String DEBUG_TAG = "HttpExample";
	private static final String contacts_file = "contactList.txt";
	private static SortedArrayList<Contact> readContactList = new SortedArrayList<Contact>();
	private ArrayAdapter<String> adapter;
	private ListView list;
	private static boolean flag = true;
	private ArrayList<String> localArrayList;
	private String stringUrl = "http://192.168.1.2:9000/contacts";

	public void getContactList(View v)
	{
		ConnectivityManager connMgr;
		connMgr = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageTask().execute(stringUrl);
		} else {
		}
	}

	// Uses AsyncTask to create a task away from the main UI thread. This task takes a
	// URL string and uses it to create an HttpUrlConnection. Once the connection
	// has been established, the AsyncTask downloads the contents of the webpage as
	// an InputStream. Finally, the InputStream is converted into a string, which is
	// displayed in the UI by the AsyncTask's onPostExecute method.
	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			// params comes from the execute() call: params[0] is the url.
			try {
				return downloadUrl(urls[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			try {

				JSONObject reader = new JSONObject(result);

				JSONArray contacts  = reader.getJSONArray("Contacts");
				for(int i = 0; i < contacts.length(); ++i){
					Contact C = new Contact();
					JSONObject contact = contacts.getJSONObject(i);
					C = new Contact( contact.getString("firstName"),contact.getString("lastName"),contact.getString("cellPhone"),
							contact.getString("workPhone"),contact.getString("email"));
					// check if there are addresses
					if(contact.getJSONArray("addressList") != null) {
						JSONArray addresslist = contact.getJSONArray("addressList");

						for(int j = 0; j < Integer.valueOf(contact.getString("numberOfAddresses")); ++j) {
							ContactAddress address = new ContactAddress();
							address.setAddressName(addresslist.getJSONObject(j).getString("addressName"));
							address.setStreet(addresslist.getJSONObject(j).getString("street"));
							address.setStreetNumber(addresslist.getJSONObject(j).getString("streetNumber"));
							address.setCity(addresslist.getJSONObject(j).getString("city"));
							address.setState(addresslist.getJSONObject(j).getString("state"));
							address.setZipCode(addresslist.getJSONObject(j).getString("zipCode"));
							C.addAddress(address);
						}
					}
					// add contacts w/ and w/o addresses
					readContactList.add(C);					
				}
				ContactsManager.SetContactList(readContactList);
				setUp();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 100000;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			return readIt(is, len);

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	//Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	/**
	 * Fire an intent to start the voice recognition activity.
	 */
	private void startVoiceRecognitionActivity()
	{
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Handle the results from the voice recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			// Populate the wordsList with the String values the recognition engine thought it heard
			ArrayList<String> matches = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);

			for (int i = 0;i < readContactList.size(); i++)
			{
				if (i >= this.list.getCount())
				{
					super.onActivityResult(requestCode, resultCode, data);
					return;
				}
				if ((matches.get(0)).equalsIgnoreCase((String)this.list.getItemAtPosition(i)))
				{
					Intent localIntent = new Intent(getApplicationContext(), EditContactActivity.class);
					localIntent.putExtra("Position", i);
					startActivity(localIntent);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void speakButtonClicked(View v)
	{
		startVoiceRecognitionActivity();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		if(flag) {
			this.getContactList(list);
			flag = false;
		} else 
			setUp();
	}

	private void setUp() {
		this.list = ((ListView)findViewById(R.id.list));

		Random localRandom = new Random();
		Paint localPaint = new Paint();
		localPaint.setARGB(localRandom.nextInt(211), localRandom.nextInt(211), localRandom.nextInt(211), localRandom.nextInt(211));
		this.list.setBackgroundColor(localPaint.getColor());

		// send contacts' names to display list
		java.util.ArrayList<String> contactDisplayName = new java.util.ArrayList<String>();
		for (int i = 0; i < readContactList.size(); i++) {
			contactDisplayName.add(readContactList.get(i).getFirstName() + " " + readContactList.get(i).getLastName());
		}
		// This is the array adapter, it takes the context of the activity as a
		// first parameter, the type of list view as a second parameter and your
		// array as a third parameter.
		adapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_1,
				contactDisplayName);
		// Display data
		list.setAdapter(adapter);

		//ListView Item Click Listener
		//Go to created clicked contact
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent editContactIntent = new Intent(ContactListActivity.this.getApplicationContext(), EditContactActivity.class);
				//Get clicked contact position and send it to edit contact activity
				editContactIntent.putExtra("Position", position);
				ContactListActivity.this.startActivity(editContactIntent);
			}
		});
		Button speakButton = ((Button)findViewById(R.id.speakButton));

		// Disable button if no recognition service is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(
				new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0)
		{
			speakButton.setEnabled(false);
		}

		// TODO This is the add button functionality

		((Button)findViewById(R.id.addButton)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				Intent localIntent = new Intent(ContactListActivity.this.getApplicationContext(), NewContactActivity.class);
				ContactListActivity.this.startActivity(localIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}