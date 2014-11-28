package com.example.hector.p1p2;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable, Comparable<Contact> {

    private long Id;
    private String firstName;
	private String lastName;
	private String cellPhone;
	private String workPhone;
	private String email;
	private ArrayList<ContactAddress> address = new ArrayList<ContactAddress>();

    public Contact(){

    }
    public Contact(String fN, String lN, String cP, String wP, String e) {
        this.firstName = fN;
        this.lastName = lN;
        this.cellPhone = cP;
        this.workPhone = wP;
        this.email = e;
    }

    public Contact(long iD, String fN, String lN, String cP, String wP, String e, ArrayList<ContactAddress> a){
    	this.Id = iD;
        this.firstName = fN;
        this.lastName = lN;
        this.cellPhone = cP;
        this.workPhone = wP;
        this.email = e;
        this.address = a;
    }
    
    public void setId(long iD){
    	this.Id = iD;
    }
    public void addAddress(ContactAddress paramContactAddress)
    {
        if (paramContactAddress.equals(null)) {
            throw new IllegalArgumentException("Address is invalid.");
        }
        this.address.add(paramContactAddress);
    }
    public void setFirstName(String fN){
        this.firstName = fN;
    }

    public void setLastName(String lN){
        this.lastName = lN;
    }

    public void setCellPhone(String cP){
        this.cellPhone = cP;
    }

    public void setWorkPhone(String wP){
        this.workPhone = wP;
    }

    public void setEmail(String e){
        this.email = e;
    }

    public long getId(){
    	return this.Id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getCellPhone(){
        return this.cellPhone;
    }

    public String getWorkPhone(){
        return this.workPhone;
    }

    public String getEmail(){
        return this.email;
    }

    public ContactAddress getAddress(int index){
        return this.address.get(index);
    }
    public int getNumberOfAddresses()
    {
        return this.address.size();
    }
    public void setAddressList(ArrayList<ContactAddress> paramArrayList)
    {
        this.address = paramArrayList;
    }
    public ArrayList<ContactAddress> getAddressList() {
    	return this.address;
    }

    @Override
    public String toString(){
        return "(" + this.getFirstName() + " " + this.getLastName() +")";
    }

	@Override
	public int compareTo(Contact arg0) {

		String contactAFirstLetter = String.valueOf(this.getFirstName().charAt(0));
		String contactBFirstLetter = String.valueOf(arg0.getFirstName().charAt(0));
		if(this.getFirstName().compareTo(arg0.getFirstName()) > 0)
			return 1;
        else if (this.getFirstName().compareTo(arg0.getFirstName()) == 0)
            if (this.getLastName().compareTo(arg0.getLastName()) > 0)
                return 1;
            else if (this.getFirstName().compareTo(arg0.getFirstName()) == 0)
                return 0;
            else
                return -1;
        else
            return -1;
		}
	}