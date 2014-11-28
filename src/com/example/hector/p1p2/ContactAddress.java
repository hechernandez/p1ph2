package com.example.hector.p1p2;

import java.io.Serializable;

public class ContactAddress implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addressName;
    private String street;
    private String streetNumber;
    private String city;
    private String state;
    private String zipCode;

    public ContactAddress(){
    }

    public ContactAddress(String aN, String s, String sN, String c, String st, String z){
        this.addressName = aN;
        this.street = s;
        this.streetNumber = sN;
        this.city = c;
        this.state = st;
        this.zipCode = z;
    }

    public void setAddressName(String aN){
        this.addressName = aN;
    }

    public void setStreet(String s){
        this.street = s;
    }

    public void setStreetNumber(String sN){
        this.streetNumber = sN;
    }

    public void setCity(String c){
        this.city = c;
    }

    public void setState(String st){
        this.state = st;
    }

    public void setZipCode(String z){
        this.zipCode = z;
    }

    public String getAddressName(){
        return this.addressName;
    }

    public String getStreet(){
        return this.street;
    }

    public String getStreetNumber(){
        return this.streetNumber;
    }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }

    public String getZipCode(){
        return this.zipCode;
    }
}

