package com.example.hector.p1p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ShowContactActivity extends ActionBarActivity {

    private Bundle b;
    private Contact currentContact;
    private Intent i;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        this.i = getIntent();
        this.b = this.i.getExtras();
        this.position = this.b.getInt("Position");
        this.currentContact = ContactsManager.getContactFromList(this.position);
        ((TextView)findViewById(R.id.firstName_show)).setText(this.currentContact.getFirstName());
        ((TextView)findViewById(R.id.lastName_show)).setText(this.currentContact.getLastName());
        ((TextView)findViewById(R.id.cell_phone_show)).setText(this.currentContact.getCellPhone());
        ((TextView)findViewById(R.id.workPhone_show)).setText(this.currentContact.getWorkPhone());
        ((TextView)findViewById(R.id.email_show)).setText(this.currentContact.getEmail());
        
        makeUneditable();
        
        if (this.currentContact.getNumberOfAddresses() > 0)
        {
            if (this.currentContact.getNumberOfAddresses() == 1)
            {
                ((TextView)findViewById(R.id.home_address)).setText(this.currentContact.getAddressList().get(0).getAddressName());
                ((TextView)findViewById(R.id.street)).setText(this.currentContact.getAddressList().get(0).getStreet());
                ((TextView)findViewById(R.id.number)).setText(this.currentContact.getAddressList().get(0).getStreetNumber());
                ((TextView)findViewById(R.id.city)).setText(this.currentContact.getAddressList().get(0).getCity());
                ((TextView)findViewById(R.id.state)).setText(this.currentContact.getAddressList().get(0).getState());
                ((TextView)findViewById(R.id.zip_code)).setText(this.currentContact.getAddressList().get(0).getZipCode());
            }
            if (this.currentContact.getNumberOfAddresses() == 2)
            {
            	((TextView)findViewById(R.id.home_address)).setText(this.currentContact.getAddressList().get(0).getAddressName());
                ((TextView)findViewById(R.id.street)).setText(this.currentContact.getAddressList().get(0).getStreet());
                ((TextView)findViewById(R.id.number)).setText(this.currentContact.getAddressList().get(0).getStreetNumber());
                ((TextView)findViewById(R.id.city)).setText(this.currentContact.getAddressList().get(0).getCity());
                ((TextView)findViewById(R.id.state)).setText(this.currentContact.getAddressList().get(0).getState());
                ((TextView)findViewById(R.id.zip_code)).setText(this.currentContact.getAddressList().get(0).getZipCode());
                ((TextView)findViewById(R.id.work_address)).setText(this.currentContact.getAddressList().get(1).getAddressName());
                ((TextView)findViewById(R.id.street_work)).setText(this.currentContact.getAddressList().get(1).getStreet());
                ((TextView)findViewById(R.id.number_work)).setText(this.currentContact.getAddressList().get(1).getStreetNumber());
                ((TextView)findViewById(R.id.city_work)).setText(this.currentContact.getAddressList().get(1).getCity());
                ((TextView)findViewById(R.id.state_work)).setText(this.currentContact.getAddressList().get(1).getState());
                ((TextView)findViewById(R.id.zip_code_work)).setText(this.currentContact.getAddressList().get(1).getZipCode());
            }
        }

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View paramAnonymousView)
            {
                ContactsManager.deleteAddress(0, position);
                Intent localIntent = new Intent(getApplicationContext(), EditContactActivity.class);
                localIntent.putExtra("Position", position);
                startActivity(localIntent);
            }
        });
        findViewById(R.id.deleteButtonWork).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                ContactsManager.deleteAddress(1, position);
                Intent localIntent = new Intent(getApplicationContext(), EditContactActivity.class);
                localIntent.putExtra("Position", position);
                startActivity(localIntent);
            }
        });
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_contact, menu);
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

    private void makeUneditable() {
    	findViewById(R.id.firstName_show).setEnabled(false);
        findViewById(R.id.firstName_show).setFocusable(false);
        findViewById(R.id.lastName_show).setEnabled(false);
        findViewById(R.id.lastName_show).setFocusable(false);
        findViewById(R.id.cell_phone_show).setEnabled(false);
        findViewById(R.id.cell_phone_show).setFocusable(false);
        findViewById(R.id.workPhone_show).setEnabled(false);
        findViewById(R.id.workPhone_show).setFocusable(false);
        findViewById(R.id.email_show).setEnabled(false);
        findViewById(R.id.email_show).setFocusable(false);
        findViewById(R.id.home_address).setEnabled(false);
        findViewById(R.id.home_address).setFocusable(false);
        findViewById(R.id.street).setEnabled(false);
        findViewById(R.id.street).setFocusable(false);
        findViewById(R.id.number).setEnabled(false);
        findViewById(R.id.number).setFocusable(false);
        findViewById(R.id.city).setEnabled(false);
        findViewById(R.id.city).setFocusable(false);
        findViewById(R.id.state).setEnabled(false);
        findViewById(R.id.state).setFocusable(false);
        findViewById(R.id.zip_code).setEnabled(false);
        findViewById(R.id.zip_code).setFocusable(false);
        findViewById(R.id.work_address).setEnabled(false);
        findViewById(R.id.work_address).setFocusable(false);
        findViewById(R.id.street_work).setEnabled(false);
        findViewById(R.id.street_work).setFocusable(false);
        findViewById(R.id.number_work).setEnabled(false);
        findViewById(R.id.number_work).setFocusable(false);
        findViewById(R.id.city_work).setEnabled(false);
        findViewById(R.id.city_work).setFocusable(false);
        findViewById(R.id.state_work).setEnabled(false);
        findViewById(R.id.state_work).setFocusable(false);
        findViewById(R.id.zip_code_work).setEnabled(false);
        findViewById(R.id.zip_code_work).setFocusable(false);		
	}
}

