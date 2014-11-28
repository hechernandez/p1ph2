package com.example.hector.p1p2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewContactActivity extends ActionBarActivity {
	private String url = "http://192.168.1.2:9000/contact";
	private Contact C= new Contact();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
	}

	public void connect(View v) throws IOException, JSONException {
		String stringUrl = this.url;
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			this.contactAdded();

			try {

				JSONObject jsonContact = new JSONObject();
				jsonContact.put("firstName", this.C.getFirstName());
				jsonContact.put("lastName", this.C.getLastName());
				jsonContact.put("cellPhone", this.C.getCellPhone());
				jsonContact.put("workPhone", this.C.getWorkPhone());
				jsonContact.put("email", this.C.getEmail());
				jsonContact.put("addresses", this.C.getAddressList());

				StringEntity requestEntity = new StringEntity(jsonContact.toString());

				HttpPost postMethod = new HttpPost(stringUrl);
				postMethod.setEntity(requestEntity);
				postMethod.setHeader("Accept", "application/json");
				postMethod.setHeader("Content-type", "application/json");

				// Execute the Post
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(postMethod);

				Intent intent = new Intent(this, ContactListActivity.class);
				startActivity(intent);

			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		else {
			Toast.makeText(NewContactActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}

	public void contactAdded()
	{
		EditText firstN = ((EditText)findViewById(R.id.firstName));
		EditText lastN = ((EditText)findViewById(R.id.lastName));
		EditText cellP = ((EditText)findViewById(R.id.cellPhone));
		EditText workP = ((EditText)findViewById(R.id.workPhone));
		EditText eMail = ((EditText)findViewById(R.id.email));
		C.setFirstName(firstN.getText().toString());
		C.setLastName(lastN.getText().toString());
		C.setCellPhone((cellP.getText().toString()));
		C.setFirstName(workP.getText().toString());
		C.setFirstName(eMail.getText().toString());
		ContactsManager.addContact(C);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_contact, menu);
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