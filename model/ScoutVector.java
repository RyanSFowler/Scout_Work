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
private final SimpleStringProperty firstName;
private final SimpleStringProperty middleInitial;
private final SimpleStringProperty lastName;
private final SimpleStringProperty dateOfBirth;
private final SimpleStringProperty email;
private final SimpleStringProperty phoneNumber;
private final SimpleStringProperty status;

 public ScoutVector(Vector<String> book)
    {
       scoutId =  new SimpleStringProperty(book.elementAt(0));
	     firstName =  new SimpleStringProperty(book.elementAt(1));
       middleInitial =  new SimpleStringProperty(book.elementAt(2));
       lastName =  new SimpleStringProperty(book.elementAt(3));
       dateOfBirth =  new SimpleStringProperty(book.elementAt(4));
       email =  new SimpleStringProperty(book.elementAt(5));
       phoneNumber =  new SimpleStringProperty(book.elementAt(6));
       status =  new SimpleStringProperty(book.elementAt(7));
    }

    public void setMiddleInitial(String MiddleInitial) {
        middleInitial.set(MiddleInitial);
    }
    public void setScoutId(String ScoutId) {
        scoutId.set(ScoutId);
    }

    public void setFirstName(String FirstName) {
        firstName.set(FirstName);
    }
    public void setLastName(String LastName) {
        lastName.set(LastName);
    }
    public void setDateOfBirth(String DateOfBirth) {
        dateOfBirth.set(DateOfBirth);
    }

    public void setEmail(String Email) {
        email.set(Email);
    }
    public void setPhonenumber(String PhoneNumber) {
        phoneNumber.set(PhoneNumber);
    }
    public void setStatus(String Status) {
        status.set(Status);
    }

    public String getMiddleInitial() {
        return middleInitial.get();
    }

    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public String getEmail() {
        return email.get();
    }
    public String getStatus() {
        return status.get();
    }
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    public String getScoutId() {
        return scoutId.get();
    }
}
