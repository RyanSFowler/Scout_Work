/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import impresario.IView;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.View;
import userinterface.ViewFactory;

/**
 *
 * @author berghen
 */
public class Tree extends EntityBase implements IView, IModel {
    
     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     public Vector<String> ModifyTree;
     private static final String myTableName = "TREE";
     private String updateStatusMessage = "";
     public String ErrorUpdate = "";
     public String Barcode;
     public String Notes;
     
     public Tree(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddTreeView();
            }
            else if (type == "Update") {
                createUpdateTreeView();
            }
            else if (type == "Remove") {
                createRemoveTreeView();
            }
        }
     
     public void createAddTreeView() {
            Scene currentScene = (Scene)myViews.get("AddNewTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("AddNewTreeView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("AddNewTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void createRemoveTreeView() {
         //System.out.print("createRemoveTreeView");
            Scene currentScene = (Scene)myViews.get("RemoveTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("RemoveTreeView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("RemoveTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void createUpdateTreeView() {
            Scene currentScene = (Scene)myViews.get("UpdateTreeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("UpdateTreeView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("UpdateTreeView", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void createUpdateTree2View() {
            Scene currentScene = (Scene)myViews.get("UpdateTreeView2");
            
            if (currentScene == null)
            {
                View newView = ViewFactory.createView("UpdateTreeView2", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("UpdateTreeView2", currentScene);
            }
            swapToView(currentScene);        
        }
     
     public void setDependencies()
	{
            dependencies = new Properties();
            myRegistry.setDependencies(dependencies);
	}
     
     public Object getState(String key)
	{
            if (key.equals("Tree"))
		return this;
            return null;
	}
     
     public Vector<String> getResultFromDB(String key)
	{
            if (key.equals("ModifyTree")) {
                return ModifyTree;
            }
            return null;
	}
     
     public void setNotes(String Notes) {
         
     }
     
     public void FindTreeInDatabase(String title)
	{
	    String query = "SELECT * FROM " + myTableName + " WHERE Barcode = " + title + ";";
	    Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
	    try
            {
		if (allDataRetrieved != null)
		{
                    for (Properties p : allDataRetrieved)
            	    {
                        ModifyTree b = new ModifyTree(p);
                        ModifyTree = b.getVector();
		    }
                }
		else
		{
                    throw new InvalidPrimaryKeyException("No matching barcode for number: "
							     + title + ".");
		}
	    } catch (InvalidPrimaryKeyException e) {
		System.err.println("Error: " + e);
	    }
	}
     
     public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("Done") == true)
            {
                myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();

            }
            else if (key.equals("UpdateTree") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateTreeInDatabase();
                }
            }
            else if (key.equals("ModifyTree") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   //System.out.print("ModifyTree:" + persistentState.getProperty("Barcode"));
                   FindTreeInDatabase(persistentState.getProperty("Barcode"));
                }
            }
            else if (key.equals("ModifyTree2") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   //System.out.print("ModifyTree:" + persistentState.getProperty("Barcode"));
                   Barcode = persistentState.getProperty("Barcode");
                   Notes = persistentState.getProperty("Notes");
                   createUpdateTree2View();
                }
            }
            else if (key.equals("ModifyTree3") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateTreeInDatabase();
                   myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();
                }
            }
            else if (key.equals("AddNewTree") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    insert();
                }
            }
            else if (key.equals("RemoveTree") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    RemoveTree();
                }
            }
            
	}
        
        public String getBarcode() {
            return Barcode;
        }
     
        public void RemoveTree() {
            try
            {
                if (persistentState.getProperty("Barcode") != null)
                {
                String query = "SELECT * FROM " + myTableName + " WHERE Barcode = '" + persistentState.getProperty("Barcode") + "' ;";
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                Properties whereClause = new Properties();
		whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
		deletePersistentState(mySchema, whereClause);
		updateStatusMessage = "Remove Tree  : " + persistentState.getProperty("Barcode") + " Remove successfully in database!";
		System.out.println(updateStatusMessage);
             }
                else {
                      System.out.print("No match with :" + persistentState.getProperty("Barcode"));
                    // Return an error (no barcode match)
                }
            }
            else
            {
                // Return an error (barcode empty)
            }
        }
        catch (SQLException ex)
	{
            // Return a SQL Error
            //updateStatusMessage = "Error in installing account data in database!";
        }
        }
     
        public void insert() {
            //System.out.print("Insert Add Tree");
            dependencies = new Properties();
            dependencies.put("Barcode", persistentState.getProperty("Barcode"));
            dependencies.put("Notes", persistentState.getProperty("Notes"));
            //System.out.print("dependencies:" + dependencies);
            try {
                int i = insertAutoIncrementalPersistentState(this.mySchema, dependencies);
            } catch (SQLException ex) {
                //System.out.print("Error:" + ex);
                Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
     
     private void UpdateTreeInDatabase()
     {
         try
	{
            if (Barcode != null)
            {
                String query = "SELECT * FROM " + myTableName + " WHERE Barcode = '" + Barcode + "' ;";
               // System.out.print("Query:" + query);
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
               // System.out.println("Vector:" + allDataRetrieved);
                persistentState.setProperty("Barcode", Barcode);
                initializeSchema(myTableName);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                    Properties whereClause = new Properties();
                    //System.out.println(persistentState);
                    whereClause.setProperty("Barcode", Barcode);
                    updatePersistentState(mySchema, persistentState, whereClause);
                    updateStatusMessage = "Add New Tree  : " + persistentState.getProperty("Barcode") + " Add successfully in database!";
                    System.out.println(updateStatusMessage);
             }
                else {
                    System.out.print("No match with :" + persistentState.getProperty("Barcode"));
                   // ErrorUpdate = "Error";
                    // Return an error (no barcode match)
                }
            }
            else
            {
                // Return an error (barcode empty)
            }
        }
        catch (SQLException ex)
	{
            // Return a SQL Error
            //updateStatusMessage = "Error in installing account data in database!";
        }
     }
     
     public String getErrorUpdate() {
         return (ErrorUpdate);
     }
     
   protected void initializeSchema(String tableName)
	{
            if (mySchema == null)
            {
                mySchema = getSchemaInfo(tableName);
            }
	}

   public void updateState(String key, Object value)
	{
            stateChangeRequest(key, value);
	}


     
}
