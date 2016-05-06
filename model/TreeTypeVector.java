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
public class TreeTypeVector {

private final SimpleStringProperty barcodePrefix;
private final SimpleStringProperty TypeDescription;
private final SimpleStringProperty Cost;

 public TreeTypeVector(Vector<String> book)
    {
	barcodePrefix =  new SimpleStringProperty(book.elementAt(0));
	TypeDescription =  new SimpleStringProperty(book.elementAt(1));
        Cost =  new SimpleStringProperty(book.elementAt(2));
    }
    
    public void setCost(String Code) {
        Cost.set(Code);
    }
 
    public String getCost() {
        return Cost.get();
    }
    
    public void setBarcode(String Barcode) {
        barcodePrefix.set(Barcode);
    }

    public void setDescription(String Notes) {
        TypeDescription.set(Notes);
    }
    
    public String getBarcode() {
        return barcodePrefix.get();
    }
    
    public String getDescription() {
        return TypeDescription.get();
    }
}
