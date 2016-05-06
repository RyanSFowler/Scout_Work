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
public class TreeVector {

private final SimpleStringProperty barcode;
private final SimpleStringProperty notes;

 public TreeVector(Vector<String> book)
    {
	barcode =  new SimpleStringProperty(book.elementAt(0));
	notes =  new SimpleStringProperty(book.elementAt(1));
    }
    
    public void setBarcode(String Barcode) {
        barcode.set(Barcode);
    }

    public void setNotes(String Notes) {
        notes.set(Notes);
    }
    
    public String getBarcode() {
        return barcode.get();
    }
    
    public String getNotes() {
        return notes.get();
    }
}
