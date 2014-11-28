package com.example.hector.p1p2;

import android.content.Context;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ContactsManager
{
    /**
     * Server vars
     */
    private String serverUrl;
    private String contactUrl;
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    private CloseableHttpResponse response;
    private ObjectMapper mapper = new ObjectMapper();

    private static ArrayList<ContactAddress> addressList = new ArrayList<ContactAddress>();
    private static SortedArrayList<Contact> contactList = new SortedArrayList<Contact>();
//    private static ContactStore contactStore = new ContactStore();

    public ContactsManager(String serverUrl){
        if (serverUrl == null){
            throw new IllegalArgumentException("Server url cannot be null.");
        }
        this.serverUrl = serverUrl;
        this.contactUrl = this.serverUrl + "/contact";
        this.httpclient = HttpClients.createDefault();
		this.mapper = new ObjectMapper();
    }
    // Get a contact based on Id.
    // Exceptions deal with server errors (IOException) or malformed HTTP client protocol evaluation
    // (ClientProtocolException)
    public Contact getContact(int id) throws ClientProtocolException, IOException {
        // Init the client
        //this.httpclient = HttpClients.createDefault();

        // Set up route to get contact with a given id
        String getRoute = this.contactUrl + "/" + id;
        // Set up http get operation on the given route
        HttpGet httpget = new HttpGet(getRoute);
        // Execute get operation
        CloseableHttpResponse response = this.httpclient.execute(httpget);
        Contact C = null;
        // get the status code
        int statusCode = response.getStatusLine().getStatusCode();
        // code 200 : OK - found it!
        if (statusCode == HttpStatus.SC_OK){
            C = readContact(response);
        }
        // code 404 : Not found
        else if (statusCode == HttpStatus.SC_NOT_FOUND){
            C = null;
        }
        else {
            // In android client app, simply show an error message. Do not kill the app!
            throw new IllegalStateException("Something is wrong with server.");
        }
        // Close response
        response.close();
        //this.httpclient.close();
        return C;
    }
    // Get a contact based on Id.
    // Exceptions deal with server errors (IOException) or malformed HTTP client protocol evaluation
    // (ClientProtocolException)
    public SortedArrayList<Contact> getAllContacts() throws ClientProtocolException, IOException {
        int id = 0;
        // Set up route to get contact with a given id
        while(true) {
            ++id;
            Contact C;
            C =this.getContact(id);
            if(C != null) {
                contactList.add(C);
            }else {
                break;
            }
        }
        return contactList;
    }
    private Contact readContact(CloseableHttpResponse response)
            throws IOException, JsonProcessingException, JsonParseException,
            JsonMappingException {
        Contact C;

        // Read the response from server and convert to String. This will help parse JSON data.
        HttpEntity entity = response.getEntity();
        String line = EntityUtils.toString(entity, "UTF-8");

        // DEBUG line - just to look at what server sent. Not to be used in actual app
        System.out.println("Data Read: " + line);

        // Convert String to JSON Tree
        JsonNode data = mapper.readTree(line);
        // Find the contact data under the tag contact
        JsonNode contact = data.with("contact");
        // Convert again to JSON string
        String contactStr;
        contactStr = contact.asText();
        // Use the mapper to convert JSON string into contact Java Plain Old Object (POJO)
        C = mapper.readValue(contactStr, Contact.class);
        return C;
    }

    public Contact storeContact(Contact newcontact) throws ClientProtocolException, IOException{
        // Convert the contact to JSON String
        String newstring = mapper.writeValueAsString(newcontact);
        // Build a Request to server
        StringEntity requestEntity = new StringEntity(newstring, HTTP.UTF_8);
        // Build URL
        String url = this.contactUrl;
        // Build POST message since this is a new object
        HttpPost postMethod = new HttpPost(url);
        // Set the payload entity for the post
        postMethod.setEntity(requestEntity);
        // Execute the Post
        response = httpclient.execute(postMethod);
        // Get the status code
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_CREATED){
            Contact C = readContact(response);
            response.close();
            return C;
        }
        else{
            response.close();
            return null;
        }
    }

    public boolean deleteContact(int id) throws ClientProtocolException, IOException{

        // Set up route to get contact with a given id
        String getRoute = this.contactUrl + "/" + id;
        // Set up http delete operation on the given route
        HttpDelete httpdelete = new HttpDelete(getRoute);
        // Execute delete operation
        CloseableHttpResponse response = this.httpclient.execute(httpdelete);
        boolean result;
        // get the status code
        int statusCode = response.getStatusLine().getStatusCode();
        // code 200 : OK - found it!
        if (statusCode == HttpStatus.SC_NO_CONTENT){
            result=	true;
        }
        // code 404 : Not found
        else if (statusCode == HttpStatus.SC_NOT_FOUND){
            result= false;
        }
        else {
            // In android client app, simply show an error message. Do not kill the app!
            throw new IllegalStateException("Something is wrong with server.");
        }
        // Close response
        response.close();

        return result;
    }

    public Contact updateContact(Contact newcontact) throws ClientProtocolException, IOException{
        // Convert the contact to JSON String
        String newstring = mapper.writeValueAsString(newcontact);
        // Build a Request to server
        StringEntity requestEntity = new StringEntity(newstring, HTTP.UTF_8);
        // Build URL
        String url = this.contactUrl + "/"+ newcontact.getId();
        // Build PUT message since this is a new object
        HttpPut putMethod = new HttpPut(url);

        // Set the payload entity for the put
        putMethod.setEntity(requestEntity);
        // Execute the Post
        response = httpclient.execute(putMethod);
        // Get the status code
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK){
            Contact C = readContact(response);
            response.close();
            return C;
        }
        else{
            response.close();
            return null;
        }
    }






    public static void SetContactList(SortedArrayList<Contact> paramSortedArrayList)
    {
        contactList = paramSortedArrayList;
    }

    public static void addAddress(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    {
        ContactAddress newAddress = new ContactAddress();
        contactList.get(paramInt).addAddress(newAddress);
    }

    public static void addContact(Contact paramContact)
    {
        contactList.add(paramContact);
    }

    public static void addAddressesToContact(int paramInt)
    {
        contactList.get(paramInt).setAddressList(addressList);
    }

    public static void deleteContactFromList(int paramInt)
    {
        contactList.remove(paramInt);
    }

//    public static void deleteContactsFile(Context paramContext)
//    {
//        contactStore.clear(paramContext);
//    }

    public static void editContact(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ArrayList<ContactAddress> paramArrayList)
    {
        contactList.get(paramInt).setFirstName(paramString1);
        contactList.get(paramInt).setLastName(paramString2);
        contactList.get(paramInt).setCellPhone(paramString3);
        contactList.get(paramInt).setWorkPhone(paramString4);
        contactList.get(paramInt).setEmail(paramString5);
        contactList.get(paramInt).setAddressList(paramArrayList);
    }

    public static Contact getContactFromList(int paramInt)
    {
        return (Contact)contactList.get(paramInt);
    }

    public static ContactAddress getContactAddress(int paramInt)
    {
        return addressList.get(paramInt);
    }

    public static SortedList<Contact> getContactList()
    {
        return contactList;
    }

    public static SortedArrayList<Contact> getContactsFromList()
    {
        return contactList;
    }

    public static int getNumberOfAddresses()
    {
        return addressList.size();
    }

//    public static SortedArrayList<Contact> readContacts(FileInputStream paramInputStream, Context paramContext)
//    {
//        contactList = new ContactStore().readContacts(paramInputStream);
//        return contactList;
//    }

    public static void replaceContact(int paramInt, Contact paramContact)
    {
        contactList.remove(paramInt);
        contactList.add(paramContact);
    }

//    public static void writeContacts(SortedArrayList<Contact> paramSortedArrayList, Context paramContext)
//    {
//        contactStore.writeContacts(paramSortedArrayList, paramContext);
//    }

    public static void deleteAddress(int i, int position) {
        if (i == 0) {
            contactList.get(position).getAddressList().remove(0);
        } else {
            contactList.get(position).getAddressList().remove(1);
        }
    }
}