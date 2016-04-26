/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import impresario.IModel;
import impresario.IView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

import userinterface.View;
import userinterface.ViewFactory;

public class Session extends EntityBase implements IView, IModel {
    
     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "SESSION";
     private String updateStatusMessage = "";
     private ObservableList allScouts; 
     private Properties shiftDependencies;
    
     
     public Session(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type.equals("Open")) {
                createOpenSessionView();
            }
            else if (type.equals("Close")) {
                createCloseSessionView();
            }
        }
     
     public void createOpenSessionView() {
            Scene currentScene = (Scene)myViews.get("OpenSessionView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("OpenSessionView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("OpenSessionView", currentScene);
            }
            swapToView(currentScene);        
     }
     
     public void createCloseSessionView() {
            Scene currentScene = (Scene)myViews.get("CloseShiftView");

            if (currentScene == null)
            {
                View newView = ViewFactory.createView("CloseShiftView", this);
                currentScene = new Scene(newView);
                currentScene.getStylesheets().add("styleSheet.css");
                myViews.put("CloseShiftView", currentScene);
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
            if (key.equals("Session"))
            	return this;
            return null;
	}
     
    public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("Done") == true)
            {
                myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();

            }
            else if (key.equals("OpenSession") == true)
            {
                if (value != null)
                {
                	persistentState = (Properties) value;
                	if(checkForOpenShift()){
                		insert();
                	}
                }
            }
            else if (key.equals("CloseSession") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    UpdateSessionInDatabase();
                }
            }
	}
        
        
     
        public void insert() {
            //System.out.print("Insert Add Tree");
            dependencies = new Properties();
            dependencies.put("Date", persistentState.getProperty("Date"));
            dependencies.put("StartTime", persistentState.getProperty("StartTime"));
            dependencies.put("EndTime", persistentState.getProperty("EndTime"));
            dependencies.put("StartingCash", persistentState.getProperty("StartCash"));
            dependencies.put("Notes", persistentState.getProperty("Notes"));
            //System.out.print("dependencies:" + dependencies);
            try {
                int i = insertAutoIncrementalPersistentState(this.mySchema, dependencies);
                //<--------------------------------need to get the below line to work to add in shifts when opening session
                //createShifts(i);
            } catch (SQLException ex) {
                //System.out.print("Error:" + ex);
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void createShifts(int id){
        	Properties schema = getSchemaInfo("SHIFT");
        	String[] scoutArray = {persistentState.getProperty("Scout1"), 
        							persistentState.getProperty("Scout2"), 
        							persistentState.getProperty("Scout3")};
        	String[] companionArray = {persistentState.getProperty("Scout1Companion"), 
										persistentState.getProperty("Scout2Companion"), 
										persistentState.getProperty("Scout3Companion")};
        	for (int r = 0; r < scoutArray.length; r++) {
        		if(scoutArray[r] != null){
					shiftDependencies = new Properties();
					shiftDependencies.put("SessionId", id);
					shiftDependencies.put("ScoutId", scoutArray[r]);
					shiftDependencies.put("CompanionName", companionArray[r]);
					shiftDependencies.put("StartTime", persistentState.getProperty("StartTime"));
					shiftDependencies.put("EndTime", persistentState.getProperty("EndTime"));
					shiftDependencies.put("CompanionHours", 
							getShiftLength(persistentState.getProperty("StartTime"),
											persistentState.getProperty("EndTime")));
					System.out.println(shiftDependencies);
        		}
        		
        		try {
                    int i = insertAutoIncrementalPersistentState(schema, shiftDependencies);
                } catch (SQLException ex) {
                    //System.out.print("Error:" + ex);
                    Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
        	
        }
     
        
        //<-------------------------------------------------------still has tree code.... need to code for closing session
     private void UpdateSessionInDatabase()
     {
         try
	{
            if (persistentState.getProperty("Barcode") != null)
            {
                String query = "SELECT * FROM " + myTableName + " WHERE Barcode = '" + persistentState.getProperty("Barcode") + "' ;";
                System.out.print("Query:" + query);
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                Properties whereClause = new Properties();
		whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
		updatePersistentState(mySchema, persistentState, whereClause);
		updateStatusMessage = "Add New Tree  : " + persistentState.getProperty("Barcode") + " Add successfully in database!";
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

   
   public ObservableList<String> findAllScouts()
	{

		String query = "SELECT ScoutId, FirstName, LastName FROM SCOUT ORDER BY LastName;";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		//System.out.println(allDataRetrieved);
		
		if (allDataRetrieved != null)
		{
			allScouts = FXCollections.observableArrayList();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextScoutData = (Properties)allDataRetrieved.elementAt(cnt);
				String scoutId = (String)nextScoutData.getProperty("ScoutId");
				String firstName = (String)nextScoutData.getProperty("FirstName");
				String lastName = (String)nextScoutData.getProperty("LastName");
				String scoutInfo = scoutId + ", " + lastName + ", " + firstName;
				allScouts.add(scoutInfo);
				
				//Scout scout = new Scout(nextScoutData);

				/*if (scout != null)
				{
					addScout(scout);
				}*/
			}

		}
		else{}
		return allScouts;
	}
   
   public boolean checkForOpenShift()
	{
	   boolean isEmptyTable = true;
	   String query = "SELECT * FROM SESSION LIMIT 1;";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null){
			isEmptyTable = false;
		}
		query = "SELECT Id FROM SESSION WHERE EndingCash IS NULL;";
		allDataRetrieved = getSelectQueryResult(query);
		
		if (allDataRetrieved != null || isEmptyTable)
		{
			return true;
			/*allScouts = FXCollections.observableArrayList();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextScoutData = (Properties)allDataRetrieved.elementAt(cnt);
				String scoutId = (String)nextScoutData.getProperty("ScoutId");
				String firstName = (String)nextScoutData.getProperty("FirstName");
				String lastName = (String)nextScoutData.getProperty("LastName");
				String scoutInfo = scoutId + ", " + lastName + ", " + firstName;
				allScouts.add(scoutInfo);
				
				//Scout scout = new Scout(nextScoutData);

				if (scout != null)
				{
					addScout(scout);
				}
			}

		}*/
		}
		return false;
		//return allScouts;
	}
   
   	private int getShiftLength(String startTime, String endTime){
   		int startHour = Integer.parseInt(startTime.substring(0, startTime.indexOf(":")));
   		int endHour = Integer.parseInt(endTime.substring(0, endTime.indexOf(":")));
   		if(startHour > endHour){
   			endHour += 12;
   		}
   		return endHour - startHour;
   	}
   	
   
}
