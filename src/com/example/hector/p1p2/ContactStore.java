//package controllers;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//import models.Contact;
//import models.ContactAddress;
//import models.SortedArrayList;
//
//
//public class ContactStore {
//
//	private FileInputStream input;
//	private File file= null;
//	private BufferedReader br;
//	private InputStreamReader inputStream;
//	private OutputStreamWriter output;
//	private PrintWriter writer;
//
//	private final int CONTACT_FIELDS = 6;
//	private int ADDRESS_FIELDS = 0;
//
//	public ContactStore() {
//	}
//	public SortedArrayList<Contact> readContacts() {
//
//		SortedArrayList<Contact> tempList = new SortedArrayList<Contact>();
//
//		try {
//
//			this.file = Application.getFile(); 
//			this.input = new FileInputStream(this.file); //initialize fileinputstream
//			inputStream = new InputStreamReader(this.input); //initialize inputstreamreader
//			br = new BufferedReader(inputStream); //initialize our reader
//
//		} catch(Exception e) {
//			e.getStackTrace();
//		}
//
//		try{
//			String line = br.readLine();
//			while (line != null) {
//				Contact temp = new Contact();
//				for(int i=0; i < CONTACT_FIELDS; i++){ //from first name to addressfields
//					if(i == 0){
//						System.out.println(line);
//						temp.setId(Long.valueOf(line.substring(2))); // extract id number from format id#
//					}
//					if(i == 1){
//						System.out.println(line);
//						temp.setFirstName(line);
//					}
//					if(i == 2){
//						System.out.println(line);
//						temp.setLastName(line);
//					}
//					if(i == 3){
//						System.out.println(line);
//						temp.setCellPhone(line);
//					}
//					if(i == 4){
//						System.out.println(line);
//						temp.setWorkPhone(line);
//					}
//					if(i == 5){
//						System.out.println(line);
//						temp.setEmail(line);
//
//					}
//					line = br.readLine();
//				}
//				if(line.contains("address")) {
//					char addressTotal = line.charAt(13);
//					this.ADDRESS_FIELDS = Character.getNumericValue(addressTotal); // extract address fields from format addressfields#
//					System.out.println("ADDRESS FIELDS: " + this.ADDRESS_FIELDS);
//					ArrayList<ContactAddress> addresses = new ArrayList<ContactAddress>();
//					line = br.readLine();
//					for(int i=0; i < this.ADDRESS_FIELDS; i++){ //from address name to zip code
//						ContactAddress address = new ContactAddress();
//						for(int j=0; j < 6; j++){
//							 if(j == 0){
//			                    	System.out.println(line);
//			                    	address.setAddressName(line);
//			                    }
//			                    if(j == 1){
//			                    	System.out.println(line);
//			                    	address.setStreet(line);
//			                    }
//			                    if(j == 2){
//			                    	System.out.println(line);
//			                    	address.setStreetNumber(line);
//			                    }
//			                    if(j == 3){
//			                    	System.out.println(line);
//			                    	address.setCity(line);
//			                    }
//			                    if(j == 4){
//			                    	System.out.println(line);
//			                    	address.setState(line);
//			                    }
//			                    if(j == 5){
//			                    	System.out.println(line);
//			                    	address.setZipCode(line);
//			                    }
//
//							line = br.readLine();
//						}
//						addresses.add(address);
//					}
//					temp.setAddressList(addresses);
//				}
//				tempList.add(temp);
//				break;
//			}
//			inputStream.close();
//			br.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return tempList;
//	}
//
//	public void writeContacts(SortedArrayList<Contact> contacts){
//
//		try{
//			writer = new PrintWriter(Application.getFile());
//
//			writer.write("");
//
//			for(int i=0; i < contacts.size(); i++){
//				Contact temp = contacts.get(i);
//				writer.write("id" + temp.getId() + "\n");
//				writer.write(temp.getFirstName() + "\n");
//				writer.write(temp.getLastName() + "\n");
//				writer.write(temp.getCellPhone() + "\n");
//				writer.write(temp.getWorkPhone() + "\n");
//				writer.write(temp.getEmail() + "\n");
//				if(temp.getNumberOfAddresses() > 0) {
//					writer.write("addressfields" + temp.getNumberOfAddresses() + "\n");
//					for (int j = 0; j < temp.getNumberOfAddresses(); ++j){
//						if (temp.getAddress(j) != null) {
//							for (int k = 0; k < ADDRESS_FIELDS; ++k) {
//								if(j == 0){
//									writer.write(temp.getAddress(j).getAddressName() + "\n");
//								}
//								if(j == 1){
//									writer.write(temp.getAddress(j).getStreet() + "\n");
//								}
//								if(j == 2){
//									writer.write(temp.getAddress(j).getStreetNumber() + "\n");
//								}
//								if(j == 3){
//									writer.write(temp.getAddress(j).getCity() + "\n");
//								}
//								if(j == 4){
//									writer.write(temp.getAddress(j).getState() + "\n");
//								}
//								if(j == 5){
//									writer.write(temp.getAddress(j).getZipCode() + "\n");
//								}
//							}
//						}
//					}
//				}
//			}
//
//			writer.close();
//			output.close();
//
//		} catch(IOException e){
//			e.printStackTrace();
//		}
//	}
//	public void clear() {
//		try {
//			writer = new PrintWriter(Application.getFile());
//			writer.write("");
//			writer.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}
//
