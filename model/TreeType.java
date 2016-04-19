/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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


public class TreeType extends EntityBase implements IView, IModel {

     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "TREETYPE";
     private String updateStatusMessage = "";


     public TreeType(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddTreeTypeView();
            }
            else if (type == "Modify") {
                createUpdateTreeTypeView();
            }

        }

     public void createAddTreeTypeView() {
            Scene currentScene = (Scene)myViews.get("AddNewTreeTypeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("AddNewTreeTypeView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("AddNewTreeTypeView", currentScene);
            }
            swapToView(currentScene);
        }

     public void createUpdateTreeTypeView() {
            Scene currentScene = (Scene)myViews.get("UpdateTreeTypeView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("UpdateTreeTypeView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("UpdateTreeTypeView", currentScene);
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
            if (key.equals("TreeType"))
		return this;
            return null;
	}

     public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("Done") == true)
            {
                myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();

            }
            else if (key.equals("UpdateTreeType") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateTreeTypeInDatabase();
                }
            }
            else if (key.equals("AddNewTreeType") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    insert();
                }
            }
	}


        public void insert() {
            //System.out.print("Insert Add Tree");
            dependencies = new Properties();
            dependencies.put("BarcodePrefix", persistentState.getProperty("BarcodePrefix"));
            dependencies.put("TypeDescription", persistentState.getProperty("TypeDescription"));
            dependencies.put("Cost", persistentState.getProperty("Cost"));
            //System.out.print("dependencies:" + dependencies);
            try {
                int i = insertAutoIncrementalPersistentState(this.mySchema, dependencies);
            } catch (SQLException ex) {
                //System.out.print("Error:" + ex);
                Logger.getLogger(TreeType.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

     private void UpdateTreeTypeInDatabase()
     {
         try
	{
            if (persistentState.getProperty("BarcodePrefix") != null)
            {
                String query = "SELECT * FROM " + myTableName + " WHERE BarcodePrefix = '" + persistentState.getProperty("BarcodePrefix") + "' ;";
                System.out.print("Query:" + query);
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                Properties whereClause = new Properties();
		whereClause.setProperty("BarcodePrefix", persistentState.getProperty("BarcodePrefix"));
		updatePersistentState(mySchema, persistentState, whereClause);
		updateStatusMessage = "Add New Tree Type : " + persistentState.getProperty("BarcodePrefix") + " Add successfully in database!";
		System.out.println(updateStatusMessage);
             }
                else {
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
