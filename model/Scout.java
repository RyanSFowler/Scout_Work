/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import exception.InvalidPrimaryKeyException;
import java.util.Enumeration;
import impresario.IModel;
import impresario.IView;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.UpdateScoutView2;
import userinterface.RemoveScoutView2;


public class Scout extends EntityBase implements IView, IModel {

     protected Stage myStage;
     protected TreeLotCoordinator myTreeLotCoordinator;
     protected Properties dependencies;
     public Vector<String> ModifyScout;
     public Vector<String> RemoveScout;
     private static final String myTableName = "SCOUT";
     private String updateStatusMessage = "";
     public String ErrorUpdate = "";
     public String FirstName;
     public String ScoutId;
     public String MiddleInitial;
     public String LastName;
     public String DateOfBirth;
     public String PhoneNumber;
     public String Email;
     public String Status;



     public Scout(TreeLotCoordinator l, String type) throws Exception {
            super(myTableName);
            myTreeLotCoordinator = l;
            persistentState = new Properties();
            setDependencies();
            if (type == "Add") {
                createAddScoutView();
            }
            else if (type == "Update") {
                createUpdateScoutView();
            }
            else if (type == "Remove") {
                createRemoveScoutView();
            }
        }
        //--------------------------------------------------
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
          //--------------------------------------------
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
             //--------------------------------
        public void createUpdateScoutView() {
               Scene currentScene = (Scene)myViews.get("UpdateScoutView");

               if (currentScene == null)
               {
                   View newView = ViewFactory.createView("UpdateScoutView", this);
                   currentScene = new Scene(newView);
                   currentScene.getStylesheets().add("styleSheet.css");
                   myViews.put("UpdateScoutView", currentScene);
               }
               swapToView(currentScene);
           }
            public void createUpdateScoutView2() {
                     Scene currentScene = (Scene)myViews.get("UpdateScoutView2");

                     if (currentScene == null)
                     {
                         View newView = ViewFactory.createView("UpdateScoutView2", this);
                         currentScene = new Scene(newView);
                         currentScene.getStylesheets().add("styleSheet.css");
                         myViews.put("UpdateScoutView2", currentScene);
                     }
                     swapToView(currentScene);
                 }
              public void createRemoveScoutView2() {
                     Scene currentScene = (Scene)myViews.get("RemoveScoutView2");

                     if (currentScene == null)
                     {
                         View newView = ViewFactory.createView("RemoveScoutView2", this);
                         currentScene = new Scene(newView);
                         currentScene.getStylesheets().add("styleSheet.css");
                         myViews.put("RemoveScoutView2", currentScene);
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
            if (key.equals("Scout"))
		return this;
            return null;
	}
  public Vector<String> getResultFromDB(String key)
  {
         if (key.equals("ModifyScout")) {
             return ModifyScout;
         }
         if (key.equals("RemoveScout")) {
             return RemoveScout;
         }
         return null;
       }
       public void setFirst(String First) {

       }
       public void FindScoutInDatabase(String first,String last)
       {
         first = persistentState.getProperty("FirstName");
         last =  persistentState.getProperty("LastName");
         String query = "SELECT * FROM " + myTableName + " WHERE ((FirstName LIKE '" + first +	"') AND (LastName LIKE '" + last + "'));";
         System.out.println(query);
         Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
         System.out.println("Vector:" +allDataRetrieved);
         try
         {
            if (allDataRetrieved != null)
            {
               for (Properties p : allDataRetrieved)
               {
                   ModifyScout b = new ModifyScout(p);
                   ModifyScout = b.getVector();
                   System.out.println("ModifyScout:" + ModifyScout);
               }
           }
           else
           {
               throw new InvalidPrimaryKeyException("No matching scout for : "
              + first + ".");
            }
          } catch (InvalidPrimaryKeyException e) {
              System.err.println("Error: " + e);
            }
          }
          public void FindRemoveScoutInDatabase(String first,String last)
          {
              first = persistentState.getProperty("FirstName");
              last =  persistentState.getProperty("LastName");
              String query = "SELECT * FROM " + myTableName + " WHERE ((FirstName LIKE '" + first +	"') AND (LastName LIKE '" + last + "'));";
              System.out.println(query);
              Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
              System.out.println("Vector:" +allDataRetrieved);
              try
              {
                  if (allDataRetrieved != null)
                  {
                      for (Properties p : allDataRetrieved)
                      {
                          RemoveScout b = new RemoveScout(p);
                          RemoveScout = b.getVector();
                          System.out.println("RemoveScout:" + RemoveScout);
                        }
                  }
                  else
                  {
                    throw new InvalidPrimaryKeyException("No matching scout for : "
                    + first + ".");
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
            else if (key.equals("UpdateScout") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateScoutInDatabase();
                }
            }
            else if (key.equals("ModifyScout") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   //System.out.print("ModifyTree:" + persistentState.getProperty("Barcode"));
                   FindScoutInDatabase(persistentState.getProperty("FirstName"),persistentState.getProperty("LastName"));
                   ScoutId = persistentState.getProperty("ScoutId");
                   FirstName = persistentState.getProperty("FirstName");
                   LastName = persistentState.getProperty("LastName");
                }
            }
            else if (key.equals("ModifyScout2") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   ScoutId = persistentState.getProperty("ScoutId");
                   FirstName= persistentState.getProperty("FirstName");
                   MiddleInitial= persistentState.getProperty("MiddleInitial");
                   LastName= persistentState.getProperty("LastName");
                   DateOfBirth= persistentState.getProperty("DateOfBirth");
                   PhoneNumber= persistentState.getProperty("PhoneNumber");
                   Email= persistentState.getProperty("Email");
                   Status= persistentState.getProperty("Status");
                   Preferences prefs = Preferences.userNodeForPackage(UpdateScoutView2.class);
                   prefs.put("scoutId", "");
                   prefs.put("scoutId", ScoutId.toString());
                   prefs.put("firstName", "");
                   prefs.put("firstName", FirstName.toString());
                   prefs.put("lastName", "");
                   prefs.put("lastName", LastName.toString());


                   createUpdateScoutView2();
                }
            }
            else if (key.equals("ModifyScout3") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   UpdateScoutInDatabase();
                   myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();
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
                   //System.out.print("ModifyTree:" + persistentState.getProperty("Barcode"));
                   FindRemoveScoutInDatabase(persistentState.getProperty("FirstName"),persistentState.getProperty("LastName"));
                }
            }
            else if (key.equals("RemoveScout2") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;

                   ScoutId = persistentState.getProperty("ScoutId");
                   FirstName= persistentState.getProperty("FirstName");
                  // MiddleInitial= persistentState.getProperty("MiddleInitial");
                   LastName= persistentState.getProperty("LastName");
                   Status = "Inactive";
                  // DateOfBirth= persistentState.getProperty("DateOfBirth");
                  // PhoneNumber= persistentState.getProperty("PhoneNumber");
                  // Email= persistentState.getProperty("Email");
                  // Status= persistentState.getProperty("Status");
                  Preferences prefs = Preferences.userNodeForPackage(RemoveScoutView2.class);
                  prefs.put("scoutId", "");
                  prefs.put("scoutId", ScoutId.toString());
                  prefs.put("firstName", "");
                  prefs.put("firstName", FirstName.toString());
                  prefs.put("lastName", "");
                  prefs.put("lastName", LastName.toString());


                   createRemoveScoutView2();
                }
            }
            else if (key.equals("RemoveScout3") == true)
            {
                if (value != null)
                {
                   persistentState = (Properties) value;
                   RemoveScoutInDatabase();
                   myTreeLotCoordinator.createAndShowTreeLotCoordinatorView();
                }
            }

	}
  public String getFirstName(){
    return FirstName;
  }
  public String getMiddleInitial(){
    return MiddleInitial;
  }
  public String getLastName(){
    return LastName;
  }
  public String getDateOfBirth(){
    return DateOfBirth;
  }
  public String getPhoneNumber(){
    return PhoneNumber;
  }
  public String getEmail(){
    return Email;
  }
  public String getStatus(){
    return Status;
  }

    /*    public void RemoveScout() {
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
        }*/


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
    String first = persistentState.getProperty("FirstName");
    String last =  persistentState.getProperty("LastName");
      try
{
         if (first != null && last != null)
         {
             String query = "SELECT * FROM " + myTableName + " WHERE ((FirstName LIKE '" + first +	"') AND (LastName LIKE '" + last + "'));";
             System.out.print("Query:" + query);
             Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
             System.out.println(allDataRetrieved);
             if (allDataRetrieved != null ) {//&& allDataRetrieved.size() == 1
             Properties whereClause = new Properties();
             whereClause.setProperty("LastName", persistentState.getProperty("LastName"));
             updatePersistentState(mySchema, persistentState, whereClause);
             updateStatusMessage = "update scout : " + persistentState.getProperty("FirstName") + " Added successfully in database!";
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

  private void RemoveScoutInDatabase()
  {
    String first = persistentState.getProperty("FirstName");
    String last =  persistentState.getProperty("LastName");
      try
{
         if (first != null && last != null)
         {
             String query = "SELECT * FROM " + myTableName + " WHERE ((FirstName LIKE '" + first +	"') AND (LastName LIKE '" + last + "'));";
             System.out.print("Query:" + query);
             Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
             System.out.println(allDataRetrieved);
             if (allDataRetrieved != null ) {//&& allDataRetrieved.size() == 1
             Properties whereClause = new Properties();
             whereClause.setProperty("LastName", persistentState.getProperty("LastName"));
             updatePersistentState(mySchema, persistentState, whereClause);
             updateStatusMessage = "Remove scout : " + persistentState.getProperty("LastName") + " removed successfully in database!";
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
  public void done()
  {
    myTreeLotCoordinator.transactionDone();
  }

  public String getErrorUpdate() {
      return (ErrorUpdate);
  }

}
