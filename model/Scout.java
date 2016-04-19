/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Enumeration;
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
import model.ScoutCollection;


public class Scout extends EntityBase implements IView, IModel {

     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     private static final String myTableName = "SCOUT";
     private String updateStatusMessage = "";


     public Scout(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddScoutView();
            }
            else if (type == "Modify") {
                createEnterModifyScoutView();
            }
            else if (type == "Remove") {
                createEnterRemoveScoutView();
            }
        }
        public Scout(Properties props)
	      {
		        super(myTableName);

		        setDependencies();
		        setData(props);
	      }
        //-----------------------------------------------------------------------------------
	      public void setData(Properties props)
	      {
		        persistentState = new Properties();
		        Enumeration allKeys = props.propertyNames();
		        while (allKeys.hasMoreElements() == true)
		        {
			           String nextKey = (String)allKeys.nextElement();
			           String nextValue = props.getProperty(nextKey);

			          if (nextValue != null)
			          {
				              persistentState.setProperty(nextKey, nextValue);
			          }
		        }
	      }
        public void createEnterModifyScoutView() {
               Scene currentScene = (Scene)myViews.get("EnterModifyScoutView");

               if (currentScene == null)
               {
                   View newView = ViewFactory.createView("EnterModifyScoutView", this);
                   currentScene = new Scene(newView);
                   currentScene.getStylesheets().add("styleSheet.css");
                   myViews.put("EnterModifyScoutView", currentScene);
               }
               swapToView(currentScene);
           }

        public void createAddScoutView() {
               Scene currentScene = (Scene)myViews.get("AddScoutView");

               if (currentScene == null)
               {
                   View newView = ViewFactory.createView("AddScoutView", this);
                   currentScene = new Scene(newView);
                   currentScene.getStylesheets().add("styleSheet.css");
                   myViews.put("AddScoutView", currentScene);
               }
               swapToView(currentScene);
           }
           public void createEnterRemoveScoutView() {
                  Scene currentScene = (Scene)myViews.get("EnterRemoveScoutView");

                  if (currentScene == null)
                  {
                      View newView = ViewFactory.createView("EnterRemoveScoutView", this);
                      currentScene = new Scene(newView);
                      currentScene.getStylesheets().add("styleSheet.css");
                      myViews.put("EnterRemoveScoutView", currentScene);
                  }
                  swapToView(currentScene);
              }

        public void createModifyScoutView() {
               Scene currentScene = (Scene)myViews.get("ModifyScoutView");

               if (currentScene == null)
               {
                   View newView = ViewFactory.createView("ModifyScoutView", this);
                   currentScene = new Scene(newView);
                   currentScene.getStylesheets().add("styleSheet.css");
                   myViews.put("ModifyScoutView", currentScene);
               }
               swapToView(currentScene);
           }

         public void createRemoveScoutView() {
                Scene currentScene = (Scene)myViews.get("RemoveScoutView");
                if (currentScene == null)
                {
                      View newView = ViewFactory.createView("RemoveScoutView", this);
                      currentScene = new Scene(newView);
                      currentScene.getStylesheets().add("styleSheet.css");
                      myViews.put("RemoveScoutView", currentScene);
                }
                swapToView(currentScene);
           }
     public void setDependencies()
	{
            dependencies = new Properties();
            myRegistry.setDependencies(dependencies);
	}
  public static int compare(Scout a, Scout b)
  {
    String aNum = (String)a.getState("scoutId");//this or title
    String bNum = (String)b.getState("scoutId");

    return aNum.compareTo(bNum);
  }

     public Object getState(String key)
	{
            if (key.equals("Scout"))
		return this;
            return null;
	}

     public void stateChangeRequest(String key, Object value)
	{
            if (key.equals("Done") == true)
            {
                myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();

            }
            else if (key.equals("ModifyScout") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateScoutInDatabase();
                }
            }
            else if (key.equals("AddScout") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    insert();
                }
            }
            else if (key.equals("RemoveScout") == true)
            {
                if (value != null)
                {
                    persistentState = (Properties) value;
                    RemoveScout();
                }
            }

	}

        public void RemoveScout() {
            try
            {
                if (persistentState.getProperty("ScoutId") != null)
                {
                String query = "SELECT * FROM " + myTableName + " WHERE ScoutId = '" + persistentState.getProperty("ScoutId") + "' ;";
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                Properties whereClause = new Properties();
		            whereClause.setProperty("ScoutId", persistentState.getProperty("ScoutId"));
		            deletePersistentState(mySchema, whereClause);
		            updateStatusMessage = "Remove Scout  : " + persistentState.getProperty("ScoutId") + " Remove successfully in database!";
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
        public Vector<String> getEntryListView()
        {
        		Vector<String> v = new Vector<String>();
            v.addElement(persistentState.getProperty("ScoutId"));
        		v.addElement(persistentState.getProperty("FirstName"));
        		v.addElement(persistentState.getProperty("MiddleInitial"));
        		v.addElement(persistentState.getProperty("LastName"));
        		v.addElement(persistentState.getProperty("DateOfBirth"));
        		v.addElement(persistentState.getProperty("PhoneNumber"));
            v.addElement(persistentState.getProperty("Email"));
            v.addElement(persistentState.getProperty("Status"));
            v.addElement(persistentState.getProperty("DateStatusUpdated"));

        		return v;
        }
        public void insert() {
            //System.out.print("Insert Add Tree");
            dependencies = new Properties();
            dependencies.put("FirstName", persistentState.getProperty("FirstName"));
            dependencies.put("MiddleInitial", persistentState.getProperty("MiddleInitial"));
            dependencies.put("LastName", persistentState.getProperty("LastName"));
            dependencies.put("DateOfBirth", persistentState.getProperty("DateOfBirth"));
            dependencies.put("PhoneNumber", persistentState.getProperty("PhoneNumber"));
            dependencies.put("Email", persistentState.getProperty("Email"));
            dependencies.put("Status", persistentState.getProperty("Status"));
            //System.out.print("dependencies:" + dependencies);
            try {
                int i = insertAutoIncrementalPersistentState(this.mySchema, dependencies);
            } catch (SQLException ex) {
                //System.out.print("Error:" + ex);
                Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

     private void UpdateScoutInDatabase()
     {
         try
	{
            if (persistentState.getProperty("ScoutId") != null)
            {
                String query = "SELECT * FROM " + myTableName + " WHERE ScoutId = '" + persistentState.getProperty("ScoutId") + "' ;";
                System.out.print("Query:" + query);
                Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
                if (allDataRetrieved != null && allDataRetrieved.size() == 1) {
                Properties whereClause = new Properties();
		whereClause.setProperty("ScoutId", persistentState.getProperty("ScoutId"));
		updatePersistentState(mySchema, persistentState, whereClause);
		updateStatusMessage = "Add New Scout  : " + persistentState.getProperty("ScoutId") + " Add successfully in database!";
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
  public void modifyScoutDone()
  {
    createEnterModifyScoutView();
  }
  public void done()
  {
    myTreeLotCoordinator.transactionDone();
  }



}
