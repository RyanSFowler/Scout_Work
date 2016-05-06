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

//import com.oracle.webservices.internal.api.message.PropertySet.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

import userinterface.View;
import userinterface.ViewFactory;

public class Shift extends EntityBase implements IView, IModel {

     protected Stage myStage;
     //protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "SHIFT";
     private String updateStatusMessage = "";
     private int sessionId;


     public Shift(int id) throws Exception {
            super(myTableName);
            sessionId = id;
            //myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
        }

     public void setDependencies()
     {
            dependencies = new Properties();
            myRegistry.setDependencies(dependencies);
     }

     public Object getState(String key)
	{
            if (key.equals("Shift"))
            	return this;
            return null;
	}

    public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("OpenShift")){
            	if (value != null)
                {
                    persistentState = (Properties) value;
                    //createShifts();
                }
            }
            else if (key.equals("CloseShift")){
            	if (value != null)
                {
                    persistentState = (Properties) value;
                  //  closeShifts();
                }
            }
	}

  /*      public void createShifts(String[] scoutArray,	String[] companionArray){
        	System.out.println("creating shifts");
        	String[] scoutArray = {persistentState.getProperty("Scout1"),
        							persistentState.getProperty("Scout2"),
        							persistentState.getProperty("Scout3")};
        	String[] companionArray = {persistentState.getProperty("Scout1Companion"),
										persistentState.getProperty("Scout2Companion"),
										persistentState.getProperty("Scout3Companion")};
        	for (int r = 0; r < scoutArray.length; r++) {
        		System.out.println("for loop iteration");
        		if(scoutArray[r] != null){
					dependencies = new Properties();
					dependencies.put("SessionId", sessionId);
					dependencies.put("ScoutId", Integer.parseInt(scoutArray[r]));
					dependencies.put("CompanionName", companionArray[r]);
					dependencies.put("StartTime", persistentState.getProperty("StartTime"));
					dependencies.put("EndTime", null);
					dependencies.put("CompanionHours",
							getShiftLength(persistentState.getProperty("StartTime"),
											null));
					System.out.println(dependencies);
        		}

        		try {
                    int i = insertAutoIncrementalPersistentState(this.mySchema, dependencies);
                } catch (SQLException ex) {
                    //System.out.print("Error:" + ex);
                    Logger.getLogger(Shift.class.getName()).log(Level.SEVERE, null, ex);
                }
			}

        }
*/
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

   	private int getShiftLength(String startTime, String endTime){
   		int startHour = Integer.parseInt(startTime.substring(0, startTime.indexOf(":")));
   		int endHour = Integer.parseInt(endTime.substring(0, endTime.indexOf(":")));
   		if(startHour > endHour){
   			endHour += 12;
   		}
   		return endHour - startHour;
   	}
}
