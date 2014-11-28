package com.example.hector.p1p2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContactActivity
        extends ActionBarActivity
{
    private static final String DEBUG_TAG = "EditContact";
    private String url = "http://192.168.1.2:9000/contacts/";

    private static final String contacts_file = "contactList.txt";
    private SortedArrayList<Contact> contactList = new SortedArrayList<Contact>();
    EditText emailField;
    EditText lastNameField;
    EditText nameField;
    EditText numberField;
    private int position = 0;
    EditText workNumberField;
    Contact currentContact;
    
    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_edit_contact);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
            Intent i = getIntent();
            Bundle b = i.getExtras();
            this.position = b.getInt("Position");
            currentContact = ContactsManager.getContactFromList(this.position);
            this.nameField = ((EditText)findViewById(R.id.firstNameEdit));
            this.nameField.setText(currentContact.getFirstName());
            this.lastNameField = ((EditText)findViewById(R.id.lastNameEdit));
            this.lastNameField.setText(currentContact.getLastName());
            this.numberField = ((EditText)findViewById(R.id.cellPhoneEdit));
            this.numberField.setText(currentContact.getCellPhone());
            this.workNumberField = ((EditText)findViewById(R.id.workPhoneEdit));
            this.workNumberField.setText(currentContact.getWorkPhone());
            this.emailField = ((EditText)findViewById(R.id.emailEdit));
            this.emailField.setText(currentContact.getEmail());
            //TODO CHECK!!!!
            ((Button)findViewById(R.id.add_addressEdit)).setOnClickListener(new OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    Intent localIntent = new Intent(EditContactActivity.this.getApplicationContext(), NewAddressActivity.class);
                    Bundle localBundle = new Bundle();
                    localBundle.putInt("Position", EditContactActivity.this.position);
                    localIntent.putExtras(localBundle);
                    EditContactActivity.this.startActivity(localIntent);
                }
            });
            ((Button)findViewById(R.id.contact_informationEdit)).setOnClickListener(new OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    Intent localIntent = new Intent(EditContactActivity.this.getApplicationContext(), ShowContactActivity.class);
                    localIntent.putExtra("Position", EditContactActivity.this.position);
                    EditContactActivity.this.startActivity(localIntent);
                }
            });
//            ((Button)findViewById(R.id.contact_informationSave)).setOnClickListener(new OnClickListener()
//            {
//                public void onClick(View paramAnonymousView)
//                {
//                    ContactsManager.editContact(EditContactActivity.this.position, EditContactActivity.this.nameField.getText().toString(), EditContactActivity.this.lastNameField.getText().toString(), EditContactActivity.this.numberField.getText().toString(), EditContactActivity.this.workNumberField.getText().toString(), EditContactActivity.this.emailField.getText().toString(), ContactsManager.getContactFromList(EditContactActivity.this.position).getAddressList());
//                    //ContactsManager.writeContacts(ContactsManager.getContactsFromList(), EditContactActivity.this);
//                    Toast.makeText(EditContactActivity.this.getApplicationContext(), "Contact Edited", Toast.LENGTH_SHORT).show();
//                    Intent localIntent = new Intent(paramAnonymousView.getContext(), ContactListActivity.class);
//                    EditContactActivity.this.startActivity(localIntent);
//                }
//            });
            ((Button)findViewById(R.id.delete_contactEdit)).setOnClickListener(new OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    ContactsManager.deleteContactFromList(EditContactActivity.this.position);
                    //ContactsManager.writeContacts(ContactsManager.getContactsFromList(), EditContactActivity.this);
                    Toast.makeText(EditContactActivity.this.getApplicationContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                    Intent localIntent = new Intent(paramAnonymousView.getContext(), ContactListActivity.class);
                    EditContactActivity.this.startActivity(localIntent);
                }
            });
    }
    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void connect(View v) throws IOException, JSONException {
        // Gets the URL from the UI's text field.
        String stringUrl = this.url += String.valueOf(this.currentContact.getId());
        
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if (networkInfo != null && networkInfo.isConnected()) {
        	  // Given a URL, establishes an HttpUrlConnection and retrieves
        	  // the web page content as a InputStream, which it returns as
        	  // a string.
        	this.contactEdited();
        	
        	      try {
        	    	  
        	    	  JSONObject jsonContact = new JSONObject();
        	    	  jsonContact.put("firstName", this.currentContact.getFirstName());
        	    	  jsonContact.put("lastName", this.currentContact.getLastName());
        	    	  jsonContact.put("cellPhone", this.currentContact.getCellPhone());
        	    	  jsonContact.put("workPhone", this.currentContact.getWorkPhone());
        	    	  jsonContact.put("email", this.currentContact.getEmail());
        	    	  jsonContact.put("id", String.valueOf(this.currentContact.getId()));
        	    	  
        	    	  // Build a Request to server
        	    	  StringEntity requestEntity = new StringEntity(jsonContact.toString());

        	    	  // Build PUT message since this is a new object
        	    	  HttpPut putMethod = new HttpPut(stringUrl);
        	    	  putMethod.setEntity(requestEntity);
        	    	  putMethod.setHeader("Accept", "application/json");
        	    	  putMethod.setHeader("Content-type", "application/json");

        	    	  // Execute the Post
        	    	  DefaultHttpClient httpclient = new DefaultHttpClient();
        	    	  HttpResponse response = httpclient.execute(putMethod);
        	    	  
        	          Intent intent = new Intent(this, ShowContactActivity.class);
        	          intent.putExtra("contact", this.currentContact);
        	          startActivity(intent);

        	      } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
        	      	} 
        }
        else {
            Toast.makeText(EditContactActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

	private void contactEdited() {
        EditText contactFirstNameView = (EditText) findViewById(R.id.firstNameEdit);
        currentContact.setFirstName(contactFirstNameView.getText().toString());
        EditText contactLastNameView = (EditText) findViewById(R.id.lastNameEdit);
        currentContact.setLastName(contactLastNameView.getText().toString());
        EditText cellPhoneView = (EditText) findViewById(R.id.cellPhoneEdit);
        currentContact.setCellPhone(cellPhoneView.getText().toString());
        EditText workPhoneView = (EditText) findViewById(R.id.workPhoneEdit);
        currentContact.setWorkPhone(workPhoneView.getText().toString());
        EditText emailView = (EditText) findViewById(R.id.emailEdit);
        currentContact.setEmail(emailView.getText().toString());
	}
    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.edit_contact, paramMenu);
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