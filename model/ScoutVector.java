/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author berghen
 */
public class ScoutVector {
private final SimpleStringProperty scoutId;
private final SimpleStringProperty firstname;
private final SimpleStringProperty middleInitial;
private final SimpleStringProperty lastName;
private final SimpleStringProperty dateOfBirth;
private final SimpleStringProperty phoneNumber;
private final SimpleStringProperty email;
private final SimpleStringProperty status;
private final SimpleStringProperty dateStatusUpdated;

 public ScoutVector(Vector<String> book)
    {
      System.out.println("Book"+book);
  scoutId =  new SimpleStringProperty(book.elementAt(0));
	firstname =  new SimpleStringProperty(book.elementAt(1));
	middleInitial =  new SimpleStringProperty(book.elementAt(2));
  lastName=  new SimpleStringProperty(book.elementAt(3));
  dateOfBirth =  new SimpleStringProperty(book.elementAt(4));
  phoneNumber =  new SimpleStringProperty(book.elementAt(5));
  email =  new SimpleStringProperty(book.elementAt(6));
  status =  new SimpleStringProperty(book.elementAt(7));
  dateStatusUpdated =  new SimpleStringProperty(book.elementAt(8));
    }
/*
    public void setBarcode(String Barcode) {
        barcode.set(Barcode);
    }

    public void setNotes(String Notes) {
        notes.set(Notes);
    }
/*
    public String getFirstName(){
      return firstName.get();
    }
    public String getMiddleInitial(){
      return middleInitial.get();
    }
    public String getLastName(){
      return lastName.get();
    }
    public String getDateOfBirth(){
      return dateOfBirth.get();
    }
    public String getPhoneNumber(){
      return phoneNumber.get();
    }
    public String getEmail(){
      return email.get();
    }
    public String getStatus(){
      return status.get();
    }



    public void setFirstName(String FirstName){
       firstName.set(FirstName);
    }
    public void setMiddleInitial(String MiddleInitial){
       middleInitial.set(MiddleInitial);
    }
    public void setLastName(String LastName){
       lastName.set(LastName);
    }
    public void setDateOfBirth(String DateOfBirth){
       dateOfBirth.set(DateOfBirth);
    }
    public void setPhoneNumber(String PhoneNumber){
       phoneNumber.set(PhoneNumber);
    }
    public void setEmail(String Email){
       email.set(Email);
    }
    public void setStatus(String Status){
       status.set(Status);
    }
    */
}
